package com.example.vincentwang.rainbowtranslate.store

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.vincentwang.rainbowtranslate.R
import com.example.vincentwang.rainbowtranslate.adapter.SearchStoreAdapter
import com.example.vincentwang.rainbowtranslate.data.WordMain
import com.example.vincentwang.rainbowtranslate.framework.BaseFragment
import com.example.vincentwang.rainbowtranslate.translate.TranslateModel
import com.example.vincentwang.rainbowtranslate.util.ToolUtils
import kotlinx.android.synthetic.main.fragment_search_store.*
import java.text.SimpleDateFormat
import java.util.*


class SearchStoreFragment : BaseFragment(), SearchStoreContract.View, View.OnClickListener {

    private var presenter: SearchStoreContract.Presenter? = null

    private var startday: Calendar? = null
    private var endday: Calendar? = null
    private lateinit var searchStoreAdapter: SearchStoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = SearchStorePresenter(this, TranslateModel())
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_search_store, container, false)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
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
                        if (text_startDay.text.toString() != resources.getString(R.string.starting_day) && text_endDay.text.toString() != resources.getString(R.string.ending_day)) {
                            button_confirm.isEnabled = true
                        } else {
                            button_confirm.isEnabled = false
                        }
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

        var mLayoutManager = LinearLayoutManager(context)
        recyclerview_store_word_list.layoutManager = mLayoutManager

        searchStoreAdapter = SearchStoreAdapter({ word ->
            communicator.gotoTranslateFragment(word)
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
                val myFormat = "yyyy/MM/dd" //In which you need put here
                val sdf = SimpleDateFormat(myFormat, Locale.TAIWAN)
                text_startDay!!.text = sdf.format(startday!!.time)
                text_endDay!!.setText(R.string.ending_day)
                button_confirm.isEnabled = false
            })
            R.id.text_endDay -> showDateDailog(DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                endday = ToolUtils.setCalendarByDate(year, month, day)
                val myFormat = "yyyy/MM/dd" //In which you need put here
                val sdf = SimpleDateFormat(myFormat, Locale.TAIWAN)
                text_endDay!!.text = sdf.format(endday!!.time)
                if (text_startDay!!.text.toString() != "") {
                    button_confirm.isEnabled = true
                }
            })
            R.id.button_confirm -> presenter!!.searchbuttonclick(spinner_store_period.selectedItemPosition, startday, endday)
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


}// Required empty public constructor
