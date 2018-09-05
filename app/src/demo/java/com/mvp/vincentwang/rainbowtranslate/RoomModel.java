package com.mvp.vincentwang.rainbowtranslate;

import android.support.annotation.NonNull;

import com.mvp.vincentwang.rainbowtranslate.room.AppDbHelper;
import com.mvp.vincentwang.rainbowtranslate.room.RoomContract;
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
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class RoomModel implements RoomContract.Model {

    private AppDbHelper appDbHelper;

    public RoomModel(AppDbHelper appDbHelper){
        this.appDbHelper = appDbHelper;
    }


    @Override
    public Flowable<ArrayList<WordMain>> wordMainToday() {
        return null;
    }

    @Override
    public Flowable<ArrayList<WordMain>> wordMainAll() {
        return null;
    }

    @NotNull
    @Override
    public Flowable<List<WordTotalInfo>> getWordTranslateInfo(@Nullable final String word) {
        return appDbHelper.getWordMainByWordMain(word)
                .flatMap(new Function<List<WordMain>, SingleSource<List<WordTotalInfo>>>() {
                    @Override
                    public SingleSource<List<WordTotalInfo>> apply(List<WordMain> wordMains) throws Exception {
//                        if(wordMains.size() == 0){
//                        }
//                        return null;
                        return saveWordTranslateInfo(word).singleOrError();
                    }
                }).toFlowable();

//        return Flowable.create(new FlowableOnSubscribe<ArrayList<com.mvp.vincentwang.rainbowtranslate.data.WordTotalInfo>>() {
//            @Override
//            public void subscribe(FlowableEmitter<ArrayList<com.mvp.vincentwang.rainbowtranslate.data.WordTotalInfo>> e) throws Exception {
//                ArrayList<com.mvp.vincentwang.rainbowtranslate.data.WordTotalInfo> wordTotalInfos;
//                List<com.mvp.vincentwang.rainbowtranslate.data.WordMain> wordMainList = getWordMainbyWord(word);
//                if (wordMainList.size() != 0) {
//                    com.mvp.vincentwang.rainbowtranslate.data.WordMain wordMain = wordMainList.get(0);
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

    @NotNull
    @Override
    public Flowable<ArrayList<WordMain>> getWordMainPeriod(@NotNull Calendar startDay, @NotNull Calendar endDay) {
        return null;
    }

    @Override
    public void setSearchWord(@NotNull String word) {

    }

    @NotNull
    @Override
    public Flowable<ArrayList<WordTotalInfo>> getStoreWordTranslateInfo() {
        return null;
    }


    private Observable<List<WordTotalInfo>> saveWordTranslateInfo(final String sWord) throws Exception {

        return Observable.create(new ObservableOnSubscribe<Document>() {
            @Override
            public void subscribe(final ObservableEmitter<Document> e) throws Exception {
                JsoupUtil.getInstance().getHttp("http://dictionary.cambridge.org/zht/%E8%A9%9E%E5%85%B8/%E8%8B%B1%E8%AA%9E-%E6%BC%A2%E8%AA%9E-%E7%B9%81%E9%AB%94/" + sWord
                        , new JsoupUtil.ResponseCallback() {
                            @Override
                            public void onResponse(Document document) throws IOException {
                                e.onNext(document);
                            }
                        });

            }
        }).flatMap(new Function<Document, ObservableSource<List<WordTotalInfo>>>() {
            @Override
            public ObservableSource<List<WordTotalInfo>> apply(Document document) throws Exception {
                return saveWordInDB(document);
            }
        });

//        JsoupUtil.getInstance().getHttp("http://dictionary.cambridge.org/zht/%E8%A9%9E%E5%85%B8/%E8%8B%B1%E8%AA%9E-%E6%BC%A2%E8%AA%9E-%E7%B9%81%E9%AB%94/" + sWord
//                , new JsoupUtil.ResponseCallback() {
//                    @Override
//                    public void onResponse(Document document) throws IOException {
//                        saveWordInDB(document);
//                    }
//                });

    }

    private Observable<List<WordTotalInfo>> saveWordInDB(final Document document) {
       final String wordid = UUID.randomUUID().toString();
       final String timeid = UUID.randomUUID().toString();
        final Elements body = document.select("div[class=entry-body__el clrd js-share-holder]");
        final Element word = body.select("span[class=hw]").first();

       return insertWordmain(wordid, word.text(), 1)
                .flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                        return insertSearchTime(timeid, wordid, new Date());
                    }
                }).flatMap(new Function<Boolean, ObservableSource<List<WordTotalInfo>>>() {
                   @Override
                   public ObservableSource<List<WordTotalInfo>> apply(Boolean aBoolean) throws Exception {
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
                               List<WordExample> wordExamples = new ArrayList<>();
                               for (Element ex : examp) {
                                   String exampleid = UUID.randomUUID().toString();
                                   Elements english_example = ex.select("span[class=eg]");
                                   Elements chinese_example_tran = ex.select("span[class=trans]");
                                   WordExample wordExample = new WordExample(exampleid, wordinfoid, english_example.text(), chinese_example_tran.text());
                                   wordExamples.add(wordExample);
                               }
                               wordTotalInfo.setWordExamples(wordExamples);
                           }
                       }
                       return appDbHelper.insertWordTotalInfo(list);
                   }
               });
    }
    private Observable<Boolean> insertWordmain(String wordid, @NonNull String word, int times) {
        WordMain wordMain = new WordMain(wordid,word,times);
        return appDbHelper.insertWordMain(wordMain);
    }

    private Observable<Boolean> insertSearchTime(String searchtimeid, @NonNull String wordid,
                                  @NonNull Date searchtime) {
        SearchTime searchTime = new SearchTime(searchtimeid,wordid,searchtime);
        return appDbHelper.insertSearchTime(searchTime);
    }

    private Observable<Boolean> insertWordinfo(String wordinfoid, @NonNull String wordid,
                                String chinesemean, String englishmean, String type) {
        WordInfo wordInfo = new WordInfo(wordinfoid,wordid,chinesemean,englishmean,type);
        return appDbHelper.insertWordInfo(wordInfo);
    }

    private Observable<Boolean> insertWordExample(String exampleid, @NonNull String wordinfoid,String example, String exampletranslate) {
        WordExample wordExample = new WordExample(exampleid,wordinfoid,example,exampletranslate);
        return appDbHelper.insertWordExample(wordExample);
    }

}
