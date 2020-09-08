package com.ihsinformatics.endtb.database.location;

import com.ihsinformatics.endtb.database.Entities.Location;
import com.ihsinformatics.endtb.database.data.DbContentHelper;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Naveed Iqbal on 11/29/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class LocationInsertHelper {

    public CopyOnWriteArrayList<Location> locationsPool;
    private boolean hasMoreFeed = true;

    public boolean isHasMoreFeed() {
        return hasMoreFeed;
    }

    public void setHasMoreFeed(boolean hasMoreFeed) {
        this.hasMoreFeed = hasMoreFeed;
    }

    public LocationInsertHelper() {
        locationsPool = new CopyOnWriteArrayList<>();
    }

    public void insertLocation(Location arg) {
        locationsPool.add(arg);
    }

    public void start() {
        DbContentHelper.getInstance().insertLocations(locationsPool);
    }
}
