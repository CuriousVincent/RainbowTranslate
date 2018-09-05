package com.mvp.vincentwang.rainbowtranslate.room;

import com.mvp.vincentwang.rainbowtranslate.room.data.SearchTime;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordExample;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordInfo;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordMain;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Single;

public class AppDbHelper implements DbHelper {
    private final AppDatabase mAppDatabase;


    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<Boolean> insertWordMain(final WordMain wordMain) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.wordMainDao().insert(wordMain);
                return true;
            }
        });
    }
    @Override
    public Observable<Boolean> insertWordInfo(final WordInfo wordInfo) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.wordInfoDao().insert(wordInfo);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> insertWordExample(final WordExample wordExample) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.wordExampleDao().insert(wordExample);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> insertSearchTime(final SearchTime searchTime) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.searchTimeDao().insert(searchTime);
                return true;
            }
        });
    }

    @Override
    public Observable<List<WordTotalInfo>> insertWordTotalInfo(final List<WordTotalInfo> wordTotalInfos) {
        return Observable.fromCallable(new Callable<List<WordTotalInfo>>() {
            @Override
            public List<WordTotalInfo> call() throws Exception {
                return wordTotalInfos;
            }
        });
    }

    @Override
    public Single<List<WordMain>> getWordMainByWordMain(String word) {
        return mAppDatabase.wordMainDao().loadByword(word);
    }
}
