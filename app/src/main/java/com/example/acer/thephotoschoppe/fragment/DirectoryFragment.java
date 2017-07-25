package com.example.acer.thephotoschoppe.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.acer.thephotoschoppe.R;
import com.example.acer.thephotoschoppe.database.DatabaseHandler;
import com.example.acer.thephotoschoppe.models.Photographer;

/**
 * A simple {@link Fragment} subclass.
 */
public class DirectoryFragment extends Fragment {


    private static final String TAG="ViewUserActivity";
    private DatabaseHandler dbHandler;
    public  DatabaseHandler getDBHandler(){
        if(dbHandler==null){
            dbHandler=new DatabaseHandler(context);
        }
        return dbHandler;
    }

    private Context context;

    private Photographer[] photographers;
    ListView listView;
    public DirectoryFragment() {
        // Required empty public constructor
//        context=getActivity().getApplicationContext();
//        Log.d(TAG,context+"");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity();
        final View rootView=inflater.inflate(R.layout.fragment_directory, container, false);
        listView=(ListView)rootView.findViewById(R.id.listView);

        photographers=getDBHandler().getPhotographers();

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return photographers.length;
            }

            @Override
            public Object getItem(int i) {
                return photographers[i];
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View convertView, ViewGroup viewGroup) {
                final Photographer photographer=photographers[i];
                View cellUser=null;
                if(convertView==null){
                    cellUser= LayoutInflater.from(context).inflate(R.layout.directory_list_item,null);

                }
                else {
                    cellUser=convertView;
                }

                DirectoryFragment.PlaceHolder ph=(DirectoryFragment.PlaceHolder)cellUser.getTag();
                final TextView firstNameTV;
                TextView lastNameTV;
                final TextView emailTV;
                final TextView mobileTV;
                ImageView mobileIV;
                ImageView emailIV;

                if(ph==null){
                    firstNameTV=(TextView) cellUser.findViewById(R.id.first_name);
                    lastNameTV= (TextView) cellUser.findViewById(R.id.last_name);
                    emailTV=(TextView) cellUser.findViewById(R.id.email);
                    mobileTV=(TextView) cellUser.findViewById(R.id.mobile);
                    mobileIV=(ImageView)cellUser.findViewById(R.id.icon_phone);
                    emailIV=(ImageView)cellUser.findViewById(R.id.icon_email);


                    ph=new DirectoryFragment.PlaceHolder();
                    ph.firstName=firstNameTV;
                    ph.lastName=lastNameTV;
                    ph.email=emailTV;
                    ph.mobile=mobileTV;
                    ph.iconEmail=emailIV;
                    ph.iconPhone=mobileIV;
                    cellUser.setTag(ph);

                }
                else {
                    firstNameTV=ph.firstName;
                    lastNameTV=ph.lastName;
                    emailTV=ph.email;
                    mobileTV=ph.mobile;
                    emailIV=ph.iconEmail;
                    mobileIV=ph.iconPhone;

                }


                firstNameTV.setText(photographer.getFirstName());
                lastNameTV.setText(photographer.getLastName());

                emailTV.setText(photographer.getEmail());
                emailTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     /* Create the Intent */
                        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

/* Fill it with Data */
                        emailIntent.setType("plain/text");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailTV.getText().toString()});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hello, "+firstNameTV.getText());


/* Send it off to the Activity-Chooser */
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    }
                });



                emailIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       /* Create the Intent */
                        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

/* Fill it with Data */
                        emailIntent.setType("plain/text");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailTV.getText().toString()});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hello, "+firstNameTV.getText());


/* Send it off to the Activity-Chooser */
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));

                    }
                });


                mobileTV.setText(photographer.getMobile());
                mobileTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Uri number = Uri.parse("tel:"+mobileTV.getText());
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                        startActivity(callIntent);

                    }
                });



                mobileIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri number = Uri.parse("tel:"+mobileTV.getText());
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                        startActivity(callIntent);

                    }
                });

                return cellUser;
            }
        });
        return rootView;
    }

    private class PlaceHolder{
        TextView firstName;
        TextView lastName;
        TextView email;
        TextView mobile;
        ImageView iconPhone;
        ImageView iconEmail;

    }






}
