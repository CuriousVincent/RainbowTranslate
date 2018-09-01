package com.mvp.vincentwang.rainbowtranslate.room.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
@Entity
public class SearchTime {
    //primary key
    @PrimaryKey
    private String searchtimeid;
    private String wordid;
    private Date searchtime;
}
