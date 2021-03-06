package com.example.acer.thephotoschoppe.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.thephotoschoppe.R;



public class LoginActivity extends AppCompatActivity {

    private static final String TAG ="LoginActivity" ;

    private EditText txt_username;
    private EditText txt_password;

    private TextView err_username;
    private TextView err_password;

    private Button btn_login;


    SharedPreferences preferences;
    private SharedPreferences getInstance(){
        if(preferences==null){
            preferences=getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        }
        return preferences;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setEnabled(false);

        err_username=(TextView) findViewById(R.id.username_err);
        err_password=(TextView)findViewById(R.id.password_err);
        err_username.setVisibility(View.INVISIBLE);
        err_password.setVisibility(View.INVISIBLE);

        txt_username=(EditText)findViewById(R.id.txt_username);
        txt_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(!txt_username.getText().toString().equals("")){
                        if(!hasError()){
                            btn_login.setEnabled(true);
                        }else {
                            btn_login.setEnabled(false);

                        }


                    }else {
                        err_username.setText(getString(R.string.username_required));
                        err_username.setVisibility(View.VISIBLE);
                        btn_login.setEnabled(false);
                    }

                }
                else {
                    err_username.setText("");
                    err_password.setVisibility(View.INVISIBLE);

                }
            }
        });

        txt_password=(EditText)findViewById(R.id.txt_password);
        txt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(!txt_password.getText().toString().equals("")){
                        if(!hasError()){
                            btn_login.setEnabled(true);
                        }
                        else {
                            btn_login.setEnabled(false);

                        }

                    }
                    else {
                        err_password.setText(getString(R.string.password_required));
                        err_password.setVisibility(View.VISIBLE);
                        btn_login.setEnabled(false);
                    }

                }
                else {
                    err_password.setText("");
                    err_password.setVisibility(View.INVISIBLE);
                }
            }
        });




    }

    public void onClickLogin(View view){
        err_username.setVisibility(View.INVISIBLE);
        err_password.setVisibility(View.INVISIBLE);
        boolean flag=false;
        if(txt_password.getText().toString().equals("")){
            err_password.setVisibility(View.VISIBLE);
            err_password.setText(getString(R.string.username_required));
          flag=true;
        }
        if(txt_username.getText().toString().equals("")){
            err_username.setVisibility(View.VISIBLE);
            err_username.setText(getString(R.string.username_required));
            flag=true;



        }
        if(flag){

            //clear data
            txt_username.setText("");
            txt_password.setText("");
        }
        else {

            String storedUsername=getInstance().getString(getString(R.string.key_username),null);
            String storedPassword=getInstance().getString(getString(R.string.key_password),null);
            if(storedUsername.equals(txt_username.getText().toString())){
                if(storedPassword.equals(txt_password.getText().toString())){
                    SharedPreferences.Editor editor=getInstance().edit();
                    editor.putBoolean(getString(R.string.key_login),true);
                    editor.putBoolean(getString(R.string.key_registered),true);
                    editor.commit();

                    //success message
                    Toast.makeText(LoginActivity.this,"Successfully logged into the system.",Toast.LENGTH_LONG).show();


                    //start new activity and kill the current activity
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
                else {
                    //error message
                    err_password.setVisibility(View.VISIBLE);
                    err_password.setText(getString(R.string.incorrect_password));


                    //clear data

                    txt_password.setText("");


                }
            }else {
                //error message
                err_password.setVisibility(View.VISIBLE);
                err_password.setText(getString(R.string.incorrect_username));

                //clear data
                txt_username.setText("");
                txt_password.setText("");

            }

        }

    }

    public void signUpFirst(View view){
        SharedPreferences.Editor editor=getInstance().edit();
        editor.putBoolean(getString(R.string.key_request_sign_up),true);

        editor.commit();
        //start new activity and kill the current activity
        Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
        intent.putExtra("flag",true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }


    private boolean hasError(){
        if(err_username.getVisibility()==View.VISIBLE ||
                err_password.getVisibility()==View.VISIBLE){
            return true;
        }
        else {
            return false;
        }

    }

}
