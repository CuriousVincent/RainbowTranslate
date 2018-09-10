package com.mvp.vincentwang.rainbowtranslate.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mvp.vincentwang.rainbowtranslate.R
import com.mvp.vincentwang.rainbowtranslate.room.data.WordMain
import kotlinx.android.synthetic.main.item_search_store_result.view.*

/**
 * Created by vincentwang on 2017/8/23.
 */

class SearchStoreAdapter(val itemClickListener:(String)->Unit) : RecyclerView.Adapter<SearchStoreAdapter.ViewHolder>() {

    private var wordMains: List<WordMain>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_store_result, parent, false)
        return ViewHolder(view,itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (wordMains != null) {
            holder.wordText.text=wordMains!![position].word
        }
    }

    override fun getItemCount(): Int {
        return if (wordMains != null) {
            wordMains!!.size
        } else {
            0
        }
    }

    fun setWordMain(wordMains: List<WordMain>?){
        this.wordMains = wordMains
        notifyDataSetChanged()
    }


    class ViewHolder(view: View,val itemClickListener:(String)->Unit) : RecyclerView.ViewHolder(view) {
        val wordText = view.text_wordList_word
        init {
            view.card_wordList_view.setOnClickListener{
                itemClickListener(wordText.text.toString())
            }
        }

    }
}
