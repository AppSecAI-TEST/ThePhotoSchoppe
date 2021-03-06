package com.example.acer.thephotoschoppe.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.acer.thephotoschoppe.R;
import com.example.acer.thephotoschoppe.fragment.DirectoryFragment;
import com.example.acer.thephotoschoppe.fragment.MoreFragment;
import com.example.acer.thephotoschoppe.fragment.PortfolioFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String  TAG ="NavigationActivity" ;
    ConstraintLayout layout;


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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        PortfolioFragment portfolioFragment=new PortfolioFragment();
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.main_layout,portfolioFragment).commit();
        this.setTitle(getString(R.string.title_activity_main));
        layout=(ConstraintLayout)findViewById(R.id.constraint);
        layout.setBackgroundResource(R.drawable.background10);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (id == R.id.nav_portfolio) {
            PortfolioFragment portfolioFragment=new PortfolioFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.main_layout,portfolioFragment).commit();
            this.setTitle("Portfolio");
            layout.setBackgroundResource(R.drawable.background10);

            // Handle the camera action

        } else if (id == R.id.nav_directory) {
            DirectoryFragment directoryFragment=new DirectoryFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.main_layout,directoryFragment).commit();
            this.setTitle("Directory");
            layout.setBackgroundResource(R.drawable.images_back);



        } else if (id == R.id.nav_more) {
            MoreFragment moreFragment=new MoreFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.main_layout,moreFragment).commit();
            this.setTitle("Contact Information");

            layout.setBackgroundResource(R.drawable.background11);



        }else if (id == R.id.nav_logout) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Logout");
            alertDialog.setMessage("Do you want to logout from this app?");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"CANCEL",
                    new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor=getInstance().edit();
                            editor.putBoolean(getString(R.string.key_login),false);
                            editor.putBoolean(getString(R.string.key_registered),true);
                            //commit changes to make in the editor
                            editor.commit();

                            //finish the current activity and start new activity
                            Intent intent =new Intent(MainActivity.this,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    });


            alertDialog.show();



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
