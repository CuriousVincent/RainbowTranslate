package com.mvp.vincentwang.rainbowtranslate.room.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(entity = WordMain.class,
        parentColumns = "wordid",
        childColumns = "wordid"),
        indices={@Index(value="wordid")})
public class WordInfo {

    //primary key
    @PrimaryKey
    @NonNull
    private String wordinfoid;

    private String wordid;

    private String chinesemean;

    private String englishmean;

    private String type;

    public WordInfo(@NonNull String wordinfoid, @NonNull String wordid, String chinesemean, String englishmean, String type){
        this.wordinfoid = wordinfoid;
        this.wordid = wordid;
        this.chinesemean = chinesemean;
        this.englishmean = englishmean;
        this.type = type;
    }

    public String getWordinfoid() {
        return wordinfoid;
    }

    public String getWordid() {
        return wordid;
    }

    public String getChinesemean() {
        return chinesemean;
    }

    public String getEnglishmean() {
        return englishmean;
    }

    public String getType() {
        return type;
    }
}
