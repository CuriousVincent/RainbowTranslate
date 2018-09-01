package com.mvp.vincentwang.rainbowtranslate.framework;

import android.content.Context;

import com.mvp.vincentwang.rainbowtranslate.data.DaoMaster;
import com.mvp.vincentwang.rainbowtranslate.data.DaoSession;
import com.mvp.vincentwang.rainbowtranslate.data.SearchTimeDao;
import com.mvp.vincentwang.rainbowtranslate.data.WordExampleDao;
import com.mvp.vincentwang.rainbowtranslate.data.WordInfoDao;
import com.mvp.vincentwang.rainbowtranslate.data.WordMainDao;

/**
 * Created by vincentwang on 2017/8/17.
 */

public class DBService {
    private static final String DB_NAME = "greedDaoDemo.db";
    private DaoSession daoSession;

    public DBService(Context context)
    {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);

        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());

        daoSession = daoMaster.newSession();
    }

    public WordMainDao getWordMainDao(){
        return daoSession.getWordMainDao();
    }
    public SearchTimeDao getSearchTimeDao(){
        return daoSession.getSearchTimeDao();
    }
    public WordInfoDao getWordInfoDao(){
        return daoSession.getWordInfoDao();
    }
    public WordExampleDao getWordExampleDao(){
        return daoSession.getWordExampleDao();
    }
}
