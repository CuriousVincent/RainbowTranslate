package com.example.vincentwang.rainbowtranslate.data;

import java.util.List;

/**
 * Created by vincentwang on 2017/8/18.
 */

public class WordTotalInfo {

    WordMain word;
    WordInfo wordInfo;
    List<WordExample> wordExamples;

    public WordMain getWord() {
        return word;
    }

    public void setWord(WordMain word) {
        this.word = word;
    }

    public WordInfo getWordInfo() {
        return wordInfo;
    }

    public void setWordInfo(WordInfo wordInfo) {
        this.wordInfo = wordInfo;
    }

    public List<WordExample> getWordExamples() {
        return wordExamples;
    }

    public void setWordExamples(List<WordExample> wordExamples) {
        this.wordExamples = wordExamples;
    }
}
