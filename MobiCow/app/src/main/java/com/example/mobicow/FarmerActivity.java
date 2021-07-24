package com.example.mobicow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;


public class FarmerActivity extends AppCompatActivity {
private TextView profile;
String text;
CardView sale;
CardView mycow;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);
        sale = (CardView) findViewById(R.id.sale);
        mycow = (CardView) findViewById(R.id.mycow);

     //profile= (TextView) findViewById(R.id.profile);

        //Bundle bundle =getIntent().getExtras();
        //text= bundle.getString("role");
        //profile.setText(text);
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SalesActivity.class);
                startActivity(intent);
            }
        });
     mycow.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getApplicationContext(),MycowActivity.class);
             startActivity(intent);
         }
     });
    }
}
