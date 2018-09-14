package com.mvp.vincentwang.rainbowtranslate

import com.mvp.vincentwang.rainbowtranslate.room.data.WordMain
import com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo
import io.reactivex.Flowable
import java.util.*

interface Model
{
    fun wordMainToday(): Flowable<List<WordMain>>

    fun wordMainAll(): Flowable<List<WordMain>>

    fun getWordTranslateInfo(word: String?): Flowable<List<WordTotalInfo>>

    fun getWordMainPeriod(startDay: Calendar, endDay: Calendar): Flowable<List<WordMain>>

    fun setSearchWord(word: String)

    fun getSearchWord():Flowable<String>

    fun getStoreWordTranslateInfo(): Flowable<List<WordTotalInfo>>
}