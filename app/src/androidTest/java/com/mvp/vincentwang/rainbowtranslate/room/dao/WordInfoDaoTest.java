package com.mvp.vincentwang.rainbowtranslate.room.dao;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.mvp.vincentwang.rainbowtranslate.room.AppDatabase;
import com.mvp.vincentwang.rainbowtranslate.room.data.SearchTime;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordExample;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordInfo;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordMain;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static org.hamcrest.Matchers.is;

public class WordInfoDaoTest {

    AppDatabase db;
    WordMain wordMain;
    WordInfo wordInfo;
    WordExample wordExample;
    SearchTime searchTime;

    @Before
    public void setUp() throws Exception {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class).build();
        wordMain = new WordMain("3", "test", 123);
        wordInfo = new WordInfo("2", "3", "4", "5", "6");
        wordExample = new WordExample("7", "2", "9", "10");
        Calendar rightNow = Calendar.getInstance();
        searchTime = new SearchTime("11", "3", rightNow.getTime());
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void insertConflict() {
        try {
            db.wordMainDao().insert(wordMain);
            List<WordMain> wordMains = new ArrayList<>();
            wordMains.add(new WordMain("2", "test1", 124));
//            wordMains.add( new WordMain("3", "test2", 124));
//            wordMains.add( new WordMain("3", "test3", 124));
//            wordMains.add( new WordMain("3", "test4", 124));
//            wordMains.add( new WordMain("3", "test5", 124));
            wordMains.add(new WordMain("40", "test5", 124));
            wordMains.add(new WordMain("3", "test7", 125));
            db.wordMainDao().insert(wordMains);


//            db.beginTransaction();
//            db.runInTransaction(new Runnable() {
//                @Override
//                public void run() {
//                    db.wordMainDao().insert(wordMain);
//                    List<WordMain> wordMains = new ArrayList<>();
//                    wordMains.add( new WordMain("2", "test1", 124));
//                    wordMains.add( new WordMain("3", "test7", 125));
//                    wordMains.add( new WordMain("40", "test5", 124));
//                    db.wordMainDao().insert(wordMains);
//                    db.setTransactionSuccessful();
//                }
//            });


        } catch (Exception e) {
            System.out.print(e.toString());
        }

        db.wordMainDao().all().subscribe(new SingleObserver<List<WordMain>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<WordMain> wordMains) {
                Assert.assertThat(wordMains.size(), is(1));
                Assert.assertThat(wordMains.get(0).getWordid(), is("3"));
                Assert.assertThat(wordMains.get(0).getWord(), is("test"));
                Assert.assertThat(wordMains.get(0).getTimes(), is(123));
            }

            @Override
            public void onError(Throwable e) {
                System.out.print(e.toString());
            }
        });
    }

    @Test
    public void conflict() {
        try {
            db.wordMainDao().insert(wordMain);
            db.beginTransaction();
            db.runInTransaction(new Runnable() {
                @Override
                public void run() {
                    List<WordMain> wordMains = new ArrayList<>();
                    wordMains.add(new WordMain("2", "test1", 124));
                    wordMains.add(new WordMain("3", "test7", 125));
                    wordMains.add(new WordMain("40", "test5", 124));
                    db.wordMainDao().insert(wordMains);
                    db.setTransactionSuccessful();
                }
            });
        } catch (Exception e) {

        }
        db.wordMainDao().all().subscribe(new SingleObserver<List<WordMain>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<WordMain> wordMains) {
                Assert.assertThat(wordMains.size(), is(1));
                Assert.assertThat(wordMains.get(0).getWordid(), is("3"));
                Assert.assertThat(wordMains.get(0).getWord(), is("test"));
                Assert.assertThat(wordMains.get(0).getTimes(), is(123));
            }

            @Override
            public void onError(Throwable e) {
                System.out.print(e.toString());
            }
        });
    }


    @Test
    public void insert() {
        wordMain = new WordMain("3", "test", 123);
        wordInfo = new WordInfo("2", "3", "4", "5", "6");
        wordExample = new WordExample("7", "2", "9", "10");
        db.wordMainDao().insert(wordMain);
        db.wordInfoDao().insert(wordInfo);
        db.wordExampleDao().insert(wordExample);
        Single<List<WordTotalInfo>> s = db.wordInfoDao().loadAllByIds("3");

        s.subscribe(new Consumer<List<WordTotalInfo>>() {
            @Override
            public void accept(List<WordTotalInfo> wordTotalInfos) throws Exception {
                WordTotalInfo wordTotalInfo = wordTotalInfos.get(0);
                WordInfo wordInfo = wordTotalInfo.getWordInfo();
                WordExample wordExample = wordTotalInfo.getWordExamples().get(0);
                Assert.assertThat(wordInfo.getWordinfoid(), is("2"));
                Assert.assertThat(wordInfo.getWordid(), is("3"));
                Assert.assertThat(wordInfo.getChinesemean(), is("4"));
                Assert.assertThat(wordInfo.getEnglishmean(), is("5"));
                Assert.assertThat(wordInfo.getType(), is("6"));
                Assert.assertThat(wordExample.getExampleid(), is("7"));
                Assert.assertThat(wordExample.getWordinfoid(), is("2"));
                Assert.assertThat(wordExample.getExample(), is("9"));
                Assert.assertThat(wordExample.getExampletranslate(), is("10"));
            }
        });
    }

    @Test
    public void InsertSearchTime() {
        db.wordMainDao().insert(wordMain);
        db.searchTimeDao().insert(searchTime);
//        Single<List<SearchTime>> s = db.searchTimeDao().loadAll();
        Calendar from = Calendar.getInstance();
        from.add(Calendar.MONTH, -1);
        Calendar to = Calendar.getInstance();
        to.add(Calendar.MONTH, 1);
        Single<List<SearchTime>> s = db.searchTimeDao().findWordMainBetweenDates(from.getTime(), to.getTime());

        s.subscribe(new Consumer<List<SearchTime>>() {
            @Override
            public void accept(List<SearchTime> searchTimes) throws Exception {
                Assert.assertThat(searchTimes.get(0).getWordid(), is("3"));
                Assert.assertThat(searchTimes.get(0).getSearchtimeid(), is("11"));
            }
        });
    }
}