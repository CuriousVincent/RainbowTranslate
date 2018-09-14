package com.mvp.vincentwang.rainbowtranslate;


import android.support.annotation.NonNull;

import com.mvp.vincentwang.rainbowtranslate.room.AppDbHelper;
import com.mvp.vincentwang.rainbowtranslate.room.data.SearchTime;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordExample;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordInfo;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordMain;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo;
import com.mvp.vincentwang.rainbowtranslate.util.JsoupUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

/**
 * Created by vincentwang on 2017/8/17.
 */

public class TranslateModel implements Model {

    private String storeWord="";
    private AppDbHelper appDbHelper;

    public TranslateModel(AppDbHelper appDbHelper) {
        this.appDbHelper = appDbHelper;
    }

    @NonNull
    @Override
    public Flowable<List<WordMain>> getWordMainPeriod(final Calendar startDay, final Calendar endDay) {
        return appDbHelper.findWordMainBetweenDates(startDay.getTime(),endDay.getTime()).toFlowable();

    }

    @Override
    public Flowable<List<WordMain>> wordMainToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        Date today = calendar.getTime();
        calendar.add(Calendar.DATE,1);
        Date next = calendar.getTime();
        return appDbHelper.findWordMainBetweenDates(today,next).toFlowable();
    }

    @Override
    public Flowable<List<WordMain>> wordMainAll() {
        return appDbHelper.getWordMainAll().toFlowable();
//        return Flowable.create(new FlowableOnSubscribe<ArrayList<com.mvp.vincentwang.rainbowtranslate.room.data.WordMain>>() {
//            @Override
//            public void subscribe(FlowableEmitter<ArrayList<com.mvp.vincentwang.rainbowtranslate.room.data.WordMain>> e) throws Exception {
//                ArrayList<com.mvp.vincentwang.rainbowtranslate.room.data.WordMain> wordMains = getWordMain();
//                if (wordMains != null) {
//                    e.onNext(wordMains);
//                } else {
//                    e.onError(new Exception());
//                }
//            }
//        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public void setSearchWord(@NonNull String word) {
        this.storeWord = word;
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public Flowable<List<WordTotalInfo>> getStoreWordTranslateInfo() {

//        return Flowable.create(new FlowableOnSubscribe<ArrayList<com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo>>() {
//            @Override
//            public void subscribe(FlowableEmitter<ArrayList<com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo>> e) throws Exception {
//                ArrayList<com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo> wordTotalInfos;
//                List<com.mvp.vincentwang.rainbowtranslate.room.data.WordMain> wordMainList = getWordMainbyWord(storeWord);
//                if (wordMainList.size() != 0) {
//                    com.mvp.vincentwang.rainbowtranslate.room.data.WordMain wordMain = wordMainList.get(0);
//                    String wordid = wordMain.getWordid();
//                    String timeid = UUID.randomUUID().toString();
//                    insertSearchTime(timeid, wordid, new Date());
//                    wordMain.setTimes(wordMain.getTimes() + 1);
//                    wordMainDao.update(wordMain);
//                    wordTotalInfos = getWordTotalInfo(wordMain);
//                    e.onNext(wordTotalInfos);
//                } else {
//                    e.onError(new Exception());
//                }
//            }
//        }, BackpressureStrategy.BUFFER);
        return appDbHelper.getWordMainByWord(storeWord)
                .flatMap(new Function<List<WordMain>, SingleSource<List<WordTotalInfo>>>() {
                    @Override
                    public SingleSource<List<WordTotalInfo>> apply(List<WordMain> wordMains) throws Exception {
                        return getWordTranslateInfo(wordMains.get(0));
                    }
                }).toFlowable();
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public Flowable<List<WordTotalInfo>> getWordTranslateInfo(@Nullable final String word) {

        return appDbHelper.getWordMainByWord(word)
                .flatMap(new Function<List<WordMain>, SingleSource<List<WordTotalInfo>>>() {
                    @Override
                    public SingleSource<List<WordTotalInfo>> apply(List<WordMain> wordMains) throws Exception {
                        if (wordMains.size() != 0) {
                            return getWordTranslateInfo(wordMains.get(0));
                        }
                        return saveWordTranslateInfo(word);
                    }
                }).toFlowable();

//        return Flowable.create(new FlowableOnSubscribe<ArrayList<com.mvp.vincentwang.rainbowtranslate.com.mvp.vincentwang.rainbowtranslate.data.WordTotalInfo>>() {
//            @Override
//            public void subscribe(FlowableEmitter<ArrayList<com.mvp.vincentwang.rainbowtranslate.com.mvp.vincentwang.rainbowtranslate.data.WordTotalInfo>> e) throws Exception {
//                ArrayList<com.mvp.vincentwang.rainbowtranslate.com.mvp.vincentwang.rainbowtranslate.data.WordTotalInfo> wordTotalInfos;
//                List<com.mvp.vincentwang.rainbowtranslate.com.mvp.vincentwang.rainbowtranslate.data.WordMain> wordMainList = getWordMainbyWord(word);
//                if (wordMainList.size() != 0) {
//                    com.mvp.vincentwang.rainbowtranslate.com.mvp.vincentwang.rainbowtranslate.data.WordMain wordMain = wordMainList.get(0);
//                    String wordid = wordMain.getWordid();
//                    String timeid = UUID.randomUUID().toString();
//                    insertSearchTime(timeid, wordid, new Date());
//                    wordMain.setTimes(wordMain.getTimes() + 1);
//                    wordMainDao.update(wordMain);
//                    wordTotalInfos = getWordTotalInfo(wordMain);
//                } else {
//                    saveWordTranslateInfo(word);
//                    wordMainList = getWordMainbyWord(word);
//                    wordTotalInfos = getWordTotalInfo(wordMainList.get(0));
//
//                }
//                e.onNext(wordTotalInfos);
//            }
//        }, BackpressureStrategy.BUFFER);
    }

    private Single<List<WordTotalInfo>> getWordTranslateInfo(final WordMain wordMain){
        wordMain.setTimes(wordMain.getTimes() + 1);
        String wordid = wordMain.getWordid();
        String timeid = UUID.randomUUID().toString();
        return insertSearchTime(timeid, wordid, new Date())
                .flatMap(new Function<Boolean, SingleSource<Boolean>>() {
                    @Override
                    public SingleSource<Boolean> apply(Boolean aBoolean) throws Exception {
                        return appDbHelper.updateWordMain(wordMain);
                    }
                })
                .flatMap(new Function<Boolean, SingleSource<List<WordTotalInfo>>>() {
                    @Override
                    public SingleSource<List<WordTotalInfo>> apply(Boolean aBoolean) throws Exception {
                        return  appDbHelper.getWordTotalInfoByWordid(wordMain.getWordid());
                    }
                });
    }

    private Single<List<WordTotalInfo>> saveWordTranslateInfo(final String sWord) throws Exception {
        return Single.create(new SingleOnSubscribe<Document>() {
            @Override
            public void subscribe(final SingleEmitter<Document> e) throws Exception {
                JsoupUtil.getInstance().getHttp("http://dictionary.cambridge.org/zht/%E8%A9%9E%E5%85%B8/%E8%8B%B1%E8%AA%9E-%E6%BC%A2%E8%AA%9E-%E7%B9%81%E9%AB%94/" + sWord
                        , new JsoupUtil.ResponseCallback() {
                            @Override
                            public void onResponse(Document document) throws IOException {
                                e.onSuccess(document);
                            }
                        });
            }
        }).flatMap(new Function<Document, SingleSource<List<WordTotalInfo>>>() {
            @Override
            public SingleSource<List<WordTotalInfo>> apply(Document document) throws Exception {
                return saveWordInDB(document);
            }
        });

//                .flatMap(new Function<Document, SingleEmitter<List<WordTotalInfo>>>() {
//            @Override
//            public SingleEmitter<List<WordTotalInfo>> apply(Document document) throws Exception {
//                return saveWordInDB(document);
//            }
//        });

//        JsoupUtil.getInstance().getHttp("http://dictionary.cambridge.org/zht/%E8%A9%9E%E5%85%B8/%E8%8B%B1%E8%AA%9E-%E6%BC%A2%E8%AA%9E-%E7%B9%81%E9%AB%94/" + sWord
//                , new JsoupUtil.ResponseCallback() {
//                    @Override
//                    public void onResponse(Document document) throws IOException {
//                        saveWordInDB(document);
//                    }
//                });

    }

    private Single<List<WordTotalInfo>> saveWordInDB(final Document document) {
        final String wordid = UUID.randomUUID().toString();
        final String timeid = UUID.randomUUID().toString();
        final Elements body = document.select("div[class=entry-body__el clrd js-share-holder]");
        final Element word = body.select("span[class=hw]").first();

        return insertWordmain(wordid, word.text(), 1)
                .flatMap(new Function<Boolean, SingleSource<Boolean>>() {
                             @Override
                             public SingleSource<Boolean> apply(Boolean aBoolean) throws Exception {
                                 return insertSearchTime(timeid, wordid, new Date());
                             }
                         }
                )
                .flatMap(new Function<Boolean, SingleSource<List<WordTotalInfo>>>() {
                    @Override
                    public SingleSource<List<WordTotalInfo>> apply(Boolean aBoolean) throws Exception {
                        List<WordTotalInfo> list = new ArrayList<>();

                        for (Element bd : body) {
                            WordTotalInfo wordTotalInfo = new WordTotalInfo();
                            Elements type = bd.select("span[class=pos]");
                            Elements sensebody = bd.select("div[class=sense-body]");
                            Elements english_trans = sensebody.select("b[class=def]");
                            Elements defbody = sensebody.select("span[class=def-body]");

                            for (int i = 0; i < english_trans.size(); i++) {
                                String wordinfoid = UUID.randomUUID().toString();
                                Element chinese_trans = defbody.get(i).select("span[class=trans]").first();
                                Elements examp = defbody.get(i).select("div[class=examp emphasized]");
                                WordInfo wordInfo = new WordInfo(wordinfoid, wordid, chinese_trans.text(), english_trans.get(i).text(), type.text());
                                wordTotalInfo.setWordInfo(wordInfo);
                                appDbHelper.insertWordInfo(wordInfo);
                                List<WordExample> wordExamples = new ArrayList<>();
                                for (Element ex : examp) {
                                    String exampleid = UUID.randomUUID().toString();
                                    Elements english_example = ex.select("span[class=eg]");
                                    Elements chinese_example_tran = ex.select("span[class=trans]");
                                    WordExample wordExample = new WordExample(exampleid, wordinfoid, english_example.text(), chinese_example_tran.text());
                                    wordExamples.add(wordExample);
                                    appDbHelper.insertWordExample(wordExample);
                                }
                                wordTotalInfo.setWordExamples(wordExamples);
                            }
                            list.add(wordTotalInfo);
                        }
                        return getwordTotalInfo(list);
                    }
                });

        //        return insertWordmain(wordid, word.text(), 1)
//                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
//                    @Override
//                    public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
//                        return insertSearchTime(timeid, wordid, new Date());
//                    }
//                })
//                .flatMap(new Function<Boolean, ObservableSource<List<WordTotalInfo>>>() {
//                    @Override
//                    public ObservableSource<List<WordTotalInfo>> apply(Boolean aBoolean) throws Exception {
//                        return saveWordTotalInfo(body, wordid);
//                    }
//                });
    }

    private Single<List<WordTotalInfo>> getwordTotalInfo(final List<WordTotalInfo> list) {
        return Single.create(new SingleOnSubscribe<List<WordTotalInfo>>() {
            @Override
            public void subscribe(SingleEmitter<List<WordTotalInfo>> e) throws Exception {
                e.onSuccess(list);
            }
        });
    }


//    private Observable<WordTotalInfo> saveWordExample(final WordTotalInfo wordTotalInfo) {
//        final List<WordExample> wordExamples = new ArrayList<>();
//        return Observable.create(new ObservableOnSubscribe<WordExample>() {
//            @Override
//            public void subscribe(ObservableEmitter<WordExample> e) throws Exception {
//                for (Element ex : wordTotalInfo.getEx()) {
//                    String exampleid = UUID.randomUUID().toString();
//                    Elements english_example = ex.select("span[class=eg]");
//                    Elements chinese_example_tran = ex.select("span[class=trans]");
//                    WordExample wordExample = new WordExample(exampleid, wordTotalInfo.getWordInfo().getWordinfoid(), english_example.text(), chinese_example_tran.text());
//                    appDbHelper.insertWordExample(wordExample);
//                    e.onNext(wordExample);
//                }
//            }
//        }).map(new Function<WordExample, List<WordExample>>() {
//            @Override
//            public List<WordExample> apply(WordExample wordExample) throws Exception {
//                wordExamples.add(wordExample);
//                return wordExamples;
//            }
//        }).map(new Function<List<WordExample>, WordTotalInfo>() {
//            @Override
//            public WordTotalInfo apply(List<WordExample> wordExamples) throws Exception {
//                wordTotalInfo.setWordExamples(wordExamples);
//                return wordTotalInfo;
//            }
//        });
//    }


//    private Observable<WordTotalInfo> saveWordInfo(final WordTotalInfo wordTotalInfo, final String wordid) {
//        final Elements type = wordTotalInfo.getBody().select("span[class=pos]");
//        final Elements sensebody = wordTotalInfo.getBody().select("div[class=sense-body]");
//        final Elements english_trans = sensebody.select("b[class=def]");
//        final Elements defbody = sensebody.select("span[class=def-body]");
//        return Observable.create(new ObservableOnSubscribe<WordTotalInfo>() {
//            @Override
//            public void subscribe(ObservableEmitter<WordTotalInfo> e) throws Exception {
//
//                for (int i = 0; i < english_trans.size(); i++) {
//                    String wordinfoid = UUID.randomUUID().toString();
//                    Element chinese_trans = defbody.get(i).select("span[class=trans]").first();
//                    Elements examp = defbody.get(i).select("div[class=examp emphasized]");
//                    WordInfo wordInfo = new WordInfo(wordinfoid, wordid, chinese_trans.text(), english_trans.get(i).text(), type.text());
//                    wordTotalInfo.setWordInfo(wordInfo);
//                    wordTotalInfo.setEx(examp);
//                    appDbHelper.insertWordInfo(wordInfo);
//                    e.onNext(wordTotalInfo);
//                }
//            }
//        }).flatMap(new Function<WordTotalInfo, ObservableSource<WordTotalInfo>>() {
//
//                       @Override
//                       public ObservableSource<WordTotalInfo> apply(WordTotalInfo examp) throws Exception {
//
//                           return saveWordExample(examp);
//                       }
//                   }
//        );
//    }


//    private Observable<List<WordTotalInfo>> saveWordTotalInfo(final Elements body, final String wordid) {
//        final List<WordTotalInfo> list = new ArrayList<>();
//        return Observable.create(new ObservableOnSubscribe<WordTotalInfo>() {
//            @Override
//
//            public void subscribe(ObservableEmitter<WordTotalInfo> e) throws Exception {
//                for (Element bd : body) {
//                    WordTotalInfo wordTotalInfo = new WordTotalInfo();
//                    wordTotalInfo.setBody(bd);
//                    e.onNext(wordTotalInfo);
//                }
//            }
//        }).flatMap(new Function<WordTotalInfo, ObservableSource<WordTotalInfo>>() {
//            @Override
//            public ObservableSource<WordTotalInfo> apply(WordTotalInfo wordTotalInfo) throws Exception {
//                return saveWordInfo(wordTotalInfo, wordid);
//            }
//        }).map(new Function<WordTotalInfo, List<WordTotalInfo>>() {
//            @Override
//            public List<WordTotalInfo> apply(WordTotalInfo wordTotalInfo) throws Exception {
//                list.add(wordTotalInfo);
//                return list;
//            }
//        });
//        for (Element bd : body) {
//            WordTotalInfo wordTotalInfo = new WordTotalInfo();
//            Elements type = bd.select("span[class=pos]");
//            Elements sensebody = bd.select("div[class=sense-body]");
//            Elements english_trans = sensebody.select("b[class=def]");
//            Elements defbody = sensebody.select("span[class=def-body]");
//
//            for (int i = 0; i < english_trans.size(); i++) {
//                String wordinfoid = UUID.randomUUID().toString();
//                Element chinese_trans = defbody.get(i).select("span[class=trans]").first();
//                Elements examp = defbody.get(i).select("div[class=examp emphasized]");
//                WordInfo wordInfo = new WordInfo(wordinfoid, wordid, chinese_trans.text(), english_trans.get(i).text(), type.text());
//                wordTotalInfo.setWordInfo(wordInfo);
//                List<WordExample> wordExamples = new ArrayList<>();
//                for (Element ex : examp) {
//                    String exampleid = UUID.randomUUID().toString();
//                    Elements english_example = ex.select("span[class=eg]");
//                    Elements chinese_example_tran = ex.select("span[class=trans]");
//                    WordExample wordExample = new WordExample(exampleid, wordinfoid, english_example.text(), chinese_example_tran.text());
//                    wordExamples.add(wordExample);
//                }
//                wordTotalInfo.setWordExamples(wordExamples);
//            }
//        }
//        return appDbHelper.insertWordTotalInfo(list);
//    }


    private Single<Boolean> insertWordmain(String wordid, @NonNull String word, int times) {
        WordMain wordMain = new WordMain(wordid, word, times);
        return appDbHelper.insertWordMain(wordMain);
    }

    private Single<Boolean> insertSearchTime(String searchtimeid, @NonNull String wordid,
                                             @NonNull Date searchtime) {
        SearchTime searchTime = new SearchTime(searchtimeid, wordid, searchtime);
        return appDbHelper.insertSearchTime(searchTime);
    }

    private Single<Boolean> updateWordMain(WordMain wordMain){
        return appDbHelper.updateWordMain(wordMain);
    }

    @NotNull
    @Override
    public Flowable<String> getSearchWord() {
        return Flowable.just(storeWord);
    }
}
