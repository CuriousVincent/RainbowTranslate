package com.example.vincentwang.rainbowtranslate.translate;

import com.example.vincentwang.rainbowtranslate.data.SearchTime;
import com.example.vincentwang.rainbowtranslate.data.SearchTimeDao;
import com.example.vincentwang.rainbowtranslate.data.WordExample;
import com.example.vincentwang.rainbowtranslate.data.WordExampleDao;
import com.example.vincentwang.rainbowtranslate.data.WordInfo;
import com.example.vincentwang.rainbowtranslate.data.WordInfoDao;
import com.example.vincentwang.rainbowtranslate.data.WordMain;
import com.example.vincentwang.rainbowtranslate.data.WordMainDao;
import com.example.vincentwang.rainbowtranslate.data.WordTotalInfo;
import com.example.vincentwang.rainbowtranslate.factory.ServiceFactory;
import com.example.vincentwang.rainbowtranslate.translate.TranslateContract.Model;
import com.example.vincentwang.rainbowtranslate.util.JsoupUtil;
import com.example.vincentwang.rainbowtranslate.util.ToolUtils;

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

/**
 * Created by vincentwang on 2017/8/17.
 */

public class TranslateModel implements Model {

    private WordMainDao wordMainDao;
    private WordInfoDao wordInfoDao;
    private WordExampleDao wordExampleDao;
    private SearchTimeDao searchTimeDao;


    public TranslateModel() {
        wordMainDao = ServiceFactory.getDBService().getWordMainDao();
        wordInfoDao = ServiceFactory.getDBService().getWordInfoDao();
        wordExampleDao = ServiceFactory.getDBService().getWordExampleDao();
        searchTimeDao = ServiceFactory.getDBService().getSearchTimeDao();
    }


    private List<WordTotalInfo> getWordTotalInfo(WordMain wordMain) {
        List<WordTotalInfo> wordTotalInfos = new ArrayList<>();
        String wordid = wordMain.getWordid();
        List<WordInfo> wordInfolist = getWordInfobyWordId(wordid);
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

    private List<WordInfo> getWordInfobyWordId(String wordid) {
        QueryBuilder<WordInfo> wordinfoquery = wordInfoDao.queryBuilder();
        wordinfoquery.where(WordInfoDao.Properties.Wordid.eq(wordid));
        List<WordInfo> wordInfolist = wordinfoquery.list();
        return wordInfolist;
    }

    private List<WordExample> getWordExamplesbyWordInfoId(String wordinfoid) {
        QueryBuilder<WordExample> wordexquery = wordExampleDao.queryBuilder();
        wordexquery.where(WordExampleDao.Properties.Wordinfoid.eq(wordinfoid));
        List<WordExample> wordexlist = wordexquery.list();
        return wordexlist;
    }

    private List<WordMain> getWordMain() {
        List<WordMain> wordmainlist = wordMainDao.loadAll();
        return wordmainlist;
    }

    private List<SearchTime> getTodaySearchTime(){
        List<SearchTime> searchTimes = queryOneDayData(searchTimeDao.queryBuilder(),SearchTimeDao.Properties.Searchtime,new Date());
        return searchTimes;
    }

    private List<SearchTime> getPeriodDateSearchTime(Calendar startDay, Calendar endDay){
        List<SearchTime> searchTimes = queryPeriodDateData(searchTimeDao.queryBuilder(),SearchTimeDao.Properties.Searchtime,startDay,endDay);
        return searchTimes;
    }

    private List<WordMain> getWordMain(List<SearchTime> searchTimes){

        List<WordMain> wordMains = new ArrayList<>();
        for (int time = searchTimes.size()-1;time>=0;time--) {
            wordMains.add(getWordMainbyWordId(searchTimes.get(time).getWordid()).get(0));
        }
        ToolUtils.rmRepeadtedElementByOrder(wordMains);
        return wordMains;
    }


    @Override
    public void getWordTranslateInfo(String word, GetWordCallback callback) {
        try {
            List<WordTotalInfo> wordTotalInfos;
            List<WordMain> wordMainList = getWordMainbyWord(word);
            if (wordMainList.size() != 0) {
                WordMain wordMain = wordMainList.get(0);
                String wordid = wordMain.getWordid();
                String timeid = UUID.randomUUID().toString();
                insertSearchTime(timeid,wordid,new Date());
                wordMain.setTimes(wordMain.getTimes()+1);
                wordMainDao.update(wordMain);
                wordTotalInfos = getWordTotalInfo(wordMain);
            } else {
                saveWordTranslateInfo(word);
                wordMainList = getWordMainbyWord(word);
                wordTotalInfos = getWordTotalInfo(wordMainList.get(0));

            }
            callback.getWord(wordTotalInfos);
        } catch (Exception e) {
            callback.onWordnotfound();
        }
    }

    @Override
    public void getWordMain(int spinnerposition ,GetWordMainCallback callback,Calendar startDay,Calendar endDay) {

        List<WordMain> wordMains = null;
        switch (spinnerposition){

            case 0:
                wordMains = getWordMain(getTodaySearchTime());
                break;
            case 1:
                wordMains = getWordMain(getPeriodDateSearchTime(startDay,endDay));
                break;
            case 2:
                wordMains = getWordMain();
                break;
        }

        if(wordMains != null) {
            callback.getWordMain(wordMains);
        } else {
            callback.dataError();
        }
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
    private <T> List<T> queryOneDayData(QueryBuilder<T> builder, Property dateProperty, Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        List<T> queryList = queryPeriodDateData(builder,dateProperty,calendar,calendar);


//        Date today = calendar.getTime();
//        calendar.add(Calendar.DAY_OF_MONTH,1);
//        Date nextDay = calendar.getTime();
//        List<T> queryList = builder.where(builder.and(dateProperty.ge(today), dateProperty.lt(nextDay))).list();
        return queryList;
    }
    private <T> List<T> queryPeriodDateData(QueryBuilder<T> builder, Property dateProperty, Calendar startDate,Calendar endDate){
        Calendar startcalendar = startDate;
        Date startDay = startcalendar.getTime();
        Calendar endcalendar = endDate;
        endcalendar.add(Calendar.DAY_OF_MONTH,1);
        Date endDay = endcalendar.getTime();
        List<T> queryList = builder.where(builder.and(dateProperty.ge(startDay), dateProperty.lt(endDay))).list();
        return queryList;
    }
}
