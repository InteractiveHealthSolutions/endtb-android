package com.ihsinformatics.endtb.network.uploader;

import android.content.Context;

import com.ihsinformatics.endtb.database.Entities.Address;
import com.ihsinformatics.endtb.database.Entities.Patient;
import com.ihsinformatics.endtb.database.Entities.PatientAttributes;
import com.ihsinformatics.endtb.database.Entities.PersonAttributeType;
import com.ihsinformatics.endtb.database.Entities.SendableData;
import com.ihsinformatics.endtb.database.data.DbContentHelper;
import com.ihsinformatics.endtb.network.DataSender;
import com.ihsinformatics.endtb.network.ParamNames;
import com.ihsinformatics.endtb.network.Sendable;
import com.ihsinformatics.endtb.utils.ELimsApplication;
import com.ihsinformatics.endtb.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Naveed Iqbal on 4/6/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class PatientFormsUploader implements Sendable {

    private List<SendableData> saveablePatientForms;
    private Context context;
    private int uploaded_forms = 0;
    private static PatientFormsUploader instance;
    public boolean isRunning;


    public static PatientFormsUploader getInstance(Context context) {
        if(instance ==  null)
            instance = new PatientFormsUploader(context);

        return instance;
    }
    private PatientFormsUploader(Context context) {
        isRunning = false;
        this.context = context;
    }

    public void startUploadingForms() {
        if(isRunning)
            return;
        uploaded_forms = 0;
        saveablePatientForms = DbContentHelper.getInstance().fetchAllSendableDataByType(SendableData.DATA_TYPE_CREATE_PATIENT);
        if(saveablePatientForms.size()>0) {
            isRunning = true;
            SendableData sendableData = saveablePatientForms.get(0);
            new DataSender(context, this, 0, null, DataSender.REQUEST_TYPE.POST, true).execute(sendableData.getUrlPostfix(), sendableData.getJsonData());
        } else {
            try {
                startUploadingEncounters();
            } catch (JSONException e) {
                Logger.log(e);
            }
        }
    }

    @Override
    public void send(JSONArray data, int respId, String responseReference) {

    }

    private void startUploadingEncounters() throws JSONException {
        isRunning = false;
        EncounterFormsUploader.getInstance(context).startUploadingForms();
    }

    @Override
    public void onResponseReceived(JSONObject resp, int respId, String responseReference) {
        // update patient id
        try {
            SendableData lastSaved = saveablePatientForms.get(uploaded_forms);
            if (!resp.has(ParamNames.SERVER_ERROR)) {
                ELimsApplication.daoSession.getSendableDataDao().delete(lastSaved);
                if (lastSaved.getDataype().equals(SendableData.DATA_TYPE_CREATE_PATIENT)) {
                    JSONObject personJson = resp.getJSONObject(ParamNames.PERSON);
                    JSONObject nameObject = personJson.getJSONObject(ParamNames.PREFERRED_NAME);
                    JSONObject addressJson = personJson.optJSONObject(ParamNames.PREFERRED_ADDRESS);
                    if(addressJson == null) return;
                    JSONArray attributesArray = personJson.getJSONArray(ParamNames.ATTRIBUTES);

                    Patient patient = DbContentHelper.getInstance().fetchPatientById(lastSaved.getPatientId());
                    patient.setUuid(resp.getString(ParamNames.UUID));
                    patient.setNameUUID(nameObject.getString(ParamNames.UUID));
                    ELimsApplication.daoSession.getPatientDao().update(patient);

                    for (int i = 0; i < attributesArray.length(); i++) {
                        String typeName = attributesArray.getJSONObject(i).getString(ParamNames.DISPLAY);
                        typeName = typeName.split(" =")[0];
                        String typeUUID = attributesArray.getJSONObject(i).getString(ParamNames.UUID);
                        PersonAttributeType attribType = DbContentHelper.getInstance().fetchPersonAttributeType(typeName);
                        PatientAttributes attributes = DbContentHelper.getInstance().fetchPatientAttribute(patient.getPatientId(), attribType.getAttributeName());
                        attributes.setUuid(typeUUID);
                        ELimsApplication.daoSession.getPatientAttributesDao().update(attributes);
                    }
                    Address address = DbContentHelper.getInstance().fetchAddress(patient.getPatientId());
                    address.setUuid(addressJson.getString(ParamNames.UUID));
                    ELimsApplication.daoSession.getAddressDao().update(address);

                }
            } else {
                lastSaved.setErrorMessage(resp.get(ParamNames.SERVER_ERROR).toString());
                lastSaved.setNumberOfUploadAttempts(lastSaved.getNumberOfUploadAttempts()+1);
                ELimsApplication.daoSession.getSendableDataDao().update(lastSaved);
            }

            uploaded_forms++;
            if(uploaded_forms < saveablePatientForms.size()) {
                SendableData sendableData = saveablePatientForms.get(uploaded_forms);
                new DataSender(context, this, 0, null, DataSender.REQUEST_TYPE.POST, true).execute(sendableData.getUrlPostfix(), sendableData.getJsonData());
            } else {
                startUploadingEncounters();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}