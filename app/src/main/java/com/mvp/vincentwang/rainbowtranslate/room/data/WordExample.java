package com.mvp.vincentwang.rainbowtranslate.room.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(entity = WordInfo.class,
        parentColumns = "wordinfoid",
        childColumns = "wordinfoid"),
        indices={@Index(value="wordinfoid")})
public class WordExample {
    //primary key
    @PrimaryKey
    @NonNull
    private String exampleid;
    private String wordinfoid;
    private String example;
    private String exampletranslate;

    public WordExample(String exampleid, @NonNull String wordinfoid, String example, String exampletranslate) {
        this.exampleid = exampleid;
        this.wordinfoid = wordinfoid;
        this.example = example;
        this.exampletranslate = exampletranslate;
    }

    public String getExampleid() {
        return exampleid;
    }

    public String getWordinfoid() {
        return wordinfoid;
    }

    public String getExample() {
        return example;
    }

    public String getExampletranslate() {
        return exampletranslate;
    }
}
