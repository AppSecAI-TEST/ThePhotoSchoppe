package com.example.acer.thephotoschoppe.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.acer.thephotoschoppe.R;
import com.example.acer.thephotoschoppe.database.DatabaseHandler;
import com.example.acer.thephotoschoppe.models.Photographer;

import roboguice.inject.InjectView;

public class DirectoryActivity extends AppCompatActivity {


    private static final String TAG="ViewUserActivity";
    private DatabaseHandler dbHandler;
    public  DatabaseHandler getDBHandler(){
        if(dbHandler==null){
            dbHandler=new DatabaseHandler(this);
        }
        return dbHandler;
    }

    private Photographer[] photographers;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
        listView=(ListView)findViewById(R.id.listView);

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
                    cellUser= LayoutInflater.from(DirectoryActivity.this).inflate(R.layout.directory_list_item,null);

                }
                else {
                    cellUser=convertView;
                }

                PlaceHolder ph=(PlaceHolder)cellUser.getTag();
                TextView firstNameTV;
                TextView lastNameTV;
                TextView emailTV;
                TextView mobileTV;

                if(ph==null){
                    firstNameTV=(TextView) cellUser.findViewById(R.id.first_name);
                    lastNameTV= (TextView) cellUser.findViewById(R.id.last_name);
                    emailTV=(TextView) cellUser.findViewById(R.id.email);
                    mobileTV=(TextView) cellUser.findViewById(R.id.mobile);


                    ph=new PlaceHolder();
                    ph.firstName=firstNameTV;
                    ph.lastName=lastNameTV;
                    ph.email=emailTV;
                    ph.mobile=mobileTV;
                    cellUser.setTag(ph);

                }
                else {
                    firstNameTV=ph.firstName;
                    lastNameTV=ph.lastName;
                    emailTV=ph.email;
                    mobileTV=ph.mobile;

                }


                firstNameTV.setText(photographer.getFirstName());
                lastNameTV.setText(photographer.getLastName());
                emailTV.setText(photographer.getEmail());
                mobileTV.setText(photographer.getMobile());

                cellUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent=new Intent(DirectoryActivity.this, UpdateUserActivity.class);
//                        intent.putExtra("id",user.getId());
//                        intent.putExtra("name",user.getName());
//                        intent.putExtra("age",user.getAge());
//                        intent.putExtra("city",user.getCity());
//
//                        startActivity(intent);
                    }
                });


                return cellUser;
            }
        });
    }

    private class PlaceHolder{
        TextView firstName;
        TextView lastName;
        TextView email;
        TextView mobile;
    }
}
