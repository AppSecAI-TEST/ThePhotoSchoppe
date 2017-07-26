package com.example.acer.thephotoschoppe.models;

import android.os.Message;

/**
 * Created by prabodhaharankahadeniya on 7/26/17.
 */

public class Photo {


    private String title;
    private String takenDate;
    private String publishedDate;
    private String webUrl;
    private String srcUrl;


    public Photo(String title,String webUrl,String srcUrl,String takenDate,String publishedDate){

        this.title=title;
        this.webUrl=webUrl;
        this.srcUrl=srcUrl;
        this.publishedDate=publishedDate;
        this.takenDate=takenDate;



    }

    public String getTitle() {
        return title;
    }

    public String getTakenDate() {
        return takenDate;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getSrcUrl() {
        return srcUrl;
    }
}
