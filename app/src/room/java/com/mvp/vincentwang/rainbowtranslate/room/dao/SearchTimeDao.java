package com.mvp.vincentwang.rainbowtranslate.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

import com.mvp.vincentwang.rainbowtranslate.room.data.SearchTime;

@Dao
public interface SearchTimeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SearchTime searchTime);
    @Delete
    void delete(SearchTime searchTime);
    @Update
    void update(SearchTime searchTime);
}
