package com.mvp.vincentwang.rainbowtranslate.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mvp.vincentwang.rainbowtranslate.room.data.WordInfo;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface WordInfoDao {
    @Query("SELECT * FROM WordInfo WHERE wordid IN (:wordid)")
    Single<List<WordTotalInfo>> loadAllByIds(String wordid);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WordInfo wordInfo);
    @Delete
    void delete(WordInfo wordInfo);

    @Update
    void update(WordInfo wordInfo);
}
