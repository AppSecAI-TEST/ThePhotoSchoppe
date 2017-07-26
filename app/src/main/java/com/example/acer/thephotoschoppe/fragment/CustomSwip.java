package com.example.acer.thephotoschoppe.fragment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.acer.thephotoschoppe.R;
import com.example.acer.thephotoschoppe.models.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by acer on 7/27/2017.
 */

public class CustomSwip extends PagerAdapter {

    private ArrayList<Photo> photos;
//    private int [] imageResources ={R.drawable.capture1,R.drawable.capture2,R.drawable.capture3,R.drawable.capture4,R.drawable.capture5};
    private Context ctx;
    private LayoutInflater layoutInflater;

    public CustomSwip(Context c,int position) {
        ctx=c;
        photos=PortfolioFragment.getPhotos();
//       instantiateItem((ViewGroup) ctx,position);

    }

    @Override
    public int getCount() {

        return photos.size();
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater= (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=layoutInflater.inflate(R.layout.custom_swipe_activity,container,false);
        ImageView imageView=(ImageView) itemView.findViewById(R.id.swip_image_view);


        Picasso.with(ctx)
                .load(photos.get(position).getSrcUrl())
                .into(imageView);
        container.addView(itemView);
        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return  (view==object);
    }


}
