package com.mvp.vincentwang.rainbowtranslate.room.data;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Relation;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class WordTotalInfo {
    @Embedded
    WordInfo wordInfo;
    @Relation(parentColumn = "wordinfoid",
            entityColumn = "wordinfoid", entity = WordExample.class)
    List<WordExample> wordExamples;

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

    @Ignore
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}

