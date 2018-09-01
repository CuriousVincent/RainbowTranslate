package com.mvp.vincentwang.rainbowtranslate.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by vincentwang on 2017/7/27.
 */
@Entity
public class WordInfo {
    @Id(autoincrement = true)
    private Long id;

    //primary key
    @Index(unique = true)
    private String wordinfoid;

    @NotNull
    private String wordid;

    private String chinesemean;

    private String englishmean;

    private String type;

    @Generated(hash = 1577906928)
    public WordInfo(Long id, String wordinfoid, @NotNull String wordid,
            String chinesemean, String englishmean, String type) {
        this.id = id;
        this.wordinfoid = wordinfoid;
        this.wordid = wordid;
        this.chinesemean = chinesemean;
        this.englishmean = englishmean;
        this.type = type;
    }

    @Generated(hash = 112235395)
    public WordInfo() {
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

    public String getWordid() {
        return this.wordid;
    }

    public void setWordid(String wordid) {
        this.wordid = wordid;
    }

    public String getChinesemean() {
        return this.chinesemean;
    }

    public void setChinesemean(String chinesemean) {
        this.chinesemean = chinesemean;
    }

    public String getEnglishmean() {
        return this.englishmean;
    }

    public void setEnglishmean(String englishmean) {
        this.englishmean = englishmean;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }




}
