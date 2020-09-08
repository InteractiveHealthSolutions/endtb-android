package com.ihsinformatics.endtb.network.uploader;

import android.content.Context;

import com.ihsinformatics.endtb.database.Entities.Encounter;
import com.ihsinformatics.endtb.database.Entities.Obs;
import com.ihsinformatics.endtb.database.Entities.Order;
import com.ihsinformatics.endtb.database.Entities.Patient;
import com.ihsinformatics.endtb.database.Entities.SendableData;
import com.ihsinformatics.endtb.database.data.DbContentHelper;
import com.ihsinformatics.endtb.database.json_helper.OpenMRSJsonCreator;
import com.ihsinformatics.endtb.network.Config;
import com.ihsinformatics.endtb.network.DataSender;
import com.ihsinformatics.endtb.network.ParamNames;
import com.ihsinformatics.endtb.network.Sendable;
import com.ihsinformatics.endtb.utils.ELimsApplication;
import com.ihsinformatics.endtb.utils.OpenMRSMappings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naveed Iqbal on 4/6/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class EncounterFormsUploader implements Sendable {

    private List<SendableData> saveableEncounterForms;
    private Context context;
    private int uploaded_forms = 0;
    private static EncounterFormsUploader instance;
    private boolean isRunning;

    public static EncounterFormsUploader getInstance(Context context) {
        if(instance == null)
            instance = new EncounterFormsUploader(context);
        return instance;
    }

    private EncounterFormsUploader(Context context) {
        isRunning = false;
        this.context = context;
    }

    public void startUploadingForms() throws JSONException {
        if(isRunning)
            return;
        uploaded_forms = 0;
        saveableEncounterForms = DbContentHelper.getInstance().fetchAllSendableDataByType(SendableData.DATA_TYPE_CREATE_ENCOUNTER);
        if(saveableEncounterForms.size()>0) {
            isRunning = true;
            SendableData sendableData = saveableEncounterForms.get(0);
            Patient patient = sendableData.getPatient();
            JSONObject data = new JSONObject(sendableData.getJsonData());
            data.put("patient", patient.getUuid());
            new DataSender(context, this, 0, null, DataSender.REQUEST_TYPE.POST, true).execute(sendableData.getUrlPostfix()+"?v=full", data.toString());
        }  else {
            startUploadingOrders();
        }
    }

    @Override
    public void send(JSONArray data, int respId, String responseReference) {

    }

    @Override
    public void onResponseReceived(JSONObject resp, int respId, String responseReference) {
        // update encounter id
        try {
            SendableData lastSaved = saveableEncounterForms.get(uploaded_forms);
            if(!resp.has(ParamNames.SERVER_ERROR)) {
                ELimsApplication.daoSession.getSendableDataDao().delete(lastSaved);
                if(lastSaved.getDataype().equals(SendableData.DATA_TYPE_CREATE_ENCOUNTER)) {
                    // Update encounter UUID
                    Encounter encounter = DbContentHelper.getInstance().fetchEncounterById(lastSaved.getReferenceId());
                    String newUUID = resp.getString(ParamNames.UUID);
                    encounter.setUuid(newUUID);
                    encounter.setIsUploaded(true);
                    ELimsApplication.daoSession.getEncounterDao().update(encounter);
                    // Updating observations with new uuids
                    JSONArray obsJsonArray = resp.getJSONArray(ParamNames.OBS);
                    // JSONObject conceptMap = resp.getJSONObject("conceptMap");
                    List<Obs> obsList = new ArrayList<>();
                    for (int i = 0; i < obsJsonArray.length(); i++) {
                        JSONObject obsObject = obsJsonArray.getJSONObject(i);
                        String obsUUID = obsObject.getString(ParamNames.UUID);
                        if(!obsObject.has(ParamNames.CONCEPT))
                            continue;
                        String conceptUUID = obsObject.getJSONObject(ParamNames.CONCEPT).getString(ParamNames.UUID);
                        Obs obs = DbContentHelper.getInstance().fetchObsByConceptUUID(encounter.getId(), conceptUUID);
                        obs.setUuid(obsUUID);
                        obsList.add(obs);
                    }
                    ELimsApplication.daoSession.getObsDao().updateInTx(obsList);
                }
            } else {
                lastSaved.setErrorMessage(resp.get(ParamNames.SERVER_ERROR).toString());
                lastSaved.setNumberOfUploadAttempts(lastSaved.getNumberOfUploadAttempts()+1);
                ELimsApplication.daoSession.getSendableDataDao().update(lastSaved);
            }
            uploaded_forms++;
            if(uploaded_forms < saveableEncounterForms.size()) {
                SendableData sendableData = saveableEncounterForms.get(uploaded_forms);
                Patient patient = sendableData.getPatient();
                JSONObject data = new JSONObject(sendableData.getJsonData());
                data.put("patient", patient.getUuid());
                new DataSender(context, this, 0, null, DataSender.REQUEST_TYPE.POST, true).execute(sendableData.getUrlPostfix()+"?v=full", data.toString());

            }  else {
                startUploadingOrders();
            }
        } catch (JSONException e) {
            isRunning = false;
            e.printStackTrace();
        }
    }

    private void startUploadingOrders() throws JSONException {
        isRunning = false;
        OrderFormsUploadeer.getInstance(context).startUploadingForms();
    }
}

