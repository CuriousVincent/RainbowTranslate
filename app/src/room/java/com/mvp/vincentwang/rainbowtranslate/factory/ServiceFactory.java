package com.mvp.vincentwang.rainbowtranslate.factory;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.mvp.vincentwang.rainbowtranslate.Model;
import com.mvp.vincentwang.rainbowtranslate.TranslateModel;
import com.mvp.vincentwang.rainbowtranslate.framework.DBService;
import com.mvp.vincentwang.rainbowtranslate.room.AppDatabase;
import com.mvp.vincentwang.rainbowtranslate.room.AppDbHelper;

/**
 * Created by vincentwang on 2017/8/17.
 */

public class ServiceFactory {

    private static Context sContext;
    private static DBService dbService;
    private static TranslateModel translateModel;
private static AppDbHelper appDbHelper;
private static AppDatabase appDatabase;


    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }


//    public static DBService getDBService() {
//        if (dbService == null) {
//            dbService = new DBService(sContext);
//        }
//
//        return dbService;
//    }

    public static Model provideTranslateModel(){
        if (translateModel == null) {
            translateModel = new TranslateModel(provideAppDbHelper());
        }
        return translateModel;
    }

    public static AppDbHelper provideAppDbHelper(){
        if(appDbHelper ==  null){
            appDbHelper = new AppDbHelper(provideAppDatabase("Translate",sContext));
        }
        return appDbHelper;
    }
    static AppDatabase provideAppDatabase(String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration()
                .build();
    }
}
