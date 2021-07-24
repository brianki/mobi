package com.example.mobicow;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerlayout =(DrawerLayout)findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerlayout,R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }
    @Override
    public void onBackPressed() {
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        if (mDrawerlayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerlayout.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Are you sure you want to exit MobiCow?");
            alertDialogBuilder
                    .setMessage("Click yes to exit!")
                    .setCancelable(false)
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        /**
    }
        switch (menuItem.getItemId()) {
            case R.id.checkbill:
                main.setVisibility(View.GONE);
                //mdrawerlayout.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.screen, new CheckbillFragment()).commit();
                break;
            case R.id.comm:
                main.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.screen, new CommFragment()).commit();
                break;
            case R.id.farewell:
                main.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.screen, new FarewellFragment()).commit();
                break;
            case R.id.paybill:
                Intent log = new Intent(getApplicationContext(),PaybillActivity.class);
                startActivity(log);
                break;


            case R.id.email:
                // main.setVisibility(View.GONE);
                // getSupportFragmentManager().beginTransaction().replace(R.id.screen, new EmailFragment()).commit();
                // break;
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("email"));
                String[] s = {"mtrh@gmail.go.ke"};
                i.putExtra(Intent.EXTRA_EMAIL, s);
                i.putExtra(Intent.EXTRA_SUBJECT, "Replace with your subject");
                i.setType("message/rfc822");
                Intent chooser = Intent.createChooser(i, "choose gmail to initiate the mail");
                startActivity(chooser);
                break;

            case R.id.share:
                // main.setVisibility(View.GONE);
                // getSupportFragmentManager().beginTransaction().replace(R.id.screen, new ShareFragment()).commit();
                //break;
                ApplicationInfo api = getApplicationContext().getApplicationInfo();
                String apkpath = api.sourceDir;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("application/vnd.android.package-archive");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
                startActivity(Intent.createChooser(intent, "ShareVia"));**/
        DrawerLayout mdrawerlayout = findViewById(R.id.drawer);
        mdrawerlayout.closeDrawer(GravityCompat.START);
                return true;
        }

}
