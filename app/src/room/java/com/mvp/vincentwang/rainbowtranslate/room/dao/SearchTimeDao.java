package com.mvp.vincentwang.rainbowtranslate.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mvp.vincentwang.rainbowtranslate.room.data.SearchTime;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordMain;

import java.util.Date;
import java.util.List;

import io.reactivex.Single;

@Dao
public interface SearchTimeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SearchTime searchTime);
    @Delete
    void delete(SearchTime searchTime);
    @Update
    void update(SearchTime searchTime);
    @Query("SELECT * FROM SearchTime")
    Single<List<SearchTime>> loadAll();
    @Query("SELECT * FROM SearchTime s WHERE s.searchtime BETWEEN :from AND :to group by s.wordid ")
    Single<List<SearchTime>> findWordMainBetweenDates(Date from, Date to);
}
