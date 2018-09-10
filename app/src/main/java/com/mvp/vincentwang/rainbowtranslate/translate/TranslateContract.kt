package com.mvp.vincentwang.rainbowtranslate.translate

import com.mvp.vincentwang.rainbowtranslate.framework.BaseContract
import com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo

/**
 * Created by vincentwang on 2017/8/9.
 */

interface TranslateContract {
    interface View : BaseContract.View {
        fun showWordTranslateInfo(word:String,wordTotalInfos: List<WordTotalInfo>)
    }

    interface Presneter:BaseContract.Presenter {
        fun loadWordAllInfo(word: String?)
        fun loadWordAllInfoByStore()
    }
}
