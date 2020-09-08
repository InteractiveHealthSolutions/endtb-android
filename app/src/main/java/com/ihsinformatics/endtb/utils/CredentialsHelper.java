package com.ihsinformatics.endtb.utils;

import com.ihsinformatics.endtb.database.Entities.UserPermissions;
import com.ihsinformatics.endtb.database.data.DbContentHelper;

/**
 * Created by Naveed Iqbal on 10/27/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class CredentialsHelper {

    private static String userName;
    private static String userPassword;
    private static Long userId;

    public static Long getUserId() {
        return userId;
    }

    public static void setUserId(Long userId) {
        CredentialsHelper.userId = userId;
    }

    public static String getUsername() {
        return userName;
    }

    public static void setUsername(String username) {
        userName = username;
    }

    public static String getPassword() {
        return userPassword;
    }

    public static void setPassword(String password) {
        userPassword = password;
    }

    public static void nullifyCredentials() {
        userName = null;
        userPassword = null;
        userId = null;
    }

    public static boolean hasPermissions(String permissionName) {
        UserPermissions userPermission = DbContentHelper.getInstance().fetchUserPermission(userId, permissionName);
        boolean toReturn = userPermission==null ? false : true;

        return toReturn;
    }
}
