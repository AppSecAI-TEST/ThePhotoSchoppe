package com.example.acer.thephotoschoppe.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acer.thephotoschoppe.R;
import com.example.acer.thephotoschoppe.database.DatabaseHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class SignUpActivity extends AppCompatActivity {


    private static final String TAG="SignUpActivity";

    private EditText txt_username;
    private EditText txt_password;
    private EditText txt_confirm_password;
    private EditText txt_email;

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
            //Copy db
            if(copyDatabase(this)) {
                Toast.makeText(this, "Copy database success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        txt_username=(EditText)findViewById(R.id.txt_username);
        txt_password=(EditText)findViewById(R.id.txt_password);
        txt_confirm_password=(EditText)findViewById(R.id.txt_confirm_password);
        txt_email=(EditText)findViewById(R.id.txt_email);


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

        //initialize editor to edit
        SharedPreferences.Editor editor=getInstance().edit();
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


            editor.putBoolean(getString(R.string.key_registered), true);
            editor.putString(getString(R.string.key_username), txt_username.getText().toString());
            editor.putString(getString(R.string.key_password), txt_password.getText().toString());
            editor.putString(getString(R.string.key_email), txt_email.getText().toString());
            editor.putBoolean(getString(R.string.key_login), true);
            //commit changes to the shared preferences
            editor.commit();

            //success message
            Toast.makeText(SignUpActivity.this, "User registered successfully", Toast.LENGTH_LONG).show();



            //start new activity and kill the current activity
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }
    }

}
