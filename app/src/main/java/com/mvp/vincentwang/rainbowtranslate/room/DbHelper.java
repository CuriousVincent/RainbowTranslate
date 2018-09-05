package com.mvp.vincentwang.rainbowtranslate.room;

import com.mvp.vincentwang.rainbowtranslate.room.data.SearchTime;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordExample;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordInfo;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordMain;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface DbHelper {
    Observable<Boolean> insertWordMain(final WordMain wordMain);
    Observable<Boolean> insertWordInfo(final WordInfo wordInfo);
    Observable<Boolean> insertWordExample(final WordExample wordExample);
    Observable<Boolean> insertSearchTime(final SearchTime searchTime);
    Observable<List<WordTotalInfo>> insertWordTotalInfo(final List<WordTotalInfo> wordTotalInfos);
    Single<List<WordMain>> getWordMainByWordMain(String word);
}
