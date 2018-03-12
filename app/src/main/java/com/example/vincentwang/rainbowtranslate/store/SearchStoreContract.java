package com.example.vincentwang.rainbowtranslate.store;

import com.example.vincentwang.rainbowtranslate.data.WordMain;

import java.util.Calendar;
import java.util.List;

/**
 * Created by vincentwang on 2017/8/27.
 */

public interface SearchStoreContract {
    interface View{
        void showSearchList(List<WordMain> wordMains);
    }
    interface Presenter{
        void searchbuttonclick(int spinnerposition,Calendar startDay,Calendar endDay);
    }

}
