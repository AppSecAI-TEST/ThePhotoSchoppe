package com.example.acer.thephotoschoppe.flickr;

import android.content.res.Resources;

import com.example.acer.thephotoschoppe.R;

/**
 * Created by prabodhaharankahadeniya on 7/26/17.
 */

class Constants {

    static String baseUrl = "https://api.flickr.com/services/rest/";
    static String method1 = "flickr.people.getPhotos";
    static String method2 = "flickr.photos.getInfo";
    static String apiKey = "d0d447e4568eb938fbf0db36b9e30fa9";
    static String userId = "26156338%40N07";
    static String format = "json";
    static String nojsoncallback="1";

    static String photoIdPrompt= "photo_id";
    static String methodPrompt = "method";
    static String apiKeyPrompt = "api_key";
    static String userIdPrompt = "user_id";
    static String formatPrompt = "format";
    static String nojsoncallbackPrompt = "nojsoncallback";

    //https://api.flickr.com/services/rest/
    // ?method=flickr.people.getPhotos
    // &api_key=e092219e549a3ef169b692521e692cb9
    // &user_id=87044997%40N08
    // &format=json
    // &nojsoncallback=1

}
