package com.mvp.vincentwang.rainbowtranslate.room;

import com.mvp.vincentwang.rainbowtranslate.room.data.SearchTime;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordExample;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordInfo;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordMain;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo;

import java.util.Date;
import java.util.List;

import io.reactivex.Single;

public interface DbHelper {
    Single<Boolean> insertWordMain(final WordMain wordMain);
    Single<Boolean> updateWordMain(final WordMain wordMain);
    void insertWordInfo(final WordInfo wordInfo);
    void insertWordExample(final WordExample wordExample);
    Single<Boolean> insertSearchTime(final SearchTime searchTime);
    Single<List<WordMain>> getWordMainByWord(String word);
    Single<List<WordTotalInfo>> getWordTotalInfoByWordid(String wordid);
    Single<List<WordMain>> findWordMainBetweenDates(Date from, Date to);
    Single<List<WordMain>> findAllWordMain();
}
