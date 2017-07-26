package com.example.acer.thephotoschoppe.flickr;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by acer on 7/25/2017.
 */

public class FlickrManager {

    private static final String TAG="FlickrManager";


    public FlickrManager(){



    }


    public String getImageListUrl(){
        String url=Constants.baseUrl+
                "?"+Constants.methodPrompt+"="+Constants.method1+
                "&"+Constants.apiKeyPrompt+"="+Constants.apiKey+
                "&"+Constants.userIdPrompt+"="+Constants.userId+
                "&"+Constants.formatPrompt+"="+Constants.format+
                "&"+Constants.nojsoncallbackPrompt+"="+Constants.nojsoncallback;

        return url;

        //https://api.flickr.com/services/rest/
        // ?method=flickr.people.getPhotos
        // &api_key=d0d447e4568eb938fbf0db36b9e30fa9
        // &user_id=26156338%40N07
        // &format=json
        // &nojsoncallback=1


    }

    public String getImageInfoUrl(String id){
        String url=Constants.baseUrl+
                "?"+Constants.methodPrompt+"="+Constants.method2+
                "&"+Constants.apiKeyPrompt+"="+Constants.apiKey+
                "&"+Constants.photoIdPrompt+"="+id+
                "&"+Constants.formatPrompt+"="+Constants.format+
                "&"+Constants.nojsoncallbackPrompt+"="+Constants.nojsoncallback;
        return  url;
        //https://api.flickr.com/services/rest/
        // ?method=flickr.photos.getInfo
        // &api_key=d0d447e4568eb938fbf0db36b9e30fa9
        // &photo_id=35361371863
        // &format=json
        // &nojsoncallback=1

    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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



}
