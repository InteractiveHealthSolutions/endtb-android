package com.ihsinformatics.endtb.network.Downloader;

import android.content.Context;

import com.ihsinformatics.endtb.database.Entities.Drug;
import com.ihsinformatics.endtb.database.Entities.DrugDao;
import com.ihsinformatics.endtb.database.Entities.EncounterType;
import com.ihsinformatics.endtb.database.Entities.EncounterTypeDao;
import com.ihsinformatics.endtb.database.Entities.IdentifierType;
import com.ihsinformatics.endtb.database.Entities.IdentifierTypeDao;
import com.ihsinformatics.endtb.database.Entities.Location;
import com.ihsinformatics.endtb.database.Entities.LocationAttributeType;
import com.ihsinformatics.endtb.database.Entities.LocationAttributeTypeDao;
import com.ihsinformatics.endtb.database.Entities.LocationTag;
import com.ihsinformatics.endtb.database.Entities.LocationTagDao;
import com.ihsinformatics.endtb.database.Entities.OrderType;
import com.ihsinformatics.endtb.database.Entities.OrderTypeDao;
import com.ihsinformatics.endtb.database.Entities.Permission;
import com.ihsinformatics.endtb.database.Entities.PermissionDao;
import com.ihsinformatics.endtb.database.Entities.PersonAttributeType;
import com.ihsinformatics.endtb.database.Entities.PersonAttributeTypeDao;
import com.ihsinformatics.endtb.database.Entities.Role;
import com.ihsinformatics.endtb.database.Entities.User;
import com.ihsinformatics.endtb.database.Entities.UserDao;
import com.ihsinformatics.endtb.database.Entities.UserPermissions;
import com.ihsinformatics.endtb.database.Entities.UserPermissionsDao;
import com.ihsinformatics.endtb.database.data.DbContentHelper;
import com.ihsinformatics.endtb.database.json_helper.JsonToObjectParser;
import com.ihsinformatics.endtb.database.json_helper.UserHelper;
import com.ihsinformatics.endtb.network.Config;
import com.ihsinformatics.endtb.network.DataSender;
import com.ihsinformatics.endtb.network.ParamNames;
import com.ihsinformatics.endtb.network.Sendable;
import com.ihsinformatics.endtb.network.listeners.OnMetaDataDownloadListener;
import com.ihsinformatics.endtb.utils.CredentialsHelper;
import com.ihsinformatics.endtb.utils.ELimsApplication;
import com.ihsinformatics.endtb.utils.views.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Naveed Iqbal on 10/26/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class MetaDataDownloader  implements Sendable {

    private Context context;
    OnMetaDataDownloadListener metaDataDownloadListener;

    public MetaDataDownloader(Context context, OnMetaDataDownloadListener metaDataDownloadListener) {
        this.context = context;
        this.metaDataDownloadListener = metaDataDownloadListener;
    }


    public void start() {
        downloadPersonAttributeTypes();
    }

    private void downloadPersonAttributeTypes() {
        new DataSender(context, this, 0, null, DataSender.REQUEST_TYPE.GET).execute(Config.PERSON_ATTRIBUTE_TYPE);
    }

    private void downloadEncounterTypes() {
        new DataSender(context, this, 1, null, DataSender.REQUEST_TYPE.GET).execute(Config.ENCOUNTER_TYPE_RESOURCE);
    }

    private void downloadPrivileges() {
        new DataSender(context, this, 2, null, DataSender.REQUEST_TYPE.GET).execute(Config.PRIVILEGE_RESOURCE, "limit=1000");
    }

    private void downloadIdentifierTypes() {
        new DataSender(context, this, 3, null, DataSender.REQUEST_TYPE.GET).execute(Config.PATIENT_IDENTIFIER_TYPE_RESOURCE);
    }

    private void downloadOrderTypes() {
        new DataSender(context, this, 4, null, DataSender.REQUEST_TYPE.GET).execute(Config.ORDER_TYPE);
    }
    private void downloadLocationTags() {
        new DataSender(context, this, 5, null, DataSender.REQUEST_TYPE.GET).execute(Config.LOCATION_TAG);
    }
    // /openmrs/ws/rest/v1/user
    private void downloadLocationAttributeTypes() {
        new DataSender(context, this, 6, null, DataSender.REQUEST_TYPE.GET).execute(Config.LOCATION_ATTRIBUTE_TYPE_RESOURCE);
    }

    private void downloadUsers() {
        metaDataDownloadListener.update("Fetching users information...");
        new DataSender(context, this, 11, null, DataSender.REQUEST_TYPE.GET, true).execute(Config.USER_DATA_RESOURCE);
    }

    private void downloadDrugs() {
        metaDataDownloadListener.update("Fetching users information...");
        new DataSender(context, this, 12, null, DataSender.REQUEST_TYPE.GET, true).execute(Config.DRUG_RESOURCE);
    }

    private void downloadRoles() {
        new DataSender(context, this, 10, null, DataSender.REQUEST_TYPE.GET).execute(Config.ROLE_RESOURCE);
    }

    private void downloadAddressHierarchy(String startIndex) {
        metaDataDownloadListener.update("Downloading address hierarchy...");
        new DataSender(context, this, 8, null, DataSender.REQUEST_TYPE.GET, true).execute(Config.LOCATION_RESOURCE, "tags=address&startIndex="+startIndex+"&limit=1000");
    }

    private void downloadFacilities(String startIndex) {
        metaDataDownloadListener.update("Downloading facilities...");
        new DataSender(context, this, 9, null, DataSender.REQUEST_TYPE.GET, true).execute(Config.LOCATION_RESOURCE, "tags=facility,Login%20Location&startIndex="+startIndex+"&limit=1000");
    }

    @Override
    public void send(JSONArray data, int respId, String responseReference) {
        // TODO useless for this project or class at least
    }

    @Override
    public void onResponseReceived(JSONObject resp, int respId, String responseReference) throws JSONException {

        if (resp.has(ParamNames.SERVER_ERROR)) {
            Toast.makeText(context, resp.getString(ParamNames.SERVER_ERROR), Toast.LENGTH_LONG).show();
            DbContentHelper.getInstance().reCreateDatabase();
            start();
            return;
        }

        switch (respId) {
            case 0:
                PersonAttributeTypeDao attributesDao = ELimsApplication.daoSession.getPersonAttributeTypeDao();
                JSONArray attributeTypes = resp.getJSONArray(ParamNames.SERVER_RESPONSE);
                List<PersonAttributeType> attributes = JsonToObjectParser.parseAttributesFromJson(attributeTypes);
                attributesDao.insertOrReplaceInTx(attributes);
                downloadEncounterTypes();
                return;
            case 1:
                EncounterTypeDao encounterDao = ELimsApplication.daoSession.getEncounterTypeDao();
                JSONArray encounterTypesArray = resp.getJSONArray(ParamNames.SERVER_RESPONSE);
                List<EncounterType> encounterTypes = JsonToObjectParser.parseEncounterTypesFromJson(encounterTypesArray);
                encounterDao.insertOrReplaceInTx(encounterTypes);
                downloadPrivileges();
                return;
            case 2:
                PermissionDao permissionDao = ELimsApplication.daoSession.getPermissionDao();
                JSONArray permissionsArray = resp.getJSONArray(ParamNames.SERVER_RESPONSE);
                List<Permission> permissions = JsonToObjectParser.parsePermissionsFromJson(permissionsArray);
                permissionDao.insertOrReplaceInTx(permissions);
                downloadIdentifierTypes();
                return;
            case 3:
                IdentifierTypeDao identifierTypeDao = ELimsApplication.daoSession.getIdentifierTypeDao();
                JSONArray identifierTypesArray = resp.getJSONArray(ParamNames.SERVER_RESPONSE);
                List<IdentifierType> identifierTypes = JsonToObjectParser.parseIdentifierTypesFromJson(identifierTypesArray);
                identifierTypeDao.insertOrReplaceInTx(identifierTypes);
                downloadOrderTypes();
                return;
            case 4:
                OrderTypeDao orderTypeDao = ELimsApplication.daoSession.getOrderTypeDao();
                JSONArray orderTypesArray = resp.getJSONArray(ParamNames.SERVER_RESPONSE);
                List<OrderType> orderTypes = JsonToObjectParser.parseOrderTypesFromJson(orderTypesArray);
                orderTypeDao.insertOrReplaceInTx(orderTypes);
                downloadLocationTags();
                return;
            case 5:
                LocationTagDao locationTagDao = ELimsApplication.daoSession.getLocationTagDao();
                JSONArray locationTagsArray = resp.getJSONArray(ParamNames.SERVER_RESPONSE);
                List<LocationTag> locationTags = JsonToObjectParser.parseLocationTagsFromJson(locationTagsArray);
                locationTagDao.insertOrReplaceInTx(locationTags);
                downloadLocationAttributeTypes();
                return;
            case 6:
                LocationAttributeTypeDao locationAttributeTypeDao = ELimsApplication.daoSession.getLocationAttributeTypeDao();
                JSONArray locationAttributeTypesArray = resp.getJSONArray(ParamNames.SERVER_RESPONSE);
                List<LocationAttributeType> locationAttributeTypes = JsonToObjectParser.parseLocationAttributeTypesFromJson(locationAttributeTypesArray);
                locationAttributeTypeDao.insertOrReplaceInTx(locationAttributeTypes);
                downloadAddressHierarchy("0");
                return;
            case 8:
                JSONArray locationArray = resp.getJSONArray(ParamNames.SERVER_RESPONSE);
                List<Location> locations = JsonToObjectParser.parseLocationsFromJson(locationArray);
                DbContentHelper.getInstance().insertLocations(locations);
                downloadFacilities("0");
                return;
            case 9:
                JSONArray facilitiesArray = resp.getJSONArray(ParamNames.SERVER_RESPONSE);
                List<Location> facilities = JsonToObjectParser.parseLocationsFromJson(facilitiesArray);
                DbContentHelper.getInstance().insertLocations(facilities);
                downloadRoles();
                return;
            case 10:
                JSONArray rolesArray = resp.getJSONArray(ParamNames.SERVER_RESPONSE);
                List<Role> roles = JsonToObjectParser.parseRolesFromJson(rolesArray);
                ELimsApplication.daoSession.getRoleDao().insertOrReplaceInTx(roles);
                downloadUsers();
                return;
            case 11:
                UserDao userDao = ELimsApplication.daoSession.getUserDao();
                JSONArray usersArray = resp.getJSONArray(ParamNames.SERVER_RESPONSE);
                List<User> userList = new UserHelper().parseUsersFromJson(usersArray);
                User user = DbContentHelper.getInstance().fetchUserByUsername(CredentialsHelper.getUsername());
                userList.remove(user);
                userDao.insertOrReplaceInTx(userList);

                UserPermissionsDao userPermissionsDao = ELimsApplication.daoSession.getUserPermissionsDao();
                List<UserPermissions> userPermissions = new UserHelper().parsePermissionsFromUserJson(usersArray);
                userPermissionsDao.insertOrReplaceInTx(userPermissions);
                String labUUID = DbContentHelper.getInstance().fetchUserByUsername(CredentialsHelper.getUsername()).getLabUUID();
                Location userLocation = DbContentHelper.getInstance().fetchLocationByUUID(labUUID);
                downloadDrugs();
                return;
            case 12:
                JSONArray drugsArray = resp.getJSONArray(ParamNames.SERVER_RESPONSE);
                DrugDao drugDao = ELimsApplication.daoSession.getDrugDao();
                List<Drug> drugList = JsonToObjectParser.parseDrugsFromJson(drugsArray);
                drugDao.insertOrReplaceInTx(drugList);
                break;

        }

        metaDataDownloadListener.onMetaDataDownloaded();
    }
}
