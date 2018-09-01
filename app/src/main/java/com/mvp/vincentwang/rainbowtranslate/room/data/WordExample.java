package com.mvp.vincentwang.rainbowtranslate.room.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
@Entity
public class WordExample {
    //primary key
    @PrimaryKey
    private String exampleid;
    private String wordinfoid;
    private String example;
    private String exampletranslate;
}
