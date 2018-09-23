package com.mvp.vincentwang.rainbowtranslate.framework

import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.mvp.vincentwang.rainbowtranslate.R
import com.mvp.vincentwang.rainbowtranslate.store.SearchStoreFragment
import com.mvp.vincentwang.rainbowtranslate.translate.TranslateFragment
import com.mvp.vincentwang.rainbowtranslate.util.ActivityUtils
import kotlinx.android.synthetic.main.activity_translate.*


class TranslateActivity : BaseActivity() {

    lateinit var mFirebaseAnalytics: FirebaseAnalytics
    private  val translateFragment: TranslateFragment by lazy {
        TranslateFragment()
    }
    private val searchStoreFragment: SearchStoreFragment by lazy {
        SearchStoreFragment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        setContentView(R.layout.activity_translate)

        showFragment(TranslateFragment())

        button_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.search_button -> gotoTranslateFragment()
                R.id.store_button -> gotoSearchStoreFragment()
                R.id.setting_button -> {
                }
            }
            true
        }
//        Log.e("UUID",getUniquePsuedoID())
    }


    private fun showFragment(fragment: Fragment) {
        ActivityUtils.replaceFragmentToActivity(supportFragmentManager, fragment, R.id.fragment_container)
    }

    override fun gotoTranslateFragment() {
        showFragment(translateFragment)
    }

    override fun gotoSearchStoreFragment() {
        showFragment(searchStoreFragment)
    }

}
