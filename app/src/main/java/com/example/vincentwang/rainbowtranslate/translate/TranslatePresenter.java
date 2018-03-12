package com.example.vincentwang.rainbowtranslate.translate;

import com.example.vincentwang.rainbowtranslate.data.WordTotalInfo;

import java.util.List;

/**
 * Created by vincentwang on 2017/8/18.
 */

public class TranslatePresenter implements TranslateContract.Presneter {

    TranslateContract.View view;
    TranslateContract.Model model;


    public TranslatePresenter(TranslateContract.View view, TranslateContract.Model model){
        this.view = view;
        this.model = model;
    }

    @Override
    public void loadWordAllInfo(final String word) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                model.getWordTranslateInfo(word, new TranslateContract.Model.GetWordCallback() {
                    @Override
                    public void getWord(List<WordTotalInfo> wordTotalInfos) {
                        view.showWordTranslateInfo(wordTotalInfos);
                    }

                    @Override
                    public void onWordnotfound() {

                    }
                });
            }
        }).start();
    }
}
