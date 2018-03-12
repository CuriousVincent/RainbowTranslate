package com.example.vincentwang.rainbowtranslate.translate;

import com.example.vincentwang.rainbowtranslate.data.WordMain;
import com.example.vincentwang.rainbowtranslate.data.WordTotalInfo;

import java.util.Calendar;
import java.util.List;

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
        void getWordTranslateInfo(String word,GetWordCallback callback);

        void getWordMain(int spinnerposition, GetWordMainCallback callback, Calendar startDay, Calendar endDay);


        interface GetWordCallback {

            void getWord(List<WordTotalInfo> wordTotalInfos);

            void onWordnotfound();
        }
        interface GetWordMainCallback{
            void getWordMain(List<WordMain> WordMains);
            void dataError();
        }
    }
}
