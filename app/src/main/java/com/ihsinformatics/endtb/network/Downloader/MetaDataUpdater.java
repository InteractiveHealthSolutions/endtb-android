package com.ihsinformatics.endtb.network.Downloader;

import android.content.Context;
import android.os.AsyncTask;

import com.ihsinformatics.endtb.database.Entities.DaoMaster;
import com.ihsinformatics.endtb.database.Entities.Location;
import com.ihsinformatics.endtb.database.Entities.LocationDao;
import com.ihsinformatics.endtb.database.Entities.User;
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

import java.util.Date;
import java.util.List;

/**
 * Created by Naveed Iqbal on 10/26/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class MetaDataUpdater extends AsyncTask implements Sendable {

    private Context context;
    OnMetaDataDownloadListener metaDataDownloadListener;
    private Date dateFrom;

    public MetaDataUpdater(Context context, OnMetaDataDownloadListener metaDataDownloadListener) {
        this.context = context;
        this.metaDataDownloadListener = metaDataDownloadListener;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        //saveLocations("locations.json", 7);
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {

    }

    public void start(Date date) {
        dateFrom = date;
        downloadLabs("0");
    }


    private void downloadUsers() {
        metaDataDownloadListener.update("Updating users information...");
        new DataSender(context, this, 9, null, DataSender.REQUEST_TYPE.GET, true).execute(Config.USER_DATA_RESOURCE);
    }

    private void downloadLabs(String startIndex) {
        metaDataDownloadListener.update("Updating labs...");
        new DataSender(context, this, 8, null, DataSender.REQUEST_TYPE.GET, true).execute(Config.LOCATION_RESOURCE, "tags=lab&start="+startIndex+/*"&dateFrom="+ Global.MYSQL_DATE_FORMAT.format(dateFrom)+*/"&limit=99999");
    }

    @Override
    public void send(JSONArray data, int respId, String responseReference) {
        // TODO useless for this project or class at least
    }

    @Override
    public void onResponseReceived(JSONObject resp, int respId, String responseReference) throws JSONException {

        if (resp.has(ParamNames.SERVER_ERROR)) {
            Toast.makeText(context, resp.getString(ParamNames.SERVER_ERROR), Toast.LENGTH_LONG).show();
            DaoMaster.dropAllTables(ELimsApplication.daoSession.getDatabase(), true);
            DaoMaster.createAllTables(ELimsApplication.daoSession.getDatabase(), true);
            start(dateFrom);
            return;
        }

        switch (respId) {

            case 8:
                LocationDao locationDao = ELimsApplication.daoSession.getLocationDao();
                JSONArray locationArray = resp.getJSONArray(ParamNames.SERVER_RESPONSE);
                List<Location> locations = JsonToObjectParser.parseLocationsFromJson(locationArray);
                DbContentHelper.getInstance().insertLocations(locations);
                downloadUsers();
                return;
            case 9:

                JSONArray usersArray = resp.getJSONArray(ParamNames.SERVER_RESPONSE);
                List<User> userList = new UserHelper().parseUsersFromJson(usersArray);
                User user = DbContentHelper.getInstance().fetchUserByUsername(CredentialsHelper.getUsername());
                userList.remove(user);
                DbContentHelper.getInstance().insertOrUpdateUsers(userList);

                UserPermissionsDao userPermissionsDao = ELimsApplication.daoSession.getUserPermissionsDao();
                List<UserPermissions> userPermissions = new UserHelper().parsePermissionsFromUserJson(usersArray);
                userPermissionsDao.insertOrReplaceInTx(userPermissions);
                break;
        }

        metaDataDownloadListener.onMetaDataDownloaded();
    }
}
