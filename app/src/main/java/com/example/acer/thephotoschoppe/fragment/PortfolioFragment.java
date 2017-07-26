package com.example.acer.thephotoschoppe.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.acer.thephotoschoppe.R;
import com.example.acer.thephotoschoppe.flickr.FlickrManager;
import com.example.acer.thephotoschoppe.models.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;


/**
 * A simple {@link Fragment} subclass.
 */
public class PortfolioFragment extends Fragment {


    ArrayList<Photo> photosList;
    Context context;
    public PortfolioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_portfolio, container, false);
        context=getContext();
        photosList=new ArrayList<>();
        new GetContacts().execute();
        return rootView;
    }


    private class GetContacts extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog=new ProgressDialog(JsonDownloadActivity.this);
//            pDialog.setMessage("Please wait");
//            pDialog.setCancelable(false);
//            pDialog.show();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            FlickrManager handler=new FlickrManager();

            String imagesUrl=handler.getImageListUrl();
            String jsonStr=handler.makeServiceCall(imagesUrl);
            if(jsonStr!=null){
                try {
                    JSONObject jsonObject=new JSONObject(jsonStr);
                    JSONObject items=jsonObject.getJSONObject("photos");
                    JSONArray photos=items.getJSONArray("photo");

                    for (int i=0;i<10;i++){
                        JSONObject c=photos.getJSONObject(i);

                        String id=c.getString("id");
                        String title=c.getString("title");

                        Photo p=new Photo(id,title);

                        photosList.add(p);


                    }
                } catch (final JSONException e) {
                    e.printStackTrace();

                }
            }
            else {

            }


            for(Photo photo:photosList){
                String photoUrl=handler.getImageInfoUrl(photo.getId());
                String jsonInfo=handler.makeServiceCall(photoUrl);
                if(jsonInfo!=null){
                    try {
                        JSONObject jsonObject=new JSONObject(jsonInfo);
                        JSONObject items=jsonObject.getJSONObject("photo");

                        JSONObject ownerObject=items.getJSONObject("owner");
                        String ownerUsername=ownerObject.getString("username");
                        photo.setOwnerUsername(ownerUsername);

                        JSONObject datesObject=items.getJSONObject("dates");
                        String date=datesObject.getString("taken");
                        photo.setDate(date);

                        JSONObject urlObject=items.getJSONObject("urls");
                        JSONArray urls=urlObject.getJSONArray("url");
                        String url=urls.getJSONObject(0).getString("_content");
                        photo.setUrl(url);
                        Log.d("url ",photo.getUrl());



                    } catch (final JSONException e) {
                        e.printStackTrace();

                    }
                }
                else {

                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            if(pDialog.isShowing()){
//                pDialog.dismiss();
//            }
//
//
//            ListAdapter adapter=new SimpleAdapter(context,contactList,R.layout.list_item,
//                    new String[]{"name","email","mobile"},
//                    new int[]{R.id.name,R.id.email,R.id.mobile});
//            lv.setAdapter(adapter);
        }


    }


}
