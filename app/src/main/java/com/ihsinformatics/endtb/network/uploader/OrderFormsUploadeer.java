package com.ihsinformatics.endtb.network.uploader;

import android.content.Context;

import com.ihsinformatics.endtb.database.Entities.Order;
import com.ihsinformatics.endtb.database.Entities.Patient;
import com.ihsinformatics.endtb.database.Entities.SendableData;
import com.ihsinformatics.endtb.database.data.DbContentHelper;
import com.ihsinformatics.endtb.network.DataSender;
import com.ihsinformatics.endtb.network.ParamNames;
import com.ihsinformatics.endtb.network.Sendable;
import com.ihsinformatics.endtb.utils.ELimsApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Naveed Iqbal on 12/27/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class OrderFormsUploadeer implements Sendable {

    private List<SendableData> saveableEncounterForms;
    private Context context;
    private int uploaded_forms = 0;
    private static OrderFormsUploadeer instance;
    boolean isRunning;

    public static OrderFormsUploadeer getInstance(Context context) {
        if(instance == null)
            instance = new OrderFormsUploadeer(context);
        return instance;
    }

    private OrderFormsUploadeer(Context context) {
        isRunning = false;
        this.context = context;
    }

    public void startUploadingForms() throws JSONException {
        if(isRunning)
            return;
        uploaded_forms = 0;
        saveableEncounterForms = DbContentHelper.getInstance().fetchAllSendableDataByType(SendableData.DATA_TYPE_CREATE_TEST_ORDER);
        saveableEncounterForms.addAll(DbContentHelper.getInstance().fetchAllSendableDataByType(SendableData.DATA_TYPE_UPDATE_TEST_ORDER));
        if(saveableEncounterForms.size()>0) {
            isRunning = true;
            SendableData sendableData = saveableEncounterForms.get(0);
            Patient patient = sendableData.getPatient();
            Long orderId = sendableData.getReferenceId();

            Order order = DbContentHelper.getInstance().fetchOrderById(orderId);
            String encounterUUID = order.getEncounter().getUuid();

            JSONObject data = new JSONObject(sendableData.getJsonData());
            if(sendableData.getDataype().equals(SendableData.DATA_TYPE_CREATE_TEST_ORDER)) {
                data.put("patient", patient.getUuid());
                data.put("encounter", encounterUUID);
            }

            new DataSender(context, this, 0, null, DataSender.REQUEST_TYPE.POST, true).execute(sendableData.getUrlPostfix(), data.toString());
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
                if (lastSaved.getDataype().equals(SendableData.DATA_TYPE_CREATE_TEST_ORDER)) {
                    String newUUID = resp.getString(ParamNames.UUID);
                    Order order = DbContentHelper.getInstance().fetchOrderById(lastSaved.getReferenceId());
                    order.setUuid(newUUID);
                    order.setIsUploaded(true);
                    ELimsApplication.daoSession.getOrderDao().update(order);
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
                Long orderId = sendableData.getReferenceId();

                Order order = DbContentHelper.getInstance().fetchOrderById(orderId);
                String encounterUUID = order.getEncounter().getUuid();

                JSONObject data = new JSONObject(sendableData.getJsonData());
                if(sendableData.getDataype().equals(SendableData.DATA_TYPE_CREATE_TEST_ORDER)) {
                    data.put("patient", patient.getUuid());
                    data.put("encounter", encounterUUID);
                }

                new DataSender(context, this, 0,  null, DataSender.REQUEST_TYPE.POST, true).execute(sendableData.getUrlPostfix(), data.toString());
            } else {
                isRunning = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
