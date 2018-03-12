package com.example.vincentwang.rainbowtranslate.factory;

import android.content.Context;

import com.example.vincentwang.rainbowtranslate.framework.DBService;

/**
 * Created by vincentwang on 2017/8/17.
 */

public class ServiceFactory {

    private static Context sContext;
    private static DBService dbService;

    private ServiceFactory(){

    }

    public static void init(Context context)
    {
        sContext = context.getApplicationContext();
    }


    public static DBService getDBService(){
        if(dbService == null){
            dbService = new DBService(sContext);
        }

        return dbService;
    }

}
