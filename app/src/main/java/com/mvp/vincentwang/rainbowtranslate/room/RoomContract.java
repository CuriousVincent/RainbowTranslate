package com.mvp.vincentwang.rainbowtranslate.room;

import com.mvp.vincentwang.rainbowtranslate.room.data.WordMain;
import com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Flowable;

public interface RoomContract {
    interface Model {
        Flowable<ArrayList<WordMain>> wordMainToday();

        Flowable<ArrayList<WordMain>> wordMainAll();

        Flowable<List<WordTotalInfo>> getWordTranslateInfo(String word);

        Flowable<ArrayList<WordMain>> getWordMainPeriod(Calendar startDay, Calendar endDay);

        void setSearchWord(String word);

        Flowable<ArrayList<WordTotalInfo>> getStoreWordTranslateInfo();
    }
}
