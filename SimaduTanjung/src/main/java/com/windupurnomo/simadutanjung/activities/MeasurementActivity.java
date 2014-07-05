package com.windupurnomo.simadutanjung.activities;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cengalabs.flatui.FlatUI;
import com.windupurnomo.simadutanjung.R;
import com.windupurnomo.simadutanjung.fragments.FragmentArus;
import com.windupurnomo.simadutanjung.fragments.FragmentTegangan;
import com.windupurnomo.simadutanjung.listener.MeasurementTabListener;

public class MeasurementActivity extends ActionBarActivity {
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private int idGardu;
    private String noGardu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = getIntent().getIntExtra("theme", 0);
        idGardu = getIntent().getIntExtra("garduId", 0);
        noGardu = getIntent().getStringExtra("garduNo");

        setContentView(R.layout.activity_measurement);
        getActionBar().setTitle("Pengukuran " + noGardu);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(theme);

        Fragment teganganFragment = new FragmentTegangan();
        Fragment arusFragment = new FragmentArus();

        // Set up the action bar to show tabs.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab arusTab = actionBar.newTab().setText("Arus")
                .setTabListener(new MeasurementTabListener(arusFragment, getApplicationContext()));
        ActionBar.Tab teganganTab = actionBar.newTab().setText("Tegangan")
                .setTabListener(new MeasurementTabListener(teganganFragment, getApplicationContext()));

        // for each of the sections in the app, add a tab to the action bar.
        actionBar.addTab(arusTab);
        actionBar.addTab(teganganTab);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current tab position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current tab position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
                .getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.measurement, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends android.support.v4.app.Fragment {

        public static final String ARG_SECTION_NUMBER = "placeholder_text";
        public static TextView txtHello;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_measurement, container, false);
            txtHello = (TextView) rootView.findViewById(R.id.txtTab);
            //String s = Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER));
            txtHello.setText("Test");
            return rootView;
        }
    }
}
