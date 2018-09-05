package com.mvp.vincentwang.rainbowtranslate.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

import com.mvp.vincentwang.rainbowtranslate.room.data.WordExample;

@Dao
public interface WordExampleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WordExample wordExample);
    @Delete
    void delete(WordExample wordExample);
    @Update
    void update(WordExample wordExample);
}
