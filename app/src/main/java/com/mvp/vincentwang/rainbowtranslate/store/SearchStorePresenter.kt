package com.mvp.vincentwang.rainbowtranslate.store

import com.mvp.vincentwang.rainbowtranslate.R
import com.mvp.vincentwang.rainbowtranslate.data.WordMain
import com.mvp.vincentwang.rainbowtranslate.framework.BasePresenter
import com.mvp.vincentwang.rainbowtranslate.translate.TranslateContract
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

/**
 * Created by vincentwang on 2017/8/27.
 */

class SearchStorePresenter(var view: SearchStoreContract.View, var model: TranslateContract.Model) : BasePresenter(), SearchStoreContract.Presenter {
    override fun searchStoreWord(word: String) {
        model?.setSearchWord(word)
        view.gotoTranslateFragment()
    }

    override fun searchPeriod(startDay: Calendar, endDay: Calendar) {
        Flowable.create(FlowableOnSubscribe<Boolean>({ e ->
            e.onNext(startDay.before(endDay))
        }), BackpressureStrategy.BUFFER)
                .compose(applySchedulers())
                .filter { dateperiod ->
                    if (!dateperiod) {
                        view.showDialog(R.string.warning, R.string.datePeriodError)
                    }
                    dateperiod
                    dateperiod
                }
                .observeOn(Schedulers.io())
                .flatMap {
                    model.getWordMainPeriod(startDay, endDay)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ResourceSubscriber<ArrayList<WordMain>>() {
                    override fun onNext(wordMains: ArrayList<WordMain>) {
                        view.showSearchList(wordMains)
                    }

                    override fun onError(t: Throwable) {
                        view.showDialog(R.string.warning, R.string.dataError)
                    }

                    override fun onComplete() {

                    }
                })
    }

    override fun searchToday() {
        model.wordMainToday
                .compose(applySchedulers())
                .subscribeWith(object : ResourceSubscriber<ArrayList<WordMain>>() {
                    override fun onNext(wordMains: ArrayList<WordMain>) {
                        view.showSearchList(wordMains)
                    }

                    override fun onError(t: Throwable) {
                        view.showDialog(R.string.warning, R.string.dataError)
                    }

                    override fun onComplete() {

                    }
                })
    }

    override fun searchAll() {
        compositeDisposable.add(
        model.wordMainAll
                .compose(applySchedulers())
                .subscribeWith(object : ResourceSubscriber<ArrayList<WordMain>>() {
                    override fun onNext(wordMains: ArrayList<WordMain>) {
                        view.showSearchList(wordMains)
                    }

                    override fun onError(t: Throwable) {
                        view.showDialog(R.string.warning, R.string.dataError)
                    }

                    override fun onComplete() {

                    }
                }))
    }
}
