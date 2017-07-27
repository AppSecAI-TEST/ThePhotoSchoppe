package com.example.acer.thephotoschoppe.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.thephotoschoppe.R;
import com.example.acer.thephotoschoppe.listener.OnSwipeTouchListener;
import com.example.acer.thephotoschoppe.models.Photo;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SingleViewActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView titleTV;
    private TextView exploreWebTV;
    private ImageView exploreWebBtn;
    private TextView attachPhotoTV;
    private ImageView attachPhotoBtn;

    private int position;
    ArrayList<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_view);
        setTitle("Photo");

        photos=PortfolioFragment.getPhotos();

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

        exploreWebTV=(TextView)findViewById(R.id.explore_web_tv);
        exploreWebTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = PortfolioFragment.getPhotos().get(position).getWebUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        exploreWebBtn=(ImageView) findViewById(R.id.explore_web_btn);
        exploreWebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = PortfolioFragment.getPhotos().get(position).getWebUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        attachPhotoTV=(TextView)findViewById(R.id.attach_email_tv);
        attachPhotoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = PortfolioFragment.getPhotos().get(position).getSrcUrl();
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
//                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{strEmail});
//                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Test Subject");
//                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "From My App");
                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });
        attachPhotoBtn=(ImageView) findViewById(R.id.attach_email_btn);
        attachPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = PortfolioFragment.getPhotos().get(position).getSrcUrl();
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
//                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{strEmail});
//                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Test Subject");
//                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "From My App");
                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });

    }

    private SingleViewActivity getInstance(){
        return this;
    }
}
