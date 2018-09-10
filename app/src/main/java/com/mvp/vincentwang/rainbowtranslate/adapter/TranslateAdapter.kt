package com.mvp.vincentwang.rainbowtranslate.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mvp.vincentwang.rainbowtranslate.R
import com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo
import kotlinx.android.synthetic.main.item_translate_result.view.*

/**
 * Created by vincentwang on 2017/8/22.
 */

class TranslateAdapter : RecyclerView.Adapter<TranslateAdapter.ViewHolder>() {

    internal var wordTotalInfos: List<WordTotalInfo>? = null
    internal var word :String?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_translate_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (wordTotalInfos != null) {
            val wordTotalInfo = wordTotalInfos!![position]

            holder.textWord!!.text = word
            holder.textType!!.text = wordTotalInfo.wordInfo.type
            holder.textWordChinesemean!!.text = wordTotalInfo.wordInfo.chinesemean
            holder.textWordEnglishmean!!.text = wordTotalInfo.wordInfo.englishmean
            val stringBuffer = StringBuffer()
            val wordExamples = wordTotalInfo.wordExamples
            for (z in wordExamples.indices) {
                stringBuffer.append(wordExamples[z].example + "\n")
                stringBuffer.append(wordExamples[z].exampletranslate + "\n")
            }
            holder.textWordExample!!.text = stringBuffer.toString()
        }
    }

    override fun getItemCount(): Int {
        return if (wordTotalInfos != null) {
            wordTotalInfos!!.size
        } else {
            0
        }
    }

    fun setWordTotalInfo(word:String,wordTotalInfos: List<WordTotalInfo>) {
        this.word=word
        this.wordTotalInfos = wordTotalInfos
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var textWord =view.text_word
        var textType = view.text_type
        var textWordChinesemean = view.text_word_chinesemean
        var textWordEnglishmean = view.text_word_englishmean
        var textWordExample = view.text_word_example

    }
}
