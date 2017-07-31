package com.example.acer.thephotoschoppe.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.thephotoschoppe.R;
import com.example.acer.thephotoschoppe.database.DatabaseHandler;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity {


    private static final String TAG="SignUpActivity";

    private EditText txt_username;
    private EditText txt_password;
    private EditText txt_confirm_password;
    private EditText txt_email;

    private TextView err_username;
    private TextView err_password;
    private TextView err_confirm_password;
    private TextView err_email;

    private Button btn_register;

    SharedPreferences preferences;
    private SharedPreferences getInstance(){
        if(preferences==null){
            preferences=getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        }
        return preferences;
    }

    DatabaseHandler dbHandler;
    private DatabaseHandler getDBHandler(){
        if(dbHandler==null){
            dbHandler=new DatabaseHandler(this);
        }
        return dbHandler;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Check exists database
        File database = getApplicationContext().getDatabasePath(DatabaseHandler.getDBName());
        if(false == database.exists()) {
            getDBHandler().getReadableDatabase();
            //Copy database
            if(copyDatabase(this)) {
                //database copied successfully

            } else {
                Toast.makeText(this, "Something went wrong. Please reinstall.", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(SignUpActivity.this,BlankActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        }

        btn_register=(Button)findViewById(R.id.btn_register);
        btn_register.setEnabled(false);

        err_username=(TextView) findViewById(R.id.username_err);
        err_password=(TextView)findViewById(R.id.password_err);
        err_confirm_password=(TextView)findViewById(R.id.confirm_password_err);
        err_email=(TextView)findViewById(R.id.email_err);

        err_username.setVisibility(View.INVISIBLE);
        err_password.setVisibility(View.INVISIBLE);
        err_confirm_password.setVisibility(View.INVISIBLE);
        err_email.setVisibility(View.INVISIBLE);

        txt_username=(EditText)findViewById(R.id.txt_username);
        txt_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(!txt_username.getText().toString().equals("")){
                        btn_register.setEnabled(true);
                        String error=isValidUsername(txt_username.getText().toString());
                        if(error!=null){
                            err_username.setText(error);
                            err_username.setVisibility(View.VISIBLE);
                        }

                    }
                    else {
                        err_username.setVisibility(View.VISIBLE);
                        err_username.setText(getString(R.string.username_required));
                    }

                }
                else {
                    err_username.setText("");
                }
            }
        });

        txt_password=(EditText)findViewById(R.id.txt_password);
        txt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {


                    if(!txt_password.getText().toString().equals("")){
                        btn_register.setEnabled(true);
                        String error=isValidPassword(txt_password.getText().toString());
                        if(error!=null){
                            err_password.setText(error);
                            err_password.setVisibility(View.VISIBLE);
                        }

                    }else {
                        err_password.setVisibility(View.VISIBLE);
                        err_password.setText(getString(R.string.password_required));
                    }


                }
                else {
                    err_password.setText("");
                }
            }
        });
        txt_confirm_password=(EditText)findViewById(R.id.txt_confirm_password);
        txt_confirm_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(!txt_confirm_password.getText().toString().equals("")){
                        btn_register.setEnabled(true);
                        if(!txt_confirm_password.getText().toString().equals(txt_password.getText().toString())){
                            err_confirm_password.setText(getString(R.string.password_not_match));
                            err_confirm_password.setVisibility(View.VISIBLE);

                        }

                    }else {
                        err_confirm_password.setVisibility(View.VISIBLE);
                        err_confirm_password.setText(getString(R.string.conf_password_required));
                    }

                }
                else {
                    err_confirm_password.setText("");
                }
            }
        });
        txt_email=(EditText)findViewById(R.id.txt_email);
        txt_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(!txt_email.getText().toString().equals("")){
                        btn_register.setEnabled(true);
                        if(!isValidEmail(txt_email.getText())){
                            err_email.setVisibility(View.VISIBLE);
                            err_email.setText(getString(R.string.email_error));
                        }

                    }
                    else {
                        err_email.setVisibility(View.VISIBLE);
                        err_email.setText(getString(R.string.email_required));
                    }

                }
                else {
                    err_email.setText("");
                }
            }
        });





        boolean flag=getInstance().getBoolean(getString(R.string.key_request_sign_up),false);

        Log.d(TAG,flag+"");

        if(flag==false){
            boolean isRegister=getInstance().getBoolean(getString(R.string.key_registered),false);
            boolean isLoggedIn=getInstance().getBoolean(getString(R.string.key_login),false);
            if(isRegister){
                if(isLoggedIn){
                    Intent intent =new Intent(SignUpActivity.this,MainActivity.class);
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
        SharedPreferences.Editor editor=getInstance().edit();
        editor.putBoolean(getString(R.string.key_request_sign_up),false);

        editor.commit();
    }


    private boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(DatabaseHandler.getDBName());
            String outFileName = DatabaseHandler.getDatabaseLocation() + DatabaseHandler.getDBName();
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[]buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.d(TAG,"DB copied");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveSettings(View view){
        Log.d(TAG,"on click save");

        err_username.setVisibility(View.INVISIBLE);
        err_password.setVisibility(View.INVISIBLE);
        err_confirm_password.setVisibility(View.INVISIBLE);
        err_email.setVisibility(View.INVISIBLE);

        //initialize editor to edit
        SharedPreferences.Editor editor=getInstance().edit();
        //add data to the editor
        Log.d(TAG,txt_confirm_password.getText()+"");
        boolean flag=false;

        if(txt_username.getText().toString().equals("")){
            err_username.setVisibility(View.VISIBLE);
            err_username.setText(getString(R.string.username_required));
            flag=true;
        }
        if(txt_email.getText().toString().equals("")){
            err_email.setVisibility(View.VISIBLE);
            err_email.setText(getString(R.string.email_required));
            flag=true;
        }
        if(txt_password.getText().toString().equals("")){
            err_password.setVisibility(View.VISIBLE);
            err_password.setText(getString(R.string.password_required));
            flag=true;
        }
        if(txt_confirm_password.getText().toString().equals("")){
            err_confirm_password.setVisibility(View.VISIBLE);
            err_confirm_password.setText(getString(R.string.conf_password_required));
            flag=true;
        }
        if(!flag && !txt_confirm_password.getText().toString().equals(txt_password.getText().toString())){
            err_confirm_password.setText(getString(R.string.password_not_match));
            err_confirm_password.setVisibility(View.VISIBLE);

            return;
        }
        if(isValidEmail(txt_email.getText()) && !flag) {
            String error=isValidPassword(txt_password.getText().toString());
            if(error==null){
                editor.putBoolean(getString(R.string.key_registered), true);
                editor.putString(getString(R.string.key_username), txt_username.getText().toString());
                editor.putString(getString(R.string.key_password), txt_password.getText().toString());
                editor.putString(getString(R.string.key_email), txt_email.getText().toString());
                editor.putBoolean(getString(R.string.key_login), true);
                //commit changes to the shared preferences
                editor.commit();

                //success message
                Toast.makeText(SignUpActivity.this, "User registered successfully.", Toast.LENGTH_LONG).show();

                //start new activity and kill the current activity
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }else {
                err_password.setText(error);
                err_password.setVisibility(View.VISIBLE);
            }


        }
        else {
            err_email.setVisibility(View.VISIBLE);
            err_email.setText(getString(R.string.email_error));
        }
    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private  String isValidUsername(String target){
        if(target.length()>=5){
            return null;
        }
        else {

            return getString(R.string.short_username_error);
        }

    }


    private String isValidPassword(String target){
        if(target.length()>=8){
            if(target.matches(".*\\d+.*")){
                String temp=target.toLowerCase();
                if(!target.equals(temp)){
                    Pattern regex = Pattern.compile("[$&+,:;=?@#|]");
                    Matcher matcher = regex.matcher(target);
                    if (matcher.find()){
                        return null;
                    }
                    else {
                        return getString(R.string.symbol_error);
                    }

                }
                else{
                    return getString(R.string.uppercase_error);
                }
            }else {
                return getString(R.string.number_error);
            }

        }
        else {

            return getString(R.string.short_password_error);
        }

    }

}
