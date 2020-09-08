package com.ihsinformatics.endtb.database.json_helper;

import com.ihsinformatics.endtb.database.Entities.Concept;
import com.ihsinformatics.endtb.database.Entities.ConceptDao;
import com.ihsinformatics.endtb.database.Entities.Drug;
import com.ihsinformatics.endtb.database.Entities.EncounterType;
import com.ihsinformatics.endtb.database.Entities.IdentifierType;
import com.ihsinformatics.endtb.database.Entities.Location;
import com.ihsinformatics.endtb.database.Entities.LocationAttribute;
import com.ihsinformatics.endtb.database.Entities.LocationAttributeType;
import com.ihsinformatics.endtb.database.Entities.LocationTag;
import com.ihsinformatics.endtb.database.Entities.OrderType;
import com.ihsinformatics.endtb.database.Entities.Permission;
import com.ihsinformatics.endtb.database.Entities.PersonAttributeType;
import com.ihsinformatics.endtb.database.Entities.Role;
import com.ihsinformatics.endtb.database.data.DataProvider;
import com.ihsinformatics.endtb.database.data.DbContentHelper;
import com.ihsinformatics.endtb.database.gson.Attribute;
import com.ihsinformatics.endtb.database.gson.ParentLocation;
import com.ihsinformatics.endtb.database.gson.Tag;
import com.ihsinformatics.endtb.network.ParamNames;
import com.ihsinformatics.endtb.utils.ELimsApplication;
import com.ihsinformatics.endtb.utils.Global;
import com.ihsinformatics.endtb.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Naveed Iqbal on 10/28/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class JsonToObjectParser<T> {
    // TODO use a generic method to parse objects

    public static List<PersonAttributeType> parseAttributesFromJson(JSONArray jsonArray) {
        List<PersonAttributeType> attributes = new ArrayList<>();
        try {
            for(int i=0; i<jsonArray.length(); i++) {
                String uuid = jsonArray.getJSONObject(i).getString(ParamNames.UUID);
                String display = jsonArray.getJSONObject(i).getString(ParamNames.DISPLAY);
                attributes.add(new PersonAttributeType(null, display, uuid));
            }
        } catch (JSONException e) {
            Logger.log(e);
        }

        return attributes;
    }

    public static List<EncounterType> parseEncounterTypesFromJson(JSONArray jsonArray) {
        List<EncounterType> attributes = new ArrayList<>();
        try {
            for(int i=0; i<jsonArray.length(); i++) {
                String uuid = jsonArray.getJSONObject(i).getString(ParamNames.UUID);
                String display = jsonArray.getJSONObject(i).getString(ParamNames.DISPLAY);
                attributes.add(new EncounterType(null, display, uuid));
            }
        } catch (JSONException e) {
            Logger.log(e);
        }

        return attributes;
    }

    public static List<Permission> parsePermissionsFromJson(JSONArray jsonArray) {
        List<Permission> permissions = new ArrayList<>();
        try {
            for(int i=0; i<jsonArray.length(); i++) {
                String uuid = jsonArray.getJSONObject(i).getString(ParamNames.UUID);
                String display = jsonArray.getJSONObject(i).getString(ParamNames.DISPLAY);
                permissions.add(new Permission(null, display, uuid));
            }
        } catch (JSONException e) {
            Logger.log(e);
        }

        return permissions;
    }

    public static List<IdentifierType> parseIdentifierTypesFromJson(JSONArray jsonArray) {
        List<IdentifierType> identifierTypesList = new ArrayList<>();
        try {
            for(int i=0; i<jsonArray.length(); i++) {
                String uuid = jsonArray.getJSONObject(i).getString(ParamNames.UUID);
                String display = jsonArray.getJSONObject(i).getString(ParamNames.DISPLAY);
                identifierTypesList.add(new IdentifierType(null, display, uuid));
            }
        } catch (JSONException e) {
            Logger.log(e);
        }

        return identifierTypesList;
    }

    public static List<OrderType> parseOrderTypesFromJson(JSONArray jsonArray) {
        List<OrderType> orderTypeList = new ArrayList<>();
        try {
            for(int i=0; i<jsonArray.length(); i++) {
                String uuid = jsonArray.getJSONObject(i).getString(ParamNames.UUID);
                String display = jsonArray.getJSONObject(i).getString(ParamNames.DISPLAY);
                orderTypeList.add(new OrderType(null, display, uuid));
            }
        } catch (JSONException e) {
            Logger.log(e);
        }

        return orderTypeList;
    }

    public static List<LocationTag> parseLocationTagsFromJson(JSONArray jsonArray) {
        List<LocationTag> locationTagList = new ArrayList<>();
        try {
            for(int i=0; i<jsonArray.length(); i++) {
                String uuid = jsonArray.getJSONObject(i).getString(ParamNames.UUID);
                String display = jsonArray.getJSONObject(i).getString(ParamNames.DISPLAY);
                locationTagList.add(new LocationTag(null, display, uuid));
            }
        } catch (JSONException e) {
            Logger.log(e);
        }

        return locationTagList;
    }

    public static List<LocationAttributeType> parseLocationAttributeTypesFromJson(JSONArray jsonArray) {
        List<LocationAttributeType> locationAttributeTypeList = new ArrayList<>();
        try {
            for(int i=0; i<jsonArray.length(); i++) {
                String uuid = jsonArray.getJSONObject(i).getString(ParamNames.UUID);
                String display = jsonArray.getJSONObject(i).getString(ParamNames.DISPLAY);
                locationAttributeTypeList.add(new LocationAttributeType(null, display, uuid));
            }
        } catch (JSONException e) {
            Logger.log(e);
        }

        return locationAttributeTypeList;
    }

    public static List<Role> parseRolesFromJson(JSONArray jsonArray) {
        List<Role> roleList = new ArrayList<>();
        try {
            for(int i=0; i<jsonArray.length(); i++) {
                String uuid = jsonArray.getJSONObject(i).getString(ParamNames.UUID);
                String display = jsonArray.getJSONObject(i).getString(ParamNames.DISPLAY);
                roleList.add(new Role(null, display, uuid));
            }
        } catch (JSONException e) {
            Logger.log(e);
        }

        return roleList;
    }
    public static List<Drug> parseDrugsFromJson(JSONArray jsonArray) {
        List<Drug> drugsList = new ArrayList<>();
        ConceptDao conceptDao = ELimsApplication.daoSession.getConceptDao();
        try {
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject drugObj = jsonArray.getJSONObject(i);
                String uuid = drugObj.getString(ParamNames.UUID);
                String name = drugObj.getString(ParamNames.NAME);
                String dosageForm = drugObj.getString(ParamNames.DOSAGE_FORM);
                JSONObject conceptObject = drugObj.getJSONObject(ParamNames.CONCEPT);
                String conceptName = conceptObject.getString(ParamNames.DISPLAY);
                String conceptUUID = conceptObject.getString(ParamNames.UUID);
                Concept concept = new Concept(null, conceptName, conceptName, conceptUUID, DataProvider.CONCEPT_DATA_TYPE.NONE.toString());
                long conceptId = conceptDao.insert(concept);
                drugsList.add(new Drug(null, name, uuid, dosageForm, conceptId));
            }
        } catch (JSONException e) {
            Logger.log(e);
        }

        return drugsList;
    }



    public static Location parseLocationsFromGsonLocation(com.ihsinformatics.endtb.database.gson.Location gsonLocation) {

        try {
            String parentLocationName = null;
            ParentLocation parentLocation = gsonLocation.getParentLocation();
            if(parentLocation != null)
                parentLocationName = parentLocation.getUuid();

            // String dateCreatedString = gsonLocation.getAuditInfo().getDateCreated();
            // Date dateCreated = Global.OPENMRS_TIMESTAMP_FORMAT.parse(dateCreatedString);
            Location location = new Location(
                    null,
                    gsonLocation.getUuid(),
                    gsonLocation.getDisplay(),
                    gsonLocation.getCountry(),
                    gsonLocation.getStateProvince()==null?null:gsonLocation.getStateProvince().toString(),
                    gsonLocation.getCountyDistrict()==null?null:gsonLocation.getCountyDistrict().toString(),
                    gsonLocation.getCityVillage()==null?null:gsonLocation.getCityVillage().toString(),
                    null,
                    null,
                    /*gsonLocation.getAddress1()==null?null:gsonLocation.getAddress1().toString(),
                    gsonLocation.getAddress2()==null?null:gsonLocation.getAddress2().toString(),*/
                    gsonLocation.getAddress3()==null?null:gsonLocation.getAddress3().toString(),
                    gsonLocation.getAddress4()==null?null:gsonLocation.getAddress4().toString(),
                    gsonLocation.getAddress5()==null?null:gsonLocation.getAddress5().toString(),
                    gsonLocation.getAddress6()==null?null:gsonLocation.getAddress6().toString(),
                    parentLocationName,
                    new Date());
            List<Tag> tags = gsonLocation.getTags();

            if(tags!=null) {
                ArrayList<LocationTag> tagsList = new ArrayList<>();
                for(Tag tag: tags) {
                    LocationTag locationTag = DbContentHelper.getInstance().fetchLocationTagByName(tag.getDisplay());
                    tagsList.add(locationTag);
                }
                location.setLocationTags(tagsList);
            }

            List<Attribute> attributeList = gsonLocation.getAttributes();
            if(attributeList!=null) {
                ArrayList<LocationAttribute> LocationAttributeList = new ArrayList<>();

                for(Attribute locationAttribute: attributeList) {
                    String valueString = locationAttribute.getValue();
                    String uuidString = locationAttribute.getUuid();
                    // JSONObject attributeTypeJson = locationAttribute.getJSONObject(ParamNames.ATTRIBUTE_TYPES);
                    String attributeTypeString = locationAttribute.getAttributeType().getDisplay();
                    LocationAttributeType locationAttributeType = DbContentHelper.getInstance().fetchLocationAttributeTypeByName(attributeTypeString);
                    if(locationAttributeType!=null)
                        LocationAttributeList.add(new LocationAttribute(null, valueString, uuidString, location.getId(),  locationAttributeType.getId()));
                }
                location.setLocationAttributes(LocationAttributeList);
            }

            return location;
        } catch (Exception e) {
            Logger.log(e);
        }

        return null;
    }

    public static LinkedList<Location> parseLocationsFromJson(JSONArray jsonArray) {
        LinkedList<Location> locationLinkedList = new LinkedList<>();
        try {
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String parentLocationName = null;
                if(!obj.isNull(ParamNames.PARENT_LOCATION)) {
                    JSONObject parentLocation = obj.getJSONObject(ParamNames.PARENT_LOCATION);
                    parentLocationName = parentLocation.getString(ParamNames.UUID);
                }
                String dateCreatedString = obj.getJSONObject(ParamNames.AUDIT_INFO).getString(ParamNames.DATE_CREATED);
                Date dateCreated = Global.OPENMRS_TIMESTAMP_FORMAT.parse(dateCreatedString);
                Location location = new Location(
                        null,
                        obj.getString(ParamNames.UUID),
                        obj.getString(ParamNames.DISPLAY),
                        obj.getString(ParamNames.COUNTRY),
                        obj.getString(ParamNames.STATE),
                        obj.getString(ParamNames.DISTRICT),
                        obj.getString(ParamNames.CITY),
                        obj.getString(ParamNames.ADDRESS1),
                        obj.getString(ParamNames.ADDRESS2),
                        obj.getString(ParamNames.ADDRESS3),
                        obj.getString(ParamNames.ADDRESS4),
                        obj.getString(ParamNames.ADDRESS5),
                        obj.getString(ParamNames.ADDRESS6),
                        parentLocationName,
                        dateCreated);
                if(!obj.isNull(ParamNames.LOCATION_TAGS)) {
                    ArrayList<LocationTag> tagsList = new ArrayList<>();
                    JSONArray tagsArray = obj.getJSONArray(ParamNames.LOCATION_TAGS);
                    for(int j=0; j<tagsArray.length(); j++) {
                        JSONObject tag = tagsArray.getJSONObject(j);
                        LocationTag locationTag = DbContentHelper.getInstance().fetchLocationTagByName(tag.getString(ParamNames.DISPLAY));
                        tagsList.add(locationTag);
                    }
                    location.setLocationTags(tagsList);
                }

                if(!obj.isNull(ParamNames.ATTRIBUTES)) {
                    ArrayList<LocationAttribute> LocationAttributeList = new ArrayList<>();
                    JSONArray locationAttributesArray = obj.getJSONArray(ParamNames.ATTRIBUTES);
                    for(int j=0; j<locationAttributesArray.length(); j++) {
                        JSONObject locationAttribute = locationAttributesArray.getJSONObject(j);
                        String valueString = locationAttribute.getString(ParamNames.VALUE);
                        String uuidString = locationAttribute.getString(ParamNames.UUID);
                        JSONObject attributeTypeJson = locationAttribute.getJSONObject(ParamNames.ATTRIBUTE_TYPES);
                        String attributeTypeString = attributeTypeJson.getString(ParamNames.DISPLAY);
                        LocationAttributeType locationAttributeType = DbContentHelper.getInstance().fetchLocationAttributeTypeByName(attributeTypeString);
                        if(locationAttributeType!=null)
                            LocationAttributeList.add(new LocationAttribute(null, valueString, uuidString, location.getId(),  locationAttributeType.getId()));
                    }
                    location.setLocationAttributes(LocationAttributeList);
                }

                locationLinkedList.add(location);
            }
        } catch (JSONException e) {
            Logger.log(e);
        } catch (ParseException e) {
            Logger.log(e);
        }

        return locationLinkedList;
    }
}
