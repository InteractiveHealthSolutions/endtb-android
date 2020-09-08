package com.ihsinformatics.endtb.Screens.views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ihsinformatics.endtb.R;
import com.ihsinformatics.endtb.Screens.patient_list_recycler.PatientModel;
import com.ihsinformatics.endtb.database.Entities.Patient;
import com.ihsinformatics.endtb.database.data.DbContentHelper;
import com.ihsinformatics.endtb.network.ParamNames;
import com.ihsinformatics.endtb.utils.OpenMRSMappings;
import com.ihsinformatics.endtb.utils.views.InputWidget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Nabil shafi on 3/14/2018.
 */

public class ContactView extends LinearLayout implements AdapterView.OnItemSelectedListener {

    private InputWidget patientName;
    private InputWidget lastName;
    private InputWidget age;
    private InputWidget gender;
    private InputWidget relation;
    private InputWidget otherRelation;
    private Spinner spOther;
    private Context context;
    private Activity activity;
    private View view;
    private PatientModel patient;
    private int contactNumber;
    public ContactView(Context context, int contactNumber, PatientModel patient) {
        super(context);
        this.context = context;
        this.patient = patient;
        this.contactNumber = contactNumber;
        activity = (Activity) context;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_contact_info, null);
        addView(view);
        ((TextView)view.findViewById(R.id.etContactNumber)).setText(getResources().getString(R.string.contact)+" "+contactNumber);
        initControl();
    }

    private void initControl() {

        patientName = new InputWidget(activity, view, R.id.rlQuestion_firstName, R.id.tvQuestion_firstName, R.id.tvMessage_firstName, R.id.spAnswer_firstName, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        lastName = new InputWidget(activity, view, R.id.rlQuestion_lastName, R.id.tvQuestion_lastName, R.id.tvMessage_lastName, R.id.spAnswer_lastName, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        age = new InputWidget(activity, view, R.id.rlQuestion_contactAge, R.id.tvQuestion_contactAge, R.id.tvMessage_contactAge, R.id.spAnswer_contactAge, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);
        gender = new InputWidget(activity, view, R.id.rlQuestion_gender, R.id.tvQuestion_gender, R.id.tvMessage_gender, R.id.spAnswer_gender, InputWidget.INPUT_WIDGET_TYPE.SPINNER);
        relation = new InputWidget(activity, view, R.id.rlQuestion_relation, R.id.tvQuestion_relation, R.id.tvMessage_relation, R.id.spAnswer_relation, InputWidget.INPUT_WIDGET_TYPE.SPINNER);
        otherRelation = new InputWidget(activity, view, R.id.rlQuestion_relationOther, R.id.tvQuestion_relationOther, R.id.tvMessage_relationOther, R.id.spAnswer_relationOther, InputWidget.INPUT_WIDGET_TYPE.EDIT_TEXT);

        otherRelation.setVisibility(View.GONE);
        spOther = (Spinner) relation.getClickableView();
        spOther.setOnItemSelectedListener(this);


        gender.setSpinnerValues(getResources().getStringArray(R.array.gender));
        relation.setSpinnerValues(getResources().getStringArray(R.array.relations));
    }

    public boolean isValid() {

            return true;
    }

    public JSONObject generateJSON(Long encounterId) {
        return null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public JSONObject getAnswer() {
        List<Patient> contacts = DbContentHelper.getInstance().fetchPatientsByAttribute(OpenMRSMappings.ATTRIBUTE_TYPE_RELATIVE_ID.getName(), patient.getId());
        String identifierString = patient.getId()+"-"+(contactNumber+contacts.size());
        JSONObject patientObj = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        JSONArray namesArray = new JSONArray();
        JSONObject nameObj = new JSONObject();

        JSONArray identifiersArray = new JSONArray();
        JSONObject identifierObj = new JSONObject();

        JSONArray attributesArray = new JSONArray();
        JSONObject relationAttrib = new JSONObject();
        JSONObject relativeIDAttrib = new JSONObject();

        String relationString;
        if(otherRelation.getVisibility() != View.VISIBLE)
            relationString = relation.getValue();
        else
            relationString = otherRelation.getValue();

        try {

            // making identifier
            identifierObj.put(ParamNames.IDENTIFIER_TYPE, OpenMRSMappings.PATIENT_IDENTIFIER_TYPE_CONTACT_IDENTIFIER.getUuid());
            identifierObj.put(ParamNames.IDENTIFIER, identifierString);
            identifiersArray.put(identifierObj);

            // adding name
            nameObj.put(ParamNames.GIVEN_NAME, patientName.getValue());
            nameObj.put(ParamNames.FAMILY_NAME, lastName.getValue());
            namesArray.put(nameObj);

            // adding relation attributes
            relationAttrib.put(ParamNames.ATTRIBUTE_TYPES, OpenMRSMappings.ATTRIBUTE_TYPE_RELATION.getUuid());
            relationAttrib.put(ParamNames.VALUE, relationString);
            attributesArray.put(relationAttrib);

            relativeIDAttrib.put(ParamNames.ATTRIBUTE_TYPES, OpenMRSMappings.ATTRIBUTE_TYPE_RELATIVE_ID.getUuid());
            relativeIDAttrib.put(ParamNames.VALUE, patient.getId());
            attributesArray.put(relativeIDAttrib);

            // finalizing json
            jsonObject.put(ParamNames.NAMES, namesArray);
            jsonObject.put(ParamNames.ATTRIBUTES, attributesArray);

            int a = Integer.parseInt(age.getValue());
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, a-(a+a));
            jsonObject.put("birthdate", new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()));

            // jsonObject.put(ParamNames.AGE, age.getValue());
            jsonObject.put(ParamNames.GENDER, gender.getValue());

            patientObj.put(ParamNames.PERSON, jsonObject);
            patientObj.put(ParamNames.IDENTIFIERS, identifiersArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return patientObj;
    }
}
