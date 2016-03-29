package com.example.xiong.uchat.bean;

/**
 * Created by xiong on 2016/3/29.
 */
public class MessageListItemBean {
    private String path;
    private String name;
    private String tempContent;
    private String time;

    public MessageListItemBean() {
    }

    public MessageListItemBean(String path, String name, String tempContent, String time) {
        this.path = path;
        this.name = name;
        this.tempContent = tempContent;
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTempContent() {
        return tempContent;
    }

    public void setTempContent(String tempContent) {
        this.tempContent = tempContent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
