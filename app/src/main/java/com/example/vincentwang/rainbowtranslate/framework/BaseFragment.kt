package com.example.vincentwang.rainbowtranslate.framework

import android.content.Context
import android.support.v4.app.Fragment

/**
 * Created by vincentwang on 2018/2/23.
 */
abstract class BaseFragment: Fragment() {
    lateinit var communicator :BaseContract.View

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        communicator = context as BaseContract.View
    }
}