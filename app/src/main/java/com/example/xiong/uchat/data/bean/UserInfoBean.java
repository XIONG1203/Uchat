package com.example.xiong.uchat.data.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by xiong on 2016/4/2.
 */

@Table(name = "userinfo")
public class UserInfoBean {

    @Column(name = "id", isId = true)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "userid")
    private String userid;

    @Column(name = "password")
    private String password;

    @Column(name = "avatarPath")
    private String avatarPath;


    @Column(name = "token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                '}';
    }
}
