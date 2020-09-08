package com.ihsinformatics.endtb.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.ihsinformatics.endtb.Screens.DailyTreatmentMonitoring;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nabil shafi on 3/14/2018.
 */

public class AndroidPermissionsHelper {

    private static AndroidPermissionsHelper instance;
    private AndroidPermissionsHelper() {}

    public static AndroidPermissionsHelper getInstance() {
        if (instance == null)
            instance = new AndroidPermissionsHelper();

        return instance;
    }

    public void checkPermissions(AppCompatActivity activity, int requestCode, String... permissions){
        List<String> permissionsList = new ArrayList<>();

        for(int i=0; i<permissions.length; i++) {
            String perm = permissions[i];
            if (!checkIfPermissionGranted(activity, perm)) {
                permissionsList.add(perm);
            }
        }
        if(permissionsList.size()>0)
            askForPermissions(activity, requestCode, permissionsList.toArray(new String[permissionsList.size()]));
    }

    private boolean checkIfPermissionGranted(AppCompatActivity activity, String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }

        return false;
    }

    private void askForPermissions(AppCompatActivity activity, int requestCode, String... permissions) {
        if (Build.VERSION.SDK_INT >= 23)
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }
}

