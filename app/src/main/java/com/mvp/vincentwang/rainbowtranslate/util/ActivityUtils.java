package com.mvp.vincentwang.rainbowtranslate.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static kotlin.jvm.internal.Intrinsics.checkNotNull;

/**
 * Created by vincentwang on 2018/2/23.
 */

public class ActivityUtils {

    public static void replaceFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId){
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId,fragment);
        transaction.commit();
    }
//    public static String getUniquePsuedoID() {
//        String serial = null;
//        String m_szDevIDShort = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10; //13 位
//        try {
//            serial = android.os.Build.class.getField("SERIAL").get(null).toString(); //API>=9 使用serial号
//            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
//        } catch (Exception exception) {
//            //serial需要一个初始化
//            serial = "serial"; // 随便一个初始化
//        }
//        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
//
//    }
}
