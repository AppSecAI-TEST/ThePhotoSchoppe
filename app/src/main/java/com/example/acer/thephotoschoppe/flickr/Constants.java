package com.example.acer.thephotoschoppe.flickr;

/**
 * Created by prabodhaharankahadeniya on 7/26/17.
 */

class Constants {

//    static String baseUrl = "https://api.flickr.com/services/feeds/photos_public";
//    static String userId = "26156338@N07";


    static String baseUrl = "https://api.flickr.com/services/rest/";

    static String method1="flickr.galleries.getPhotos";
    static String method2="flickr.photos.getSizes";
    static String method3="flickr.photos.getInfo";

//    static String apiKey="14994975868e510312ec4f51dfc7701a";
    static String apiKey="51840175fd7e7034e83617b9d0bdd785";


    static String galleryId="61481170-72157636837427206";


    static String format = "json";
    static String nojsoncallback="1";

//    static String userIdPrompt = "user_id";

    static String methodPrompt = "method";
    static String apiKeyPrompt="api_key";
    static String galleryIdPrompt = "gallery_id";
    static String photoIdPrompt = "photo_id";


    static String formatPrompt = "format";
    static String nojsoncallbackPrompt = "nojsoncallback";


    //https://api.flickr.com/services/feeds/photos_public.gne
    // ?id=26156338@N07
    // &format=json
    // &nojsoncallback=1


    //https://api.flickr.com/services/rest/?method=flickr.galleries.getPhotos&api_key=14994975868e510312ec4f51dfc7701a&gallery_id=61481170-72157636837427206&format=json&nojsoncallback=1

//    https://api.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=14994975868e510312ec4f51dfc7701a&photo_id=1498869632&format=json&nojsoncallback=1

//    https://api.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=14994975868e510312ec4f51dfc7701a&photo_id=1498869632&format=json&nojsoncallback=1

}
