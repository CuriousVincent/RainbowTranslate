package com.mvp.vincentwang.rainbowtranslate.store

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.mvp.vincentwang.rainbowtranslate.R
import com.mvp.vincentwang.rainbowtranslate.adapter.SearchStoreAdapter
import com.mvp.vincentwang.rainbowtranslate.data.WordMain
import com.mvp.vincentwang.rainbowtranslate.factory.ServiceFactory
import com.mvp.vincentwang.rainbowtranslate.framework.BaseFragment
import com.mvp.vincentwang.rainbowtranslate.util.ToolUtils
import kotlinx.android.synthetic.main.fragment_search_store.*
import java.text.SimpleDateFormat
import java.util.*


class SearchStoreFragment : BaseFragment(), SearchStoreContract.View, View.OnClickListener {

    val presenter :SearchStoreContract.Presenter by lazy {
        SearchStorePresenter(this, ServiceFactory.provideTranslateModel()) as SearchStoreContract.Presenter
    }

    private lateinit var startday: Calendar
    private lateinit var endday: Calendar
    private lateinit var searchStoreAdapter: SearchStoreAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinneradapter = ArrayAdapter.createFromResource(context,
                R.array.store_type_spinner_text,
                android.R.layout.simple_spinner_dropdown_item)
        spinner_store_period.adapter = spinneradapter
        spinner_store_period.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
                when (position) {
                    0 -> {
                        text_startDay.visibility = View.GONE
                        text_endDay.visibility = View.GONE
                        button_confirm!!.isEnabled = true
                    }
                    1 -> {
                        text_startDay.visibility = View.VISIBLE
                        text_endDay.visibility = View.VISIBLE
                        button_confirm.isEnabled = text_startDay.text.toString() != "" && text_endDay.text.toString() != ""
                    }
                    2 -> {
                        text_startDay.visibility = View.GONE
                        text_endDay.visibility = View.GONE
                        button_confirm.isEnabled = true
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

        val mLayoutManager = LinearLayoutManager(context)
        recyclerview_store_word_list.layoutManager = mLayoutManager

        searchStoreAdapter = SearchStoreAdapter({ word ->
            presenter.searchStoreWord(word)
        })
        recyclerview_store_word_list.adapter = searchStoreAdapter

        text_startDay.setOnClickListener(this)
        text_endDay.setOnClickListener(this)
        button_confirm.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.text_startDay -> showDateDailog(DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                startday = ToolUtils.setCalendarByDate(year, month, day)
                val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN)
                text_startDay!!.text = sdf.format(startday.time)
                button_confirm.isEnabled = text_endDay!!.text.toString() != ""
            })
            R.id.text_endDay -> showDateDailog(DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                endday = ToolUtils.setCalendarByDate(year, month, day)
                val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN)
                text_endDay!!.text = sdf.format(endday.time)
                button_confirm.isEnabled = text_startDay!!.text.toString() != ""
            })
            R.id.button_confirm -> {
                when (spinner_store_period.selectedItemPosition) {
                    0 -> presenter.searchToday()
                    1 -> presenter.searchPeriod(startday, endday)
                    2 -> presenter.searchAll()
                }
            }
        }
    }

    fun showDateDailog(onDateSetListener: DatePickerDialog.OnDateSetListener) {
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(context, R.style.datepicker, onDateSetListener, mYear, mMonth, mDay)
                .show()
    }

    override fun showSearchList(wordMains: List<WordMain>) {
        searchStoreAdapter.setWordMain(wordMains)
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }

}// Required empty public constructor
