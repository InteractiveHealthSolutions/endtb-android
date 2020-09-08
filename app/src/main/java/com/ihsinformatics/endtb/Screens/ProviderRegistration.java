package com.ihsinformatics.endtb.Screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.ihsinformatics.endtb.R;
import com.ihsinformatics.endtb.database.Entities.Address;
import com.ihsinformatics.endtb.database.Entities.AddressDao;
import com.ihsinformatics.endtb.database.Entities.EncounterDao;
import com.ihsinformatics.endtb.database.Entities.EncounterType;
import com.ihsinformatics.endtb.database.Entities.Location;
import com.ihsinformatics.endtb.database.Entities.LocationAttribute;
import com.ihsinformatics.endtb.database.Entities.PatientAttributes;
import com.ihsinformatics.endtb.database.Entities.PersonAttributeType;
import com.ihsinformatics.endtb.database.Entities.Role;
import com.ihsinformatics.endtb.database.Entities.SendableData;
import com.ihsinformatics.endtb.database.Entities.User;
import com.ihsinformatics.endtb.database.data.DataProvider;
import com.ihsinformatics.endtb.database.data.DbContentHelper;
import com.ihsinformatics.endtb.database.json_helper.OpenMRSJsonCreator;
import com.ihsinformatics.endtb.network.Config;
import com.ihsinformatics.endtb.utils.preferences.AppPreferences;
import com.ihsinformatics.endtb.utils.CredentialsHelper;
import com.ihsinformatics.endtb.utils.ELimsApplication;
import com.ihsinformatics.endtb.utils.Global;
import com.ihsinformatics.endtb.utils.views.InputWidget;
import com.ihsinformatics.endtb.utils.Logger;
import com.ihsinformatics.endtb.utils.OpenMRSMappings;
import com.ihsinformatics.endtb.utils.views.Toast;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProviderRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener, TextWatcher {

    private InputWidget date;
    private InputWidget role;
    private InputWidget facility;
    private InputWidget patientName;
    private InputWidget nic;
    private InputWidget occupation;
    private InputWidget occupationOther;
    private InputWidget fatherName;
    private InputWidget city;
    private InputWidget town;
    private InputWidget uc;
    private InputWidget address;
    private InputWidget landMark;
    private InputWidget age;
    private InputWidget dob;
    private InputWidget gender;
    private InputWidget contactNo;
    private InputWidget contactNo2;
    private InputWidget everHW;
    private InputWidget HealthWorker;
    private InputWidget canInject;
    private InputWidget isTrainedAtFacility;
    private InputWidget trainingFacility;
    private InputWidget trainedBy;
    private InputWidget languagesSpoken;
    private InputWidget otherLanguage;
    private InputWidget username;
    private InputWidget password;
    private Button btnSave;
    private TextView tvPatientId;
    private User user;
    private DbContentHelper dbContentHelper;
    private boolean dobNotUpdateCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_registration);
        initControls();
    }

    private void initControls() {
        DataProvider dataProvider = DataProvider.getInstance();
        dbContentHelper = DbContentHelper.getInstance();
        tvPatientId = (TextView) findViewById(R.id.tvPatientId);
        String toDate = Global.MYSQL_DATE_FORMAT.format(new Date());
        dob = new InputWidget(this, R.id.rlQuestion_dob, R.id.tvQuestion_dob, R.id.tvMessage_dob, R.id.etAnswer_dob, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        dob.setValue(toDate);
        date = new InputWidget(this, R.id.rlQuestion_date, R.id.tvQuestion_date, R.id.tvMessage_date, R.id.etAnswer_date, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        date.setValue(toDate);
        role = new InputWidget(this, R.id.rlQuestion_lab, R.id.tvQuestion_lab, R.id.tvMessage_lab, R.id.spAnswer_lab, InputWidget.INPUT_WIDGET_TYPE.SPINNER);
        role.setSpinnerValues(getResources().getStringArray(R.array.roles));
        facility = new InputWidget(this, R.id.rlQuestion, R.id.tvQuestion, R.id.tvMessage, R.id.spAnswer, InputWidget.INPUT_WIDGET_TYPE.SPINNER);
        facility.setSpinnerValues(DbContentHelper.getInstance().fetchLocationsByTag(OpenMRSMappings.LOCATION_TAG_FACILITY.getName(), null));
        patientName = new InputWidget(this, R.id.rlQuestion_name, R.id.tvQuestion_name, R.id.tvMessage_name, R.id.etAnswer_name, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        nic = new InputWidget(this, R.id.rlQuestion_nic, R.id.tvQuestion_nic, R.id.tvMessage_nic, R.id.etAnswer_nic, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        occupation = new InputWidget(this, R.id.rlQuestion_occupation, R.id.tvQuestion_occupation, R.id.tvMessage_occupation, R.id.spAnswer_occupation, InputWidget.INPUT_WIDGET_TYPE.SPINNER);
        occupationOther = new InputWidget(this, R.id.rlQuestion_occupationOther, R.id.tvQuestion_occupationOther, R.id.tvMessage_occupationOther, R.id.etAnswer_occupationOther, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        occupation.setSpinnerValues(getResources().getStringArray(R.array.occupations));
        fatherName = new InputWidget(this, R.id.rlQuestion_fname, R.id.tvQuestion_fname, R.id.tvMessage_fname, R.id.etAnswer_fname, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        age = new InputWidget(this, R.id.rlQuestion_age, R.id.tvQuestion_age, R.id.tvMessage_age, R.id.etAnswer_age, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        gender = new InputWidget(this, R.id.rlQuestion_gender, R.id.tvQuestion_gender, R.id.tvMessage_gender, R.id.spAnswer_gender, InputWidget.INPUT_WIDGET_TYPE.SPINNER);
        gender.setSpinnerValues(dataProvider.getGenders());
        contactNo = new InputWidget(this, R.id.rlQuestion_contact, R.id.tvQuestion_contact, R.id.tvMessage_contact, R.id.etAnswer_contact, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        contactNo2 = new InputWidget(this, R.id.rlQuestion_contact2, R.id.tvQuestion_contact2, R.id.tvMessage_contact2, R.id.etAnswer_contact2, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        city = new InputWidget(this, R.id.rlQuestion_city, R.id.tvQuestion_city, R.id.tvMessage_city, R.id.spAnswer_city, InputWidget.INPUT_WIDGET_TYPE.SPINNER);
        town = new InputWidget(this, R.id.rlQuestion_town, R.id.tvQuestion_town, R.id.tvMessage_town, R.id.spAnswer_town, InputWidget.INPUT_WIDGET_TYPE.SPINNER);
        uc = new InputWidget(this, R.id.rlQuestion_upazila, R.id.tvQuestion_upazila, R.id.tvMessage_upazila, R.id.spAnswer_upazila, InputWidget.INPUT_WIDGET_TYPE.SPINNER);
        address = new InputWidget(this, R.id.rlQuestion_paddress, R.id.tvQuestion_paddress, R.id.tvMessage_paddress, R.id.etAnswer_paddress, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        landMark = new InputWidget(this, R.id.rlQuestion_landMark, R.id.tvQuestion_landMark, R.id.tvMessage_landMark, R.id.etAnswer_landMark, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        everHW = new InputWidget(this, R.id.rlQuestion_everHealthWorker, R.id.tvQuestion_everHealthWorker, R.id.tvMessage_everHealthWorker, R.id.rgAnswer_everHealthWorker, InputWidget.INPUT_WIDGET_TYPE.RADIO_GROUP);
        HealthWorker = new InputWidget(this, R.id.rlQuestion_isHealthWorker, R.id.tvQuestion_isHealthWorker, R.id.tvMessage_isHealthWorker, R.id.rgAnswer_isHealthWorker, InputWidget.INPUT_WIDGET_TYPE.RADIO_GROUP);
        canInject = new InputWidget(this, R.id.rlQuestion_canAdministerInjection, R.id.tvQuestion_canAdministerInjection, R.id.tvMessage_canAdministerInjection, R.id.rgAnswer_canAdministerInjection, InputWidget.INPUT_WIDGET_TYPE.RADIO_GROUP);
        isTrainedAtFacility = new InputWidget(this, R.id.rlQuestion_trained_at_facility, R.id.tvQuestion_trained_at_facility, R.id.tvMessage_trained_at_facility, R.id.rgAnswer_trained_at_facility, InputWidget.INPUT_WIDGET_TYPE.RADIO_GROUP);
        trainingFacility = new InputWidget(this, R.id.rlQuestion_whichFacility, R.id.tvQuestion_whichFacility, R.id.tvMessage_whichFacility, R.id.spAnswer_whichFacility, InputWidget.INPUT_WIDGET_TYPE.SPINNER);
        trainingFacility.setSpinnerValues(DbContentHelper.getInstance().fetchLocationsByTag(OpenMRSMappings.LOCATION_TAG_FACILITY.getName(), null));
        trainedBy = new InputWidget(this, R.id.rlQuestion_trainedBy, R.id.tvQuestion_trainedBy, R.id.tvMessage_trainedBy, R.id.etAnswer_trainedBy, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        languagesSpoken = new InputWidget(this, R.id.rlQuestion_languages, R.id.tvQuestion_languages, R.id.tvMessage_languages, R.id.spAnswer_languages, InputWidget.INPUT_WIDGET_TYPE.SPINNER);
        languagesSpoken.setSpinnerValues(getResources().getStringArray(R.array.languages));
        otherLanguage = new InputWidget(this, R.id.rlQuestion_otherLanguage, R.id.tvQuestion_otherLanguage, R.id.tvMessage_otherLanguage, R.id.etAnswer_otherLanguage, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);

        username = new InputWidget(this, R.id.rlQuestion_username, R.id.tvQuestion_username, R.id.tvMessage_username, R.id.etAnswer_username, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        password = new InputWidget(this, R.id.rlQuestion_password, R.id.tvQuestion_password, R.id.tvMessage_password, R.id.etAnswer_password, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);

        EditText etFatherName = (EditText) fatherName.getClickableView();
        EditText etPatientNAme = (EditText) patientName.getClickableView();

        etFatherName.addTextChangedListener(this);
        etPatientNAme.addTextChangedListener(this);
        List<String> pStatuses = new ArrayList<>();

        try {
            city.setSpinnerValues(dbContentHelper.fetchLocationsByTag(OpenMRSMappings.LOCATION_TAG_CITY.getName(), null));
        } catch (IllegalStateException e) {
            Logger.log(e);
            DbContentHelper.getInstance().reCreateDatabase();
            AppPreferences.getInstance(this).addOrUpdateBooleanPreference(AppPreferences.KEY.IS_FIRST_RUN, true);
            startActivity(new Intent(this, Login.class));
            finish();
            Toast.makeText(this, getResources().getString(R.string.illegal_state), Toast.LENGTH_LONG).show();
        }

        ((Spinner)role.getClickableView()).setOnItemSelectedListener(this);
        ((Spinner)city.getClickableView()).setOnItemSelectedListener(this);
        ((Spinner)town.getClickableView()).setOnItemSelectedListener(this);
        ((Spinner)uc.getClickableView()).setOnItemSelectedListener(this);
        ((Spinner)languagesSpoken.getClickableView()).setOnItemSelectedListener(this);
        ((Spinner)facility.getClickableView()).setOnItemSelectedListener(this);
        ((Spinner)occupation.getClickableView()).setOnItemSelectedListener(this);
        ((EditText)age.getClickableView()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals("")) {
                    if (!dobNotUpdateCheck) {
                        Calendar c = Calendar.getInstance();
                        String val = age.getValue();
                        int a = Integer.parseInt(val);
                        c.add(Calendar.YEAR, a - (a * 2));
                        dobNotUpdateCheck = true;
                        setDOBValue(c.getTime());
                    } else {
                        dobNotUpdateCheck = false;
                    }
                }
            }
        });
        ((RadioGroup)isTrainedAtFacility.getClickableView()).setOnCheckedChangeListener(this);
        ((RadioGroup) HealthWorker.getClickableView()).setOnCheckedChangeListener(this);


        date.getClickableView().setOnClickListener(this);
        dob.getClickableView().setOnClickListener(this);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        // facility.setVisibility(View.GONE);
        nic.setVisibility(View.GONE);
        contactNo.setVisibility(View.GONE);
        contactNo2.setVisibility(View.GONE);
        everHW.setVisibility(View.GONE);
        HealthWorker.setVisibility(View.GONE);
        occupation.setVisibility(View.GONE);
        occupationOther.setVisibility(View.GONE);
        city.setVisibility(View.GONE);
        town.setVisibility(View.GONE);
        uc.setVisibility(View.GONE);
        address.setVisibility(View.GONE);
        canInject.setVisibility(View.GONE);
        isTrainedAtFacility.setVisibility(View.GONE);
        trainingFacility.setVisibility(View.GONE);
        trainedBy.setVisibility(View.GONE);
        languagesSpoken.setVisibility(View.GONE);
        otherLanguage.setVisibility(View.GONE);
        user = dbContentHelper.fetchUserByUsername(CredentialsHelper.getUsername());
    }

    private void setDOBValue(Date date) {
        dob.setValue(Global.MYSQL_DATE_FORMAT.format(date));
        if(!dobNotUpdateCheck) {
            int ageYears = (
                    Years.yearsBetween(
                            new DateTime(date),
                            new DateTime()).getYears());

            dobNotUpdateCheck = true;
            age.setValue(ageYears+"");
        } else {
            dobNotUpdateCheck = false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selection = parent.getItemAtPosition(position).toString();
        if(parent == role.getClickableView()) {
            if(selection.equals(OpenMRSMappings.ROLE_TREATMENT_SUPPORTER.getName())){
                contactNo.setVisibility(View.VISIBLE);
                contactNo2.setVisibility(View.VISIBLE);
                city.setVisibility(View.VISIBLE);
                town.setVisibility(View.VISIBLE);
                uc.setVisibility(View.VISIBLE);
                address.setVisibility(View.VISIBLE);
                HealthWorker.setVisibility(View.VISIBLE);
                // occupation.setVisibility(View.VISIBLE);

                everHW.setVisibility(View.GONE);

                nic.setVisibility(View.VISIBLE);
                canInject.setVisibility(View.VISIBLE);
                isTrainedAtFacility.setVisibility(View.VISIBLE);
                languagesSpoken.setVisibility(View.VISIBLE);
            } else if(selection.equals(OpenMRSMappings.ROLE_TREATMENT_COORDINATOR.getName())){
                contactNo.setVisibility(View.VISIBLE);
                contactNo2.setVisibility(View.VISIBLE);
                city.setVisibility(View.VISIBLE);
                town.setVisibility(View.VISIBLE);
                uc.setVisibility(View.VISIBLE);
                address.setVisibility(View.VISIBLE);

                nic.setVisibility(View.GONE);
                canInject.setVisibility(View.GONE);
                isTrainedAtFacility.setVisibility(View.GONE);
                trainingFacility.setVisibility(View.GONE);
                trainedBy.setVisibility(View.GONE);
                languagesSpoken.setVisibility(View.GONE);
                HealthWorker.setVisibility(View.GONE);
                // occupation.setVisibility(View.GONE);

                everHW.setVisibility(View.VISIBLE);
            } else {
                contactNo.setVisibility(View.GONE);
                contactNo2.setVisibility(View.GONE);
                city.setVisibility(View.GONE);
                town.setVisibility(View.GONE);
                uc.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
                nic.setVisibility(View.GONE);
                canInject.setVisibility(View.GONE);
                isTrainedAtFacility.setVisibility(View.GONE);
                trainingFacility.setVisibility(View.GONE);
                trainedBy.setVisibility(View.GONE);
                languagesSpoken.setVisibility(View.GONE);
                everHW.setVisibility(View.GONE);
                HealthWorker.setVisibility(View.GONE);
                // occupation.setVisibility(View.GONE);
            }
            updateUserName();
        } else if(parent == city.getClickableView()) {
            //divisionId = dbContentHelper.fetchLocationsAttribute(OpenMRSMappings.LOCATION_ATTRIBUTE_TYPE_IDENTIFIER.getName(), city.getValue()).getValue();
            town.setSpinnerValues(dbContentHelper.fetchLocationsByTag(OpenMRSMappings.LOCATION_TAG_TOWN.getName(), selection));
        } else if(parent == town.getClickableView()) {
            //divisionId = dbContentHelper.fetchLocationsAttribute(OpenMRSMappings.LOCATION_ATTRIBUTE_TYPE_IDENTIFIER.getName(), city.getValue()).getValue();
            uc.setSpinnerValues(dbContentHelper.fetchLocationsByTag(OpenMRSMappings.LOCATION_TAG_UC.getName(), selection));
        } else if(parent == languagesSpoken.getClickableView()) {
            if(selection.equalsIgnoreCase("other")) {
                otherLanguage.setVisibility(View.VISIBLE);
            } else {
                otherLanguage.setVisibility(View.GONE);
            }
        } else if(parent==facility.getClickableView()) {
            updateUserName();
        } else if(parent == occupation.getClickableView()) {
            if(occupation.getValue().equals(OpenMRSMappings.CONCEPT_OTHER.getName()))
                occupationOther.setVisibility(View.VISIBLE);
            else
                occupationOther.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void updateUserName(){
        if(facility.getValue().equalsIgnoreCase(""))
            return;
        String id = null;
        DbContentHelper dbContentHelper = DbContentHelper.getInstance();
        int numberOfUsers = DbContentHelper.getInstance().findNumberOfUsers();
        LocationAttribute locationAttribute = DbContentHelper.getInstance().fetchLocationsAttribute(OpenMRSMappings.LOCATION_ATTRIBUTE_TYPE_IDENTIFIER.getName(), facility.getValue());
        String facilityCode = locationAttribute.getValue();
        if(role.getValue().equalsIgnoreCase(OpenMRSMappings.ROLE_TREATMENT_SUPPORTER.getName())) {
            id = facilityCode+String.format("%04d", ++numberOfUsers);
            while (dbContentHelper.fetchUserByUsername(id)!=null) {
                id = facilityCode+String.format("%04d", ++numberOfUsers);
            }
        } else {
            // initials + facility code + name initials + a 3-digit serial number
            id = Global.getFirstLetters(role.getValue())+
            // facilityCode+
            Global.getFirstLetters(patientName.getValue()+" "+fatherName.getValue())+
            String.format("%03d", ++numberOfUsers);
            while (dbContentHelper.fetchUserByUsername(id)!=null) {
                id = Global.getFirstLetters(role.getValue())
                        // + facilityCode
                        + Global.getFirstLetters(patientName.getValue()+" "+fatherName.getValue())
                        + String.format("%03d", ++numberOfUsers);
            }
        }

        username.setValue(id);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == date.getClickableView().getId()) {
            Intent intent = new Intent(this, DateSelector.class);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, -1);
            DateSelector.MIN_DATE = c.getTime();
            DateSelector.MAX_DATE = new Date();
            intent.putExtra(DateSelector.DATE_TYPE, DateSelector.WIDGET_TYPE.DATE.toString());
            startActivityForResult(intent, 1);
        }if(id == dob.getClickableView().getId()) {
            Intent intent = new Intent(this, DateSelector.class);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, -1);
            DateSelector.MAX_DATE = new Date();
            intent.putExtra(DateSelector.DATE_TYPE, DateSelector.WIDGET_TYPE.DATE.toString());
            startActivityForResult(intent, 2);
        } else {
            try {
                saveProvider();
            } catch (ParseException e) {
                Logger.log(e);
            }
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null) {
            if (data.hasExtra("value")) {
                Calendar calendar = (Calendar) data.getSerializableExtra("value");
                switch (requestCode) {
                    case 1:
                        date.setValue(Global.MYSQL_DATE_FORMAT.format(calendar.getTime()));
                        break;
                    case 2:
                        setDOBValue(calendar.getTime());
                        break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setPositiveButton(getResources().getString(R.string.leave), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProviderRegistration.super.onBackPressed();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), null)
                .setMessage(getResources().getString(R.string.form_not_saved))
                .setTitle(getResources().getString(R.string.exit_app)).show();
    }

    private void saveProvider() throws ParseException {
        if(!validate()) {

            return;
        }
        DbContentHelper dbContentHelper = DbContentHelper.getInstance();
        Role assignedRole = dbContentHelper.fetchRoleByName(role.getValue());

        if(assignedRole == null) {
            Toast.makeText(this, getResources().getString(R.string.lab_id),Toast.LENGTH_LONG).show();
            return;
        }
        // Saving Patient
        // Long id, String userName, String password, String uuid, String prividerUUID, String sessionId, String labUUID, Boolean voided
        User newUser = new User(
                null,
                username.getValue(),
                password.getValue(),
                patientName.getValue(),
                fatherName.getValue(),
                gender.getValue(),
                Integer.parseInt(age.getValue()),
                null, null, null, null, null, false);

        long userId = ELimsApplication.daoSession.getUserDao().insert(newUser);
        // Saving encounter
        EncounterDao encounterDao = ELimsApplication.daoSession.getEncounterDao();
        EncounterType encounterType = dbContentHelper.fetchEncounterType(OpenMRSMappings.ENCOUNTER_TYPE_PROVIDER_REGISTRATION.getName());
        Date encounterDate = Global.MYSQL_DATE_FORMAT.parse(date.getValue());
        String locationName = facility.getValue();
        Location location = dbContentHelper.fetchLocationByName(locationName);
        /*Encounter encounter = new Encounter(null,
                UUID.randomUUID().toString(),
                encounterDate,
                patient.getPatientId(),
                encounterType.getId(),
                location.getId(),
                CredentialsHelper.getUserId(),
                false, false);
        Long encounterId = encounterDao.insert(encounter);*/

        // Saving address
        AddressDao addressDao = ELimsApplication.daoSession.getAddressDao();
        Address lAddress = new Address(null,
                city.getValue(),
                town.getValue(),
                uc.getValue(),
                landMark.getValue(),
                address.getValue(),
                null, false,
                userId);
        addressDao.insert(lAddress);
        // Saving patient attributes
        List<PatientAttributes> patientAttributesList = new ArrayList<>();

        if (occupationOther.getVisibility() == View.VISIBLE) {
            PersonAttributeType occupationAttributeType = dbContentHelper.fetchPersonAttributeType(OpenMRSMappings.ATTRIBUTE_TYPE_OCCUPATION.getName());
            patientAttributesList.add(new PatientAttributes(null,
                    occupationOther.getValue(),
                    userId,
                    occupationAttributeType.getAttributeId(), null));
        } else if (occupation.getVisibility() == View.VISIBLE) {
            PersonAttributeType occupationAttributeType = dbContentHelper.fetchPersonAttributeType(OpenMRSMappings.ATTRIBUTE_TYPE_OCCUPATION.getName());
            patientAttributesList.add(new PatientAttributes(null,
                    occupation.getValue(),
                    userId,
                    occupationAttributeType.getAttributeId(), null));
        }
        if (contactNo.getVisibility() == View.VISIBLE) {
            PersonAttributeType contactAttributeType = dbContentHelper.fetchPersonAttributeType(OpenMRSMappings.ATTRIBUTE_TYPE_PRIMARY_CONTACT.getName());
            patientAttributesList.add(new PatientAttributes(null,
                    contactNo.getValue(),
                    userId,
                    contactAttributeType.getAttributeId(), null));
        }
        if (contactNo2.getVisibility() == View.VISIBLE) {
            PersonAttributeType contactAttributeType = dbContentHelper.fetchPersonAttributeType(OpenMRSMappings.ATTRIBUTE_TYPE_PRIMARY_SECONDARY_CONTACT.getName());
            patientAttributesList.add(new PatientAttributes(null,
                    contactNo2.getValue(),
                    userId,
                    contactAttributeType.getAttributeId(), null));
        }
        if (everHW.getVisibility() == View.VISIBLE) {
            PersonAttributeType contactAttributeType = dbContentHelper.fetchPersonAttributeType(OpenMRSMappings.ATTRIBUTE_TYPE_WAS_HW.getName());
            patientAttributesList.add(new PatientAttributes(null,
                    everHW.getValue(),
                    userId,
                    contactAttributeType.getAttributeId(), null));
        }
        if (HealthWorker.getVisibility() == View.VISIBLE) {
            PersonAttributeType contactAttributeType = dbContentHelper.fetchPersonAttributeType(OpenMRSMappings.ATTRIBUTE_TYPE_IS_HW.getName());
            patientAttributesList.add(new PatientAttributes(null,
                    HealthWorker.getValue(),
                    userId,
                    contactAttributeType.getAttributeId(), null));
        }
        if (canInject.getVisibility() == View.VISIBLE) {
            PersonAttributeType contactAttributeType = dbContentHelper.fetchPersonAttributeType(OpenMRSMappings.ATTRIBUTE_TYPE_CAN_INJECT.getName());
            patientAttributesList.add(new PatientAttributes(null,
                    canInject.getValue(),
                    userId,
                    contactAttributeType.getAttributeId(), null));
        }
        if (isTrainedAtFacility.getVisibility() == View.VISIBLE) {
            PersonAttributeType contactAttributeType = dbContentHelper.fetchPersonAttributeType(OpenMRSMappings.ATTRIBUTE_TYPE_IS_TRAINED_AT_FACILITY.getName());
            patientAttributesList.add(new PatientAttributes(null,
                    isTrainedAtFacility.getValue(),
                    userId,
                    contactAttributeType.getAttributeId(), null));
        }
        if (trainingFacility.getVisibility() == View.VISIBLE) {
            PersonAttributeType contactAttributeType = dbContentHelper.fetchPersonAttributeType(OpenMRSMappings.ATTRIBUTE_TYPE_TRAINING_FACILITY.getName());
            patientAttributesList.add(new PatientAttributes(null,
                    trainingFacility.getValue(),
                    userId,
                    contactAttributeType.getAttributeId(), null));
        }
        if (trainedBy.getVisibility() == View.VISIBLE) {
            PersonAttributeType contactAttributeType = dbContentHelper.fetchPersonAttributeType(OpenMRSMappings.ATTRIBUTE_TYPE_TRAINED_BY.getName());
            patientAttributesList.add(new PatientAttributes(null,
                    trainedBy.getValue(),
                    userId,
                    contactAttributeType.getAttributeId(), null));
        }
        if (languagesSpoken.getVisibility() == View.VISIBLE) {
            String languageValue = null;
            if(languagesSpoken.getValue().equalsIgnoreCase("other")) {
                languageValue = languagesSpoken.getValue();
            } else {
                languageValue = otherLanguage.getValue();
            }
            PersonAttributeType contactAttributeType = dbContentHelper.fetchPersonAttributeType(OpenMRSMappings.ATTRIBUTE_TYPE_LANGUAGE.getName());
            patientAttributesList.add(new PatientAttributes(null,
                    languageValue,
                    userId,
                    contactAttributeType.getAttributeId(), null));
        }

        ELimsApplication.daoSession.getPatientAttributesDao().insertInTx(patientAttributesList);
        Date birthDate = Global.MYSQL_DATE_FORMAT.parse(dob.getValue());
        JSONObject patientJSON = new OpenMRSJsonCreator().createProviderJson(birthDate, newUser, new Role[]{assignedRole}, lAddress, patientAttributesList, location);
        SendableData createPatientData = new SendableData(null, patientJSON.toString(), null, Config.USER_DATA_RESOURCE, false, SendableData.DATA_TYPE_CREATE_PROVIDER, newUser.getId(), newUser.getId(), 0, null);
        ELimsApplication.daoSession.getSendableDataDao().insert(createPatientData);
        Toast.makeText(this, R.string.record_saved, Toast.LENGTH_LONG).show();
        finish();


    }

    private boolean validate() {
        String message = getResources().getString(R.string.fill_required);
        boolean toReturn = true;
        if(date.isMandatory() && date.getVisibility() == View.VISIBLE) {
            if(date.getValue().isEmpty()) {
                toReturn = false;
                date.requestFocus();
            }
        }
        if(patientName.isMandatory() && patientName.getVisibility() == View.VISIBLE) {
            if(patientName.getValue().isEmpty()) {
                toReturn = false;
                patientName.requestFocus();
            }
        }
        if(fatherName.isMandatory() && fatherName.getVisibility() == View.VISIBLE) {
            if(fatherName.getValue().isEmpty()) {
                toReturn = false;
                fatherName.requestFocus();
            }
        }if(address.isMandatory() && address.getVisibility() == View.VISIBLE) {
            if(address.getValue().isEmpty()) {
                toReturn = false;
                address.requestFocus();
            }
        }
        if(age.isMandatory() && age.getVisibility() == View.VISIBLE) {
            if(age.getValue().isEmpty()) {
                toReturn = false;
                age.requestFocus();
            } else {
                int ageInt = Integer.parseInt(age.getValue());
                if(ageInt>110) {
                    age.requestFocus();
                    Toast.makeText(this, R.string.max_age_error, Toast.LENGTH_LONG).show();
                    return  false;
                }
            }
        }
        if(contactNo.isMandatory() && contactNo.getVisibility() == View.VISIBLE) {
            if(contactNo.getValue().isEmpty()) {
                contactNo.requestFocus();
                toReturn = false;
            }
        }
        if(everHW.isMandatory() && everHW.getVisibility() == View.VISIBLE) {
            if(everHW.getValue().isEmpty()) {
                everHW.requestFocus();
                toReturn = false;
            }
        }

        if(HealthWorker.isMandatory() && HealthWorker.getVisibility() == View.VISIBLE) {
            if(HealthWorker.getValue().isEmpty()) {
                HealthWorker.requestFocus();
                toReturn = false;
            }
        }

        if(canInject.isMandatory() && canInject.getVisibility() == View.VISIBLE) {
            if(canInject.getValue().isEmpty()) {
                canInject.requestFocus();
                toReturn = false;
            }
        }

        if(isTrainedAtFacility.isMandatory() && isTrainedAtFacility.getVisibility() == View.VISIBLE) {
            if(isTrainedAtFacility.getValue().isEmpty()) {
                isTrainedAtFacility.requestFocus();
                toReturn = false;
            }
        }

        if(trainingFacility.isMandatory() && trainingFacility.getVisibility() == View.VISIBLE) {
            if(trainingFacility.getValue().isEmpty()) {
                trainingFacility.requestFocus();
                toReturn = false;
            }
        }
        if(trainedBy.isMandatory() && trainedBy.getVisibility() == View.VISIBLE) {
            if(trainedBy.getValue().isEmpty()) {
                trainedBy.requestFocus();
                toReturn = false;
            }
        }
        if(languagesSpoken.isMandatory() && languagesSpoken.getVisibility() == View.VISIBLE) {
            if(languagesSpoken.getValue().isEmpty()) {
                languagesSpoken.requestFocus();
                toReturn = false;
            }
        }
        if(username.isMandatory() && username.getVisibility() == View.VISIBLE) {
            if(username.getValue().isEmpty()) {
                username.requestFocus();
                toReturn = false;
            } else {
                if(dbContentHelper.fetchUserByUsername(username.getValue())!=null) {
                    username.requestFocus();
                    toReturn = false;
                    message = getResources().getString(R.string.username_acquired);
                }
            }
        }
        if(password.isMandatory() && password.getVisibility() == View.VISIBLE) {
            if(password.getValue().isEmpty() || password.getValue().length()<8 || password.getValue().equals(password.getValue().toLowerCase())) {
                password.requestFocus();
                toReturn = false;
                message = getResources().getString(R.string.password_error);
            }
        }
        if(occupationOther.isMandatory() && occupationOther.getVisibility() == View.VISIBLE) {
            if(occupationOther.getValue().isEmpty()) {
                occupationOther.requestFocus();
                toReturn = false;
            }
        }
        if(!toReturn)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return toReturn;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String selection = ((RadioButton)findViewById(checkedId)).getText().toString();
         if(group == isTrainedAtFacility.getClickableView()) {
            if (selection.equalsIgnoreCase("yes")) {
                trainingFacility.setVisibility(View.VISIBLE);
                trainedBy.setVisibility(View.VISIBLE);
            } else {
                trainingFacility.setVisibility(View.GONE);
                trainedBy.setVisibility(View.GONE);
            }
        }if(group == HealthWorker.getClickableView()) {
            if (selection.equalsIgnoreCase("yes")) {
                occupation.setVisibility(View.VISIBLE);
            } else {
                occupation.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        updateUserName();
    }
}
