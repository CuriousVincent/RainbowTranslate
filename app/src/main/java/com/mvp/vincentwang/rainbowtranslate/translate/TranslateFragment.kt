package com.mvp.vincentwang.rainbowtranslate.translate


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.mvp.vincentwang.rainbowtranslate.R
import com.mvp.vincentwang.rainbowtranslate.adapter.TranslateAdapter
import com.mvp.vincentwang.rainbowtranslate.factory.ServiceFactory
import com.mvp.vincentwang.rainbowtranslate.framework.BaseFragment
import com.mvp.vincentwang.rainbowtranslate.room.data.WordTotalInfo
import kotlinx.android.synthetic.main.fragment_translate.*


/**
 * A simple [Fragment] subclass.
 * Use the [TranslateFragment.newInstance] com.mvp.vincentwang.rainbowtranslate.factory method to
 * create an instance of this fragment.
 */
class TranslateFragment : BaseFragment(), FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnHomeActionClickListener, FloatingSearchView.OnSearchListener, TranslateContract.View {

    private lateinit var translateAdapter: TranslateAdapter

    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    private lateinit var wordTotalInfos: List<WordTotalInfo>

    private var word: String? = null

    val presenter: TranslateContract.Presneter by lazy {
        TranslatePresenter(this, ServiceFactory.provideTranslateModel()!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_translate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_view.setOnHomeActionClickListener(this)
        search_view.setOnQueryChangeListener(this)
        search_view.setOnSearchListener(this)

        mLayoutManager = LinearLayoutManager(context)
        recyclerview_translate.layoutManager = mLayoutManager

        translateAdapter = TranslateAdapter()

        recyclerview_translate.adapter = translateAdapter

//        presenter.loadWordAllInfoByStore()
    }


    override fun onSearchTextChanged(oldQuery: String, newQuery: String) {
        word = newQuery
    }

    override fun onHomeClicked() {
        presenter.loadWordAllInfo(word)
    }

    override fun onSuggestionClicked(searchSuggestion: SearchSuggestion) {

    }

    override fun onSearchAction(currentQuery: String) {
        presenter.loadWordAllInfo(word)
    }

    override fun showWordTranslateInfo(word:String,wordTotalInfos: List<WordTotalInfo>) {
        this.wordTotalInfos = wordTotalInfos
        this.word =word
        translateAdapter.setWordTotalInfo(word,wordTotalInfos)
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }
}// Required empty public constructor
