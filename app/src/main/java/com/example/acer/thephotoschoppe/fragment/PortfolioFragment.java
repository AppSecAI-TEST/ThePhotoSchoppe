package com.example.acer.thephotoschoppe.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.thephotoschoppe.R;
import com.example.acer.thephotoschoppe.flickr.FlickrManager;
import com.example.acer.thephotoschoppe.models.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class PortfolioFragment extends Fragment {


    ArrayList<Photo> photosList;
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
                      //  Log.d("url ",photo.getUrl());



                    } catch (final JSONException e) {
                        e.printStackTrace();

                    }
                }
                else {

                }

                if(checkInternetConnection()){
                    InputStream in=null;
                    Message msg=Message.obtain();
                    Bitmap bitmap = null;

                    msg.what=1;
                    try{
                        in=openHttpConnection(photoUrl);
                        Log.d(TAG,photo.getUrl());
                        bitmap= BitmapFactory.decodeStream(in);
                        Log.d(TAG,bitmap+"");
                        Bundle bundle=new Bundle();
                        bundle.putParcelable("bitmap",bitmap);
                        msg.setData(bundle);
                        in.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    Log.d(TAG,""+msg);
                    photo.setMsg(msg);
                }
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
Log.d(TAG,result+"");
            gridView.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return photosList.size();
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


                    Photo photo=photosList.get(i);
                    View cellPhoto=null;
                    if(convertView==null){
                        cellPhoto= LayoutInflater.from(context).inflate(R.layout.grid_view_cell,null);

                    }
                    else {
                        cellPhoto=convertView;
                    }

                    PlaceHolder ph=(PlaceHolder)cellPhoto.getTag();

                    ImageView image;

                    if(ph==null){

                        image=(ImageView)cellPhoto.findViewById(R.id.image_view);


                        ph=new PlaceHolder();
                        ph.image=image;

                        cellPhoto.setTag(ph);

                    }
                    else {
                        image=ph.image;


                    }

//                    Handler messageHandler = new Handler() {
//                        public void handleMessage(Message msg) {
//                            super.handleMessage(msg);
//                            ImageView img = (ImageView) cellPhoto.findViewById(R.id.image_view);
//                            img.setImageBitmap((Bitmap) (msg.getData().getParcelable("bitmap")));
//
//                        }
//                    };
//
//                    messageHandler.sendMessage(photosList.get(i).getMsg());
                    Bitmap bitmap=photo.getMsg().getData().getParcelable("bitmap");

Log.d(TAG,bitmap+"");
                   // Bitmap scaledBitmap = scaleDown(bitmap, 100, true);
                    //Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap,  600 ,600, true);
                    image.setImageBitmap(bitmap);


                    return cellPhoto;


                }
            });
        }

//        public Bitmap scaleDown(Bitmap realImage, float maxImageSize,
//                                       boolean filter) {
//
//            float ratio = Math.min(
//                    (float) maxImageSize / realImage.getWidth(),
//                    (float) maxImageSize / realImage.getHeight());
//            int width = Math.round((float) ratio * realImage.getWidth());
//            int height = Math.round((float) ratio * realImage.getHeight());
//
//            Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
//                    height, filter);
//            return newBitmap;
//        }

        private class PlaceHolder{
            ImageView image;

        }
    }

    private InputStream openHttpConnection(String url){
        InputStream in=null;
        int resCode= -1;
        try {
            URL urlStr=new URL(url);
            URLConnection urlConn = urlStr.openConnection();
            if(!(urlConn instanceof HttpURLConnection)){
                throw new IOException("URL is not a http url");
            }

            HttpURLConnection httpConn=(HttpURLConnection)urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            resCode=httpConn.getResponseCode();
            if(resCode==HttpURLConnection.HTTP_OK){
                in = httpConn.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    private boolean checkInternetConnection(){
        ConnectivityManager connect=(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);

        if(connect.getActiveNetworkInfo().getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connect.getActiveNetworkInfo().getState() ==
                        NetworkInfo.State.CONNECTING){


            return true;
        }
        else if(connect.getActiveNetworkInfo().getState() ==
                NetworkInfo.State.DISCONNECTED){


            return  false;
        }
        return false;
    }


}
