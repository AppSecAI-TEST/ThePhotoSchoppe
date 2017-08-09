package com.example.acer.thephotoschoppe.fragment;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.thephotoschoppe.R;
import com.example.acer.thephotoschoppe.flickr.FlickrManager;
import com.example.acer.thephotoschoppe.listener.OnSwipeTouchListener;
import com.example.acer.thephotoschoppe.models.Photo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.support.v4.content.FileProvider.getUriForFile;

public class SingleViewActivity extends AppCompatActivity {

    private static final String TAG="SingleView";
    private ImageView imageView;
    private TextView titleTV;

    Context context;
    private int position;
    ArrayList<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_view);
        setTitle("Photo");
        context=this;

        photos= FlickrManager.getPhotos();

        // Get intent data
        Intent i = getIntent();

        // Selected image id
        position = i.getExtras().getInt("id");
        String url=i.getExtras().getString("url");

        imageView = (ImageView) findViewById(R.id.imageView);

        titleTV = (TextView) findViewById(R.id.title);
        titleTV.setText((position+1)+" of "+photos.size());
        Picasso.with(this)
                .load(url)
                .into(imageView);

        imageView.setOnTouchListener(new OnSwipeTouchListener(this){

            public void onSwipeTop() {
//                    Toast.makeText(SingleViewActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                //Toast.makeText(SingleViewActivity.this, "right", Toast.LENGTH_SHORT).show();
                if(position>0){
                    position=position-1;
                    titleTV.setText((position+1)+" of "+photos.size());
                    Picasso.with(getInstance())
                            .load(photos.get(position).getSrcUrl())
                            .into(imageView);
                }



            }
            public void onSwipeLeft() {
                //Toast.makeText(SingleViewActivity.this, "left", Toast.LENGTH_SHORT).show();

                if(position+1<photos.size()){
                    position=position+1;
                    titleTV.setText((position+1)+" of "+photos.size());
                    Picasso.with(getInstance())
                            .load(photos.get(position).getSrcUrl())
                            .into(imageView);
                }
            }
            public void onSwipeBottom() {
//                Toast.makeText(SingleViewActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });

        TextView exploreWebTV = (TextView) findViewById(R.id.explore_web_tv);
        exploreWebTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exploreInWeb();
            }
        });
        ImageView exploreWebBtn = (ImageView) findViewById(R.id.explore_web_btn);
        exploreWebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             exploreInWeb();
            }
        });

        final TextView attachPhotoTV = (TextView) findViewById(R.id.attach_email_tv);
        attachPhotoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            attachPhoto();
            }
        });
        ImageView attachPhotoBtn = (ImageView) findViewById(R.id.attach_email_btn);
        attachPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            attachPhoto();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void attachPhoto(){
        Photo photo = photos.get(position);
        //save photo to the phone
        saveToPhone(photo);


//        test(photo.getSrcUrl());
      //  File imageFile=getImageFile(photo);


        Log.d(TAG,context.getFilesDir()+"");

        File imagePath = new File(context.getFilesDir(), "images");
        File newFile = new File(imagePath, photo.getName());
        Uri contentUri = getUriForFile(context, "com.thephotoschoppe.fileprovider", newFile);
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//                emailIntent.setType("plain/text");

        emailIntent.setType("image/jpeg");
//
//        ImageView textImage=(ImageView)findViewById(R.id.test);
//        Log.d(TAG,imageFile+"");
//        Picasso.with(context).load(imageFile).into(textImage);
//        File imageFile=

        emailIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,photo.getSrcUrl());
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///mnt/sdcard/Myimage.jpeg"));

    }

    private void exploreInWeb(){
        String url = photos.get(position).getWebUrl();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private SingleViewActivity getInstance(){
        return this;
    }

    private void saveToPhone(Photo photo){
        Log.d(TAG,"inside save to phone");
        new DownloadImage().execute();

       // Log.d("get application context",getApplicationContext()+"");
//        Picasso.with(context)
//                .load(photo.getSrcUrl())
//                .into(picassoImageTarget(getApplicationContext(), "images", photo.getName()));


    }



    private File getImageFile(Photo photo){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File myImageFile = new File(directory, photo.getName());
        return myImageFile;

    }


    private Target picassoImageTarget(Context context, final String imageDir, final String imageName) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final File myImageFile = new File(directory, imageName); // Create image file
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("image", "image saved to >>>" + myImageFile.getAbsolutePath());

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {}
            }
        };
    }



    public String getUrl(){
        return photos.get(position).getSrcUrl();
    }



    private class DownloadImage extends AsyncTask<Void, Void, Void> {

        private Bitmap bitmap = null;
        Message msg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {


            if(checkInternetConnection()){
                InputStream in=null;


                msg.what=1;
                try{
                    in=openHttpConnection(getUrl());
                    bitmap= BitmapFactory.decodeStream(in);
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("bitmap",bitmap);
                    msg.setData(bundle);
                    in.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            messageHandler.sendMessage(msg);



        }

        private Handler messageHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Bitmap bitmapImage= msg.getData().getParcelable("bitmap");
                //write to the file

                String filename = "images";
                String string = "Hello world!";
                FileOutputStream outputStream;

                try {
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(string.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

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
            ConnectivityManager connect=(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

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
//    private static Target getTarget(final String url){
//        Log.d(TAG,"inside target");
//
//        Target target = new Target(){
//
//            @Override
//            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        Log.d(TAG,Environment.getExternalStorageDirectory().getPath() + "/" + url);
//                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + url);
//                        try {
//                            file.createNewFile();
//                            FileOutputStream ostream = new FileOutputStream(file);
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
//                            ostream.flush();
//                            ostream.close();
//                        } catch (IOException e) {
//                            Log.e("IOException", e.getLocalizedMessage());
//                        }
//                    }
//                }).start();
//
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        };
//        return target;
//    }
}
