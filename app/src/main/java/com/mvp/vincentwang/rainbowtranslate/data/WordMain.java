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
public class WordMain {
    @Id(autoincrement = true)
    private Long id;

    //primary key
    @Index(unique = true)
    private String wordid;

    //primary key
    @Index(unique = true)
    private String word;

    @NotNull
    private int times;
    
    @Generated(hash = 404212969)
    public WordMain() {
    }
    @Generated(hash = 572830261)
    public WordMain(Long id, String wordid, String word, int times) {
        this.id = id;
        this.wordid = wordid;
        this.word = word;
        this.times = times;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getWordid() {
        return this.wordid;
    }
    public void setWordid(String wordid) {
        this.wordid = wordid;
    }
    public String getWord() {
        return this.word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public int getTimes() {
        return this.times;
    }
    public void setTimes(int times) {
        this.times = times;
    }
}
