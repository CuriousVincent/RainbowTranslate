package com.mvp.vincentwang.rainbowtranslate.translate

import com.mvp.vincentwang.rainbowtranslate.R
import com.mvp.vincentwang.rainbowtranslate.data.WordTotalInfo
import com.mvp.vincentwang.rainbowtranslate.framework.BasePresenter
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

/**
 * Created by vincentwang on 2017/8/18.
 */

class TranslatePresenter(val view: TranslateContract.View, val model: TranslateContract.Model) : BasePresenter(), TranslateContract.Presneter {
    override fun loadWordAllInfoByStore() {
        model.getStoreWordTranslateInfo()
                .compose(applySchedulers())
                .subscribeWith(object : ResourceSubscriber<ArrayList<WordTotalInfo>>() {
                    override fun onNext(wordTotalInfos: ArrayList<WordTotalInfo>) {
                        view.showWordTranslateInfo(wordTotalInfos)
                    }

                    override fun onError(t: Throwable) {
                    }

                    override fun onComplete() {

                    }
                })
    }

    override fun loadWordAllInfo(word: String?) {
        model.getWordTranslateInfo(word)
                .compose(applySchedulers())
                .subscribeWith(object : ResourceSubscriber<ArrayList<WordTotalInfo>>() {
                    override fun onNext(wordTotalInfos: ArrayList<WordTotalInfo>) {
                        view.showWordTranslateInfo(wordTotalInfos)
                    }

                    override fun onError(t: Throwable) {
                        view.showDialog(R.string.warning, R.string.dataError)
                    }

                    override fun onComplete() {

                    }
                })
    }
}
