package com.example.acer.thephotoschoppe.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.acer.thephotoschoppe.R;

public class BlankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        TextView textView=(TextView)findViewById(R.id.msg_tv);
        LinearLayout layout=(LinearLayout)findViewById(R.id.linear_layout);



        Intent intent=getIntent();
        int viewId=intent.getExtras().getInt("view");
        if(viewId==1){
            Log.d("msg","installation error");
            textView.setText("Something went wrong. Please reinstall the application.");
            layout.setBackgroundResource(R.color.colorSilver);

        }
        else{
            Log.d("msg","connection error");
            textView.setText("Something went wrong. Please your internet connection.");
            layout.setBackgroundResource(R.color.colorDarkRed);


        }
    }

    public void onClickBack(View view){
        finish();
        finish();
        Intent intent=new Intent(BlankActivity.this,SignUpActivity.class);
        startActivity(intent);
    }
}
