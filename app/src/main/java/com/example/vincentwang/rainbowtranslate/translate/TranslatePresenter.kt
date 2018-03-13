package com.example.vincentwang.rainbowtranslate.translate

import com.example.vincentwang.rainbowtranslate.data.WordTotalInfo
import com.example.vincentwang.rainbowtranslate.framework.BasePresenter
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

/**
 * Created by vincentwang on 2017/8/18.
 */

class TranslatePresenter(internal var view: TranslateContract.View, internal var model: TranslateContract.Model) : BasePresenter(), TranslateContract.Presneter {

    override fun loadWordAllInfo(word: String) {
        model.getWordTranslateInfo(word)
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
}
