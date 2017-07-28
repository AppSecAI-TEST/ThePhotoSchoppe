package com.example.acer.thephotoschoppe.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.acer.thephotoschoppe.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {


    private TextView emailTV;
    private TextView phoneTV;
    private ImageButton btnLocate;



    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_more, container, false);



        emailTV=(TextView)rootView.findViewById(R.id.tv_email);
        emailTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create the Intent
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                //Fill it with Data
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailTV.getText().toString()});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Inquiry for the Photo Schoppe.");


                // Send it off to the Activity-Chooser
                startActivity(emailIntent);


            }
        });
        phoneTV=(TextView)rootView.findViewById(R.id.tv_mobile);
        phoneTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse("tel:"+phoneTV.getText());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);

            }
        });
        btnLocate=(ImageButton)rootView.findViewById(R.id.btn_locate);
        btnLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri location = Uri.parse("geo:0,0?q=9600+Great+Hills+Trl,+Austin,+Tx+78759,+United+States");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

                startActivity(mapIntent);
            }
        });

        return rootView;
    }




}
