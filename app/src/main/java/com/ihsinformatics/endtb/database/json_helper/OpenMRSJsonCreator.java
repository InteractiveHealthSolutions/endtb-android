package com.ihsinformatics.endtb.database.json_helper;

import com.ihsinformatics.endtb.database.Entities.Address;
import com.ihsinformatics.endtb.database.Entities.Concept;
import com.ihsinformatics.endtb.database.Entities.Encounter;
import com.ihsinformatics.endtb.database.Entities.Location;
import com.ihsinformatics.endtb.database.Entities.Obs;
import com.ihsinformatics.endtb.database.Entities.Order;
import com.ihsinformatics.endtb.database.Entities.Patient;
import com.ihsinformatics.endtb.database.Entities.PatientAttributes;
import com.ihsinformatics.endtb.database.Entities.PatientIdentifier;
import com.ihsinformatics.endtb.database.Entities.Role;
import com.ihsinformatics.endtb.database.Entities.User;
import com.ihsinformatics.endtb.database.data.DataProvider;
import com.ihsinformatics.endtb.database.data.DbContentHelper;
import com.ihsinformatics.endtb.database.data.DefaultData;
import com.ihsinformatics.endtb.network.ParamNames;
import com.ihsinformatics.endtb.utils.Global;
import com.ihsinformatics.endtb.utils.Logger;
import com.ihsinformatics.endtb.utils.OpenMRSMappings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Nabil shafi on 12/20/2017.
 */

public class OpenMRSJsonCreator {
    /**
     * Creates a json object acceptable in  OpenMRS RESTfull API
     * @param patient the patient object that needs to be created
     * @param address address of the Patient
     * @param patientAttributes attributes of the patient
     * @param location location where patient is created
     * @see Patient Address PatientAttributes Location
     */

    DefaultData defaultData;

    public JSONObject createPatientJson(Patient patient, Address address, List<PatientAttributes> patientAttributes, Location location) {

        try {
            PatientIdentifier identifier = DbContentHelper.getInstance().fetchPatientIdentifier(patient.getPatientId(), OpenMRSMappings.PATIENT_IDENTIFIER_TYPE_PATIENT_IDENTIFIER.getName());
            JSONObject patientJSON = new JSONObject();
            JSONObject person = new JSONObject();
            JSONArray namesArray = new JSONArray();
            namesArray.put(new JSONObject()
                    .put("givenName", patient.getGivenName())
                    .put("familyName", patient.getFamilyName()));
            JSONArray identifiers = new JSONArray();
            identifiers.put(new JSONObject()
                    .put("identifier", identifier.getIdentifier())
                    .put("identifierType", OpenMRSMappings.PATIENT_IDENTIFIER_TYPE_PATIENT_IDENTIFIER.getUuid())
                    .put("location", location.getUuid())
                    .put("preferred", "true"));
            JSONArray addresses = new JSONArray();
            addresses.put(new JSONObject()
                    .put("stateProvince", address.getCity())
                    .put("countyDistrict", address.getTown())
                    .put("cityVillage", address.getUc())
                    .put("address3", address.getLandMark())
                    .put("address6", address.getAddress()));
            JSONArray attributes = new JSONArray();
            for(PatientAttributes attrib: patientAttributes) {
                attributes.put(new JSONObject()
                        .put("attributeType", attrib.getPersonAttributeType().getUuid())
                        .put("value", attrib.getValue()));
            }

            // Adding in person JSON
            person.put("names", namesArray);
            person.put("gender", patient.getGender());
            int age = patient.getAge();
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, age-(age+age));
            person.put("birthdate", Global.MYSQL_DATE_FORMAT.format(c.getTime()));
            person.put("addresses", addresses);
            person.put("attributes", attributes);
            // Adding in patient JSON
            patientJSON.put("person", person);
            patientJSON.put("identifiers", identifiers);

            return  patientJSON;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public JSONObject createPatientUpdateJson(Patient patient, Address address, List<PatientAttributes> patientAttributes, Location location) {

        try {
            JSONObject patientJSON = new JSONObject();
            JSONObject person = new JSONObject();
            JSONArray namesArray = new JSONArray();
            namesArray.put(new JSONObject()
                    .put("givenName", patient.getGivenName())
                    .put("familyName", patient.getFamilyName())
                    .put(ParamNames.UUID, patient.getNameUUID()));
            JSONArray addresses = new JSONArray();
            addresses.put(new JSONObject()
                    .put("stateProvince", address.getCity())
                    .put("countyDistrict", address.getTown())
                    .put("cityVillage", address.getUc())
                    .put("address3", address.getLandMark())
                    .put("address6", address.getAddress())
                    .put(ParamNames.UUID, address.getUuid()));
            JSONArray attributes = new JSONArray();
            for(PatientAttributes attrib: patientAttributes) {
                attributes.put(new JSONObject()
                        .put("attributeType", attrib.getPersonAttributeType().getUuid())
                        .put("value", attrib.getValue())
                        .put(ParamNames.UUID, attrib.getUuid()));
            }

            // Adding in person JSON
            person.put("names", namesArray);
            person.put("gender", patient.getGender());
            int age = patient.getAge();
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, age-(age+age));
            person.put("birthdate", Global.MYSQL_DATE_FORMAT.format(c.getTime()));
            person.put("addresses", addresses);
            person.put("attributes", attributes);
            // Adding in patient JSON
            patientJSON.put("person", person);

            return  patientJSON;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public JSONObject createProviderJson(Date birthDate, User patient, Role[] roles, Address address, List<PatientAttributes> patientAttributes, Location location) {

        try {
            // PatientIdentifier identifier = DbContentHelper.getInstance().fetchPatientIdentifier(patient.getPatientId(), OpenMRSMappings.PATIENT_IDENTIFIER_TYPE_PATIENT_REGISTRATION.getName());
            JSONObject providerJson = new JSONObject();
            JSONObject person = new JSONObject();
            JSONArray namesArray = new JSONArray();
            namesArray.put(new JSONObject()
                    .put(ParamNames.GIVEN_NAME, patient.getGivenName())
                    .put(ParamNames.FAMILY_NAME, patient.getFamilyName()));

            JSONArray addresses = new JSONArray();
            addresses.put(new JSONObject()
                    .put("cityVillage", address.getCity())
                    .put("address4", address.getTown())
                    .put("address5", address.getUc())
                    .put("address1", address.getAddress())
                    .put("address3", address.getLandMark()));
            JSONArray attributes = new JSONArray();
            for(PatientAttributes attrib: patientAttributes) {
                attributes.put(new JSONObject()
                        .put("attributeType", attrib.getPersonAttributeType().getUuid())
                        .put(ParamNames.VALUE, attrib.getValue()));
            }
            // Creating in roles
            JSONArray rolesArray = new JSONArray();
            for(int i=0; i<roles.length; i++) {
                // JSONObject roleObj = new JSONObject();
                // roleObj.put(ParamNames.ROLE, roles[i].getRoleName());
                rolesArray.put(roles[i].getUuid()/*roleObj*/);
            }
            // Adding in person JSON
            person.put("names", namesArray);
            person.put(ParamNames.GENDER, patient.getGender());
            int age = patient.getAge();
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, age-(age+age));
            person.put("birthdate", Global.MYSQL_DATE_FORMAT.format(birthDate/*c.getTime()*/));
            person.put("addresses", addresses);
            person.put("attributes", attributes);
            // Adding in patient JSON
            providerJson.put("person", person);
            providerJson.put("username", patient.getUserName());
            providerJson.put("password", patient.getPassword());
            providerJson.put(ParamNames.ROLES, rolesArray);

            return  providerJson;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public JSONObject createContactJson(User contact, Patient patient, Location location) {

        /*{
  "personB": "string",
  "personA": "string",
  "relationshipType": "string",
  "endDate": "string",
  "startDate": "string"
}*/

        try {
            JSONObject person = new JSONObject();
            JSONArray namesArray = new JSONArray();
            namesArray.put(new JSONObject()
                    .put(ParamNames.GIVEN_NAME, contact.getGivenName())
                    .put(ParamNames.FAMILY_NAME, contact.getFamilyName()));

            // Adding in person JSON
            person.put("names", namesArray);
            person.put(ParamNames.GENDER, contact.getGender());
            int age = contact.getAge();
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, age-(age+age));
            person.put("birthdate", Global.MYSQL_DATE_FORMAT.format(c.getTime()));

            JSONObject relation = new JSONObject();
            relation.put(ParamNames.PERSON_A, patient.getUuid());
            relation.put(ParamNames.PERSON_B, person);

            return  relation;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public JSONObject createOrderJSON(Order order, Encounter encounter, User user) {

        try {
            JSONObject orderJSON = new JSONObject();
            // orderJSON.put("patient", encounter.getPatient().getUuid());
            orderJSON.put("type", "testorder");
            // orderJSON.put("encounter", encounter.getUuid());
            orderJSON.put("dateActivated", Global.MYSQL_DATE_FORMAT.format(encounter.getEncounterDate()));
            orderJSON.put("orderer", user.getPrividerUUID());
            orderJSON.put("concept", OpenMRSMappings.CONCEPT_TEST_ORDER.getUuid());
            orderJSON.put("careSetting", "c365e560-c3ec-11e3-9c1a-0800200c9a66");
            orderJSON.put("accessionNumber", order.getAccessionNumber());
            return orderJSON;
        } catch (JSONException e) {
            Logger.log(e);
        }

        return null;
    }

    public JSONObject createOrderUpdateJSON(String[] keys, String[] values) {

        try {
            JSONObject orderJSON = new JSONObject();
            // orderJSON.put("patient", encounter.getPatient().getUuid());
            for(int i = 0; i<keys.length; i++) {
                orderJSON.put(keys[i], values[i]);
            }

            return orderJSON;
        } catch (JSONException e) {
            Logger.log(e);
        }

        return null;
    }

    public JSONObject createEncounterJSON(Patient p, Encounter encounter, List<Obs> obsList, User user) {
        DefaultData defaultData = new DefaultData();
        try {
            JSONObject encounterJSON = new JSONObject();
            JSONArray obsArray = new JSONArray();
            for(Obs obs: obsList) {
                Concept valueConcept = defaultData.getConcepts().get(obs.getValue());
                String value = null;
                if(valueConcept == null) {
                    if(!obs.getValue().isEmpty())
                        value = obs.getValue();
                    else
                        continue; // do not add observation
                }
                else
                    value = valueConcept.getUuid();
                JSONObject requestObject = new JSONObject()
                        .put("concept", obs.getConceptSimply().getUuid())
                        .put("value", value)
                        .put("voided", obs.getVoided());
                // to update the existing observation in openmrs
                if (obs.getUuid()!=null)
                    requestObject.put(ParamNames.UUID, obs.getUuid());
                obsArray.put(requestObject);
            }

            JSONArray providersArray = new JSONArray();
            providersArray.put(new JSONObject().put("provider", user.getUuid()));

            encounterJSON.put("encounterDatetime", Global.MYSQL_DATE_FORMAT.format(encounter.getEncounterDate()));
            // encounterJSON.put("patient", encounter.getPatient().getUuid());
            // encounterJSON.put("uuid", encounter.getUuid());
            encounterJSON.put("location", encounter.getLocation().getUuid());
            encounterJSON.put("encounterType", encounter.getEncounterType().getUuid());
            encounterJSON.put("voided", false);
            encounterJSON.put(ParamNames.OBS, obsArray);
            encounterJSON.put(ParamNames.PATIENT, p.getUuid());
            // encounterJSON.put("encounterProviders", providersArray);
            return encounterJSON;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public JSONObject createDTMEncounterJSON(Patient p, Encounter encounter, List<Obs> obsList, User user, JSONArray obsJsonArray) {
        defaultData = new DefaultData();
        try {
            JSONObject encounterJSON = new JSONObject();
            JSONArray obsArray = new JSONArray();
            for(Obs obs: obsList) {
                JSONObject obsObject = createObsJson(obs, obsList, null);
                if(obsObject!=null)
                    obsArray.put(obsObject);
            }

            JSONArray providersArray = new JSONArray();
            providersArray.put(new JSONObject().put(ParamNames.PROVIDER, user.getUuid()));

            encounterJSON.put(ParamNames.ENCOUNTER_DATE_TIME, Global.MYSQL_DATE_FORMAT.format(encounter.getEncounterDate()));
            // encounterJSON.put("patient", encounter.getPatient().getUuid());
            // encounterJSON.put("uuid", encounter.getUuid());
            encounterJSON.put(ParamNames.LOCATION, encounter.getLocation().getUuid());
            encounterJSON.put(ParamNames.ENCOUNTER_TYPE, encounter.getEncounterType().getUuid());
            encounterJSON.put(ParamNames.VOIDED, false);
            encounterJSON.put(ParamNames.OBS, obsArray);
            encounterJSON.put(ParamNames.PATIENT, p.getUuid());
            // encounterJSON.put("encounterProviders", providersArray);
            return encounterJSON;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private JSONObject createObsJson(Obs obs, List<Obs> obsList, Obs parentObs) throws JSONException {
        if(parentObs == null && obs.getParentObsLocalUUID() != null) {
            return null; // User needs to pass parentObs, if parentObsLocalUUID is not null
        }
        JSONObject parentJSONObj = createObs(obs);
        JSONArray childrenArray = new JSONArray();
        for(Obs cObs: obsList) {
            if(cObs.getParentObsLocalUUID()!=null
                    && cObs.getParentObsLocalUUID().equalsIgnoreCase(obs.getLocalUUID())) {
                childrenArray.put(createObsJson(cObs, obsList, obs)); // what if there are further nested obs
            }
        }
        if(childrenArray.length()>0)
            parentJSONObj.put(ParamNames.GROUP_MEMBERS, childrenArray);

        return parentJSONObj;
    }

    private JSONObject createObs(Obs obs) throws JSONException {
        // obs.getConcept().getDataType().equals(DataProvider.CONCEPT_DATA_TYPE.C)
        JSONObject requestObject = null;
        String value = null;

        if(obs.getConcept().getDataType().equals(DataProvider.CONCEPT_DATA_TYPE.CODED)) {
            Concept valueConcept = defaultData.getConcepts().get(obs.getValue());
            if (valueConcept != null)
                value = valueConcept.getUuid();
            else
                value = obs.getValue();
        } else
            value = obs.getValue();

        requestObject = new JSONObject()
                .put(ParamNames.CONCEPT, obs.getConcept().getUuid())
                .put(ParamNames.VALUE, value)
                .put(ParamNames.VOIDED, obs.getVoided());
        // to update the existing observation in openmrs
        if (obs.getUuid()!=null)
            requestObject.put(ParamNames.UUID, obs.getUuid());
        // obsArray.put(requestObject);
        return requestObject;
    }
}
