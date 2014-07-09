package com.windupurnomo.simadutanjung.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;
import com.windupurnomo.simadutanjung.MainActivity;
import com.windupurnomo.simadutanjung.R;
import com.windupurnomo.simadutanjung.entities.FragmentDataShare;
import com.windupurnomo.simadutanjung.entities.Gardu;
import com.windupurnomo.simadutanjung.entities.MeasurementVariableName;
import com.windupurnomo.simadutanjung.fragments.FragmentArus;
import com.windupurnomo.simadutanjung.fragments.FragmentTegangan;
import com.windupurnomo.simadutanjung.listener.MeasurementTabListener;
import com.windupurnomo.simadutanjung.server.MeasurementService;
import com.windupurnomo.simadutanjung.util.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeasurementActivity extends ActionBarActivity {
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private static final String TAG = "MeasurementActivity";
    public String PREFS_NAME = "DataMeasurement-";
    private int theme;
    private SharedPreferences sp;
    private ProgressDialog progressDialog;
    private MeasurementService measurementRequest;
    private FragmentTegangan fragmentTegangan;
    private FragmentArus fragmentArus;
    private int idGardu;
    private String noGardu;
    private float daya;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theme = getIntent().getIntExtra("theme", 0);
        idGardu = getIntent().getIntExtra("garduId", 0);
        noGardu = getIntent().getStringExtra("garduNo");
        num = getIntent().getIntExtra("measurementNumber", 0);
        daya = getIntent().getFloatExtra("daya", 0f);
        FragmentDataShare.procNum = num;

        setContentView(R.layout.activity_measurement);
        getActionBar().setTitle("Pengukuran " + noGardu);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(theme);
        getActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, theme, false, 2));

        fragmentTegangan = new FragmentTegangan();
        fragmentArus = new FragmentArus();

        // Set up the action bar to show tabs.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab arusTab = actionBar.newTab().setText("Arus")
                .setTabListener(new MeasurementTabListener(fragmentArus, getApplicationContext()));
        ActionBar.Tab teganganTab = actionBar.newTab().setText("Tegangan")
                .setTabListener(new MeasurementTabListener(fragmentTegangan, getApplicationContext()));

        // for each of the sections in the app, add a tab to the action bar.
        actionBar.addTab(arusTab);
        actionBar.addTab(teganganTab);

        resetFragmentDataShare();
        measurementRequest = new MeasurementService();
        new MeasurementActivityAsync().execute("load");
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
        try {
            if (id == R.id.action_settings) {
                return true;
            }

            if (id == R.id.option_menu_analisis){
                Intent in = new Intent(getApplicationContext(), MeasurementAnalysis.class);
                in.putExtra("daya", daya);
                in.putExtra("garduNo", noGardu);
                in.putExtra("theme", theme);
                startActivity(in);
            }

            //save in preference
            if (id == R.id.option_menu_save){
                FragmentDataShare.measurementDate = new Date();
                SharedPreferences.Editor editor = sp.edit();
                saveDataToSharedPref(editor);
                editor.putBoolean(MeasurementVariableName.dataStatus, true);
                editor.commit();
                Toast.makeText(this, "Saving to local storage" , Toast.LENGTH_LONG).show();
                goToHomeActivity();
            }

            //upload to internet
            if (id == R.id.option_menu_upload){
                Date measDate = FragmentDataShare.measurementDate;
                FragmentDataShare.measurementDate = measDate == null ? new Date() : measDate;
                FragmentDataShare.status = 1;
                new MeasurementActivityAsync().execute("submit");
            }

            //publish to internet
            if (id == R.id.option_menu_publish){
                Date measDate = FragmentDataShare.measurementDate;
                FragmentDataShare.measurementDate = measDate == null ? new Date() : measDate;
                FragmentDataShare.status = 2;

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Publish gardu " + noGardu +"? Process ini akan mengakhiri pengukuran untuk gardu " + noGardu + ".");
                builder.setTitle("Publish");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new MeasurementActivityAsync().execute("submit");
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setIcon(R.drawable.ic_action_web_site);
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
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
        if(!isThereDataLocal && NetworkUtil.isNetworkAvailable(this)) {
            String response = measurementRequest.sendGetRequest(MeasurementService.urlSelectAll + "?gardu="+idGardu);
            processMeasurementResponse(response);
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

    private void initFragmentDataShare(JSONObject obj) {
        try {
            FragmentDataShare.id = obj.getInt("id");
            FragmentDataShare.garduId = idGardu;// obj.getInt("gardu_id");
            FragmentDataShare.batch = obj.getInt("batch");
            FragmentDataShare.procNum = obj.getInt("proc_num");
            FragmentDataShare.status = obj.getInt("status");
            FragmentDataShare.phaseR = (float)obj.getDouble("phase_r");
            FragmentDataShare.phaseS = (float)obj.getDouble("phase_s");
            FragmentDataShare.phaseT = (float)obj.getDouble("phase_t");
            FragmentDataShare.netral = (float)obj.getDouble("netral");
            FragmentDataShare.phaseRNhA = (float)obj.getDouble("phase_r_nh_a");
            FragmentDataShare.phaseSNhA = (float)obj.getDouble("phase_s_nh_a");
            FragmentDataShare.phaseTNhA = (float)obj.getDouble("phase_t_nh_a");
            FragmentDataShare.netralNhA = (float)obj.getDouble("netral_nh_a");
            FragmentDataShare.phaseRBebanA = (float)obj.getDouble("phase_r_beban_a");
            FragmentDataShare.phaseSBebanA = (float)obj.getDouble("phase_s_beban_a");
            FragmentDataShare.phaseTBebanA = (float)obj.getDouble("phase_t_beban_a");
            FragmentDataShare.netralBebanA = (float)obj.getDouble("netral_beban_a");
            FragmentDataShare.phaseRNhB = (float)obj.getDouble("phase_r_nh_b");
            FragmentDataShare.phaseSNhB = (float)obj.getDouble("phase_s_nh_b");
            FragmentDataShare.phaseTNhB = (float)obj.getDouble("phase_t_nh_b");
            FragmentDataShare.netralNhB = (float)obj.getDouble("netral_nh_b");
            FragmentDataShare.phaseRBebanB = (float)obj.getDouble("phase_r_beban_b");
            FragmentDataShare.phaseSBebanB = (float)obj.getDouble("phase_s_beban_b");
            FragmentDataShare.phaseTBebanB = (float)obj.getDouble("phase_t_beban_b");
            FragmentDataShare.netralBebanB = (float)obj.getDouble("netral_beban_b");
            FragmentDataShare.phaseRNhC = (float)obj.getDouble("phase_r_nh_c");
            FragmentDataShare.phaseSNhC = (float)obj.getDouble("phase_s_nh_c");
            FragmentDataShare.phaseTNhC = (float)obj.getDouble("phase_t_nh_c");
            FragmentDataShare.netralNhC = (float)obj.getDouble("netral_nh_c");
            FragmentDataShare.phaseRBebanC = (float)obj.getDouble("phase_r_beban_c");
            FragmentDataShare.phaseSBebanC = (float)obj.getDouble("phase_s_beban_c");
            FragmentDataShare.phaseTBebanC = (float)obj.getDouble("phase_t_beban_c");
            FragmentDataShare.netralBebanC = (float)obj.getDouble("netral_beban_c");
            FragmentDataShare.phaseRNhD = (float)obj.getDouble("phase_r_nh_d");
            FragmentDataShare.phaseSNhD = (float)obj.getDouble("phase_s_nh_d");
            FragmentDataShare.phaseTNhD = (float)obj.getDouble("phase_t_nh_d");
            FragmentDataShare.netralNhD = (float)obj.getDouble("netral_nh_d");
            FragmentDataShare.phaseRBebanD = (float)obj.getDouble("phase_r_beban_d");
            FragmentDataShare.phaseSBebanD = (float)obj.getDouble("phase_s_beban_d");
            FragmentDataShare.phaseTBebanD = (float)obj.getDouble("phase_t_beban_d");
            FragmentDataShare.netralBebanD = (float)obj.getDouble("netral_beban_d");

            /*Tegangan*/
            FragmentDataShare.rn = (float)obj.getDouble("rn");
            FragmentDataShare.sn = (float)obj.getDouble("sn");
            FragmentDataShare.tn = (float)obj.getDouble("tn");
            FragmentDataShare.rs = (float)obj.getDouble("rs");
            FragmentDataShare.st = (float)obj.getDouble("st");
            FragmentDataShare.rt = (float)obj.getDouble("rt");
            FragmentDataShare.rnA = (float)obj.getDouble("rn_a");
            FragmentDataShare.snA = (float)obj.getDouble("sn_a");
            FragmentDataShare.tnA = (float)obj.getDouble("tn_a");
            FragmentDataShare.rsA = (float)obj.getDouble("rs_a");
            FragmentDataShare.stA = (float)obj.getDouble("st_a");
            FragmentDataShare.rtA = (float)obj.getDouble("rt_a");
            FragmentDataShare.tiangUjungA = obj.getString("tiangujung_a");
            FragmentDataShare.rnB = (float)obj.getDouble("rn_b");
            FragmentDataShare.snB = (float)obj.getDouble("sn_b");
            FragmentDataShare.tnB = (float)obj.getDouble("tn_b");
            FragmentDataShare.rsB = (float)obj.getDouble("rs_b");
            FragmentDataShare.stB = (float)obj.getDouble("st_b");
            FragmentDataShare.rtB = (float)obj.getDouble("rt_b");
            FragmentDataShare.tiangUjungB = obj.getString("tiangujung_b");
            FragmentDataShare.rnC = (float)obj.getDouble("rn_c");
            FragmentDataShare.snC = (float)obj.getDouble("sn_c");
            FragmentDataShare.tnC = (float)obj.getDouble("tn_c");
            FragmentDataShare.rsC = (float)obj.getDouble("rs_c");
            FragmentDataShare.stC = (float)obj.getDouble("st_c");
            FragmentDataShare.rtC = (float)obj.getDouble("rt_c");
            FragmentDataShare.tiangUjungC = obj.getString("tiangujung_c");
            FragmentDataShare.rnD = (float)obj.getDouble("rn_d");
            FragmentDataShare.snD = (float)obj.getDouble("sn_d");
            FragmentDataShare.tnD = (float)obj.getDouble("tn_d");
            FragmentDataShare.rsD = (float)obj.getDouble("rs_d");
            FragmentDataShare.stD = (float)obj.getDouble("st_d");
            FragmentDataShare.rtD = (float)obj.getDouble("rt_d");
            FragmentDataShare.tiangUjungD = obj.getString("tiangujung_d");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void processMeasurementResponse(String response){
        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONArray jsonArray = jsonObj.getJSONArray("gardu");
            Log.d(TAG, "data lengths: " + jsonArray.length());
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-M-dd hh:mm:ss");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                initFragmentDataShare(obj);
            }
        } catch (JSONException e) {
            FragmentDataShare.garduId = idGardu;
            Log.d(TAG, e.getMessage());
        }
    }

    private void responseSubmit(String resultRequest){
        int replyCode = 200;
        if(resultRequest.length() > 2)
            Toast.makeText(this, "Result Request: " + resultRequest, Toast.LENGTH_LONG).show();
        if (replyCode == 99) {
            Toast.makeText(MeasurementActivity.this, "Upload to internet failed.", Toast.LENGTH_LONG).show();
        } else if (replyCode == 200) {
            SharedPreferences.Editor editor = sp.edit();
            resetSharePref(editor);
            editor.putString(MeasurementVariableName.garduName, noGardu);
            editor.putBoolean(MeasurementVariableName.dataStatus, false);
            editor.commit();
            Toast.makeText(MeasurementActivity.this, "Saving to internet.\nLocal storage will be deleted." , Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(MeasurementActivity.this, "Upload Gagal. Code: " +replyCode , Toast.LENGTH_LONG).show();
        }
        goToHomeActivity();
    }


    private class MeasurementActivityAsync extends AsyncTask<String, Void, String> {
        private String processType;
        private int replyCode;
        private String resultRequest;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MeasurementActivity.this);
            progressDialog.setMessage("retrieving...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            processType = params[0];
            if(params[0] == "submit"){
                resultRequest = measurementRequest.sendPostRequest(MeasurementService.urlSubmit);
            }else{
                setupSharedPreferences();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (processType == "submit") {
                        responseSubmit(resultRequest);
                    } else {
                        fragmentArus.dataInitialization();
                    }
                }
            });
        }

    }
}
