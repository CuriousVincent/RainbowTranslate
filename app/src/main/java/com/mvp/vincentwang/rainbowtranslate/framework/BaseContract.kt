package com.mvp.vincentwang.rainbowtranslate.framework

/**
 * Created by vincentwang on 2018/2/23.
 */
interface BaseContract {
    interface View : ProgressbarListener {
        fun gotoTranslateFragment()
        fun gotoSearchStoreFragment()
        fun showDialog(title:Int,message:Int)
        fun showDialog(title:String,message: String)
    }

    interface Presenter {
        fun unsubscribe()
    }
}