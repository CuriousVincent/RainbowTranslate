package com.mvp.vincentwang.rainbowtranslate.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.mvp.vincentwang.rainbowtranslate.room.dao.SearchTimeDao;
import com.mvp.vincentwang.rainbowtranslate.room.dao.WordExampleDao;
import com.mvp.vincentwang.rainbowtranslate.room.dao.WordInfoDao;
import com.mvp.vincentwang.rainbowtranslate.room.dao.WordMainDao;
import com.mvp.vincentwang.rainbowtranslate.room.data.SearchTime;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordExample;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordInfo;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordMain;

@Database(entities = {WordMain.class, WordInfo.class, WordExample.class, SearchTime.class}, version = 1)
@TypeConverters({DateConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract WordMainDao wordMainDao();

    public abstract WordInfoDao wordInfoDao();

    public abstract WordExampleDao wordExampleDao();

    public abstract SearchTimeDao searchTimeDao();


}
