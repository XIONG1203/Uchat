package com.example.xiong.uchat.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.xiong.uchat.Util.CommonParameter;
import com.example.xiong.uchat.data.bean.UserInfoBean;

/**
 * Created by asus on 2016/3/9.
 */
public class UserInfoPrefCache {

    /**
     * 更新某个字段内容
     *
     * @param context
     */
    public static void updateUserInfo(Context context, String field, String content) {
        SharedPreferences.Editor editor = context.getSharedPreferences(CommonParameter.USER_CACHE_PREF, Context.MODE_PRIVATE).edit();
        editor.putString(field, content);
        editor.commit();
    }

    /**
     * 更新boolean字段内容
     *
     * @param context
     * @param field
     * @param flag
     */
    public static void updateUserInfo(Context context, String field, Boolean flag) {
        SharedPreferences.Editor editor = context.getSharedPreferences(CommonParameter.USER_CACHE_PREF, Context.MODE_PRIVATE).edit();
        editor.putBoolean(field, flag);
        editor.commit();
    }

    /**
     * 得到某个字段内容
     *
     * @param context
     * @param field
     * @return
     */
    public static String getUserInfo(Context context, String field) {
        SharedPreferences pref = context.getSharedPreferences(CommonParameter.USER_CACHE_PREF, Context.MODE_PRIVATE);
        return pref.getString(field, "");
    }

    /**
     * 得到boolean字段内容
     *
     * @param context
     * @param field
     * @return
     */
    public static Boolean getUserInfoBoolean(Context context, String field) {
        SharedPreferences pref = context.getSharedPreferences(CommonParameter.USER_CACHE_PREF, Context.MODE_PRIVATE);
        return pref.getBoolean(field, false);
    }

    /**
     * 保存用户登录信息
     *
     * @param context
     * @param userBean
     */
    public static void saveUserLoginInfo(Context context, UserInfoBean userBean) {
        SharedPreferences.Editor editor = context.getSharedPreferences(CommonParameter.USER_CACHE_PREF, Context.MODE_PRIVATE).edit();
        editor.putString("state", CommonParameter.STATE_LOGIN);
        editor.putString("name", userBean.getName());
        editor.putString("password", userBean.getPassword());
        editor.putString("avatarPath", userBean.getAvatarPath());
        editor.putString("userid", userBean.getUserid());
        editor.putString("token", userBean.getToken());
        editor.apply();
    }

    public static void clearPersonalInfo(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(CommonParameter.USER_CACHE_PREF, Context.MODE_PRIVATE).edit();
        editor.putString("token", "");
        editor.apply();
    }

}
