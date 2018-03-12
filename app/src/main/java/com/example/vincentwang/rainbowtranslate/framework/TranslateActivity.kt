package com.example.vincentwang.rainbowtranslate.framework

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.vincentwang.rainbowtranslate.R
import com.example.vincentwang.rainbowtranslate.store.SearchStoreFragment
import com.example.vincentwang.rainbowtranslate.translate.TranslateFragment
import com.example.vincentwang.rainbowtranslate.util.ActivityUtils
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_translate.*



class TranslateActivity : BaseActivity(){

    lateinit var mFirebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        setContentView(R.layout.activity_translate)

        showFragment(TranslateFragment())

        button_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.search_button -> gotoTranslateFragment("")
                R.id.store_button -> showFragment(SearchStoreFragment())
                R.id.setting_button -> {
                }
            }
            true
        }
    }

    private fun showFragment(fragment: Fragment) {
        ActivityUtils.replaceFragmentToActivity(supportFragmentManager, fragment, R.id.fragment_container)
    }

    override fun gotoTranslateFragment(word: String) {
        showFragment(TranslateFragment.newInstance(word))
    }

    override fun gotoSearchStoreFragment() {
        showFragment(SearchStoreFragment())
    }

}
