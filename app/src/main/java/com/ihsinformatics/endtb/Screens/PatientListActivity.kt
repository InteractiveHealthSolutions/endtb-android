package com.ihsinformatics.endtb.Screens

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.LinearLayout

import com.ihsinformatics.endtb.R
import com.ihsinformatics.endtb.Screens.patient_list_recycler.PatientModel
import com.ihsinformatics.endtb.Screens.patient_list_recycler.PatientsAdapter
import com.ihsinformatics.endtb.database.data.DataProvider
import com.ihsinformatics.endtb.database.data.DbContentHelper
import com.ihsinformatics.endtb.utils.views.InputWidget

class PatientListActivity : AppCompatActivity(), View.OnClickListener {

    private var rvMain: RecyclerView? = null
    private var llMain: LinearLayout? = null
    private var patientName: InputWidget? = null
    private var division: InputWidget? = null
    private var district: InputWidget? = null
    private var upazila: InputWidget? = null
    private var union: InputWidget? = null
    private var age: InputWidget? = null
    private var gender: InputWidget? = null
    private var occupation: InputWidget? = null
    // private InputWidget contactNo;
    private var btnSave: Button? = null
    private var mAdapter: PatientsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_search)
        llMain = findViewById<View>(R.id.activity_patient_registeration) as LinearLayout
        rvMain = findViewById<View>(R.id.rvMain) as RecyclerView
        val dataProvider = DataProvider.getInstance()
        patientName = InputWidget(this, R.id.rlQuestion_name, R.id.tvQuestion_name, R.id.tvMessage_name, R.id.etAnswer_name, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT)
        occupation = InputWidget(this, R.id.rlQuestion_occupation, R.id.tvQuestion_occupation, R.id.tvMessage_occupation, R.id.spAnswer_occupation, InputWidget.INPUT_WIDGET_TYPE.SPINNER)
        occupation!!.setSpinnerValues(dataProvider.occupations)
        division = InputWidget(this, R.id.rlQuestion_division, R.id.tvQuestion_division, R.id.tvMessage_division, R.id.spAnswer_division, InputWidget.INPUT_WIDGET_TYPE.SPINNER)
        division!!.setSpinnerValues(dataProvider.genders)
        district = InputWidget(this, R.id.rlQuestion_district, R.id.tvQuestion_district, R.id.tvMessage_district, R.id.spAnswer_district, InputWidget.INPUT_WIDGET_TYPE.SPINNER)
        district!!.setSpinnerValues(dataProvider.genders)
        upazila = InputWidget(this, R.id.rlQuestion_upazila, R.id.tvQuestion_upazila, R.id.tvMessage_upazila, R.id.spAnswer_upazila, InputWidget.INPUT_WIDGET_TYPE.SPINNER)
        upazila!!.setSpinnerValues(dataProvider.genders)
        union = InputWidget(this, R.id.rlQuestion_union, R.id.tvQuestion_union, R.id.tvMessage_union, R.id.spAnswer_union, InputWidget.INPUT_WIDGET_TYPE.SPINNER)
        union!!.setSpinnerValues(dataProvider.genders)
        age = InputWidget(this, R.id.rlQuestion_age, R.id.tvQuestion_age, R.id.tvMessage_age, R.id.etAnswer_age, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT)
        gender = InputWidget(this, R.id.rlQuestion_gender, R.id.tvQuestion_gender, R.id.tvMessage_gender, R.id.spAnswer_gender, InputWidget.INPUT_WIDGET_TYPE.SPINNER)
        gender!!.setSpinnerValues(dataProvider.genders)
        // contactNo = new InputWidget(this, R.id.rlQuestion_contact, R.id.tvQuestion_contact, R.id.tvMessage_contact, R.id.etAnswer_contact, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);

        btnSave = findViewById<View>(R.id.btnSave) as Button
        btnSave!!.setOnClickListener(this)
        llMain!!.visibility = View.GONE
        rvMain!!.visibility = View.VISIBLE
        val patientModelList = PatientModel.castFromPatients(DbContentHelper.getInstance().fetchAllPatients())
        initPatientsList(patientModelList)
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.btnSave -> {
            }
        }
    }

    private fun initPatientsList(searchedPatients: List<PatientModel>) {
        mAdapter = PatientsAdapter(this, searchedPatients)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        rvMain!!.layoutManager = mLayoutManager
        rvMain!!.itemAnimator = DefaultItemAnimator()
        rvMain!!.adapter = mAdapter
        mAdapter!!.notifyDataSetChanged() // no need though
    }

    companion object {

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
