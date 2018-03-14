package com.example.vincentwang.rainbowtranslate.framework

import android.content.Context
import android.support.v4.app.Fragment

/**
 * Created by vincentwang on 2018/2/23.
 */
abstract class BaseFragment: Fragment(),BaseContract.View {
    lateinit var communicator :BaseContract.View

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        communicator = context as BaseContract.View
    }

    override fun showProgressBar() {
        communicator.showProgressBar()
    }

    override fun hideProgressBar() {
        communicator.hideProgressBar()
    }

    override fun gotoSearchStoreFragment() {
        communicator.gotoSearchStoreFragment()
    }

    override fun gotoTranslateFragment(word: String) {
        communicator.gotoTranslateFragment(word)
    }

    override fun showDialog(title: String, message: String) {
        communicator.showDialog(title,message)
    }

    override fun showDialog(title: Int, message: Int) {
        communicator.showDialog(title,message)
    }
}