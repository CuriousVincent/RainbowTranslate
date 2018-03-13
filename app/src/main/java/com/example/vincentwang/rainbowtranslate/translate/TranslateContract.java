package com.example.vincentwang.rainbowtranslate.translate;

import com.example.vincentwang.rainbowtranslate.data.WordMain;
import com.example.vincentwang.rainbowtranslate.data.WordTotalInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by vincentwang on 2017/8/9.
 */

public interface TranslateContract {
    interface View{
        void showWordTranslateInfo(List<WordTotalInfo> wordTotalInfos);
    }
    interface Presneter{
        void loadWordAllInfo(String word);
    }
    interface Model{

        Flowable<ArrayList<WordTotalInfo>> getWordTranslateInfo(String word);

        Flowable<ArrayList<WordMain>> getWordMain(int spinnerposition, Calendar startDay, Calendar endDay);

    }
}
