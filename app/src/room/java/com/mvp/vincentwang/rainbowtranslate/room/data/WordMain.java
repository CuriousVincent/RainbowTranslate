package com.mvp.vincentwang.rainbowtranslate.room.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(indices={@Index(value="word", unique=true)})
public class WordMain {
    @PrimaryKey
    @NonNull
    private String wordid;
    @NonNull
    @ColumnInfo(name = "word")
    private String word;

    private int times;

    public WordMain(String wordid, String word, int times) {
        this.wordid = wordid;
        this.word = word;
        this.times = times;
    }

    public String getWordid() {
        return wordid;
    }

    public String getWord() {
        return word;
    }

    public int getTimes() {
        return times;
    }

    public void setWordid(@NonNull String wordid) {
        this.wordid = wordid;
    }

    public void setWord(@NonNull String word) {
        this.word = word;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
