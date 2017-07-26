package com.example.acer.thephotoschoppe.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.thephotoschoppe.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class SingleViewActivity extends AppCompatActivity {

//    ViewPager viewPager;
//    CustomSwip  customSwip;

    private ImageView imageView;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_view);
//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Photo");


        // Get intent data
        Intent i = getIntent();

        // Selected image id
        position = i.getExtras().getInt("id");
        String url=i.getExtras().getString("url");



//
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
//
        Picasso.with(this)
                .load(url)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                LayoutInflater li = LayoutInflater.from(SingleViewActivity.this);
                View promptsView = li.inflate(R.layout.prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        SingleViewActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

//                final EditText userInput = (EditText) promptsView
//                        .findViewById(R.id.editTextDialogUserInput);

                ImageButton attachEmail=(ImageButton)promptsView.findViewById(R.id.attach_email_btn);
                attachEmail.setOnClickListener(new View.OnClickListener() {
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

                ImageButton exploreWeb=(ImageButton)promptsView.findViewById(R.id.explore_web_btn);
                exploreWeb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = PortfolioFragment.getPhotos().get(position).getWebUrl();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });

                TextView attachEmailTV=(TextView) promptsView.findViewById(R.id.attach_email_tv);
                attachEmailTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String url = PortfolioFragment.getPhotos().get(position).getSrcUrl();
                        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        emailIntent.setType("application/image");
//                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{strEmail});
//                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Test Subject");
//                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "From My App");
                        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));

                    }
                });

                TextView exploreWebTV=(TextView)promptsView.findViewById(R.id.explore_web_tv);
                exploreWebTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = PortfolioFragment.getPhotos().get(position).getWebUrl();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


            }
        });
//        viewPager=(ViewPager)findViewById(R.id.viewPager);
//        customSwip=new CustomSwip(this,position);
//        viewPager.setAdapter(customSwip);

    }
}
