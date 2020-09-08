package com.ihsinformatics.endtb.database.location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.ihsinformatics.endtb.database.gson.Location;
import com.ihsinformatics.endtb.database.json_helper.JsonToObjectParser;
import com.ihsinformatics.endtb.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Naveed Iqbal on 11/27/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class LocationJsonHelper {

    public static Location readStream(InputStream is) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
            Gson gson = new GsonBuilder().create();
            LocationInsertHelper locationInsertHelper = new LocationInsertHelper();
            // Read file in stream mode
            reader.beginArray();
            while (reader.hasNext()) {
                // Read data into object model
                Location gsonLocation = gson.fromJson(reader, Location.class);
                com.ihsinformatics.endtb.database.Entities.Location location = JsonToObjectParser.parseLocationsFromGsonLocation(gsonLocation);
                locationInsertHelper.insertLocation(location);
            }

            reader.close();
            locationInsertHelper.start();
        } catch (UnsupportedEncodingException ex) {
            Logger.log(ex);
        } catch (IOException ex) {
            Logger.log(ex);
        }

        return null;
    }

    public JSONObject loadJSONFromAsset(InputStream is) {
        JSONObject obj = null;
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            obj = new JSONObject(json);
        } catch (IOException ex) {
            Logger.log(ex);
            return null;
        } catch (JSONException e1) {
            Logger.log(e1);
        }
        return obj;
    }

}
