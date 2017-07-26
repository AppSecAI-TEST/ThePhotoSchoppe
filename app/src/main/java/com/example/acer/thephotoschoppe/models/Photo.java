package com.example.acer.thephotoschoppe.models;

import android.os.Message;

/**
 * Created by prabodhaharankahadeniya on 7/26/17.
 */

public class Photo {

    private String id;
    private String ownerUsername;
    private String title;
    private String date;
    private String url;
    private Message msg;

    public Photo(String id,String title){
        this.id=id;
        this.title=title;

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public Message getMsg() {
        return msg;
    }
}
