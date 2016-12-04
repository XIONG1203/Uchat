package com.example.xiong.uchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.example.xiong.uchat.base.BaseActionBarActivity;
import com.example.xiong.uchat.data.UserInfoPrefCache;
import com.example.xiong.uchat.data.bean.UserInfoBean;

import org.xutils.ex.DbException;


public class WelcomeActivity extends BaseActionBarActivity {

    private View view;
    private String token;
    private String[] user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.activity_main, null);
        this.setContentView(view);
        setActionBarVisibility(View.GONE);
        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(3500);
        view.setAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                initUser();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                token = UserInfoPrefCache.getUserInfo(WelcomeActivity.this, "token");
                if (token != null && !token.equals("")) {
                    startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                } else {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initUser() {
        user = new String[]{"xiongjiachen"};
        for (int i = 0; i < user.length; i++) {
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.setId(user[i]);
            userInfoBean.setUserid(user[i]);
            userInfoBean.setName(user[i]);
            userInfoBean.setPassword(user[i]);
            if (i == 0)
                userInfoBean.setAvatarPath("res:///" + R.drawable.avatar_kin);
            else if (i == 1)
                userInfoBean.setAvatarPath("res:///" + R.drawable.avatar);
            else
                userInfoBean.setAvatarPath("res:///" + R.drawable.qq);
            try {
                getDbManager().save(userInfoBean);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }


    }
}
