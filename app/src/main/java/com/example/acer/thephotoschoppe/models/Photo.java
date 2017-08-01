package com.example.acer.thephotoschoppe.models;

import android.os.Message;

/**
 * Created by prabodhaharankahadeniya on 7/26/17.
 */

public class Photo {

    private String id;
    private String title;
    private String webUrl;
    private String srcUrl;


//    public Photo(String title,String webUrl,String srcUrl,String takenDate,String publishedDate){
//
//        this.title=title;
//        this.webUrl=webUrl;
//        this.srcUrl=srcUrl;
//        this.publishedDate=publishedDate;
//        this.takenDate=takenDate;
//
//
//
//    }

    public Photo(String id,String title){
        this.id=id;
        this.title=title;
    }




    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public String getWebUrl() {
        return webUrl;
    }

    public String getSrcUrl() {
        return srcUrl;
    }
}
