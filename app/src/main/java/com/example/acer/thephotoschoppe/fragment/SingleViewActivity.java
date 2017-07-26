package com.example.acer.thephotoschoppe.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.acer.thephotoschoppe.R;
import com.squareup.picasso.Picasso;

public class SingleViewActivity extends AppCompatActivity {

    ViewPager viewPager;
    CustomSwip  customSwip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_view);



//        // Get intent data
//        Intent i = getIntent();
//
//        // Selected image id
//        int position = i.getExtras().getInt("id");
//        String url=i.getExtras().getString("url");
//
//
//        ImageView imageView = (ImageView) findViewById(R.id.singleView);
//
//        Picasso.with(this)
//                .load(url)
//                .into(imageView);

        viewPager=(ViewPager)findViewById(R.id.viewPager);
        customSwip=new CustomSwip(this);
        viewPager.setAdapter(customSwip);

    }
}
