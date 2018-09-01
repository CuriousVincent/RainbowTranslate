package com.mvp.vincentwang.rainbowtranslate.room.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
@Entity
public class WordMain {
    @PrimaryKey
    private String wordid;

    @PrimaryKey
    private String word;

    private int times;
}
