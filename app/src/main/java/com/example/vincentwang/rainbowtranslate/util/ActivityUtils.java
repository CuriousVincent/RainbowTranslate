package com.example.vincentwang.rainbowtranslate.util;

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

}
