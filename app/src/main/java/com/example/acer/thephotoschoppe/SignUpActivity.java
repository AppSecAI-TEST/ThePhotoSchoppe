package com.example.acer.thephotoschoppe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_sign_up)
public class SignUpActivity extends RoboActivity {

    @InjectView(R.id.txt_username) private EditText txt_username;
    @InjectView(R.id.txt_password) private EditText txt_password;
    @InjectView(R.id.txt_confirm_password) private EditText txt_confirm_password;
    @InjectView(R.id.txt_email) private EditText txt_email;

    private static final String TAG="SignUpActivity";

    private static final String KEY_REGISTERED="registered";
    private static final String KEY_USERNAME="username";
    private static final String KEY_PASSWORD="password";
    private static final String KEY_EMAIL="email";
    private static final String KEY_LOGIN="login";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences=getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        boolean isRegister=preferences.getBoolean(KEY_REGISTERED,false);
        boolean isLoggedIn=preferences.getBoolean(KEY_LOGIN,false);
        if(isRegister){
            if(isLoggedIn){
                Intent intent =new Intent(SignUpActivity.this,NavigationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
            else {
                Intent intent =new Intent(SignUpActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }


        }

    }


    public void saveSettings(View view){
        Log.d(TAG,"on click save");
        SharedPreferences preferences=getSharedPreferences(getString(R.string.shared_preferences),Context.MODE_PRIVATE);
        //initialize editor to edit
        SharedPreferences.Editor editor=preferences.edit();
        //add data to the editor
        Log.d(TAG,txt_confirm_password.getText()+"");
        if(txt_username.getText().toString().equals("") ||
                txt_email.getText().toString().equals("") ||
                txt_password.getText().toString().equals("") ||
                txt_confirm_password.getText().toString().equals("")){
            Toast.makeText(SignUpActivity.this,"Please make sure to fill the form properly",Toast.LENGTH_LONG).show();

            return;
        }else if(!txt_confirm_password.getText().toString().equals(txt_password.getText().toString())){
            Toast.makeText(SignUpActivity.this,"Password confirmation did not matched",Toast.LENGTH_LONG).show();
//            txt_confirm_password.
            return;
        }
        else {
            editor.putBoolean(KEY_REGISTERED,true);
            editor.putString(KEY_USERNAME,txt_username.getText().toString());
            editor.putString(KEY_PASSWORD,txt_password.getText().toString());
            editor.putString(KEY_EMAIL,txt_email.getText().toString());
            editor.putBoolean(KEY_LOGIN,true);
            //commit changes to the shared preferences
            editor.commit();

            //success message
            Toast.makeText(SignUpActivity.this,"User registered successfully",Toast.LENGTH_LONG).show();


            //start new activity and kill the current activity
            Intent intent=new Intent(SignUpActivity.this,NavigationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }



    }



}
