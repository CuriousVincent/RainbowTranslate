package com.mvp.vincentwang.rainbowtranslate.store

import com.mvp.vincentwang.rainbowtranslate.framework.BaseContract
import com.mvp.vincentwang.rainbowtranslate.room.data.WordMain
import java.util.*

/**
 * Created by vincentwang on 2017/8/27.
 */

interface SearchStoreContract {
    interface View:BaseContract.View {
        fun showSearchList(wordMains: List<WordMain>)
    }

    interface Presenter:BaseContract.Presenter {
        fun searchPeriod(startDay: Calendar, endDay: Calendar)
        fun searchToday()
        fun searchAll()
        fun searchStoreWord(word:String)
    }
}
