package com.example.vincentwang.rainbowtranslate.store

import com.example.vincentwang.rainbowtranslate.data.WordMain
import com.example.vincentwang.rainbowtranslate.framework.BasePresenter
import com.example.vincentwang.rainbowtranslate.translate.TranslateContract
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

/**
 * Created by vincentwang on 2017/8/27.
 */

class SearchStorePresenter(internal var view: SearchStoreContract.View, internal var model: TranslateContract.Model) : BasePresenter(), SearchStoreContract.Presenter {
    override fun searchPeriod(startDay: Calendar?, endDay: Calendar?) {
        model.getWordMainPeriod(startDay, endDay)
                .compose(applySchedulers())
                .subscribeWith(object : ResourceSubscriber<ArrayList<WordMain>>() {
                    override fun onNext(wordMains: ArrayList<WordMain>) {
                        view.showSearchList(wordMains)
                    }

                    override fun onError(t: Throwable) {

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

                    }

                    override fun onComplete() {

                    }
                })
    }

    override fun searchAll() {
        model.wordMainAll
                .compose(applySchedulers())
                .subscribeWith(object : ResourceSubscriber<ArrayList<WordMain>>() {
                    override fun onNext(wordMains: ArrayList<WordMain>) {
                        view.showSearchList(wordMains)
                    }

                    override fun onError(t: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
    }
}
