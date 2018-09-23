package com.mvp.vincentwang.rainbowtranslate.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mvp.vincentwang.rainbowtranslate.room.data.WordMain;

import java.util.Date;
import java.util.List;

import io.reactivex.Single;

@Dao
public interface WordMainDao {
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void insert(WordMain wordMain);

    @Delete
    void delete(WordMain wordMain);

    @Update
    void update(WordMain wordMain);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(List<WordMain> wordMain);

    @Query("SELECT * FROM WordMain")
    Single<List<WordMain>> all();


    @Query("SELECT * FROM WordMain WHERE word IN (:word)")
    Single<List<WordMain>> loadByword(String word);

    @Query("SELECT * FROM WordMain w INNER JOIN SearchTime s ON w.wordid = s.wordid group by w.wordid order by s.searchtime desc")
    Single<List<WordMain>> loadAll();

    @Query("SELECT * FROM WordMain w INNER JOIN SearchTime s ON w.wordid = s.wordid WHERE s.searchtime BETWEEN :from AND :to group by w.wordid order by s.searchtime desc")
    Single<List<WordMain>> findWordMainBetweenDates(Date from, Date to);
}
