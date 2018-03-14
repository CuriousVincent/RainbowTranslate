package com.example.vincentwang.rainbowtranslate.store

import com.example.vincentwang.rainbowtranslate.data.WordMain
import com.example.vincentwang.rainbowtranslate.framework.BasePresenter
import com.example.vincentwang.rainbowtranslate.translate.TranslateContract
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

/**
 * Created by vincentwang on 2017/8/27.
 */

class SearchStorePresenter(var view: SearchStoreContract.View, var model: TranslateContract.Model) : BasePresenter(), SearchStoreContract.Presenter {
    override fun searchPeriod(startDay: Calendar?, endDay: Calendar?) {
        if (startDay != null && endDay != null && startDay.before(endDay)) {
            model.getWordMainPeriod(startDay, endDay)
                    .compose(applySchedulers())
                    .subscribeWith(object : ResourceSubscriber<ArrayList<WordMain>>() {
                        override fun onNext(wordMains: ArrayList<WordMain>) {
                            view.showSearchList(wordMains)
                        }

                        override fun onError(t: Throwable) {
                            view.showDialog("查詢","查無資料")
                        }

                        override fun onComplete() {

                        }
                    })
        }else
        {
            view.showDialog("查無資料","日期錯誤")
        }
    }

    override fun searchToday() {
        model.wordMainToday
                .compose(applySchedulers())
                .subscribeWith(object : ResourceSubscriber<ArrayList<WordMain>>() {
                    override fun onNext(wordMains: ArrayList<WordMain>) {
                        view.showSearchList(wordMains)
                    }

                    override fun onError(t: Throwable) {
                        view.showDialog("查詢","查無資料")
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
                        view.showDialog("查詢","查無資料")
                    }

                    override fun onComplete() {

                    }
                })
    }
}
