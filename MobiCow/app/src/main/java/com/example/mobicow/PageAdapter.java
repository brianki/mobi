package com.example.mobicow;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
 import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return  new SaleFragment();
            case 1:
                return  new ConsumeFragment();
            case 2:
                return  new PayFragment();
                default:
                    return  null;
        }

    }

     PageAdapter(FragmentManager fm,int numOfTabs) {
        super(fm);
        this.numOfTabs =numOfTabs;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
