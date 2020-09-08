package com.ihsinformatics.endtb.utils;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.ihsinformatics.endtb.database.Entities.DaoMaster;
import com.ihsinformatics.endtb.database.Entities.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Naveed Iqbal on 10/24/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class ELimsApplication extends MultiDexApplication {

    public static DaoSession daoSession;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        context = getApplicationContext();
    }


}

