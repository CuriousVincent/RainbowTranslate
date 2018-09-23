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
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by vincentwang on 2017/8/17.
 */

public class TranslateModel implements Model {

    private String storeWord = "";
    private AppDbHelper appDbHelper;

    public TranslateModel(AppDbHelper appDbHelper) {
        this.appDbHelper = appDbHelper;
    }

    @NonNull
    @Override
    public Flowable<List<WordMain>> getWordMainPeriod(final Calendar startDay, final Calendar endDay) {
        startDay.set(Calendar.MILLISECOND, 0);
        startDay.set(Calendar.SECOND, 0);
        startDay.set(Calendar.MINUTE, 0);
        startDay.set(Calendar.HOUR, 0);
        endDay.set(Calendar.MILLISECOND, 0);
        endDay.set(Calendar.SECOND, 0);
        endDay.set(Calendar.MINUTE, 0);
        endDay.set(Calendar.HOUR, 0);
        endDay.add(Calendar.DATE,1);
        return appDbHelper.findWordMainBetweenDates(startDay.getTime(), endDay.getTime()).toFlowable();

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
        return appDbHelper.findWordMainBetweenDates(today, today).toFlowable();
    }

    @Override
    public Flowable<List<WordMain>> wordMainAll() {
        return appDbHelper.getWordMainAll().toFlowable();
    }

    @Override
    public void setSearchWord(@NonNull String word) {
        this.storeWord = word;
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public Flowable<List<WordTotalInfo>> getStoreWordTranslateInfo() {

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
    }

    private Single<List<WordTotalInfo>> getWordTranslateInfo(final WordMain wordMain) {
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
                        return appDbHelper.getWordTotalInfoByWordid(wordMain.getWordid());
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
                })
                .flatMap(new Function<Boolean, SingleSource<List<WordTotalInfo>>>() {
                    @Override
                    public SingleSource<List<WordTotalInfo>> apply(Boolean aBoolean) throws Exception {
                        return saveWordTotalInfo(body, wordid);
                    }
                });

//        return insertWordmain(wordid, word.text(), 1)
//                .flatMap(new Function<Boolean, SingleSource<Boolean>>() {
//                             @Override
//                             public SingleSource<Boolean> apply(Boolean aBoolean) throws Exception {
//                                 return insertSearchTime(timeid, wordid, new Date());
//                             }
//                         }
//                )
//                .flatMap(new Function<Boolean, SingleSource<List<WordTotalInfo>>>() {
//                    @Override
//                    public SingleSource<List<WordTotalInfo>> apply(Boolean aBoolean) throws Exception {
//                        List<WordTotalInfo> list = new ArrayList<>();
//
//                        for (Element bd : body) {
//                            WordTotalInfo wordTotalInfo = new WordTotalInfo();
//                            Elements type = bd.select("span[class=pos]");
//                            Elements sensebody = bd.select("div[class=sense-body]");
//                            Elements english_trans = sensebody.select("b[class=def]");
//                            Elements defbody = sensebody.select("span[class=def-body]");
//
//                            for (int i = 0; i < english_trans.size(); i++) {
//                                String wordinfoid = UUID.randomUUID().toString();
//                                Element chinese_trans = defbody.get(i).select("span[class=trans]").first();
//                                Elements examp = defbody.get(i).select("div[class=examp emphasized]");
//                                WordInfo wordInfo = new WordInfo(wordinfoid, wordid, chinese_trans.text(), english_trans.get(i).text(), type.text());
//                                wordTotalInfo.setWordInfo(wordInfo);
//                                appDbHelper.insertWordInfo(wordInfo);
//                                List<WordExample> wordExamples = new ArrayList<>();
//                                for (Element ex : examp) {
//                                    String exampleid = UUID.randomUUID().toString();
//                                    Elements english_example = ex.select("span[class=eg]");
//                                    Elements chinese_example_tran = ex.select("span[class=trans]");
//                                    WordExample wordExample = new WordExample(exampleid, wordinfoid, english_example.text(), chinese_example_tran.text());
//                                    wordExamples.add(wordExample);
//                                    appDbHelper.insertWordExample(wordExample);
//                                }
//                                wordTotalInfo.setWordExamples(wordExamples);
//                            }
//                            list.add(wordTotalInfo);
//                        }
//                        return getwordTotalInfo(list);
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


    private Observable<List<WordExample>> saveWordExample(final Elements examp, final String wordinfoid) {
        final List<WordExample> wordExamples = new ArrayList<>();
        Observable.create(new ObservableOnSubscribe<WordExample>() {
            @Override
            public void subscribe(ObservableEmitter<WordExample> e) throws Exception {
                for (Element ex : examp) {
                    String exampleid = UUID.randomUUID().toString();
                    Elements english_example = ex.select("span[class=eg]");
                    Elements chinese_example_tran = ex.select("span[class=trans]");
                    WordExample wordExample = new WordExample(exampleid, wordinfoid, english_example.text(), chinese_example_tran.text());
                    e.onNext(wordExample);
                }
            }
        }).subscribe(new Observer<WordExample>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WordExample wordExample) {
                appDbHelper.insertWordExample(wordExample);
                wordExamples.add(wordExample);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        return Observable.just(wordExamples);
    }


    private Observable<WordTotalInfo> saveWordInfo(Element body, final String wordid) {
        final Elements type = body.select("span[class=pos]");
        final Elements sensebody = body.select("div[class=sense-body]");
        final Elements english_trans = sensebody.select("b[class=def]");
        final Elements defbody = sensebody.select("span[class=def-body]");
        final WordTotalInfo wordTotalInfo = new WordTotalInfo();

        Observable.create(new ObservableOnSubscribe<Elements>() {
            @Override
            public void subscribe(ObservableEmitter<Elements> e) throws Exception {
                for (int i = 0; i < english_trans.size(); i++) {
                    String wordinfoid = UUID.randomUUID().toString();
                    Element chinese_trans = defbody.get(i).select("span[class=trans]").first();
                    Elements examp = defbody.get(i).select("div[class=examp emphasized]");
                    WordInfo wordInfo = new WordInfo(wordinfoid, wordid, chinese_trans.text(), english_trans.get(i).text(), type.text());
                    wordTotalInfo.setWordInfo(wordInfo);
                    appDbHelper.insertWordInfo(wordInfo);
                    e.onNext(examp);
                }
            }
        }).flatMap(new Function<Elements, ObservableSource<List<WordExample>>>() {
            @Override
            public ObservableSource<List<WordExample>> apply(Elements elements) throws Exception {
                return saveWordExample(elements,wordTotalInfo.getWordInfo().getWordinfoid());
            }
        }).subscribe(new Observer<List<WordExample>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<WordExample> wordExamples) {
                wordTotalInfo.setWordExamples(wordExamples);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        return Observable.just(wordTotalInfo);
    }


    private Single<List<WordTotalInfo>> saveWordTotalInfo(final Elements body, final String wordid) {
        final List<WordTotalInfo> list = new ArrayList<>();
        Observable.create(new ObservableOnSubscribe<Element>() {
            @Override

            public void subscribe(ObservableEmitter<Element> e) throws Exception {
                for (Element bd : body) {
                    e.onNext(bd);
                }
            }
        }).flatMap(new Function<Element, ObservableSource<WordTotalInfo>>() {
            @Override
            public ObservableSource<WordTotalInfo> apply(Element element) throws Exception {
                return saveWordInfo(element, wordid);
            }
        }).subscribe(new Observer<WordTotalInfo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WordTotalInfo wordTotalInfo) {
                list.add(wordTotalInfo);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        return Single.create(new SingleOnSubscribe<List<WordTotalInfo>>() {
            @Override
            public void subscribe(SingleEmitter<List<WordTotalInfo>> e) throws Exception {
                e.onSuccess(list);
            }
        });
    }


    private Single<Boolean> insertWordmain(String wordid, @NonNull String word, int times) {
        WordMain wordMain = new WordMain(wordid, word, times);
        return appDbHelper.insertWordMain(wordMain);
    }

    private Single<Boolean> insertSearchTime(String searchtimeid, @NonNull String wordid,
                                             @NonNull Date searchtime) {
        SearchTime searchTime = new SearchTime(searchtimeid, wordid, searchtime);
        return appDbHelper.insertSearchTime(searchTime);
    }

    private Single<Boolean> updateWordMain(WordMain wordMain) {
        return appDbHelper.updateWordMain(wordMain);
    }

    @NotNull
    @Override
    public Flowable<String> getSearchWord() {
        return Flowable.just(storeWord);
    }
}
