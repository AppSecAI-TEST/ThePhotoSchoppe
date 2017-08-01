package com.example.acer.thephotoschoppe.flickr;


import android.util.Log;

import com.example.acer.thephotoschoppe.models.Photo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by acer on 7/25/2017.
 */

public class FlickrManager {

    private static final String TAG="FlickrManager";


    public FlickrManager(){



    }

    private static ArrayList<Photo> photos=new ArrayList<>();

//    public String getImageListUrl(){
//
////        String url=Constants.baseUrl+
////                "?"+Constants.userIdPrompt+"="+Constants.userId+
////                "&"+Constants.formatPrompt+"="+Constants.format+
////                "&"+Constants.nojsoncallbackPrompt+"="+Constants.nojsoncallback;
//
//        return url;
//
//
//        //https://api.flickr.com/services/feeds/photos_public.gne?id=26156338@N07&format=json&nojsoncallback=1
//
//
//    }

    public String getGalleryUrl(){
        String url=Constants.baseUrl+
                "?"+Constants.methodPrompt+"="+Constants.method1+
                "&"+Constants.apiKeyPrompt+"="+Constants.apiKey+
                "&"+Constants.galleryIdPrompt+"="+Constants.galleryId+
                "&"+Constants.formatPrompt+"="+Constants.format+
                "&"+Constants.nojsoncallbackPrompt+"="+Constants.nojsoncallback;

        //https://api.flickr.com/services/rest/
        // ?method=flickr.galleries.getPhotos
        // &api_key=14994975868e510312ec4f51dfc7701a
        // &gallery_id=61481170-72157636837427206
        // &format=json
        // &nojsoncallback=1

        return url;

    }

    public String getPhotoSizesUrl(String id){
        String url=Constants.baseUrl+
                "?"+Constants.methodPrompt+"="+Constants.method2+
                "&"+Constants.apiKeyPrompt+"="+Constants.apiKey+
                "&"+Constants.photoIdPrompt+"="+id+
                "&"+Constants.formatPrompt+"="+Constants.format+
                "&"+Constants.nojsoncallbackPrompt+"="+Constants.nojsoncallback;

        //https://api.flickr.com/services/rest/
        // ?method=flickr.photos.getSizes
        // &api_key=14994975868e510312ec4f51dfc7701a
        // &photo_id=1498869632
        // &format=json
        // &nojsoncallback=1

        return url;
    }

    public String getPhotoInfoUrl(String id){
        String url=Constants.baseUrl+
                "?"+Constants.methodPrompt+"="+Constants.method3+
                "&"+Constants.apiKeyPrompt+"="+Constants.apiKey+
                "&"+Constants.photoIdPrompt+"="+id+
                "&"+Constants.formatPrompt+"="+Constants.format+
                "&"+Constants.nojsoncallbackPrompt+"="+Constants.nojsoncallback;

        //https://api.flickr.com/services/rest/
        // ?method=flickr.photos.getInfo
        // &api_key=14994975868e510312ec4f51dfc7701a
        // &photo_id=1498869632
        // &format=json
        // &nojsoncallback=1

        return url;
    }



    public String makeServiceCall(String reqUrl) {
        String response = null;
        HttpURLConnection conn=null;
        try {
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }finally {
            conn.disconnect();
        }
        return response;
    }


    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static ArrayList<Photo> getPhotos() {
        return photos;
    }



}
