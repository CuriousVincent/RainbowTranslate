package com.mvp.vincentwang.rainbowtranslate.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by vincentwang on 2017/8/4.
 */
@Entity
public class WordExample {
    @Id(autoincrement = true)
    private Long id;

    //primary key
    @Index(unique = true)
    private String exampleid;

    @NotNull
    private String wordinfoid;

    private String example;
    private String exampletranslate;
    @Generated(hash = 203172191)
    public WordExample() {
    }
    @Generated(hash = 561031137)
    public WordExample(Long id, String exampleid, @NotNull String wordinfoid,
            String example, String exampletranslate) {
        this.id = id;
        this.exampleid = exampleid;
        this.wordinfoid = wordinfoid;
        this.example = example;
        this.exampletranslate = exampletranslate;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getWordinfoid() {
        return this.wordinfoid;
    }
    public void setWordinfoid(String wordinfoid) {
        this.wordinfoid = wordinfoid;
    }
    public String getExample() {
        return this.example;
    }
    public void setExample(String example) {
        this.example = example;
    }
    public String getExampletranslate() {
        return this.exampletranslate;
    }
    public void setExampletranslate(String exampletranslate) {
        this.exampletranslate = exampletranslate;
    }
    public String getExampleid() {
        return this.exampleid;
    }
    public void setExampleid(String exampleid) {
        this.exampleid = exampleid;
    }

}
