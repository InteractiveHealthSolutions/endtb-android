package com.ihsinformatics.endtb.Screens;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.ihsinformatics.endtb.R;
import com.ihsinformatics.endtb.Screens.patient_list_recycler.PatientModel;
import com.ihsinformatics.endtb.Screens.views.ContactView;
import com.ihsinformatics.endtb.database.Entities.SendableData;
import com.ihsinformatics.endtb.databinding.ActivityContactRegistrationBinding;
import com.ihsinformatics.endtb.network.Config;
import com.ihsinformatics.endtb.network.Downloader.PatientsDataDownloader;
import com.ihsinformatics.endtb.network.ParamNames;
import com.ihsinformatics.endtb.utils.ELimsApplication;
import com.ihsinformatics.endtb.utils.Global;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactRegistration extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    ActivityContactRegistrationBinding binding;
    private Spinner spTypeOfEncounter;
    private EditText etMonthOfTx;
    private Spinner spIsNew;
    private EditText etHowManyNew;
    private EditText etHowManyPeople;
    private PatientModel patientModel;
    private List<ContactView> contactViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_registration);
        spTypeOfEncounter = binding.spAnswerTypeOfEnc;
        etMonthOfTx = binding.spAnswerMonthOfTx;
        spIsNew = binding.spAnswerNewContact;
        etHowManyNew = binding.spAnswerHowManyNew;
        etHowManyPeople = binding.spAnswerHowManyPeople;

        patientModel = (PatientModel) getIntent().getSerializableExtra(Global.PATIENT_MODEL);
        etHowManyNew.addTextChangedListener(this);

        binding.btnSave.setOnClickListener(this);
        contactViewList = new ArrayList<>();
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type_of_encounter));
        binding.spAnswerTypeOfEnc.setAdapter(a);
        ArrayAdapter<String> encAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type_of_encounter));
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        binding.llContacts.removeAllViews();
        contactViewList.clear();
        if(editable.length()<1)
            return;

        int numOfPeople = Integer.valueOf(editable.toString());
        if(binding.llContacts.getChildCount() >0) {

        } else {
            addContacts(numOfPeople);
        }
    }

    private void addContacts(int numOfPeople) {
        for(int i=0; i<numOfPeople; i++) {
            ContactView cv = new ContactView(this, i+1, patientModel);
            binding.llContacts.addView(cv);
            contactViewList.add(cv);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnSave:
                for(ContactView cv: contactViewList) {
                    JSONObject ans = cv.getAnswer();
                    SendableData createPatientData = new SendableData(
                            null,
                            ans.toString(),
                            null,
                            Config.PATIENT_RESOURCE,
                            false,
                            SendableData.DATA_TYPE_CREATE_PATIENT,
                            patientModel.getDbId(),
                            patientModel.getDbId(),
                            0,
                            null);
                    ELimsApplication.daoSession.getSendableDataDao().insert(createPatientData);
                    new PatientsDataDownloader().parseAndSaveContact(ans);
                    System.out.println(ans);
                }

                finish();
                break;
        }
    }
}
