package com.example.vincentwang.rainbowtranslate.util;

import com.example.vincentwang.rainbowtranslate.data.WordMain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by vincentwang on 2017/8/30.
 */

public class ToolUtils {

    public static Calendar setCalendarByDate(int year, int month,int day){

        Calendar mCalendar = Calendar.getInstance();

        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
        mCalendar.set(Calendar.MILLISECOND, 0);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.HOUR, 0);
        return mCalendar;
    }



    public static void rmRepeadtedElementByOrder(List<WordMain> list) {
        LinkedHashSet<WordMain> loadsSet = new LinkedHashSet<WordMain>(list);
        ArrayList<WordMain> loadsList = new ArrayList<WordMain>(loadsSet);
        list.clear();
        list.addAll(loadsList);
    }

}
