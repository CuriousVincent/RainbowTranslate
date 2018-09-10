package com.mvp.vincentwang.rainbowtranslate.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mvp.vincentwang.rainbowtranslate.room.data.WordMain;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface WordMainDao {

    @Query("SELECT * FROM WordMain WHERE word IN (:word)")
    Single<List<WordMain>> loadByword(String word);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WordMain wordMain);

    @Delete
    void delete(WordMain wordMain);

    @Update
    void update(WordMain wordMain);
}
