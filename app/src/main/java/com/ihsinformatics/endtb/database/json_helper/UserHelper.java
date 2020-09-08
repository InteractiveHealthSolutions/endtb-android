package com.ihsinformatics.endtb.database.json_helper;

import com.ihsinformatics.endtb.database.Entities.Permission;
import com.ihsinformatics.endtb.database.Entities.User;
import com.ihsinformatics.endtb.database.Entities.UserDao;
import com.ihsinformatics.endtb.database.Entities.UserPermissions;
import com.ihsinformatics.endtb.database.data.DbContentHelper;
import com.ihsinformatics.endtb.network.ParamNames;
import com.ihsinformatics.endtb.utils.ELimsApplication;
import com.ihsinformatics.endtb.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naveed Iqbal on 10/27/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class UserHelper {

    public List<UserPermissions> parsePermissionsFromUserJson(JSONArray usersArray) {
        if (usersArray == null)
            return null;

        List<UserPermissions> permissions = new ArrayList<>();
        String username = null;

        for (int i = 0; i < usersArray.length(); i++) {
            try {
                JSONObject jsonObject = usersArray.getJSONObject(i);
                username = jsonObject.getString("display");
                boolean retired = jsonObject.getBoolean("retired");
                User user = DbContentHelper.getInstance().fetchUserByUsername(username);
                if(user == null)
                    continue;
                JSONArray privilegesArray = jsonObject.getJSONArray("privileges");
                for(int j=0; j<privilegesArray.length(); j++) {
                    JSONObject privilege = privilegesArray.getJSONObject(j);
                    String name = privilege.getString(ParamNames.NAME);
                    Permission permission = DbContentHelper.getInstance().fetchPermissionByPermissionName(name);
                    UserPermissions userPermission = new UserPermissions(null, user.getId(), permission.getId(), retired);
                    permissions.add(userPermission);
                }
            } catch (JSONException | NullPointerException e) {
                Logger.log(e);
                // System.out.println("============================== "+username+" ==============================");
            }
        }


        return permissions;
    }

    public List<User> parseUsersFromJson(JSONArray usersArray) {
        if (usersArray == null)
            return null;
        List<User> users = null;
        List<String> addedUsersUUids = new ArrayList<>();
        try {
            users = new ArrayList<>();
            for(int i =0; i<usersArray.length(); i++) {
                JSONObject jsonObject = usersArray.getJSONObject(i);
                String uuid = jsonObject.getString(ParamNames.UUID);
                if(addedUsersUUids.contains(uuid))
                    continue;
                addedUsersUUids.add(uuid);
                String username = jsonObject.getString("display");
                User user = parseUserFromJson(username, null, jsonObject);
                if (user!=null && !username.equals("scheduler") && !username.equals("daemon"))
                    users.add(user);
            }
        } catch (JSONException e) {
            Logger.log(e);
        }

        return users;
    }

    public static User parseUserFromJson(String username, String password, JSONObject jsonObject) {
        User user = null;
        try {
            if(!jsonObject.has(ParamNames.PROVIDER))
                return  null;
            if(jsonObject.get(ParamNames.PROVIDER) == null)
                return  null;
            if(jsonObject.getString(ParamNames.PROVIDER).equals("null"))
                return  null;
            String labUUID = null;
            String uuid = jsonObject.getString(ParamNames.UUID);
            boolean retired = jsonObject.getBoolean("retired");
            String providerUUID = jsonObject.getJSONObject(ParamNames.PROVIDER).getString(ParamNames.UUID);
            String personUUID = jsonObject.getJSONObject(ParamNames.PERSON).getString(ParamNames.UUID);
            if(!jsonObject.isNull(ParamNames.LOCATION))
                labUUID = jsonObject.getJSONObject(ParamNames.LOCATION).getString(ParamNames.UUID);
            JSONObject personJson = jsonObject.getJSONObject(ParamNames.PERSON);

            JSONArray personNames = personJson.getJSONArray(ParamNames.NAMES);
            if (personNames==null || personNames.length() == 0)
                return null;
            JSONObject nameObject = personNames.getJSONObject(0);
            String name = nameObject.getString(ParamNames.GIVEN_NAME);
            String familyName = nameObject.getString(ParamNames.FAMILY_NAME);
            int age = personJson.isNull(ParamNames.AGE)?0:personJson.getInt(ParamNames.AGE);
            String gender = personJson.getString(ParamNames.GENDER).toLowerCase().startsWith("m")?"Male":"Female";
            user = new User(
                    null,
                    username,
                    password,
                    name,
                    familyName,
                    gender,
                    age,
                    uuid,
                    providerUUID, personUUID, null, labUUID, retired);
        } catch (JSONException e) {
            Logger.log(e);
        }

        return user;
    }

    public static User isValidUser(String username, String password) {
        UserDao userDao = ELimsApplication.daoSession.getUserDao();
        List<User> user = userDao.queryBuilder()
                .where(UserDao.Properties.UserName.like(username), UserDao.Properties.Password.eq(password))
                .list();

        if(user.size()>0)
            return user.get(0);

        return null;
    }
}
