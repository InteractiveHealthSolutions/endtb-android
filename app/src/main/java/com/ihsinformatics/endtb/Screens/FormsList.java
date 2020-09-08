package com.ihsinformatics.endtb.Screens;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ihsinformatics.endtb.R;
import com.ihsinformatics.endtb.Screens.patient_list_recycler.PatientModel;
import com.ihsinformatics.endtb.SymptomScreeningActivity;
import com.ihsinformatics.endtb.database.json_helper.MonthlyReview;
import com.ihsinformatics.endtb.databinding.ActivityFormsListBinding;
import com.ihsinformatics.endtb.databinding.ActivityMonthlyReviewBinding;
import com.ihsinformatics.endtb.utils.Global;
import com.ihsinformatics.endtb.utils.OpenMRSMappings;

public class FormsList extends AppCompatActivity {

    ActivityFormsListBinding binding;
    private PatientModel patientModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forms_list);
        patientModel = (PatientModel) getIntent().getSerializableExtra(Global.PATIENT_MODEL);
        binding.setDtmFormName(OpenMRSMappings.ENCOUNTER_TYPE_DTM.getName());
        binding.setAdverseEventFormName(OpenMRSMappings.ENCOUNTER_ADVERSE_EVENT.getName());
        binding.setContactRegistryFormName(OpenMRSMappings.ENCOUNTER_TYPE_CONTACT_REGISTRY.getName());
        binding.setContactSymptomFormName(OpenMRSMappings.ENCOUNTER_TYPE_CONTACT_SYMPTOM_SCREENING.getName());
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onClick(View v) {
        Intent intent;
        Class aClass = null;
        int id = v.getId();
        switch (id) {
            case R.id.cvDTM:
            aClass = DailyTreatmentMonitoring.class;
            break;
            case R.id.cvAE:
                aClass = AdverseEvent.class;
                break;
            case R.id.cvCR:
                aClass = ContactRegistration.class;
                break;
            case R.id.cvSS:
                aClass = SymptomScreeningActivity.class;
                break;
        }

        if (aClass != null) {
            intent = new Intent(this, aClass);
            intent.putExtra(Global.PATIENT_MODEL, patientModel);
            startActivity(intent);
            finish();
        }
    }
}
