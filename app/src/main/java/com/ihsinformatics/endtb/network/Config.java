package com.ihsinformatics.endtb.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ihsinformatics.endtb.utils.preferences.AppPreferences;

/**
 * Created by Naveed Iqbal on 10/26/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class Config {

    private static final String REST_URL = "/ws/rest/v1";
    private static final String CUSTOM_API_URL = "/data/rest/tbelims";
    private static String SERVER_ADDRESS = "";
    private static String SERVER_ADDRESS_TEST = "endtbdev.irdresearch.org";
    private static String SERVER_ADDRESS_LIVE = "endtbpk.irdresearch.org";
    private static String PORT = "443";
    public static final String PROJECT_NAME = "/openmrs";
    public static final String ORDER_TYPE = REST_URL+"/ordertype";
    public static final String PERSON_ATTRIBUTE_TYPE = REST_URL+"/personattributetype";
    public static final String PERSON_RESOURCE = REST_URL+"/person";
    public static final String PATIENT_RESOURCE = REST_URL+"/patient";
    public static final String PATIENT_DATA_RESOURCE = REST_URL+"/patientdata";
    public static final String PROVIDER = REST_URL+"/provider";
    public static final String ATTRIBUTE = REST_URL+"/attribute";
    public static final String ADDRESS = REST_URL+"/address";
    public static final String PROGRAM_RESOURCE = REST_URL+"/program";
    public static final String USER__RESOURCE = REST_URL+"/user";
    public static final String USER_DATA_RESOURCE = REST_URL+"/userdata";
    public static final String PRIVILEGE_RESOURCE = REST_URL+"/privilege";
    public static final String DRUG_RESOURCE = REST_URL+"/drug";
    public static final String ROLE_RESOURCE = REST_URL+"/role";
    public static final String PATIENT_IDENTIFIER_TYPE_RESOURCE = REST_URL+"/patientidentifiertype";
    public static final String IDENTIFIER_TYPE_RESOURCE = REST_URL+"/patientidentifiertype";
    public static final String PERSON_ATTRIBUTE_TYPE_RESOURCE = REST_URL+"/personattributetype";
    public static final String ENCOUNTER_RESOURCE = REST_URL+"/encounter";
    public static final String ENCOUNTER_DATA_RESOURCE = REST_URL+"/encounterdata";
    public static final String ENCOUNTER_TYPE_RESOURCE = REST_URL+"/encountertype";
    public static final String ENCOUNTER_TYPE_PARAM = REST_URL+"/encounterType";
    public static final String CONCEPT_RESOURCE = REST_URL+"/concept";
    public static final String OBSERVATION_RESOURCE = REST_URL+"/obs";
    public static final String ORDER_RESOURCE = REST_URL+"/order";
    public static final String ORDER_DATA_RESOURCE = REST_URL+"/orderdata";
    public static final String PROVIDER_RESOURCE = REST_URL+"/provider";
    public static final String PROVIDER_ATTRIBUTE_TYPE_RESOURCE = REST_URL+"/providerattributetype";
    public static final String LOCATION_RESOURCE = REST_URL+"/locationdata";
    public static final String DEVICE_RESOURCE = CUSTOM_API_URL+"/device.json";
    public static final String LOCATION_ATTRIBUTE_TYPE_RESOURCE = REST_URL+"/locationattributetype";
    public static final String LOCATION_TAG = REST_URL+"/locationtag";
    public static final String LAB_RESOURCE = REST_URL+"/labdata";


    public static void addHeaders() {

    }

    public static String getApiEndPoint(String requestType, Context context) {
        boolean isLive = AppPreferences.getInstance(context).findBooleanPrferenceValue(AppPreferences.KEY.IS_LIVE,false);
        if (isLive) SERVER_ADDRESS = SERVER_ADDRESS_LIVE; else SERVER_ADDRESS = SERVER_ADDRESS_TEST;


        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        SERVER_ADDRESS =  SP.getString("address", SERVER_ADDRESS);
        PORT =  SP.getString("port", PORT);
        boolean isSSL = SP.getBoolean("ssl", true);
        String protocol = isSSL?"https://":"http://";
        String endPoint = protocol+SERVER_ADDRESS+":"+PORT+PROJECT_NAME+requestType;

        return endPoint;
    }

    public static String getServerAddress(Context context) {
        boolean isLive = AppPreferences.getInstance(context).findBooleanPrferenceValue(AppPreferences.KEY.IS_LIVE,false);
        if (isLive) SERVER_ADDRESS = SERVER_ADDRESS_LIVE; else SERVER_ADDRESS = SERVER_ADDRESS_TEST;
        return SERVER_ADDRESS+":"+PORT;
    }

    public static final byte[] KEY_VALUE =
    new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't',
        'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };
}
