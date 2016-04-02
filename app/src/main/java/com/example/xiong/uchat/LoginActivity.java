package com.example.xiong.uchat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xiong.uchat.base.BaseActionBarActivity;
import com.example.xiong.uchat.data.UserInfoPrefCache;
import com.example.xiong.uchat.data.bean.UserInfoBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.ex.DbException;

/**
 * Created by xiong on 2016/4/2.
 */
public class LoginActivity extends BaseActionBarActivity {

    private EditText editTextName;
    private EditText editTextPwd;
    private Button buttonLogin;
    private SimpleDraweeView imageViewAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setActionBarVisibility(View.GONE);
        initview();
    }

    private void initview() {
        imageViewAvatar = (SimpleDraweeView) findViewById(R.id.img_login_avatar);
        imageViewAvatar.setImageURI(Uri.parse("res:///" + R.drawable.new_friend));
        editTextName = (EditText) findViewById(R.id.edit_login_name);
        editTextPwd = (EditText) findViewById(R.id.edit_login_pwd);
        buttonLogin = (Button) findViewById(R.id.btn_login);

        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                UserInfoBean userInfoBean = new UserInfoBean();
                try {
                    userInfoBean = getDbManager().selector(UserInfoBean.class).where("userid", "=", charSequence.toString()).findFirst();
                    if (userInfoBean != null) {
                        imageViewAvatar.setImageURI(Uri.parse(userInfoBean.getAvatarPath()));
                    } else {
                        imageViewAvatar.setImageURI(Uri.parse("res:///" + R.drawable.new_friend));
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextName.getText().toString().equals("") || editTextPwd.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "账号和密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    UserInfoBean userInfoBean = new UserInfoBean();
                    try {

                        userInfoBean = getDbManager().selector(UserInfoBean.class).where("userid", "=", editTextName.getText().toString()).findFirst();
                        if (userInfoBean == null) {
                            Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!userInfoBean.getPassword().equals(editTextPwd.getText().toString())) {
                                Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                            } else {
                                userInfoBean.setToken(System.currentTimeMillis() + "");
                                UserInfoPrefCache.saveUserLoginInfo(getApplicationContext(), userInfoBean);
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
