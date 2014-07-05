package com.windupurnomo.simadutanjung.listener;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

import com.windupurnomo.simadutanjung.R;

/**
 * Created by Windu Purnomo on 7/5/14.
 */
public class MeasurementTabListener implements ActionBar.TabListener {
    public Fragment fragment;
    public Context context;

    public  MeasurementTabListener(Fragment fragment, Context context){
        this.fragment = fragment;
        this.context = context;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.replace(R.id.fragment_container, fragment);

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.remove(fragment);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
