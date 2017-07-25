package com.example.acer.thephotoschoppe.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.acer.thephotoschoppe.models.Photographer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prabodhaharankahadeniya on 7/24/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String TAG="DBHandler";
    private static final String DATABASE_NAME = "photoshoppe.db";
    private static final String DATABASE_LOCATION = "/data/data/com.example.acer.thephotoschoppe/databases/";


    private static final int DATABASE_VERSION=1;

    private  Context context;
    private static SQLiteDatabase database;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Photographer[] getPhotographers(){

        openReadableDatabase();
        Cursor cursor=database.rawQuery("SELECT * FROM photographer",null);

        ArrayList<Photographer> list=new ArrayList<>();

        while (cursor.moveToNext()){
            Photographer photographer=new Photographer();
            photographer.setFirstName(cursor.getString(0));
            photographer.setLastName(cursor.getString(1));
            photographer.setMobile(cursor.getString(2));
            photographer.setEmail(cursor.getString(3));
            list.add(photographer);

        }
        cursor.close();
        closeDatabase();
        return sortPhotographers(list.toArray(new Photographer[list.size()]));
        //return list.toArray(new Photographer[list.size()]);
    }

    private Photographer[] sortPhotographers(Photographer[] photographers){

        Photographer temp;
        for (int i = 0; i < photographers.length; i++)
        {
            for (int j = i + 1; j < photographers.length; j++)
            {
                if (photographers[i].getFirstName().compareTo(photographers[j].getFirstName())>0)
                {
                    temp = photographers[i];
                    photographers[i]=photographers[j];
                    photographers[j] = temp;
                }
            }
        }
        return photographers;
    }

    public void openWritableDatabase(){
        String dbPath=context.getDatabasePath(DATABASE_NAME).getPath();
        if(database!=null && database.isOpen()){
            return;
        }
        database=SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public void openReadableDatabase(){
        String dbPath=context.getDatabasePath(DATABASE_NAME).getPath();
        if(database!=null && database.isOpen()){
            return;
        }
        database=SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READONLY);
    }

    public void closeDatabase(){
        if(database!=null){
            database.close();
        }
    }



    public static String getDBName() {
        return DATABASE_NAME;
    }

    public static String getDatabaseLocation() {
        return DATABASE_LOCATION;
    }
}
