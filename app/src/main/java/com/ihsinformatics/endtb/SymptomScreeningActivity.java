package com.ihsinformatics.endtb;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ihsinformatics.endtb.Screens.patient_list_recycler.PatientModel;
import com.ihsinformatics.endtb.database.Entities.Concept;
import com.ihsinformatics.endtb.database.Entities.Encounter;
import com.ihsinformatics.endtb.database.Entities.EncounterDao;
import com.ihsinformatics.endtb.database.Entities.EncounterType;
import com.ihsinformatics.endtb.database.Entities.Obs;
import com.ihsinformatics.endtb.database.Entities.ObsDao;
import com.ihsinformatics.endtb.database.Entities.Patient;
import com.ihsinformatics.endtb.database.Entities.SendableData;
import com.ihsinformatics.endtb.database.Entities.User;
import com.ihsinformatics.endtb.database.data.DbContentHelper;
import com.ihsinformatics.endtb.database.json_helper.OpenMRSJsonCreator;
import com.ihsinformatics.endtb.databinding.ActivitySymptomScreeningBinding;
import com.ihsinformatics.endtb.network.Config;
import com.ihsinformatics.endtb.network.UnifiedBroadcastReceiver;
import com.ihsinformatics.endtb.utils.CredentialsHelper;
import com.ihsinformatics.endtb.utils.ELimsApplication;
import com.ihsinformatics.endtb.utils.Global;
import com.ihsinformatics.endtb.utils.OpenMRSMappings;
import com.ihsinformatics.endtb.utils.views.InputWidget;
import com.ihsinformatics.endtb.utils.views.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SymptomScreeningActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ActivitySymptomScreeningBinding binding;
    private PatientModel patientModel;
    private Patient contact;
    private List<Patient> contacts;
    private List<Obs> obsList;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_symptom_screening);

        patientModel = (PatientModel) getIntent().getSerializableExtra(Global.PATIENT_MODEL);
        contacts = DbContentHelper.getInstance().fetchPatientsByAttribute(OpenMRSMappings.ATTRIBUTE_TYPE_RELATIVE_ID.getName(), patientModel.getId());
        if(contacts.size()<1) {
            Toast.makeText(this, "No contacts found", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        user = DbContentHelper.getInstance().fetchUserByUsername(CredentialsHelper.getUsername());
        List<String> contactNames = new ArrayList<>();
        for(Patient p : contacts) {
            contactNames.add(p.getGivenName()+" "+p.getFamilyName());
        }
        ArrayAdapter<String> contactsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactNames);
        binding.spAnswerContact.setAdapter(contactsAdapter);

        binding.cvQuestionOutcome.setVisibility(View.GONE);
        binding.cvtbKind.setVisibility(View.GONE);
        binding.rgpastAntiTBTx.setOnCheckedChangeListener(this);

        binding.btnSave.setOnClickListener(this);
        binding.spAnswerContact.setOnItemSelectedListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if(radioGroup == binding.rgpastAntiTBTx) {
            String response = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
            if(response.equals("Yes")) {
                binding.cvQuestionOutcome.setVisibility(View.VISIBLE);
                binding.cvtbKind.setVisibility(View.VISIBLE);
            } else {
                binding.cvQuestionOutcome.setVisibility(View.GONE);
                binding.cvtbKind.setVisibility(View.GONE);

            }
        }

    }

    @Override
    public void onClick(View view) {

        DbContentHelper dbContentHelper = DbContentHelper.getInstance();

        // fetch Patient
        Patient patient = contact;

        // Saving encounter
        EncounterDao encounterDao = ELimsApplication.daoSession.getEncounterDao();
        EncounterType encounterType = null;
        encounterType = dbContentHelper.fetchEncounterType(OpenMRSMappings.ENCOUNTER_TYPE_CONTACT_SYMPTOM_SCREENING.getName());
        Date encounterDate = new Date();
        String locationName = "Registration Desk";
        com.ihsinformatics.endtb.database.Entities.Location location = dbContentHelper.fetchLocationByName(locationName);

        Encounter encounter = new Encounter(null,
                UUID.randomUUID().toString(),
                encounterDate,
                patient.getPatientId(),
                encounterType.getId(),
                location.getId(),
                CredentialsHelper.getUserId(),
                false, false);
        Long encounterId = encounterDao.insert(encounter);

        ObsDao obsDao = ELimsApplication.daoSession.getObsDao();
        obsList = new ArrayList<>();
        JSONArray obsJsonArray = new JSONArray();

        Concept facilityConcept = dbContentHelper.fetchConcept(OpenMRSMappings.CONCEPT_FACILITY_ID.getName());
        addObservation(binding.spAnswerFacilityID.getText().toString(), encounterId, facilityConcept, true);

        Concept weightConcept = dbContentHelper.fetchConcept(OpenMRSMappings.CONCEPT_WEIGHT.getName());
        addObservation(binding.spAnswerWeight.getText().toString(), encounterId, weightConcept, true);

        Concept heightConcept = dbContentHelper.fetchConcept(OpenMRSMappings.CONCEPT_HEIGHT.getName());
        addObservation(binding.spAnswerHeight.getText().toString(), encounterId, heightConcept, true);

        Concept pastTbTxConcept = dbContentHelper.fetchConcept(OpenMRSMappings.CONCEPT_CONTACT_PAST_TB_TX.getName());
        addObservation(getRadioGroupText(binding.rgpastAntiTBTx), encounterId, pastTbTxConcept, true);

        Concept coughConcept = dbContentHelper.fetchConcept(OpenMRSMappings.CONCEPT_CONTACT_COUGH.getName());
        addObservation(getRadioGroupText(binding.rgcough), encounterId, coughConcept, true);

        Concept weightLossConcept = dbContentHelper.fetchConcept(OpenMRSMappings.CONCEPT_CONTACT_WEIGHT_LOSS.getName());
        addObservation(getRadioGroupText(binding.rgthrive), encounterId, weightLossConcept, true);

        JSONObject encounterJSON = new OpenMRSJsonCreator().createEncounterJSON(contact, encounter, obsList, user);
        SendableData createEncounterData = new SendableData(null, encounterJSON.toString(), null, Config.ENCOUNTER_DATA_RESOURCE, false, SendableData.DATA_TYPE_CREATE_ENCOUNTER, contact.getPatientId(), encounterId, 0, null);
        ELimsApplication.daoSession.getSendableDataDao().insert(createEncounterData);

        Toast.makeText(this, R.string.form_saved, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(UnifiedBroadcastReceiver.ACTION_ATTEPT_TO_UPLOAD_DATA);
        sendBroadcast(intent);
    }

    private void addObservation(String value, Long encounterId, Concept conceptId, boolean isHiddenField) {
        obsList.add(new Obs(UUID.randomUUID().toString(),
                value,
                null,
                CredentialsHelper.getUserId(),
                encounterId,
                conceptId, false, null));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        contact = contacts.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private String getRadioGroupText(RadioGroup rg) {
        int id = rg.getCheckedRadioButtonId();
        if(id==-1) return "";
        String value = ((RadioButton)findViewById(id)).getText().toString();
        return value;
    }
}
