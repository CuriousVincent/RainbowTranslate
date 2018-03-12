package com.example.vincentwang.rainbowtranslate.framework

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import com.example.vincentwang.rainbowtranslate.R

/**
 * Created by vincentwang on 2017/8/17.
 */

abstract class BaseActivity : AppCompatActivity(),BaseContract.View {

    private lateinit var progressBar: ProgressBar

    fun showProgressBar(isShow:Boolean){
        progressBar = findViewById(R.id.progressbar)
        if(isShow){
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            progressBar.visibility = View.VISIBLE
        }else
        {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            progressBar.visibility = View.GONE
        }
    }

    override fun showProgressBar() {
       showProgressBar(true)
    }

    override fun hideProgressBar() {
        showProgressBar(false)
    }
}
