package com.example.xiong.uchat.base;

import android.app.Application;

import com.example.xiong.uchat.BuildConfig;
import org.xutils.DbManager;
import org.xutils.x;


/**
 * Created by xiong on 2016/2/25.
 */
public class DataBaseInit {

    private DbManager.DaoConfig daoConfig;
    private DbManager db;

    public DataBaseInit(Application application) {
        x.Ext.init(application);
        x.Ext.setDebug(BuildConfig.DEBUG);
        initDaoConfig();
        db = x.getDb(daoConfig);
    }

    private void initDaoConfig() {
        daoConfig = new DbManager.DaoConfig()
                .setDbName("qqxiongjiachen_shuaige.db")
                .setDbVersion(2)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                        // or
                        // db.dropDb();
                    }
                });
    }

    public synchronized DbManager getDb() {
        if (db == null) {
            if (daoConfig == null) {
                initDaoConfig();
            }
            db = x.getDb(daoConfig);
        }
        return db;
    }
}
