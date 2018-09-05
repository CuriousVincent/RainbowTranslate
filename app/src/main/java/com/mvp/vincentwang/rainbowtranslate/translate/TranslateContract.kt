package com.mvp.vincentwang.rainbowtranslate.translate

import com.mvp.vincentwang.rainbowtranslate.data.WordMain
import com.mvp.vincentwang.rainbowtranslate.data.WordTotalInfo
import com.mvp.vincentwang.rainbowtranslate.framework.BaseContract
import io.reactivex.Flowable
import java.util.*

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

    interface Model {
        val wordMainToday: Flowable<ArrayList<WordMain>>
        val wordMainAll: Flowable<ArrayList<WordMain>>
        fun getWordTranslateInfo(word: String?): Flowable<ArrayList<WordTotalInfo>>
        fun getWordMainPeriod(startDay: Calendar, endDay: Calendar): Flowable<ArrayList<WordMain>>
        fun setSearchWord(word:String)
        fun getStoreWordTranslateInfo():Flowable<ArrayList<WordTotalInfo>>
    }
}
