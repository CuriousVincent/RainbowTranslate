package com.mvp.vincentwang.rainbowtranslate.room.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(
        entity = WordMain.class,
        parentColumns = "wordid",
        childColumns = "wordid"),
        indices = {@Index(value = "wordid")})
public class SearchTime {
    //primary key
    @PrimaryKey
    @NonNull
    private String searchtimeid;
    private String wordid;
    private Date searchtime;

    public SearchTime(String searchtimeid, String wordid, Date searchtime) {
        this.searchtimeid = searchtimeid;
        this.wordid = wordid;
        this.searchtime = searchtime;
    }


    public String getSearchtimeid() {
        return searchtimeid;
    }

    public String getWordid() {
        return wordid;
    }

    public Date getSearchtime() {
        return searchtime;
    }
}
