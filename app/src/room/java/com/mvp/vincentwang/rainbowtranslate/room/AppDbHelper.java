package com.mvp.vincentwang.rainbowtranslate.room;

import com.mvp.vincentwang.rainbowtranslate.room.data.SearchTime;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordExample;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordInfo;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordMain;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;

public class AppDbHelper implements DbHelper {
    private final AppDatabase mAppDatabase;


    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Single<Boolean> insertWordMain(final WordMain wordMain) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.wordMainDao().insert(wordMain);
                return true;
            }
        });
    }

    @Override
    public Single<Boolean> updateWordMain(final WordMain wordMain) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.wordMainDao().update(wordMain);
                return true;
            }
        });
    }

    @Override
    public void insertWordInfo(final WordInfo wordInfo) {
        mAppDatabase.wordInfoDao().insert(wordInfo);
    }

    @Override
    public void insertWordExample(final WordExample wordExample) {
        mAppDatabase.wordExampleDao().insert(wordExample);
    }

    @Override
    public Single<Boolean> insertSearchTime(final SearchTime searchTime) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.searchTimeDao().insert(searchTime);
                return true;
            }
        });
    }


    @Override
    public Single<List<WordMain>> getWordMainByWord(String word) {
        return mAppDatabase.wordMainDao().loadByword(word);
    }

    @Override
    public Single<List<WordMain>> getWordMainAll() {
        return mAppDatabase.wordMainDao().loadAll();
    }

    @Override
    public Single<List<WordTotalInfo>> getWordTotalInfoByWordid(String wordid) {
        return mAppDatabase.wordInfoDao().loadAllByIds(wordid);
    }

    @Override
    public Single<List<WordMain>> findWordMainBetweenDates(Date from, Date to) {
        return mAppDatabase.wordMainDao().findWordMainBetweenDates(from,to);
    }

}
