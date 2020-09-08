package com.ihsinformatics.endtb.Screens.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ihsinformatics.endtb.R;
import com.ihsinformatics.endtb.database.Entities.Concept;
import com.ihsinformatics.endtb.database.Entities.Drug;
import com.ihsinformatics.endtb.database.Entities.DrugOrders;
import com.ihsinformatics.endtb.database.Entities.Obs;
import com.ihsinformatics.endtb.database.data.DbContentHelper;
import com.ihsinformatics.endtb.utils.CredentialsHelper;
import com.ihsinformatics.endtb.utils.MappingHolder;
import com.ihsinformatics.endtb.utils.drug_utils.DrugConversionData;
import com.ihsinformatics.endtb.utils.views.InputWidget;
import com.ihsinformatics.endtb.utils.OpenMRSMappings;
import com.ihsinformatics.endtb.utils.views.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nabil shafi on 3/14/2018.
 */

public class DrugView extends LinearLayout implements TextWatcher, AdapterView.OnItemSelectedListener {

    private TextView drugName;
    private TextView prescribedDose;
    private InputWidget administeredDose;
    private TextView missedDose;
    private TextView dotRate;
    private TextView drugAdministration;
    private InputWidget deliveryMethod;
    private InputWidget otherDeliveryMethod;
    private Context context;
    private Activity activity;
    private DrugOrders drugOrders;
    private View view;
    private Drug drug;
    private EditText etAdministeredDose;
    private Spinner spTreatmentDeliveryMethod;
    private float prescribedDoseValue;
    private List<Obs> obsList;
    private String doseUnit;
    private boolean isPrescribed = true;
    private MappingHolder drugAdministrationMapping;

    public DrugView(Context context, DrugOrders drugOrders) {
        super(context);
        this.context = context;
        activity = (Activity) context;
        this.drugOrders = drugOrders;
        this.drug = drugOrders.getDrug();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_dtm_drug, null);
        addView(view);
        initControl();
    }

    private void initControl() {
        drugName = (TextView) findViewById(R.id.etAnswer_drugName);
        prescribedDose = (TextView) findViewById(R.id.etAnswer_prescribedDose);
        administeredDose = new InputWidget(activity, view, R.id.rlQuestion_administeredDose, R.id.tvQuestion_administeredDose, R.id.tvMessage_administeredDose, R.id.etAnswer_administeredDose, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        missedDose = (TextView) findViewById(R.id.etAnswer_missedDoseQe);
        dotRate = (TextView) findViewById(R.id.etAnswer_dotRate);
        drugAdministration = (TextView) findViewById(R.id.etAnswer_administration);

        deliveryMethod = new InputWidget(activity, view, R.id.rlQuestion_deliveryMethod, R.id.tvQuestion_deliveryMethod, R.id.tvMessage_deliveryMethod, R.id.spAnswer_deliveryMethod, InputWidget.INPUT_WIDGET_TYPE.SPINNER);
        otherDeliveryMethod = new InputWidget(activity, view, R.id.rlQuestion_otherDeliveryMethod, R.id.tvQuestion_otherDeliveryMethod, R.id.tvMessage_otherDeliveryMethod, R.id.etAnswer_otherDeliveryMethod, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);

        drugName.setText(drug.getName());
        prescribedDoseValue = drugOrders.getDose();

        DrugConversionData conversionData = new DrugConversionData();
        DrugConversionData.DrugData drugData = conversionData.getDrugData(drug.getName());
        if(drugData!=null) {
            prescribedDoseValue = (float) prescribedDoseValue / drugData.getPacking();
            drugOrders.getDoseUnit();
            doseUnit = drugData.getUnit();
        } else {
            doseUnit = drugOrders.getDoseUnit();
        }
        prescribedDose.setText(prescribedDoseValue+" "+doseUnit);

        etAdministeredDose = (EditText) administeredDose.getClickableView();
        etAdministeredDose.addTextChangedListener(this);

        deliveryMethod.setSpinnerValues(
                context.getResources().getStringArray(R.array.treatment_delivery_method_uuids),
                context.getResources().getStringArray(R.array.treatment_delivery_method));

        otherDeliveryMethod.setVisibility(View.GONE);
        spTreatmentDeliveryMethod = (Spinner) deliveryMethod.getClickableView();
        spTreatmentDeliveryMethod.setOnItemSelectedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    public DrugView setPrescribed(boolean isPrescribed) {
        this.isPrescribed = isPrescribed;
        int visibility;
        if(isPrescribed)
            visibility = View.VISIBLE;
        else
            visibility = View.INVISIBLE;

        ((TextView)findViewById(R.id.tvAsterik3)).setVisibility(visibility);
        ((TextView)findViewById(R.id.tvAsterik4)).setVisibility(visibility);
        ((TextView)findViewById(R.id.tvAsterik5)).setVisibility(visibility);

        if(visibility == View.VISIBLE) {
            ((LinearLayout)findViewById(R.id.llDOTRate)).setVisibility(visibility);
            ((LinearLayout)findViewById(R.id.llDrugAdministration)).setVisibility(visibility);
            ((RelativeLayout)findViewById(R.id.rlQuestion_deliveryMethod)).setVisibility(visibility);
            ((RelativeLayout)findViewById(R.id.rlQuestion_otherDeliveryMethod)).setVisibility(visibility);
        } else {
            ((LinearLayout)findViewById(R.id.llDOTRate)).setVisibility(View.GONE);
            ((LinearLayout)findViewById(R.id.llDrugAdministration)).setVisibility(View.GONE);
            ((RelativeLayout)findViewById(R.id.rlQuestion_deliveryMethod)).setVisibility(View.GONE);
            ((RelativeLayout)findViewById(R.id.rlQuestion_otherDeliveryMethod)).setVisibility(View.GONE);
        }

        return this;
    }
    float dotValue, missedDoseValue;
    @Override
    public void afterTextChanged(Editable editable) {
        String editableString = editable.toString();
        if(editableString.equals("")) {
            missedDose.setText("");
            drugAdministration.setText("");
            dotRate.setText("");
            return;
        }
        if(editableString.startsWith(".")) {
            editableString = "0" + editableString;
            etAdministeredDose.setText(editableString);
            etAdministeredDose.setSelection(editableString.length());
            return;
        }

        if(editableString.lastIndexOf(".") != editableString.indexOf(".")) {

            return;
        }

        float administered = Float.parseFloat(editableString);
        missedDoseValue = prescribedDoseValue - administered;
        missedDose.setText(String.format("%.2f", missedDoseValue));

        dotValue = administered/prescribedDoseValue;
        dotRate.setText(String.format("%.2f", dotValue));

        if(dotValue >= 1) {
            drugAdministrationMapping = OpenMRSMappings.CONCEPT_FULLY_OBSERVED_PRESCRIBED_DOSE;
        } else if(dotValue==0) {
            drugAdministrationMapping = OpenMRSMappings.CONCEPT_MISSED_PRESCRIBED_DOSE;
        } else {
            drugAdministrationMapping = OpenMRSMappings.CONCEPT_INCOMPLETE_PRESCRIBES_DOSE;
        }

        drugAdministration.setText(drugAdministrationMapping.getName());

        if(administered>prescribedDoseValue) {
            new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.warning))
                    .setMessage(context.getString(R.string.warning_message))
                    .setPositiveButton(context.getString(R.string._continue), null)
                    .show();
        }

        administeredDose.setBackground(context.getResources().getDrawable(R.drawable.drop_down_shape));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedText = spTreatmentDeliveryMethod.getItemAtPosition(i).toString();
        if(selectedText.equals(context.getResources().getString(R.string.other))) {
            otherDeliveryMethod.setVisibility(View.VISIBLE);
        } else {
            otherDeliveryMethod.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public boolean isValid() {
        String answer = administeredDose.getValue();
        if(answer.lastIndexOf(".") != answer.indexOf(".") || (answer.lastIndexOf(".")+1 == answer.length() && !answer.isEmpty())) {
            Toast.makeText(context, getResources().getString(R.string.enter_administered_dose), Toast.LENGTH_LONG).show();
            administeredDose.setBackground(context.getResources().getDrawable(R.drawable.drop_down_shape_error));
            administeredDose.requestFocus();
            return false;
        }
        if(!isPrescribed)
            return true;
        boolean isValid =  true;//administeredDose.getValue().length()>0;

        if(!isValid) {
            Toast.makeText(context, getResources().getString(R.string.enter_administered_dose), Toast.LENGTH_LONG).show();
            administeredDose.setBackground(context.getResources().getDrawable(R.drawable.drop_down_shape_error));
            administeredDose.requestFocus();
        }

        return isValid;
    }

    public List<Obs> generateObs(Long encounterId) {
        obsList = new ArrayList<>();
        if(administeredDose.getValue().length()<=0)
            return obsList;
        if(administeredDose.getValue().length()<=0 && !isPrescribed)
            return obsList;

        Obs obs = null;
        obs = new Obs(
                UUID.randomUUID().toString(),
                null,
                null,
                CredentialsHelper.getUserId(),
                encounterId,
                drug.getConceptId(),
                false,
                null

        );
        obsList.add(obs);

        Concept administeredDoseConcept = DbContentHelper.getInstance().fetchConceptByUUID(OpenMRSMappings.CONCEPT_ADMINISTERED_DOSE_QUANTITY.getUuid());
        addObservation(administeredDose, encounterId, administeredDoseConcept.getId(), obs.getLocalUUID());

        Concept isPrescribedConcept = DbContentHelper.getInstance().fetchConceptByUUID(OpenMRSMappings.CONCEPT_IS_NON_PRESCRIBED.getUuid());
        addObservation(String.valueOf(!isPrescribed), encounterId, isPrescribedConcept.getId(), obs.getLocalUUID());

        if(isPrescribed) {
            Concept txDeliveryMethod = DbContentHelper.getInstance().fetchConceptByUUID(OpenMRSMappings.CONCEPT_TX_DELIVERY_METHOD.getUuid());
            addObservation(deliveryMethod, encounterId, txDeliveryMethod.getId(), obs.getLocalUUID());

            if(otherDeliveryMethod.getVisibility() == View.VISIBLE) {
                Concept txDeliveryMethodOther = DbContentHelper.getInstance().fetchConceptByUUID(OpenMRSMappings.CONCEPT_TX_DELIVERY_METHOD_OTHER.getUuid());
                addObservation(otherDeliveryMethod, encounterId, txDeliveryMethodOther.getId(), obs.getLocalUUID());
            }

            Concept txDOTRate = DbContentHelper.getInstance().fetchConceptByUUID(OpenMRSMappings.CONCEPT_DOT_RATE.getUuid());
            addObservation(dotValue + "", encounterId, txDOTRate.getId(), obs.getLocalUUID());

            Concept drugAdministrationConcept = DbContentHelper.getInstance().fetchConceptByUUID(OpenMRSMappings.CONCEPT_DRUG_ADMINISTRATION.getUuid());
            addObservation(drugAdministrationMapping.getUuid(), encounterId, drugAdministrationConcept.getId(), obs.getLocalUUID());

            Concept missedDoseConcept = DbContentHelper.getInstance().fetchConceptByUUID(OpenMRSMappings.CONCEPT_MISSED_DOSE.getUuid());
            addObservation(missedDoseValue + "", encounterId, missedDoseConcept.getId(), obs.getLocalUUID());
        }

        // Long id, String value, String uuid, Long creator, Long encounterId, Long conceptId, Boolean voided

        return obsList;
    }

    public String getDrugAdministration() {
        return drugAdministration.getText().toString();
    }

    private void addObservation(InputWidget inputWidget, Long encounterId, Long conceptId, String parentObsId) {
        if(inputWidget.getVisibility() != View.VISIBLE)
            return;

        obsList.add(new Obs(
                UUID.randomUUID().toString(),
                inputWidget.getValue(),
                null,
                CredentialsHelper.getUserId(),
                encounterId,
                conceptId, false, parentObsId));
    }

    private void addObservation(TextView inputWidget, Long encounterId, Long conceptId, String parentObsId) {
        if(inputWidget.getVisibility() != View.VISIBLE)
            return;

        obsList.add(new Obs(
                UUID.randomUUID().toString(),
                inputWidget.getText().toString(),
                null,
                CredentialsHelper.getUserId(),
                encounterId,
                conceptId, false, parentObsId));
    }

    private void addObservation(String inputWidget, Long encounterId, Long conceptId, String parentObsId) {


        obsList.add(new Obs(
                UUID.randomUUID().toString(),
                inputWidget,
                null,
                CredentialsHelper.getUserId(),
                encounterId,
                conceptId, false, parentObsId));
    }
}
