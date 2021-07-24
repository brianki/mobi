package com.example.mobicow;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class SalesActivity extends AppCompatActivity {
    ViewPager viewPage;
    TabLayout tablay;
    TabItem tabsales,tabconsum,tabpay;
    PageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        viewPage=findViewById(R.id.viewpage);
        tablay =(TabLayout)findViewById(R.id.tablay);
        tabsales = (TabItem)findViewById(R.id.tabsales);
        tabconsum =(TabItem) findViewById(R.id.tabconsum);
        tabpay = (TabItem)findViewById(R.id.tabpay);

       PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(),tablay.getTabCount());
        viewPage.setAdapter(pageAdapter);



      tablay.addOnTabSelectedListener (new TabLayout.BaseOnTabSelectedListener() {
          @Override
          public void onTabSelected(TabLayout.Tab tab) {
              viewPage.setCurrentItem(tab.getPosition());

          }

          @Override
          public void onTabUnselected(TabLayout.Tab tab) {

          }

          @Override
          public void onTabReselected(TabLayout.Tab tab) {

          }
      });
        viewPage.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablay));
        }

    }



