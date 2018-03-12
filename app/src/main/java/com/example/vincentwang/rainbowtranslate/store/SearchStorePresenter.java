package com.example.vincentwang.rainbowtranslate.store;

import com.example.vincentwang.rainbowtranslate.data.WordMain;
import com.example.vincentwang.rainbowtranslate.translate.TranslateContract;

import java.util.Calendar;
import java.util.List;

/**
 * Created by vincentwang on 2017/8/27.
 */

public class SearchStorePresenter implements SearchStoreContract.Presenter {

    SearchStoreContract.View view;

    TranslateContract.Model model;

    public SearchStorePresenter(SearchStoreContract.View view, TranslateContract.Model model) {
        this.view = view;
        this.model = model;
    }


    @Override
    public void searchbuttonclick(int spinnerposition,Calendar startDay,Calendar endDay) {

        model.getWordMain(spinnerposition, new TranslateContract.Model.GetWordMainCallback() {
            @Override
            public void getWordMain(List<WordMain> WordMains) {
                view.showSearchList(WordMains);
            }

            @Override
            public void dataError() {

            }
        },startDay,endDay);
    }
}
