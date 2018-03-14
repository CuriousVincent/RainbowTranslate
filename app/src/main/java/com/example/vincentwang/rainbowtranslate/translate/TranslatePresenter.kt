package com.example.vincentwang.rainbowtranslate.translate

import com.example.vincentwang.rainbowtranslate.data.WordTotalInfo
import com.example.vincentwang.rainbowtranslate.framework.BasePresenter
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

/**
 * Created by vincentwang on 2017/8/18.
 */

class TranslatePresenter(val view: TranslateContract.View, val model: TranslateContract.Model) : BasePresenter(), TranslateContract.Presneter {

    override fun loadWordAllInfo(word: String) {
        model.getWordTranslateInfo(word)
                .compose(applySchedulers())
                .subscribeWith(object : ResourceSubscriber<ArrayList<WordTotalInfo>>() {
                    override fun onNext(wordTotalInfos: ArrayList<WordTotalInfo>) {
                        view.showWordTranslateInfo(wordTotalInfos)
                    }

                    override fun onError(t: Throwable) {
                        view.showDialog("警告", "查無資料")
                    }

                    override fun onComplete() {

                    }
                })
    }
}
