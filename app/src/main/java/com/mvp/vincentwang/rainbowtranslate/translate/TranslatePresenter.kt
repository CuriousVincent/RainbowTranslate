package com.mvp.vincentwang.rainbowtranslate.translate

import android.util.Log
import com.mvp.vincentwang.rainbowtranslate.Model
import com.mvp.vincentwang.rainbowtranslate.R
import com.mvp.vincentwang.rainbowtranslate.framework.BasePresenter
import com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo
import io.reactivex.subscribers.ResourceSubscriber

/**
 * Created by vincentwang on 2017/8/18.
 */

class TranslatePresenter(val view: TranslateContract.View, val model: Model) : BasePresenter(), TranslateContract.Presneter {
    override fun loadWordAllInfoByStore()
    {
        var word =""
        model.getSearchWord()
                .filter{ t->
                    var result =false
                    if(t != "") {
                        result = true
                    }
                    word = t
                    result
                }
                .flatMap { t ->
                    model.getStoreWordTranslateInfo()
                }
                .compose(applySchedulers())
                .subscribeWith(object : ResourceSubscriber<List<WordTotalInfo>>() {
                    override fun onNext(wordTotalInfos: List<WordTotalInfo>) {
                        view.showWordTranslateInfo(word,wordTotalInfos)
                    }

                    override fun onError(t: Throwable) {
                        view.showDialog(R.string.warning, R.string.dataError)
                    }

                    override fun onComplete() {

                    }
                })
    }

    override fun loadWordAllInfo(word: String?) {
        model.getWordTranslateInfo(word)
                .compose(applySchedulers())
                .subscribeWith(object : ResourceSubscriber<List<WordTotalInfo>>() {
                    override fun onNext(wordTotalInfos: List<WordTotalInfo>) {
                        if (word != null) {
                            view.showWordTranslateInfo(word,wordTotalInfos)
                        }
                    }

                    override fun onError(t: Throwable) {
                        view.showDialog(R.string.warning, R.string.dataError)
                    }

                    override fun onComplete() {
                        Log.e("onComplete", "onComplete")
                    }
                })
    }
}
