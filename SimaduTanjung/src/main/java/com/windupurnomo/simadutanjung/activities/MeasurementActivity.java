package com.windupurnomo.simadutanjung.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;
import com.windupurnomo.simadutanjung.R;
import com.windupurnomo.simadutanjung.entities.FragmentDataShare;
import com.windupurnomo.simadutanjung.entities.MeasurementVariableName;
import com.windupurnomo.simadutanjung.fragments.FragmentArus;
import com.windupurnomo.simadutanjung.fragments.FragmentTegangan;
import com.windupurnomo.simadutanjung.listener.MeasurementTabListener;
import com.windupurnomo.simadutanjung.util.NetworkUtil;

public class MeasurementActivity extends ActionBarActivity {
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    public String PREFS_NAME = "DataMeasurement-";
    private SharedPreferences sp;
    private int idGardu;
    private String noGardu;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int theme = getIntent().getIntExtra("theme", 0);
        idGardu = getIntent().getIntExtra("garduId", 0);
        noGardu = getIntent().getStringExtra("garduNo");
        num = getIntent().getIntExtra("measurementNumber", 0);

        resetFragmentDataShare();
        setupSharedPreferences();

        setContentView(R.layout.activity_measurement);
        getActionBar().setTitle("Pengukuran " + noGardu + " ke-" + num);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(theme);

        Fragment teganganFragment = new FragmentTegangan();
        Fragment arusFragment = new FragmentArus();

        // Set up the action bar to show tabs.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
        //save in preference
        if (id == R.id.option_menu_save){
            String content = "Save Pressed.\nPhase-R: " + FragmentDataShare.phaseR;
            content += "\nNetral-NH-A: " + FragmentDataShare.netralNhA;
            content += "\nUjung Tiang A: " +FragmentDataShare.tiangUjungA;
            content += "\nRN: " + FragmentDataShare.rn;
            Toast.makeText(this, content , Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor = sp.edit();
            saveDataToSharedPref(editor);
            editor.putBoolean(MeasurementVariableName.dataStatus, true);
            editor.commit();
            goToHomeActivity();
        }

        //publish to internet
        if (id == R.id.option_menu_publish){
            SharedPreferences.Editor editor = sp.edit();
            resetSharePref(editor);
            editor.putString(MeasurementVariableName.garduName, noGardu);
            editor.putBoolean(MeasurementVariableName.dataStatus, false);
            editor.commit();
            goToHomeActivity();
        }

        if (id == R.id.home){
            goToHomeActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToHomeActivity(){
        Intent in = new Intent(getApplicationContext(), Home.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
    }

    private void setupSharedPreferences() {
        PREFS_NAME += noGardu + num;
        sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isThereDataLocal = sp.getBoolean(MeasurementVariableName.dataStatus, false);
        if(NetworkUtil.isNetworkAvailable(this)) {
            //it's mean there are no data on shared preferences
            //so we will get data from server
            FragmentDataShare.phaseR = 999f;
        }else{
            FragmentDataShare.phaseR = sp.getFloat(MeasurementVariableName.phaseR, 0f);
            FragmentDataShare.phaseS = sp.getFloat(MeasurementVariableName.phaseS, 0f);
            FragmentDataShare.phaseT = sp.getFloat(MeasurementVariableName.phaseT, 0f);
            FragmentDataShare.netral = sp.getFloat(MeasurementVariableName.netral, 0f);

            FragmentDataShare.phaseRNhA = sp.getFloat(MeasurementVariableName.phaseRNhA, 0f);
            FragmentDataShare.phaseSNhA = sp.getFloat(MeasurementVariableName.phaseSNhA, 0f);
            FragmentDataShare.phaseTNhA = sp.getFloat(MeasurementVariableName.phaseTNhA, 0f);
            FragmentDataShare.netralNhA = sp.getFloat(MeasurementVariableName.netralNhA, 0f);

            FragmentDataShare.phaseRNhB = sp.getFloat(MeasurementVariableName.phaseRNhB, 0f);
            FragmentDataShare.phaseSNhB = sp.getFloat(MeasurementVariableName.phaseSNhB, 0f);
            FragmentDataShare.phaseTNhB = sp.getFloat(MeasurementVariableName.phaseTNhB, 0f);
            FragmentDataShare.netralNhB = sp.getFloat(MeasurementVariableName.netralNhB, 0f);

            FragmentDataShare.phaseRNhC = sp.getFloat(MeasurementVariableName.phaseRNhC, 0f);
            FragmentDataShare.phaseSNhC = sp.getFloat(MeasurementVariableName.phaseSNhC, 0f);
            FragmentDataShare.phaseTNhC = sp.getFloat(MeasurementVariableName.phaseTNhC, 0f);
            FragmentDataShare.netralNhC = sp.getFloat(MeasurementVariableName.netralNhC, 0f);

            FragmentDataShare.phaseRNhD = sp.getFloat(MeasurementVariableName.phaseRNhD, 0f);
            FragmentDataShare.phaseSNhD = sp.getFloat(MeasurementVariableName.phaseSNhD, 0f);
            FragmentDataShare.phaseTNhD = sp.getFloat(MeasurementVariableName.phaseTNhD, 0f);
            FragmentDataShare.netralNhD = sp.getFloat(MeasurementVariableName.netralNhD, 0f);

            FragmentDataShare.phaseRBebanA = sp.getFloat(MeasurementVariableName.phaseRBebanA, 0f);
            FragmentDataShare.phaseSBebanA = sp.getFloat(MeasurementVariableName.phaseSBebanA, 0f);
            FragmentDataShare.phaseTBebanA = sp.getFloat(MeasurementVariableName.phaseTBebanA, 0f);
            FragmentDataShare.netralBebanA = sp.getFloat(MeasurementVariableName.netralBebanA, 0f);

            FragmentDataShare.phaseRBebanB = sp.getFloat(MeasurementVariableName.phaseRBebanB, 0f);
            FragmentDataShare.phaseSBebanB = sp.getFloat(MeasurementVariableName.phaseSBebanB, 0f);
            FragmentDataShare.phaseTBebanB = sp.getFloat(MeasurementVariableName.phaseTBebanB, 0f);
            FragmentDataShare.netralBebanB = sp.getFloat(MeasurementVariableName.netralBebanB, 0f);

            FragmentDataShare.phaseRBebanC = sp.getFloat(MeasurementVariableName.phaseRBebanC, 0f);
            FragmentDataShare.phaseSBebanC = sp.getFloat(MeasurementVariableName.phaseSBebanC, 0f);
            FragmentDataShare.phaseTBebanC = sp.getFloat(MeasurementVariableName.phaseTBebanC, 0f);
            FragmentDataShare.netralBebanC = sp.getFloat(MeasurementVariableName.netralBebanC, 0f);

            FragmentDataShare.phaseRBebanD = sp.getFloat(MeasurementVariableName.phaseRBebanD, 0f);
            FragmentDataShare.phaseSBebanD = sp.getFloat(MeasurementVariableName.phaseSBebanD, 0f);
            FragmentDataShare.phaseTBebanD = sp.getFloat(MeasurementVariableName.phaseTBebanD, 0f);
            FragmentDataShare.netralBebanD = sp.getFloat(MeasurementVariableName.netralBebanD, 0f);

            FragmentDataShare.rn = sp.getFloat(MeasurementVariableName.rn, 0f);
            FragmentDataShare.sn = sp.getFloat(MeasurementVariableName.sn, 0f);
            FragmentDataShare.tn = sp.getFloat(MeasurementVariableName.tn, 0f);
            FragmentDataShare.rs = sp.getFloat(MeasurementVariableName.rs, 0f);
            FragmentDataShare.st = sp.getFloat(MeasurementVariableName.st, 0f);
            FragmentDataShare.rt = sp.getFloat(MeasurementVariableName.rt, 0f);

            FragmentDataShare.rnA = sp.getFloat(MeasurementVariableName.rnA, 0f);
            FragmentDataShare.snA = sp.getFloat(MeasurementVariableName.snA, 0f);
            FragmentDataShare.tnA = sp.getFloat(MeasurementVariableName.tnA, 0f);
            FragmentDataShare.rsA = sp.getFloat(MeasurementVariableName.rsA, 0f);
            FragmentDataShare.stA = sp.getFloat(MeasurementVariableName.stA, 0f);
            FragmentDataShare.rtA = sp.getFloat(MeasurementVariableName.rtA, 0f);
            FragmentDataShare.tiangUjungA = sp.getString(MeasurementVariableName.tiangUjungA, "");

            FragmentDataShare.rnB = sp.getFloat(MeasurementVariableName.rnB, 0f);
            FragmentDataShare.snB = sp.getFloat(MeasurementVariableName.snB, 0f);
            FragmentDataShare.tnB = sp.getFloat(MeasurementVariableName.tnB, 0f);
            FragmentDataShare.rsB = sp.getFloat(MeasurementVariableName.rsB, 0f);
            FragmentDataShare.stB = sp.getFloat(MeasurementVariableName.stB, 0f);
            FragmentDataShare.rtB = sp.getFloat(MeasurementVariableName.rtB, 0f);
            FragmentDataShare.tiangUjungB = sp.getString(MeasurementVariableName.tiangUjungB, "");

            FragmentDataShare.rnC = sp.getFloat(MeasurementVariableName.rnC, 0f);
            FragmentDataShare.snC = sp.getFloat(MeasurementVariableName.snC, 0f);
            FragmentDataShare.tnC = sp.getFloat(MeasurementVariableName.tnC, 0f);
            FragmentDataShare.rsC = sp.getFloat(MeasurementVariableName.rsC, 0f);
            FragmentDataShare.stC = sp.getFloat(MeasurementVariableName.stC, 0f);
            FragmentDataShare.rtC = sp.getFloat(MeasurementVariableName.rtC, 0f);
            FragmentDataShare.tiangUjungC = sp.getString(MeasurementVariableName.tiangUjungC, "");

            FragmentDataShare.rnD = sp.getFloat(MeasurementVariableName.rnD, 0f);
            FragmentDataShare.snD = sp.getFloat(MeasurementVariableName.snD, 0f);
            FragmentDataShare.tnD = sp.getFloat(MeasurementVariableName.tnD, 0f);
            FragmentDataShare.rsD = sp.getFloat(MeasurementVariableName.rsD, 0f);
            FragmentDataShare.stD = sp.getFloat(MeasurementVariableName.stD, 0f);
            FragmentDataShare.rtD = sp.getFloat(MeasurementVariableName.rtD, 0f);
            FragmentDataShare.tiangUjungD = sp.getString(MeasurementVariableName.tiangUjungD, "");
        }
    }

    private void saveDataToSharedPref(SharedPreferences.Editor editor){
        editor.putFloat(MeasurementVariableName.phaseR, FragmentDataShare.phaseR);
        editor.putFloat(MeasurementVariableName.phaseS, FragmentDataShare.phaseS);
        editor.putFloat(MeasurementVariableName.phaseT, FragmentDataShare.phaseT);
        editor.putFloat(MeasurementVariableName.netral, FragmentDataShare.netral);

        editor.putFloat(MeasurementVariableName.phaseRNhA, FragmentDataShare.phaseRNhA);
        editor.putFloat(MeasurementVariableName.phaseSNhA, FragmentDataShare.phaseSNhA);
        editor.putFloat(MeasurementVariableName.phaseTNhA, FragmentDataShare.phaseTNhA);
        editor.putFloat(MeasurementVariableName.netralNhA, FragmentDataShare.netralNhA);

        editor.putFloat(MeasurementVariableName.phaseRNhB, FragmentDataShare.phaseRNhB);
        editor.putFloat(MeasurementVariableName.phaseSNhB, FragmentDataShare.phaseSNhB);
        editor.putFloat(MeasurementVariableName.phaseTNhB, FragmentDataShare.phaseTNhB);
        editor.putFloat(MeasurementVariableName.netralNhB, FragmentDataShare.netralNhB);

        editor.putFloat(MeasurementVariableName.phaseRNhC, FragmentDataShare.phaseRNhC);
        editor.putFloat(MeasurementVariableName.phaseSNhC, FragmentDataShare.phaseSNhC);
        editor.putFloat(MeasurementVariableName.phaseTNhC, FragmentDataShare.phaseTNhC);
        editor.putFloat(MeasurementVariableName.netralNhC, FragmentDataShare.netralNhC);

        editor.putFloat(MeasurementVariableName.phaseRNhD, FragmentDataShare.phaseRNhD);
        editor.putFloat(MeasurementVariableName.phaseSNhD, FragmentDataShare.phaseSNhD);
        editor.putFloat(MeasurementVariableName.phaseTNhD, FragmentDataShare.phaseTNhD);
        editor.putFloat(MeasurementVariableName.netralNhD, FragmentDataShare.netralNhD);

        editor.putFloat(MeasurementVariableName.phaseRBebanA, FragmentDataShare.phaseRBebanA);
        editor.putFloat(MeasurementVariableName.phaseSBebanA, FragmentDataShare.phaseSBebanA);
        editor.putFloat(MeasurementVariableName.phaseTBebanA, FragmentDataShare.phaseTBebanA);
        editor.putFloat(MeasurementVariableName.netralBebanA, FragmentDataShare.netralBebanA);

        editor.putFloat(MeasurementVariableName.phaseRBebanB, FragmentDataShare.phaseRBebanB);
        editor.putFloat(MeasurementVariableName.phaseSBebanB, FragmentDataShare.phaseSBebanB);
        editor.putFloat(MeasurementVariableName.phaseTBebanB, FragmentDataShare.phaseTBebanB);
        editor.putFloat(MeasurementVariableName.netralBebanB, FragmentDataShare.netralBebanB);

        editor.putFloat(MeasurementVariableName.phaseRBebanC, FragmentDataShare.phaseRBebanC);
        editor.putFloat(MeasurementVariableName.phaseSBebanC, FragmentDataShare.phaseSBebanC);
        editor.putFloat(MeasurementVariableName.phaseTBebanC, FragmentDataShare.phaseTBebanC);
        editor.putFloat(MeasurementVariableName.netralBebanC, FragmentDataShare.netralBebanC);

        editor.putFloat(MeasurementVariableName.phaseRBebanD, FragmentDataShare.phaseRBebanD);
        editor.putFloat(MeasurementVariableName.phaseSBebanD, FragmentDataShare.phaseSBebanD);
        editor.putFloat(MeasurementVariableName.phaseTBebanD, FragmentDataShare.phaseTBebanD);
        editor.putFloat(MeasurementVariableName.netralBebanD, FragmentDataShare.netralBebanD);




        editor.putFloat(MeasurementVariableName.rn, FragmentDataShare.rn);
        editor.putFloat(MeasurementVariableName.sn, FragmentDataShare.sn);
        editor.putFloat(MeasurementVariableName.tn, FragmentDataShare.tn);
        editor.putFloat(MeasurementVariableName.rs, FragmentDataShare.rs);
        editor.putFloat(MeasurementVariableName.st, FragmentDataShare.st);
        editor.putFloat(MeasurementVariableName.rt, FragmentDataShare.rt);

        editor.putFloat(MeasurementVariableName.rnA, FragmentDataShare.rnA);
        editor.putFloat(MeasurementVariableName.snA, FragmentDataShare.snA);
        editor.putFloat(MeasurementVariableName.tnA, FragmentDataShare.tnA);
        editor.putFloat(MeasurementVariableName.rsA, FragmentDataShare.rsA);
        editor.putFloat(MeasurementVariableName.stA, FragmentDataShare.stA);
        editor.putFloat(MeasurementVariableName.rtA, FragmentDataShare.rtA);
        editor.putString(MeasurementVariableName.tiangUjungA, FragmentDataShare.tiangUjungA);

        editor.putFloat(MeasurementVariableName.rnB, FragmentDataShare.rnB);
        editor.putFloat(MeasurementVariableName.snB, FragmentDataShare.snB);
        editor.putFloat(MeasurementVariableName.tnB, FragmentDataShare.tnB);
        editor.putFloat(MeasurementVariableName.rsB, FragmentDataShare.rsB);
        editor.putFloat(MeasurementVariableName.stB, FragmentDataShare.stB);
        editor.putFloat(MeasurementVariableName.rtB, FragmentDataShare.rtB);
        editor.putString(MeasurementVariableName.tiangUjungB, FragmentDataShare.tiangUjungB);

        editor.putFloat(MeasurementVariableName.rnC, FragmentDataShare.rnC);
        editor.putFloat(MeasurementVariableName.snC, FragmentDataShare.snC);
        editor.putFloat(MeasurementVariableName.tnC, FragmentDataShare.tnC);
        editor.putFloat(MeasurementVariableName.rsC, FragmentDataShare.rsC);
        editor.putFloat(MeasurementVariableName.stC, FragmentDataShare.stC);
        editor.putFloat(MeasurementVariableName.rtC, FragmentDataShare.rtC);
        editor.putString(MeasurementVariableName.tiangUjungC, FragmentDataShare.tiangUjungC);

        editor.putFloat(MeasurementVariableName.rnD, FragmentDataShare.rnD);
        editor.putFloat(MeasurementVariableName.snD, FragmentDataShare.snD);
        editor.putFloat(MeasurementVariableName.tnD, FragmentDataShare.tnD);
        editor.putFloat(MeasurementVariableName.rsD, FragmentDataShare.rsD);
        editor.putFloat(MeasurementVariableName.stD, FragmentDataShare.stD);
        editor.putFloat(MeasurementVariableName.rtD, FragmentDataShare.rtD);
        editor.putString(MeasurementVariableName.tiangUjungD, FragmentDataShare.tiangUjungD);
    }

    private void resetSharePref(SharedPreferences.Editor editor) {
        editor.putFloat(MeasurementVariableName.phaseR, 0f);
        editor.putFloat(MeasurementVariableName.phaseS, 0f);
        editor.putFloat(MeasurementVariableName.phaseT, 0f);
        editor.putFloat(MeasurementVariableName.netral, 0f);

        editor.putFloat(MeasurementVariableName.phaseRNhA, 0f);
        editor.putFloat(MeasurementVariableName.phaseSNhA, 0f);
        editor.putFloat(MeasurementVariableName.phaseTNhA, 0f);
        editor.putFloat(MeasurementVariableName.netralNhA, 0f);

        editor.putFloat(MeasurementVariableName.phaseRNhB, 0f);
        editor.putFloat(MeasurementVariableName.phaseSNhB, 0f);
        editor.putFloat(MeasurementVariableName.phaseTNhB, 0f);
        editor.putFloat(MeasurementVariableName.netralNhB, 0f);

        editor.putFloat(MeasurementVariableName.phaseRNhC, 0f);
        editor.putFloat(MeasurementVariableName.phaseSNhC, 0f);
        editor.putFloat(MeasurementVariableName.phaseTNhC, 0f);
        editor.putFloat(MeasurementVariableName.netralNhC, 0f);

        editor.putFloat(MeasurementVariableName.phaseRNhD, 0f);
        editor.putFloat(MeasurementVariableName.phaseSNhD, 0f);
        editor.putFloat(MeasurementVariableName.phaseTNhD, 0f);
        editor.putFloat(MeasurementVariableName.netralNhD, 0f);

        editor.putFloat(MeasurementVariableName.phaseRBebanA, 0f);
        editor.putFloat(MeasurementVariableName.phaseSBebanA, 0f);
        editor.putFloat(MeasurementVariableName.phaseTBebanA, 0f);
        editor.putFloat(MeasurementVariableName.netralBebanA, 0f);

        editor.putFloat(MeasurementVariableName.phaseRBebanB, 0f);
        editor.putFloat(MeasurementVariableName.phaseSBebanB, 0f);
        editor.putFloat(MeasurementVariableName.phaseTBebanB, 0f);
        editor.putFloat(MeasurementVariableName.netralBebanB, 0f);

        editor.putFloat(MeasurementVariableName.phaseRBebanC, 0f);
        editor.putFloat(MeasurementVariableName.phaseSBebanC, 0f);
        editor.putFloat(MeasurementVariableName.phaseTBebanC, 0f);
        editor.putFloat(MeasurementVariableName.netralBebanC, 0f);

        editor.putFloat(MeasurementVariableName.phaseRBebanD, 0f);
        editor.putFloat(MeasurementVariableName.phaseSBebanD, 0f);
        editor.putFloat(MeasurementVariableName.phaseTBebanD, 0f);
        editor.putFloat(MeasurementVariableName.netralBebanD, 0f);

        editor.putFloat(MeasurementVariableName.rn, 0f);
        editor.putFloat(MeasurementVariableName.sn, 0f);
        editor.putFloat(MeasurementVariableName.tn, 0f);
        editor.putFloat(MeasurementVariableName.rs, 0f);
        editor.putFloat(MeasurementVariableName.st, 0f);
        editor.putFloat(MeasurementVariableName.rt, 0f);

        editor.putFloat(MeasurementVariableName.rnA, 0f);
        editor.putFloat(MeasurementVariableName.snA, 0f);
        editor.putFloat(MeasurementVariableName.tnA, 0f);
        editor.putFloat(MeasurementVariableName.rsA, 0f);
        editor.putFloat(MeasurementVariableName.stA, 0f);
        editor.putFloat(MeasurementVariableName.rtA, 0f);
        editor.putString(MeasurementVariableName.tiangUjungA, "");

        editor.putFloat(MeasurementVariableName.rnB, 0f);
        editor.putFloat(MeasurementVariableName.snB, 0f);
        editor.putFloat(MeasurementVariableName.tnB, 0f);
        editor.putFloat(MeasurementVariableName.rsB, 0f);
        editor.putFloat(MeasurementVariableName.stB, 0f);
        editor.putFloat(MeasurementVariableName.rtB, 0f);
        editor.putString(MeasurementVariableName.tiangUjungB, "");

        editor.putFloat(MeasurementVariableName.rnC, 0f);
        editor.putFloat(MeasurementVariableName.snC, 0f);
        editor.putFloat(MeasurementVariableName.tnC, 0f);
        editor.putFloat(MeasurementVariableName.rsC, 0f);
        editor.putFloat(MeasurementVariableName.stC, 0f);
        editor.putFloat(MeasurementVariableName.rtC, 0f);
        editor.putString(MeasurementVariableName.tiangUjungC, "");

        editor.putFloat(MeasurementVariableName.rnD, 0f);
        editor.putFloat(MeasurementVariableName.snD, 0f);
        editor.putFloat(MeasurementVariableName.tnD, 0f);
        editor.putFloat(MeasurementVariableName.rsD, 0f);
        editor.putFloat(MeasurementVariableName.stD, 0f);
        editor.putFloat(MeasurementVariableName.rtD, 0f);
        editor.putString(MeasurementVariableName.tiangUjungD, "");
    }
    
    private void resetFragmentDataShare(){
        FragmentDataShare.phaseR = 0f;
        FragmentDataShare.phaseS = 0f;
        FragmentDataShare.phaseT = 0f;
        FragmentDataShare.netral = 0f;
        FragmentDataShare.phaseRNhA = 0f;
        FragmentDataShare.phaseSNhA = 0f;
        FragmentDataShare.phaseTNhA = 0f;
        FragmentDataShare.netralNhA = 0f;
        FragmentDataShare.phaseRBebanA = 0f;
        FragmentDataShare.phaseSBebanA = 0f;
        FragmentDataShare.phaseTBebanA = 0f;
        FragmentDataShare.netralBebanA = 0f;
        FragmentDataShare.phaseRNhB = 0f;
        FragmentDataShare.phaseSNhB = 0f;
        FragmentDataShare.phaseTNhB = 0f;
        FragmentDataShare.netralNhB = 0f;
        FragmentDataShare.phaseRBebanB = 0f;
        FragmentDataShare.phaseSBebanB = 0f;
        FragmentDataShare.phaseTBebanB = 0f;
        FragmentDataShare.netralBebanB = 0f;
        FragmentDataShare.phaseRNhC = 0f;
        FragmentDataShare.phaseSNhC = 0f;
        FragmentDataShare.phaseTNhC = 0f;
        FragmentDataShare.netralNhC = 0f;
        FragmentDataShare.phaseRBebanC = 0f;
        FragmentDataShare.phaseSBebanC = 0f;
        FragmentDataShare.phaseTBebanC = 0f;
        FragmentDataShare.netralBebanC = 0f;
        FragmentDataShare.phaseRNhD = 0f;
        FragmentDataShare.phaseSNhD = 0f;
        FragmentDataShare.phaseTNhD = 0f;
        FragmentDataShare.netralNhD = 0f;
        FragmentDataShare.phaseRBebanD = 0f;
        FragmentDataShare.phaseSBebanD = 0f;
        FragmentDataShare.phaseTBebanD = 0f;
        FragmentDataShare.netralBebanD = 0f;

    /*Tegangan*/
        FragmentDataShare.rn = 0f;
        FragmentDataShare.sn = 0f;
        FragmentDataShare.tn = 0f;
        FragmentDataShare.rs = 0f;
        FragmentDataShare.st = 0f;
        FragmentDataShare.rt = 0f;
        FragmentDataShare.rnA = 0f;
        FragmentDataShare.snA = 0f;
        FragmentDataShare.tnA = 0f;
        FragmentDataShare.rsA = 0f;
        FragmentDataShare.stA = 0f;
        FragmentDataShare.rtA = 0f;
        FragmentDataShare.tiangUjungA = "";
        FragmentDataShare.rnB = 0f;
        FragmentDataShare.snB = 0f;
        FragmentDataShare.tnB = 0f;
        FragmentDataShare.rsB = 0f;
        FragmentDataShare.stB = 0f;
        FragmentDataShare.rtB = 0f;
        FragmentDataShare.tiangUjungB = "";
        FragmentDataShare.rnC = 0f;
        FragmentDataShare.snC = 0f;
        FragmentDataShare.tnC = 0f;
        FragmentDataShare.rsC = 0f;
        FragmentDataShare.stC = 0f;
        FragmentDataShare.rtC = 0f;
        FragmentDataShare.tiangUjungC = "";
        FragmentDataShare.rnD = 0f;
        FragmentDataShare.snD = 0f;
        FragmentDataShare.tnD = 0f;
        FragmentDataShare.rsD = 0f;
        FragmentDataShare.stD = 0f;
        FragmentDataShare.rtD = 0f;
        FragmentDataShare.tiangUjungD = "";
    }
}
