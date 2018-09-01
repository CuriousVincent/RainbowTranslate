package com.mvp.vincentwang.rainbowtranslate;

import com.mvp.vincentwang.rainbowtranslate.data.SearchTime;
import com.mvp.vincentwang.rainbowtranslate.data.SearchTimeDao;
import com.mvp.vincentwang.rainbowtranslate.data.WordExample;
import com.mvp.vincentwang.rainbowtranslate.data.WordExampleDao;
import com.mvp.vincentwang.rainbowtranslate.data.WordInfo;
import com.mvp.vincentwang.rainbowtranslate.data.WordInfoDao;
import com.mvp.vincentwang.rainbowtranslate.data.WordMain;
import com.mvp.vincentwang.rainbowtranslate.data.WordMainDao;
import com.mvp.vincentwang.rainbowtranslate.data.WordTotalInfo;
import com.mvp.vincentwang.rainbowtranslate.factory.ServiceFactory;
import com.mvp.vincentwang.rainbowtranslate.translate.TranslateContract.Model;
import com.mvp.vincentwang.rainbowtranslate.util.JsoupUtil;
import com.mvp.vincentwang.rainbowtranslate.util.ToolUtils;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * Created by vincentwang on 2017/8/17.
 */

public class TranslateModel implements Model {

    private WordMainDao wordMainDao;
    private WordInfoDao wordInfoDao;
    private WordExampleDao wordExampleDao;
    private SearchTimeDao searchTimeDao;
    private String storeWord;

    public TranslateModel() {
        wordMainDao = ServiceFactory.getDBService().getWordMainDao();
        wordInfoDao = ServiceFactory.getDBService().getWordInfoDao();
        wordExampleDao = ServiceFactory.getDBService().getWordExampleDao();
        searchTimeDao = ServiceFactory.getDBService().getSearchTimeDao();
    }


    private ArrayList<WordTotalInfo> getWordTotalInfo(WordMain wordMain) {
        ArrayList<WordTotalInfo> wordTotalInfos = new ArrayList<>();
        String wordid = wordMain.getWordid();
        ArrayList<WordInfo> wordInfolist = getWordInfobyWordId(wordid);
        if (wordInfolist != null) {
            for (WordInfo wordInfo : wordInfolist) {
                String wordinfoid = wordInfo.getWordinfoid();
                List<WordExample> wordexlist = getWordExamplesbyWordInfoId(wordinfoid);
                WordTotalInfo wordTotalInfo = new WordTotalInfo();
                wordTotalInfo.setWord(wordMain);
                wordTotalInfo.setWordInfo(wordInfo);
                wordTotalInfo.setWordExamples(wordexlist);
                wordTotalInfos.add(wordTotalInfo);
            }
        }
        return wordTotalInfos;
    }

    private List<WordMain> getWordMainbyWord(String word) {
        QueryBuilder<WordMain> wordquery = wordMainDao.queryBuilder();
        wordquery.where(WordMainDao.Properties.Word.eq(word));
        List<WordMain> wordmainlist = wordquery.list();
        return wordmainlist;
    }

    private List<WordMain> getWordMainbyWordId(String wordid) {
        QueryBuilder<WordMain> wordquery = wordMainDao.queryBuilder();
        wordquery.where(WordMainDao.Properties.Wordid.eq(wordid));
        List<WordMain> wordmainlist = wordquery.list();
        return wordmainlist;
    }

    private ArrayList<WordInfo> getWordInfobyWordId(String wordid) {
        QueryBuilder<WordInfo> wordinfoquery = wordInfoDao.queryBuilder();
        wordinfoquery.where(WordInfoDao.Properties.Wordid.eq(wordid));
        ArrayList<WordInfo> wordInfolist = new ArrayList<>(wordinfoquery.list());
        return wordInfolist;
    }

    private List<WordExample> getWordExamplesbyWordInfoId(String wordinfoid) {
        QueryBuilder<WordExample> wordexquery = wordExampleDao.queryBuilder();
        wordexquery.where(WordExampleDao.Properties.Wordinfoid.eq(wordinfoid));
        List<WordExample> wordexlist = wordexquery.list();
        return wordexlist;
    }

    private ArrayList<WordMain> getWordMain() {
        ArrayList<WordMain> wordmainlist = new ArrayList<>(wordMainDao.loadAll());
        return wordmainlist;
    }

    private List<SearchTime> getTodaySearchTime() {
        List<SearchTime> searchTimes = queryOneDayData(searchTimeDao.queryBuilder(), SearchTimeDao.Properties.Searchtime, new Date());
        return searchTimes;
    }

    private List<SearchTime> getPeriodDateSearchTime(Calendar startDay, Calendar endDay) {
        List<SearchTime> searchTimes = queryPeriodDateData(searchTimeDao.queryBuilder(), SearchTimeDao.Properties.Searchtime, startDay, endDay);
        return searchTimes;
    }

    private ArrayList<WordMain> getWordMain(List<SearchTime> searchTimes) {
        ArrayList<WordMain> wordMains = new ArrayList<>();
        for (int time = searchTimes.size() - 1; time >= 0; time--) {
            wordMains.add(getWordMainbyWordId(searchTimes.get(time).getWordid()).get(0));
        }
        ToolUtils.rmRepeadtedElementByOrder(wordMains);
        return wordMains;
    }


    @Override
    public Flowable<ArrayList<WordTotalInfo>> getWordTranslateInfo(final String word) {
        return Flowable.create(new FlowableOnSubscribe<ArrayList<WordTotalInfo>>() {
            @Override
            public void subscribe(FlowableEmitter<ArrayList<WordTotalInfo>> e) throws Exception {
                ArrayList<WordTotalInfo> wordTotalInfos;
                List<WordMain> wordMainList = getWordMainbyWord(word);
                if (wordMainList.size() != 0) {
                    WordMain wordMain = wordMainList.get(0);
                    String wordid = wordMain.getWordid();
                    String timeid = UUID.randomUUID().toString();
                    insertSearchTime(timeid, wordid, new Date());
                    wordMain.setTimes(wordMain.getTimes() + 1);
                    wordMainDao.update(wordMain);
                    wordTotalInfos = getWordTotalInfo(wordMain);
                } else {
                    saveWordTranslateInfo(word);
                    wordMainList = getWordMainbyWord(word);
                    wordTotalInfos = getWordTotalInfo(wordMainList.get(0));

                }
                e.onNext(wordTotalInfos);
            }
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<ArrayList<WordMain>> getWordMainPeriod(final Calendar startDay, final Calendar endDay) {
        return Flowable.create(new FlowableOnSubscribe<ArrayList<WordMain>>() {
            @Override
            public void subscribe(FlowableEmitter<ArrayList<WordMain>> e) throws Exception {
                ArrayList<WordMain> wordMains = getWordMain(getPeriodDateSearchTime(startDay, endDay));

                if (wordMains != null) {
                    e.onNext(wordMains);
                } else {
                    e.onError(new Exception());
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<ArrayList<WordMain>> getWordMainToday() {
        return Flowable.create(new FlowableOnSubscribe<ArrayList<WordMain>>() {
            @Override
            public void subscribe(FlowableEmitter<ArrayList<WordMain>> e) throws Exception {
                ArrayList<WordMain> wordMains = getWordMain(getTodaySearchTime());
                if (wordMains != null) {
                    e.onNext(wordMains);
                } else {
                    e.onError(new Exception());
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<ArrayList<WordMain>> getWordMainAll() {
        return Flowable.create(new FlowableOnSubscribe<ArrayList<WordMain>>() {
            @Override
            public void subscribe(FlowableEmitter<ArrayList<WordMain>> e) throws Exception {
                ArrayList<WordMain> wordMains = getWordMain();
                if (wordMains != null) {
                    e.onNext(wordMains);
                } else {
                    e.onError(new Exception());
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    private void saveWordTranslateInfo(final String sWord) throws Exception {
        JsoupUtil.getInstance().getHttp("http://dictionary.cambridge.org/zht/%E8%A9%9E%E5%85%B8/%E8%8B%B1%E8%AA%9E-%E6%BC%A2%E8%AA%9E-%E7%B9%81%E9%AB%94/" + sWord
                , new JsoupUtil.ResponseCallback() {
                    @Override
                    public void onResponse(Document document) throws IOException {
                        saveWordInDB(document);
                    }
                });

    }

    private void saveWordInDB(Document document) {
        String wordid = UUID.randomUUID().toString();
        String timeid = UUID.randomUUID().toString();
        Elements body = document.select("div[class=entry-body__el clrd js-share-holder]");
        Element word = body.select("span[class=hw]").first();

        insertWordmain(wordid, word.text(), 1);
        insertSearchTime(timeid, wordid, new Date());

        for (Element bd : body) {
            Elements type = bd.select("span[class=pos]");
            Elements sensebody = bd.select("div[class=sense-body]");
            Elements english_trans = sensebody.select("b[class=def]");
            Elements defbody = sensebody.select("span[class=def-body]");

            for (int i = 0; i < english_trans.size(); i++) {
                String wordinfoid = UUID.randomUUID().toString();
                Element chinese_trans = defbody.get(i).select("span[class=trans]").first();
                Elements examp = defbody.get(i).select("div[class=examp emphasized]");
                insertWordinfo(wordinfoid, wordid, chinese_trans.text(), english_trans.get(i).text(), type.text());
                for (Element ex : examp) {
                    String exampleid = UUID.randomUUID().toString();
                    Elements english_example = ex.select("span[class=eg]");
                    Elements chinese_example_tran = ex.select("span[class=trans]");
                    insertWordExample(exampleid, wordinfoid, english_example.text(), chinese_example_tran.text());
                }
            }
        }
    }

    private void insertWordmain(String wordid, @NotNull String word, int times) {
        WordMain wordMain = new WordMain();
        wordMain.setWordid(wordid);
        wordMain.setWord(word);
        wordMain.setTimes(times);
        wordMainDao.insert(wordMain);
    }

    private void insertSearchTime(String searchtimeid, @NotNull String wordid,
                                  @NotNull Date searchtime) {
        SearchTime searchTime = new SearchTime();
        searchTime.setWordid(wordid);
        searchTime.setSearchtime(searchtime);
        searchTime.setSearchtimeid(searchtimeid);
        searchTimeDao.insert(searchTime);
    }

    private void insertWordinfo(String wordinfoid, @NotNull String wordid,
                                String chinesemean, String englishmean, String type) {
        WordInfo wordInfo = new WordInfo();
        wordInfo.setWordinfoid(wordinfoid);
        wordInfo.setEnglishmean(englishmean);
        wordInfo.setChinesemean(chinesemean);
        wordInfo.setWordid(wordid);
        wordInfo.setType(type);
        wordInfoDao.insert(wordInfo);
    }

    private void insertWordExample(String exampleid, @NotNull String wordinfoid,
                                   String example, String exampletranslate) {
        WordExample wordExample = new WordExample();
        wordExample.setExampleid(exampleid);
        wordExample.setWordinfoid(wordinfoid);
        wordExample.setExample(example);
        wordExample.setExampletranslate(exampletranslate);
        wordExampleDao.insert(wordExample);
    }

    //取得某天的所有資料
    private <T> List<T> queryOneDayData(QueryBuilder<T> builder, Property dateProperty, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        List<T> queryList = queryPeriodDateData(builder, dateProperty, calendar, calendar);


//        Date today = calendar.getTime();
//        calendar.add(Calendar.DAY_OF_MONTH,1);
//        Date nextDay = calendar.getTime();
//        List<T> queryList = builder.where(builder.and(dateProperty.ge(today), dateProperty.lt(nextDay))).list();
        return queryList;
    }

    private <T> List<T> queryPeriodDateData(QueryBuilder<T> builder, Property dateProperty, Calendar startDate, Calendar endDate) {
        Calendar startcalendar = startDate;
        Date startDay = startcalendar.getTime();
        Calendar endcalendar = endDate;
        endcalendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endDay = endcalendar.getTime();
        List<T> queryList = builder.where(builder.and(dateProperty.ge(startDay), dateProperty.lt(endDay))).list();
        return queryList;
    }

    @Override
    public void setSearchWord(@org.jetbrains.annotations.NotNull String word) {
        this.storeWord = word;
    }

    @org.jetbrains.annotations.NotNull
    @Override
    public Flowable<ArrayList<WordTotalInfo>> getStoreWordTranslateInfo() {
        return Flowable.create(new FlowableOnSubscribe<ArrayList<WordTotalInfo>>() {
            @Override
            public void subscribe(FlowableEmitter<ArrayList<WordTotalInfo>> e) throws Exception {
                ArrayList<WordTotalInfo> wordTotalInfos;
                List<WordMain> wordMainList = getWordMainbyWord(storeWord);
                if (wordMainList.size() != 0) {
                    WordMain wordMain = wordMainList.get(0);
                    String wordid = wordMain.getWordid();
                    String timeid = UUID.randomUUID().toString();
                    insertSearchTime(timeid, wordid, new Date());
                    wordMain.setTimes(wordMain.getTimes() + 1);
                    wordMainDao.update(wordMain);
                    wordTotalInfos = getWordTotalInfo(wordMain);
                    e.onNext(wordTotalInfos);
                } else {
                    e.onError(new Exception());
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
