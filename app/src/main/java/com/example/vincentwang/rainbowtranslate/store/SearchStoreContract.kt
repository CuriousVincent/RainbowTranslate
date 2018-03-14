package com.example.vincentwang.rainbowtranslate.store

import com.example.vincentwang.rainbowtranslate.data.WordMain
import com.example.vincentwang.rainbowtranslate.framework.BaseContract
import java.util.*

/**
 * Created by vincentwang on 2017/8/27.
 */

interface SearchStoreContract {
    interface View:BaseContract.View {
        fun showSearchList(wordMains: List<WordMain>)
    }

    interface Presenter {
        fun searchPeriod(startDay: Calendar?, endDay: Calendar?)
        fun searchToday()
        fun searchAll()
    }
}
