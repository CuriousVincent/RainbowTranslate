package com.example.vincentwang.rainbowtranslate.framework

import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by vincentwang on 2018/3/12.
 */

abstract class BasePresenter : BaseContract.Presenter {
    val compositeDisposable = CompositeDisposable()

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

    fun <T> applySchedulers(): FlowableTransformer<T, T> {
        return FlowableTransformer {
            upstream -> upstream.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}