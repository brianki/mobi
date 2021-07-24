package com.example.mobicow;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MycowActivity extends AppCompatActivity {
   FloatingActionButton fab,fab1,fab2;
   Animation fab_open,fab_close,fab_clock,fab_anticlock;
   boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycow);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                   // add_cow.setVisibility(View.INVISIBLE);
                    //sell_cow.setVisibility(View.INVISIBLE);
                    fab.startAnimation(fab_anticlock);
                    fab2.startAnimation(fab_close);
                    fab1.startAnimation(fab_close);
                    fab1.setClickable(false);
                    fab2.setClickable(false);
                    isOpen = false;

                } else {
                    ///add_cow.setVisibility(View.VISIBLE);
                    //sell_cow.setVisibility(View.VISIBLE);
                    fab.startAnimation(fab_clock);
                    fab2.startAnimation(fab_open);
                    fab1.startAnimation(fab_open);
                    fab1.setClickable(true);
                    fab2.setClickable(true);
                    isOpen = true;
                }
            }
        });
       fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CowsellActivity.class);
                startActivity(intent);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CowaddActivity.class);
                startActivity(intent);
            }
        });
    }

}
