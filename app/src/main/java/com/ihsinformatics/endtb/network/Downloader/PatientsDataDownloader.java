package com.ihsinformatics.endtb.network.Downloader;

import android.content.Context;
import android.databinding.DataBindingComponent;

import com.ihsinformatics.endtb.database.Entities.Concept;
import com.ihsinformatics.endtb.database.Entities.Drug;
import com.ihsinformatics.endtb.database.Entities.DrugOrders;
import com.ihsinformatics.endtb.database.Entities.Encounter;
import com.ihsinformatics.endtb.database.Entities.Location;
import com.ihsinformatics.endtb.database.Entities.Obs;
import com.ihsinformatics.endtb.database.Entities.ObsDao;
import com.ihsinformatics.endtb.database.Entities.OrderType;
import com.ihsinformatics.endtb.database.Entities.Patient;
import com.ihsinformatics.endtb.database.Entities.PatientAttributes;
import com.ihsinformatics.endtb.database.Entities.PatientIdentifier;
import com.ihsinformatics.endtb.database.Entities.PersonAttributeType;
import com.ihsinformatics.endtb.database.Entities.User;
import com.ihsinformatics.endtb.database.data.DataProvider;
import com.ihsinformatics.endtb.database.data.DbContentHelper;
import com.ihsinformatics.endtb.network.Config;
import com.ihsinformatics.endtb.network.DataSender;
import com.ihsinformatics.endtb.network.ParamNames;
import com.ihsinformatics.endtb.network.Sendable;
import com.ihsinformatics.endtb.network.listeners.OnPatientsDataDownloadListener;
import com.ihsinformatics.endtb.utils.CredentialsHelper;
import com.ihsinformatics.endtb.utils.ELimsApplication;
import com.ihsinformatics.endtb.utils.Global;
import com.ihsinformatics.endtb.utils.Logger;
import com.ihsinformatics.endtb.utils.OpenMRSMappings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Naveed Iqbal on 12/26/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class PatientsDataDownloader implements Sendable {
    private final int PATIENT_RESPONSE_ID = -1;
    private final String PATIENT_DATA = "patient_data";
    private final String ENCOUNTER_DATA = "encounter_data";
    private final String ORDER_DATA = "order_data";

    private Context context;
    private List<String> patientIdentifiers;
    private OnPatientsDataDownloadListener onPatientsDataDownloadListener;
    private List<Obs> obsList;

    public PatientsDataDownloader(OnPatientsDataDownloadListener onPatientsDataDownloadListener) {
        this.onPatientsDataDownloadListener = onPatientsDataDownloadListener;
        patientIdentifiers = new ArrayList<>();
        // /openmrs/ws/rest/v1/encounter?q=pateintIdentifier&v=full
    }

    public PatientsDataDownloader() {
    }

    public void start(Context context) {
        this.context =  context;
        downloadPatients(null);
    }

    public void start(Context context, Date dateFrom) {
        this.context =  context;
        downloadPatients(Global.MYSQL_DATE_FORMAT.format(dateFrom));
    }

    private void downloadPatients(String date) {
        onPatientsDataDownloadListener.update("Downloading patients records...");
        if (date == null || date.isEmpty())  {
            date = Global.DATA_START_DATE;
        }
        new DataSender(context, this, PATIENT_RESPONSE_ID, PATIENT_DATA, DataSender.REQUEST_TYPE.GET, true).execute(Config.PATIENT_DATA_RESOURCE, "dateFrom="+date+"&limit=99999");
    }

    private void downloadEncounter(int index) {
        onPatientsDataDownloadListener.update("Downloading patients forms...");
        if(patientIdentifiers.size()>0) {
            new DataSender(context, this, index, ENCOUNTER_DATA, DataSender.REQUEST_TYPE.GET, true).execute(Config.ENCOUNTER_RESOURCE, "q="+ patientIdentifiers.get(index));
        }
    }

    @Override
    public void send(JSONArray data, int respId, String responseReference) {
        System.out.println(data);
    }

    @Override
    public void onResponseReceived(JSONObject resp, int respId, String responseReference) throws JSONException {
        // System.out.println(resp);
        if(!resp.has(ParamNames.SERVER_ERROR)) {
            JSONArray data = resp.getJSONArray(ParamNames.SERVER_RESPONSE);
            if(respId == PATIENT_RESPONSE_ID) {
                parseAndSavePatient(data);
            } else {
                parseAndSaveEncounter(data, patientIdentifiers.get(respId));
                // TODO log if a users data is not completely downloaded
            }
        }
        respId++; // setting to start encounter downloading from index 0
        if(patientIdentifiers.size() >respId)
            downloadEncounter(respId);
        else
            onPatientsDataDownloadListener.onPatientsDataDownloaded();
    }

    private void parseAndSaveEncounter(JSONArray data, String patientId) {
        // TODO handle drug order and test order the separate ways
        if(patientId.equals("PAK-001-00137")) {
            System.out.println();
        }
        for(int i=0; i<data.length(); i++) {
            try {
                JSONObject encounterJson = data.getJSONObject(i);
                JSONArray obsArray = encounterJson.getJSONArray(ParamNames.OBS);
                boolean voided = encounterJson.getBoolean(ParamNames.VOIDED);
                JSONArray ordersArray = encounterJson.getJSONArray(ParamNames.ORDERS);
                Date encounterDate = Global.OPENMRS_TIMESTAMP_FORMAT.parse(encounterJson.getString(ParamNames.ENCOUNTER_DATE_TIME));
                Long pId = DbContentHelper.getInstance().fetchPatientIdentifier(patientId, OpenMRSMappings.PATIENT_IDENTIFIER_TYPE_PATIENT_IDENTIFIER.getName()).getPatientId();
                String encounterType = encounterJson.getJSONObject(ParamNames.ENCOUNTER_TYPE).getString(ParamNames.NAME);

                // Not all the encounters needs to be downloaded in the app
                if(!Arrays.asList(Global.SUPPORTED_FORMS).contains(encounterType))
                    continue;

                JSONObject locationJson = encounterJson.getJSONObject(ParamNames.LOCATION);
                Location location = DbContentHelper.getInstance().fetchLocationByUUID(locationJson.getString(ParamNames.UUID));
                String creatorUUID = encounterJson.getJSONObject("auditInfo").getJSONObject(ParamNames.CREATOR).getString(ParamNames.UUID);
                User user = DbContentHelper.getInstance().fetchUserByUUID(creatorUUID);
                if(user == null) {
                    continue;
                }
                // Long id, String uuid, Date encounterDate, Long patientId, Long encounterTypeId, Long locationId, Long createdBy
                Encounter encounter = new Encounter(null,
                        encounterJson.getString(ParamNames.UUID),
                        encounterDate,
                        DbContentHelper.getInstance().fetchPatientById(pId).getPatientId(),
                        DbContentHelper.getInstance().fetchEncounterType(encounterType).getId(),
                        location.getId(),
                        user.getId(),
                        true,
                        voided);
                Long encounterId = ELimsApplication.daoSession.getEncounterDao().insertOrReplace(encounter);

                // saving obs
                obsList = new ArrayList<>();
                createAndAddObsList(obsArray, encounterId, user.getId(), null);
                ELimsApplication.daoSession.getObsDao().insertOrReplaceInTx(obsList);

                // Saving order -if any
                for (int m = 0; m<ordersArray.length(); m++) {
                    JSONObject orderJson = ordersArray.getJSONObject(m);
                    boolean voidedOrder;
                    if(orderJson.has(ParamNames.VOIDED))
                        voidedOrder = orderJson.getBoolean(ParamNames.VOIDED);
                    else
                        voidedOrder = false;
                    // String orderNumber, String uuid, int dose, String doseUnit, String frequency, int quantity, String quantityUnit, String instructions,
                    // int duration, String durationUnit, String route, Long orderTypeId, Long patientId, Long createdBy, boolean voided
                    String orderNumber = orderJson.getString("orderNumber");
                    String frequency = orderJson.getJSONObject("frequency").getString(ParamNames.DISPLAY);
                    String instructions = orderJson.getString("dosingInstructions");
                    String route = orderJson.getString("route");
                    String action = orderJson.getString("action");
                    int dose = orderJson.getInt("dose");
                    String doseUnits = orderJson.getJSONObject("doseUnits").getString(ParamNames.DISPLAY);

                    int quantity = orderJson.getInt("quantity");
                    String quantityUnits = orderJson.getJSONObject("quantityUnits").getString(ParamNames.DISPLAY);
                    int duration;
                    if(orderJson.isNull("duration")) {
                        duration = 0;
                    } else
                        duration = orderJson.getInt("duration");
                    String durationUnits = orderJson.getJSONObject("durationUnits").getString(ParamNames.DISPLAY);

                    String drugConceptUUID = orderJson.getJSONObject(ParamNames.CONCEPT).getString(ParamNames.UUID);
                    System.out.println(drugConceptUUID);
                    Concept concept = DbContentHelper.getInstance().fetchConceptByUUID(drugConceptUUID);
                    if(concept==null) continue;
                    Drug drug = DbContentHelper.getInstance().fetchDrugByConceptId(concept.getId());
                    String uuid = orderJson.getString(ParamNames.UUID);
                    OrderType orderType = DbContentHelper.getInstance().fetchOrderType(OpenMRSMappings.ORDER_TYPE_DRUG_ORDER.getName());

                    Date dateActivated = null;
                    Date scheduledDate = null;
                    Date dateStopped = null;
                    Date autoExpireDate = null;

                    if(!orderJson.isNull(ParamNames.DATE_ACTIVATED)) {
                        String dateActivatedValue = orderJson.getString(ParamNames.DATE_ACTIVATED);
                        dateActivated = Global.OPENMRS_TIMESTAMP_FORMAT.parse(dateActivatedValue);
                    }
                    if(!orderJson.isNull(ParamNames.SCHEDULE_DATE)) {
                        String scheduledDateValue = orderJson.getString(ParamNames.SCHEDULE_DATE);
                        scheduledDate = Global.OPENMRS_TIMESTAMP_FORMAT.parse(scheduledDateValue);
                    }
                    if(!orderJson.isNull(ParamNames.DATE_STOPPED)) {
                        String dateStoppedValue = orderJson.getString(ParamNames.DATE_STOPPED);
                        dateStopped = Global.OPENMRS_TIMESTAMP_FORMAT.parse(dateStoppedValue);
                    }
                    if(!orderJson.isNull(ParamNames.AUTO_EXPIRE_DATE)) {
                        String autoExpireDateValue = orderJson.getString(ParamNames.AUTO_EXPIRE_DATE);
                        autoExpireDate = Global.OPENMRS_TIMESTAMP_FORMAT.parse(autoExpireDateValue);
                    }
                    if(orderType != null && drug != null) {
                        DrugOrders order = new DrugOrders(orderNumber, uuid, dose, doseUnits, frequency, quantity, quantityUnits, instructions,
                                duration, durationUnits, route, action, drug.getId(), orderType.getId(), pId, encounterId, user.getId(), voidedOrder,
                                dateActivated, scheduledDate, dateStopped, autoExpireDate);
                        ELimsApplication.daoSession.getDrugOrdersDao().insertOrReplace(order);
                    }

                }
            } catch (JSONException | ParseException | NullPointerException e) {
                Logger.log(e);
            }
        }
    }

    private void createAndAddObsList(JSONArray obsArray, long encounterId, long userId, String parentObsId) throws JSONException, ParseException {
        for (int j = 0; j < obsArray.length(); j++) {
            JSONObject obsJson = obsArray.getJSONObject(j);
            Obs obs = createObs(obsJson, encounterId, userId, parentObsId);
            String parentUUID = obs.getLocalUUID();
            obsList.add(obs);
            if(obsJson.has(ParamNames.GROUP_MEMBERS) && !obsJson.isNull(ParamNames.GROUP_MEMBERS)) {
                JSONArray groupMembers = obsJson.getJSONArray(ParamNames.GROUP_MEMBERS);
                createAndAddObsList(groupMembers, encounterId, userId, parentUUID);
            }
        }
    }

    private Obs createObs(JSONObject obsJson, long encounterId, long userId, String parentObsId) throws JSONException, ParseException {
        JSONObject conceptJson = obsJson.getJSONObject(ParamNames.CONCEPT);
        String conceptUUID = conceptJson.getString(ParamNames.UUID);
        String value = "";
        boolean voidedObs = obsJson.getBoolean(ParamNames.VOIDED);
        // Handling value
        if (obsJson.has(ParamNames.VALUE) && !obsJson.isNull(ParamNames.VALUE)) {
            Object valueObject = obsJson.get(ParamNames.VALUE);

            if (valueObject instanceof JSONObject) {
                value = obsJson.getJSONObject(ParamNames.VALUE).getString(ParamNames.UUID);
                Concept concept = DbContentHelper.getInstance().fetchConceptByUUID(value);
                if (concept != null)
                    value = concept.getShortName();
            } else {
                value = obsJson.getString(ParamNames.VALUE);
                if (value.length() > 10) {
                    if (value.substring(0, 10).matches(Global.OPENMRS_DATE_FORMAT_REGEX)) {
                        Date date = Global.OPENMRS_TIMESTAMP_FORMAT.parse(value);
                        value = Global.MYSQL_DATE_FORMAT.format(date);
                    }
                }
            }
        }
        Long conceptId = null;
        String obsUUID = obsJson.getString(ParamNames.UUID);
        Concept concept = DbContentHelper.getInstance().fetchConceptByUUID(conceptUUID);
        if(concept != null) {
            conceptId = concept.getId();
        } else {
            String conceptName = conceptJson.getString(ParamNames.DISPLAY);
            Concept newConcept = new Concept(
                    null,
                    conceptName, conceptName,
                    conceptUUID,
                    DataProvider.CONCEPT_DATA_TYPE.UNKNOWN.toString());
            conceptId = ELimsApplication.daoSession.getConceptDao().insert(newConcept);
        }
        // Long id, String value, String uuid, Long creator, Long encounterId, Long conceptId
        Obs obs = new Obs(UUID.randomUUID().toString(), value, obsUUID, userId, encounterId, conceptId, voidedObs, parentObsId);

        return obs;
    }

    public void parseAndSavePatient(JSONArray data) {

        for(int i=0; i<data.length(); i++) {
            JSONObject patientJson = null;
            try {
                patientJson = data.getJSONObject(i);

                boolean voidedPateint = patientJson.getBoolean(ParamNames.VOIDED);
                if(patientJson.has("identifiers")) {//identifiers
                    JSONArray identifiersArray = patientJson.optJSONArray("identifiers");
                    if (identifiersArray== null || identifiersArray.length()==0) continue;

                    JSONObject identifierObj = identifiersArray.getJSONObject(0);

                    if(identifierObj == null) continue;

                    String identifierTypeUUID = identifierObj.getJSONObject("identifierType").getString(ParamNames.UUID);
                    if(identifierTypeUUID.equals(OpenMRSMappings.PATIENT_IDENTIFIER_TYPE_PATIENT_IDENTIFIER.getUuid())) {
                        JSONObject personJson = patientJson.getJSONObject("person");
                        JSONObject nameObject = personJson.getJSONArray("names").getJSONObject(0);
                        //JSONObject addressJson = personJson.getJSONArray("addresses").getJSONObject(0);
                        JSONArray attributesArray = personJson.getJSONArray("attributes");
                        Patient patient = new Patient(null,
                                patientJson.getString(ParamNames.UUID),
                                nameObject.getString("givenName"),
                                nameObject.getString("familyName"),
                                personJson.getString("gender"),
                                personJson.getInt("age"),
                                nameObject.getString(ParamNames.UUID),
                                voidedPateint);
                        Long pid = ELimsApplication.daoSession.getPatientDao().insertOrReplace(patient);

                        boolean isAssignedToLoggedInUser = false;
                        // Saving attributes
                        List<PatientAttributes> attributesList = new ArrayList<>();
                        for(int j=0; j<attributesArray.length(); j++) {
                            JSONObject attributeJson = attributesArray.getJSONObject(j);
                            String attributeValue = attributeJson.getString(ParamNames.VALUE);
                            String attributeTypeUUID = attributeJson.getJSONObject(ParamNames.ATTRIBUTE_TYPES).getString(ParamNames.UUID);
                            String logedInUserPersonUUID = DbContentHelper.getInstance().fetchUserByUsername(CredentialsHelper.getUsername()).getPersonUUID();
                            if(attributeTypeUUID.equals(OpenMRSMappings.ATTRIBUTE_TYPE_TS.getUuid())) {
                                if(attributeValue.equals(logedInUserPersonUUID)) {
                                    isAssignedToLoggedInUser = true;
                                }
                            }
                            PersonAttributeType attributeType = DbContentHelper.getInstance().fetchPersonAttributeTypeByUUID(attributeTypeUUID);
                            // Long id, String value, Long patientId, Long attributeId
                            attributesList.add(new PatientAttributes(
                                    null,
                                    attributeValue,
                                    pid,
                                    attributeType.getAttributeId(),
                                    attributeJson.getString(ParamNames.UUID)));
                        }
                        if(!isAssignedToLoggedInUser) {
                            ELimsApplication.daoSession.getPatientDao().delete(patient);
                            continue;
                        }
                        ELimsApplication.daoSession.getPatientAttributesDao().insertOrReplaceInTx(attributesList);

                        Long identifierTypeId = DbContentHelper.getInstance().fetchIdentifierType(OpenMRSMappings.PATIENT_IDENTIFIER_TYPE_PATIENT_IDENTIFIER.getName()).getId();
                        String identifierString = identifierObj.getString(ParamNames.IDENTIFIER);
                        patientIdentifiers.add(identifierString);
                        PatientIdentifier patientIdentifier = new PatientIdentifier(
                                null,
                                identifierString,
                                true,
                                identifierTypeId,
                                pid
                        );
                        ELimsApplication.daoSession.getPatientIdentifierDao().insertOrReplace(patientIdentifier);

                        /*boolean voidedAddress = addressJson.getBoolean("voided");
                        Address address = new Address( // Long id, String division, String district, String upazilla, String union, String address, Long patientId
                                null,
                                addressJson.getString("stateProvince"),
                                addressJson.getString("countyDistrict"),
                                addressJson.getString("cityVillage"),
                                addressJson.getString("address3"),
                                addressJson.getString("address6"),
                                addressJson.getString(ParamNames.UUID),
                                voidedAddress,
                                pid

                        );
                        ELimsApplication.daoSession.getAddressDao().insertOrReplace(address);*/
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void parseAndSaveContact(JSONObject patientJson) {
            try {
                boolean voidedPateint = patientJson.optBoolean(ParamNames.VOIDED);
                if(patientJson.has("identifiers")) {//identifiers
                    JSONObject identifierObj = patientJson.getJSONArray("identifiers").getJSONObject(0);
                    String identifierTypeUUID = identifierObj.getString("identifierType");
                    if(identifierTypeUUID.equals(OpenMRSMappings.PATIENT_IDENTIFIER_TYPE_CONTACT_IDENTIFIER.getUuid())) {
                        JSONObject personJson = patientJson.getJSONObject("person");
                        JSONObject nameObject = personJson.getJSONArray("names").getJSONObject(0);
                        //JSONObject addressJson = personJson.getJSONArray("addresses").getJSONObject(0);
                        JSONArray attributesArray = personJson.getJSONArray("attributes");
                        Patient patient = new Patient(null,
                                patientJson.optString(ParamNames.UUID),
                                nameObject.getString("givenName"),
                                nameObject.getString("familyName"),
                                personJson.getString("gender"),
                                personJson.optInt("age"),
                                nameObject.optString(ParamNames.UUID),
                                voidedPateint);
                        Long pid = ELimsApplication.daoSession.getPatientDao().insertOrReplace(patient);

                        boolean isAssignedToLoggedInUser = false;
                        // Saving attributes
                        List<PatientAttributes> attributesList = new ArrayList<>();
                        for(int j=0; j<attributesArray.length(); j++) {
                            JSONObject attributeJson = attributesArray.getJSONObject(j);
                            String attributeValue = attributeJson.getString(ParamNames.VALUE);
                            String attributeTypeUUID = attributeJson.getString(ParamNames.ATTRIBUTE_TYPES);
                            String logedInUserPersonUUID = DbContentHelper.getInstance().fetchUserByUsername(CredentialsHelper.getUsername()).getPersonUUID();

                            PersonAttributeType attributeType = DbContentHelper.getInstance().fetchPersonAttributeTypeByUUID(attributeTypeUUID);
                            // Long id, String value, Long patientId, Long attributeId
                            attributesList.add(new PatientAttributes(
                                    null,
                                    attributeValue,
                                    pid,
                                    attributeType.getAttributeId(),
                                    attributeJson.optString(ParamNames.UUID)));
                        }

                        ELimsApplication.daoSession.getPatientAttributesDao().insertOrReplaceInTx(attributesList);

                        Long identifierTypeId = DbContentHelper.getInstance().fetchIdentifierType(OpenMRSMappings.PATIENT_IDENTIFIER_TYPE_CONTACT_IDENTIFIER.getName()).getId();
                        String identifierString = identifierObj.getString(ParamNames.IDENTIFIER);
                        PatientIdentifier patientIdentifier = new PatientIdentifier(
                                null,
                                identifierString,
                                true,
                                identifierTypeId,
                                pid
                        );
                        ELimsApplication.daoSession.getPatientIdentifierDao().insertOrReplace(patientIdentifier);

                        /*boolean voidedAddress = addressJson.getBoolean("voided");
                        Address address = new Address( // Long id, String division, String district, String upazilla, String union, String address, Long patientId
                                null,
                                addressJson.getString("stateProvince"),
                                addressJson.getString("countyDistrict"),
                                addressJson.getString("cityVillage"),
                                addressJson.getString("address3"),
                                addressJson.getString("address6"),
                                addressJson.getString(ParamNames.UUID),
                                voidedAddress,
                                pid

                        );
                        ELimsApplication.daoSession.getAddressDao().insertOrReplace(address);*/
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

    }
}
