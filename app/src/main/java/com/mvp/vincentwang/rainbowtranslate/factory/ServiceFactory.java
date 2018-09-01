package com.mvp.vincentwang.rainbowtranslate.factory;

import android.content.Context;

import com.mvp.vincentwang.rainbowtranslate.TranslateModel;
import com.mvp.vincentwang.rainbowtranslate.framework.DBService;
import com.mvp.vincentwang.rainbowtranslate.translate.TranslateContract;

/**
 * Created by vincentwang on 2017/8/17.
 */

public class ServiceFactory {

    private static Context sContext;
    private static DBService dbService;
    private static TranslateModel translateModel;


    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }


    public static DBService getDBService() {
        if (dbService == null) {
            dbService = new DBService(sContext);
        }

        return dbService;
    }

    public static TranslateContract.Model provideTranslateModel(){
        if (translateModel == null) {
            translateModel = new TranslateModel();
        }
        return translateModel;
    }

}
