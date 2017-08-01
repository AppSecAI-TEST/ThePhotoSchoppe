package com.example.acer.thephotoschoppe.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.thephotoschoppe.R;
import com.example.acer.thephotoschoppe.activities.SignUpActivity;
import com.example.acer.thephotoschoppe.flickr.FlickrManager;
import com.example.acer.thephotoschoppe.models.Photo;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class PortfolioFragment extends Fragment {


    static ArrayList<String> photoidList;
    Context context;
    GridView gridView;

    private static final String TAG="PortfolioFragment";
    public PortfolioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_portfolio, container, false);

        gridView=(GridView) rootView.findViewById(R.id.gridView);

        context=getContext();
        photoidList=new ArrayList<>();

        new GetContacts(context).execute();
        return rootView;
    }


    private class GetContacts extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;
        Context contx;

        public GetContacts(Context context){
            this.contx=context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(contx);
            pDialog.setMessage("Please wait");
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected Void doInBackground(Void... voids) {

            if(FlickrManager.getPhotos().size()==0){
                FlickrManager handler=new FlickrManager();

                String galleryUrl=handler.getGalleryUrl();
                String galleryJson=handler.makeServiceCall(galleryUrl);
                readPhotoGallery(galleryJson,handler);

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if(FlickrManager.getPhotos().size()==0){
                Toast.makeText(context,"Please check your internet connection and relaunch the app.",Toast.LENGTH_LONG).show();
            }
            else {
                gridView.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return FlickrManager.getPhotos().size();
                    }

                    @Override
                    public Object getItem(int i) {
                        return null;
                    }

                    @Override
                    public long getItemId(int i) {
                        return 0;
                    }

                    @Override
                    public View getView(int i, View convertView, ViewGroup viewGroup) {

                        Photo photo=FlickrManager.getPhotos().get(i);
                        View cellPhoto=null;
                        if(convertView==null){
                            cellPhoto= LayoutInflater.from(context).inflate(R.layout.grid_view_cell,null);
                        }
                        else {
                            cellPhoto=convertView;
                        }

                        PlaceHolder ph=(PlaceHolder)cellPhoto.getTag();

                        ImageView image;
                        TextView date;

                        if(ph==null){
                            image=(ImageView)cellPhoto.findViewById(R.id.image_view);
                            date=(TextView)cellPhoto.findViewById(R.id.title);
                            ph=new PlaceHolder();
                            ph.image=image;
                            ph.date=date;
                            cellPhoto.setTag(ph);
                        }
                        else {
                            image=ph.image;
                            date=ph.date;
                        }
                        Picasso.with(context)
                                .load(photo.getSrcUrl())
                                .resize(150,150)
                                .centerCrop()
                                .into(image);



                        date.setText(photo.getTitle());

                        return cellPhoto;
                    }
                });

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent,
                                            View v, int position, long id){
                        //Send intent to SingleViewActivity
                        Intent i = new Intent(context, SingleViewActivity.class);
                        // Pass image index
                        i.putExtra("id", position);
                        i.putExtra("url",FlickrManager.getPhotos().get(position).getSrcUrl());
                        startActivity(i);


                    }
                });
                gridView.setDrawSelectorOnTop(true);
            }


                pDialog.dismiss();

        }
        private class PlaceHolder{
            ImageView image;
            TextView date;
        }
    }



    private void readPhotoGallery(String galleryJson,FlickrManager handler){

        if(galleryJson!=null){
            try {
                JSONObject jsonObject=new JSONObject(galleryJson);

                JSONObject photoObject=jsonObject.getJSONObject("photos");
                JSONArray photos=photoObject.getJSONArray("photo");

                int breakpoint;
                if(photos.length()>10){
                    breakpoint=10;
                }
                else {
                    breakpoint=photos.length();
                }

                for (int i=0;i<breakpoint;i++){
                    JSONObject c=photos.getJSONObject(i);

                    String id=c.getString("id");
                    String title=c.getString("title");
//                    String webUrl=c.getString("link");
//                    String dateTaken=c.getString("date_taken");
//                    String datePublished=c.getString("published");
//
//                    JSONObject media=c.getJSONObject("media");
//                    String srcUrl=media.getString("m");

                    //create new photo object
                    Photo p=new Photo(id,title);

                    setPhotoInfo(p,handler);
                    //add photo to the list
                    FlickrManager.getPhotos().add(p);


                }
            } catch (final JSONException e) {

                e.printStackTrace();

            }
        }
        else {
            FlickrManager.getPhotos().clear();
            Log.d(TAG,"exception");
        }

    }


    private void setSourceUrl(Photo photo,FlickrManager handler){

        String photoSizesUrl=handler.getPhotoSizesUrl(photo.getId());


        String jsonStr=handler.makeServiceCall(photoSizesUrl);

        if(jsonStr!=null){
            try {
                JSONObject jsonObject=new JSONObject(jsonStr);

                JSONObject sizesObject=jsonObject.getJSONObject("sizes");

                JSONArray urls=sizesObject.getJSONArray("size");

                JSONObject url=urls.getJSONObject(urls.length()-1);

                photo.setSrcUrl(url.getString("source"));


            } catch (final JSONException e) {

                e.printStackTrace();

            }
        }
        else {

            Log.d(TAG,"exception");
            FlickrManager.getPhotos().clear();

        }

    }


    private void setPhotoInfo(Photo photo,FlickrManager handler){
        String infoUrl=handler.getPhotoInfoUrl(photo.getId());


        String jsonStr=handler.makeServiceCall(infoUrl);

        if(jsonStr!=null){
            try {
                JSONObject jsonObject=new JSONObject(jsonStr);

                JSONObject photoObject=jsonObject.getJSONObject("photo");
                JSONObject photos=photoObject.getJSONObject("urls");

                JSONArray urls=photos.getJSONArray("url");

                JSONObject url=urls.getJSONObject(0);

                photo.setWebUrl(url.getString("_content"));


            } catch (final JSONException e) {

                e.printStackTrace();

            }
        }
        else {

            Log.d(TAG,"exception");
            FlickrManager.getPhotos().clear();

        }

        setSourceUrl(photo,handler);



    }

}
