package com.mvp.vincentwang.rainbowtranslate.room.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
@Entity
public class WordInfo {

    //primary key
    @PrimaryKey
    private String wordinfoid;

    private String wordid;

    private String chinesemean;

    private String englishmean;

    private String type;
}
