package com.mvp.vincentwang.rainbowtranslate.room.dao;

import android.arch.persistence.room.Query;

import com.mvp.vincentwang.rainbowtranslate.room.data.WordMain;

import java.util.List;

public interface WordMainDao {

    @Query("SELECT * FROM WordMain")
    List<WordMain> getAll();

    @Query("SELECT * FROM WordMain WHERE wordid IN (:wordids)")
    List<WordMain> loadAllByIds(String[] wordids);



}
