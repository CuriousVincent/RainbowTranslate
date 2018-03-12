package com.example.vincentwang.rainbowtranslate.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * Created by vincentwang on 2017/8/4.
 */
@Entity
public class SearchTime {
    @Id(autoincrement = true)
    private Long id;

    //primary key
    @Index(unique = true)
    private String searchtimeid;

    @NotNull
    private String wordid;
    @NotNull
    private Date searchtime;
    @Generated(hash = 14955511)
    public SearchTime(Long id, String searchtimeid, @NotNull String wordid,
            @NotNull Date searchtime) {
        this.id = id;
        this.searchtimeid = searchtimeid;
        this.wordid = wordid;
        this.searchtime = searchtime;
    }
    @Generated(hash = 136036477)
    public SearchTime() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSearchtimeid() {
        return this.searchtimeid;
    }
    public void setSearchtimeid(String searchtimeid) {
        this.searchtimeid = searchtimeid;
    }
    public String getWordid() {
        return this.wordid;
    }
    public void setWordid(String wordid) {
        this.wordid = wordid;
    }
    public Date getSearchtime() {
        return this.searchtime;
    }
    public void setSearchtime(Date searchtime) {
        this.searchtime = searchtime;
    }
    
}
