package com.example.xiong.uchat;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2015/12/22.
 */
public class ActivityManager {

    private static ActivityManager mActivityManager;

    private List<Activity> activityList;
    private Activity mCurActivity;

    public static ActivityManager getInstance() {
        if (mActivityManager == null) {
            mActivityManager = new ActivityManager();
        }
        return (mActivityManager);
    }

    private ActivityManager() {
        this.activityList = new ArrayList<>();
    }

    public void add(Activity activity) {
        this.activityList.add(activity);
    }

    public void remove(Activity activity) {
        this.activityList.remove(activity);
    }

    public void removeAll() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    public void setCurActivity(Activity mCurActivity) {
        this.mCurActivity = mCurActivity;
    }


}
