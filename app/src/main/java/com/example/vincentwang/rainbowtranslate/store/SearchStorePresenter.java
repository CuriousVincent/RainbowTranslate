package com.example.vincentwang.rainbowtranslate.store;

import com.example.vincentwang.rainbowtranslate.data.WordMain;
import com.example.vincentwang.rainbowtranslate.framework.BasePresenter;
import com.example.vincentwang.rainbowtranslate.translate.TranslateContract;

import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by vincentwang on 2017/8/27.
 */

public class SearchStorePresenter extends BasePresenter implements SearchStoreContract.Presenter {

    SearchStoreContract.View view;

    TranslateContract.Model model;

    public SearchStorePresenter(SearchStoreContract.View view, TranslateContract.Model model) {
        this.view = view;
        this.model = model;
    }


    @Override
    public void searchbuttonclick(int spinnerposition,Calendar startDay,Calendar endDay) {

        model.getWordMain(spinnerposition,startDay,endDay)
                .compose(this.<ArrayList<WordMain>>applySchedulers())
                .subscribeWith(new ResourceSubscriber<ArrayList<WordMain>>() {
                    @Override
                    public void onNext(ArrayList<WordMain> wordMains) {
                        view.showSearchList(wordMains);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
