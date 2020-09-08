package com.ihsinformatics.endtb.Screens;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.ihsinformatics.endtb.R;
import com.ihsinformatics.endtb.Screens.patient_list_recycler.PatientModel;
import com.ihsinformatics.endtb.Screens.views.DrugView;
import com.ihsinformatics.endtb.database.Entities.Concept;
import com.ihsinformatics.endtb.database.Entities.DrugOrders;
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
import com.ihsinformatics.endtb.network.Config;
import com.ihsinformatics.endtb.network.UnifiedBroadcastReceiver;
import com.ihsinformatics.endtb.utils.AndroidPermissionsHelper;
import com.ihsinformatics.endtb.utils.CredentialsHelper;
import com.ihsinformatics.endtb.utils.SystemHelper;
import com.ihsinformatics.endtb.utils.drug_utils.DrugDoseUtils;
import com.ihsinformatics.endtb.utils.ELimsApplication;
import com.ihsinformatics.endtb.utils.Global;
import com.ihsinformatics.endtb.utils.preferences.AppPreferences;
import com.ihsinformatics.endtb.utils.views.InputWidget;
import com.ihsinformatics.endtb.utils.Logger;
import com.ihsinformatics.endtb.utils.OpenMRSMappings;
import com.ihsinformatics.endtb.utils.views.Toast;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class DailyTreatmentMonitoring extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private LinearLayout llPrescribed;
    private LinearLayout llNonPrescribed;
    private PatientModel patientModel;
    public  static final int requestPermissionCode = 1 ;
    public  static final int PLAY_SERVICES_RESOLUTION_REQUEST = 2;
    private InputWidget tvLongitude, tvLatitude ;
    private Context context;
    private Button button;
    private List<Obs> obsList;
    private User user;
    private List<DrugView> drugViews;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LinearLayout llLanguage;
    private TextView tvLanguage;
    private boolean restartRequest = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_treatment_monitoring);
        tvLanguage = findViewById(R.id.tvLanguage);
        String language = AppPreferences.getInstance(this).findStringPrferenceValue(AppPreferences.KEY.LANGUAGE, getResources().getString(R.string.english));
        setLocale(language);
        llPrescribed = (LinearLayout) findViewById(R.id.llPrescribed);
        llNonPrescribed = (LinearLayout) findViewById(R.id.llNonPrescribed);
        patientModel = (PatientModel) getIntent().getSerializableExtra(Global.PATIENT_MODEL);
        List<DrugOrders> drugOrders = DbContentHelper.getInstance().fetchActiveDrugOrdersByPatientId(patientModel.getDbId());
        List<Encounter> encounterList = DbContentHelper.getInstance().fetchAllEncounters();
        llNonPrescribed.setVisibility(View.GONE);
        llLanguage = findViewById(R.id.llLanguage);
        llLanguage.setOnClickListener(this);
        ((TextView)findViewById(R.id.tvPatientId)).setText(patientModel.getId());
        ((TextView)findViewById(R.id.tvPatientName)).setText(patientModel.getName());
        ((TextView)findViewById(R.id.tvAge)).setText(patientModel.getAge()+" "+getResources().getString(R.string.yr));
        if(patientModel.getGender().toLowerCase().startsWith("f")) {
            ((ImageView)findViewById(R.id.ivGender)).setImageDrawable(getResources().getDrawable(R.drawable.female_icon));
        } else {
            ((ImageView)findViewById(R.id.ivGender)).setImageDrawable(getResources().getDrawable(R.drawable.male_icon));
        }

        Date txStartDate = null;
        int dayOfTreatment = 0;
        for(Encounter e: encounterList) {
            Obs obs = DbContentHelper.getInstance().fetchObs(e.getId(), OpenMRSMappings.CONCEPT_TX_START_DATE.getShortName());
            if (obs != null) {
                String v = obs.getValue();
                try {
                    txStartDate = Global.MYSQL_DATE_FORMAT.parse(v);
                } catch (ParseException e1) {
                    Logger.log(e1);
                }
            }
        }
        if(txStartDate != null) {
            checkPermissions();
            if (checkPlayServices()) {
                // Building the GoogleApi client
                buildGoogleApiClient();
            }
            tvLongitude = new InputWidget(this, R.id.rlQuestion_longitude, R.id.tvQuestion_longitude, R.id.tvMessage_longitude, R.id.etAnswer_longitude, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
            tvLatitude = new InputWidget(this, R.id.rlQuestion_latitude, R.id.tvQuestion_latitude, R.id.tvMessage_latitude, R.id.etAnswer_latitude, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);

            displayLocation();
            dayOfTreatment = (Days.daysBetween(new DateTime(txStartDate), new DateTime()).getDays()) + 1;
            ((TextView)findViewById(R.id.tvDayOfTx)).setText(getString(R.string.dot)+": "+ dayOfTreatment+" ("+Global.MYSQL_DATE_FORMAT.format(txStartDate)+")");

            drugViews = new ArrayList<>();

            for(int i=0; i<drugOrders.size(); i++) {
                boolean isPrescribedForToday = new DrugDoseUtils().isPrescribed(drugOrders.get(i));

                DrugView drugView = new DrugView(this, drugOrders.get(i));
                drugViews.add(drugView);
                if(isPrescribedForToday) {
                    llPrescribed.addView(drugView);
                } else {
                    llNonPrescribed.addView(drugView.setPrescribed(false));
                    llNonPrescribed.setVisibility(View.VISIBLE);
                }
            }

            user = DbContentHelper.getInstance().fetchUserByUsername(CredentialsHelper.getUsername());

            context = getApplicationContext();
            button = (Button) findViewById(R.id.btnSave);
            button.setOnClickListener(this);
        } else {
            Toast.makeText(this, getResources().getString(R.string.treatment_not_started), Toast.LENGTH_LONG).show();
            DailyTreatmentMonitoring.this.finish();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    public void checkPermissions(){
        AndroidPermissionsHelper.getInstance().checkPermissions(
                DailyTreatmentMonitoring.this,
                requestPermissionCode,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int rc, String per[], int[] pResult) {
        switch (rc) {
            case requestPermissionCode:
                if (pResult.length > 0 && pResult[0] == PackageManager.PERMISSION_GRANTED) {
                    buildGoogleApiClient();
                } else {
                    Toast.makeText(DailyTreatmentMonitoring.this, "Permission Canceled, Now your application cannot access GPS.", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if(view == button) {
            saveForm();
        } else if(view == llLanguage) {
            displayLanguageMenu();
        }

    }

    private void displayLanguageMenu() {
        PopupMenu popup = new PopupMenu(this, llLanguage);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.language_menu, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                String lang = menuItem.getTitle().toString();
                if(!setLocale(lang))
                    return false;

                AppPreferences.getInstance(DailyTreatmentMonitoring.this).addOrUpdateStringPreference(AppPreferences.KEY.LANGUAGE, lang);
                restartActivity();
                return true;
            }
        });
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        restartRequest = true;
    }

    @Override
    protected void onDestroy() {
        if(!restartRequest)
            setLocale("English");
        super.onDestroy();
    }

    private boolean setLocale(String language) {
        tvLanguage.setText(language);
        Locale locale;
        if(language.equals(getResources().getString(R.string.roman)))
            locale = new Locale("hi");
        else if(language.equals(getResources().getString(R.string.urdu)))
            locale = new Locale("ur");
        else
            locale = new Locale("en");

        SystemHelper.setLocale(DailyTreatmentMonitoring.this, locale);
        OpenMRSMappings.reload();
        return true;
    }

    private void saveForm() {
        if(!validate()) {
            return;
        }

        DbContentHelper dbContentHelper = DbContentHelper.getInstance();

        // fetch Patient
        Patient patient = dbContentHelper.fetchPatientById(patientModel.getDbId());

        // Saving encounter
        EncounterDao encounterDao = ELimsApplication.daoSession.getEncounterDao();
        EncounterType encounterType = null;
        encounterType = dbContentHelper.fetchEncounterType(OpenMRSMappings.ENCOUNTER_TYPE_DTM.getName());
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

        // Saving observations
        ObsDao obsDao = ELimsApplication.daoSession.getObsDao();
        obsList = new ArrayList<>();
        JSONArray obsJsonArray = new JSONArray();
        String drugAdmin = null;
        for(DrugView drugView: drugViews) {
            if(drugAdmin!=null) {
                if(!drugView.getDrugAdministration().equals(drugAdmin))
                    drugAdmin = OpenMRSMappings.CONCEPT_INCOMPLETE_PRESCRIBES_DOSE.getName();

                if (!drugAdmin.equals(OpenMRSMappings.CONCEPT_INCOMPLETE_PRESCRIBES_DOSE.getName()))
                    drugAdmin = drugView.getDrugAdministration();
            } else
                drugAdmin = drugView.getDrugAdministration();

            obsList.addAll(drugView.generateObs(encounterId));

        }
        
        Concept drugAdministrationDayConcept = dbContentHelper.fetchConcept(OpenMRSMappings.CONCEPT_DRUG_ADMINISTRATION_OF_DAY.getName());

        if(drugAdmin.equals(OpenMRSMappings.CONCEPT_INCOMPLETE_PRESCRIBES_DOSE.getName())) {
            addObservation(OpenMRSMappings.CONCEPT_INCOMPLETE_PRESCRIBED_DAY.getUuid(), encounterId, drugAdministrationDayConcept.getId(), true);
        } else if(drugAdmin.equals(OpenMRSMappings.CONCEPT_MISSED_PRESCRIBED_DOSE.getName())) {
            addObservation(OpenMRSMappings.CONCEPT_MISSED_PRESCRIBED_DAY.getUuid(), encounterId, drugAdministrationDayConcept.getId(), true);
        } else if(drugAdmin.equals(OpenMRSMappings.CONCEPT_FULLY_OBSERVED_PRESCRIBED_DOSE.getName())) {
            addObservation(OpenMRSMappings.CONCEPT_FULLY_OBSERVED_PRESCRIBED_DAY.getUuid(), encounterId, drugAdministrationDayConcept.getId(), true);
        }

        Concept longitudeConcept = dbContentHelper.fetchConcept(OpenMRSMappings.CONCEPT_LONGITUDE.getName());
        addObservation(tvLongitude, encounterId, longitudeConcept.getId(), true);
        
        Concept latitudeConcept = dbContentHelper.fetchConcept(OpenMRSMappings.CONCEPT_LATITUDE.getName());
        addObservation(tvLatitude, encounterId, latitudeConcept.getId(), true);
        obsDao.insertInTx(obsList);

        JSONObject encounterJSON = new OpenMRSJsonCreator().createDTMEncounterJSON(patient, encounter, obsList, user, obsJsonArray);
        SendableData createEncounterData = new SendableData(null, encounterJSON.toString(), null, Config.ENCOUNTER_DATA_RESOURCE, false, SendableData.DATA_TYPE_CREATE_ENCOUNTER, patient.getPatientId(), encounterId, 0, null);
        ELimsApplication.daoSession.getSendableDataDao().insert(createEncounterData);

        Toast.makeText(this, R.string.form_saved, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(UnifiedBroadcastReceiver.ACTION_ATTEPT_TO_UPLOAD_DATA);
        sendBroadcast(intent);

        finish();
    }

    private boolean validate() {
        boolean isValid = true;

        for(DrugView drugView: drugViews) {
            if(!drugView.isValid()) {
                isValid = false;
            }
        }

        return isValid;
    }

    private void addObservation(String value, Long encounterId, Long conceptId, boolean isHiddenField) {
        obsList.add(new Obs(UUID.randomUUID().toString(),
                value,
                null,
                CredentialsHelper.getUserId(),
                encounterId,
                conceptId, false, null));
    }

    private void addObservation(InputWidget inputWidget, Long encounterId, Long conceptId, boolean isHiddenField) {
        if(!isHiddenField && inputWidget.getVisibility() != View.VISIBLE)
            return;
        obsList.add(new Obs(UUID.randomUUID().toString(),
                inputWidget.getValue(),
                null,
                CredentialsHelper.getUserId(),
                encounterId,
                conceptId, false, null));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void displayLocation() {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            double latitudeValue = mLastLocation.getLatitude();
            double longitudeValue = mLastLocation.getLongitude();

            tvLongitude.setValue(longitudeValue+"");
            tvLatitude.setValue(latitudeValue+"");
        } else {

        }
    }
}
