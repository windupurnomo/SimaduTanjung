package com.windupurnomo.simadutanjung.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;
import com.windupurnomo.simadutanjung.R;
import com.windupurnomo.simadutanjung.entities.CommonResponse;
import com.windupurnomo.simadutanjung.entities.Gardu;
import com.windupurnomo.simadutanjung.server.ServerRequest;

import org.apache.http.HttpStatus;

public class FormGardu extends ActionBarActivity {
    private EditText textNomor, textAddress, textDaya, textPenyulang;
    private RadioGroup radioTiangGroup;
    private final int APP_THEME = R.array.blood;
    private RadioButton radioTiang, radio1Tiang, radio2Tiang;
    private ProgressDialog progressDialog;
    private ServerRequest server;
    private int replyCode;
    private Gardu gardu;
    private int theme;
    private int tiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_gardu);
        initView();
        server = new ServerRequest();
        android.app.ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        theme = getIntent().getIntExtra("theme", APP_THEME);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(theme);
        getActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, theme, false, 2));
        gardu = new Gardu();
        if(getIntent().hasExtra("id")){
            int id = getIntent().getIntExtra("id", 0);
            String nomor = getIntent().getStringExtra("nomor");
            String address = getIntent().getStringExtra("address");
            float daya = getIntent().getFloatExtra("daya", 0);
            String penyulang = getIntent().getStringExtra("penyulang");
            int tiang = getIntent().getIntExtra("tiang", 0);
            textNomor.setText(nomor);
            textAddress.setText(address);
            textDaya.setText(Float.toString(daya));
            textPenyulang.setText(penyulang);
            if(tiang == 1) {
                radio1Tiang.setChecked(true);
                radio2Tiang.setChecked(false);
            }
            else{
                radio1Tiang.setChecked(false);
                radio2Tiang.setChecked(true);
            }
            gardu.setId(id);
        }else{
            gardu.setId(0);
            radio1Tiang.setChecked(true);
        }
    }

    private void initView(){
        textNomor = (EditText) findViewById(R.id.add_new_nomor_form);
        textAddress = (EditText) findViewById(R.id.add_new_address_form);
        textDaya = (EditText) findViewById(R.id.add_new_daya_form);
        textPenyulang = (EditText) findViewById(R.id.add_new_penyulang_form);
        radioTiangGroup = (RadioGroup)findViewById(R.id.radioTiangForm);
        radio1Tiang = (RadioButton)findViewById(R.id.radio1TiangForm);
        radio2Tiang = (RadioButton)findViewById(R.id.radio2TiangForm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_gardu, menu);
        return true;
    }

    private void goToHomeActivity(){
        Intent in = new Intent(getApplicationContext(), Home.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
    }

    public void onRadioClick(View view){
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio1TiangForm:
                if (checked)
                    tiang = 1;
                    break;
            case R.id.radio2TiangForm:
                if (checked)
                    tiang = 2;
                    break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                goToHomeActivity();
                break;

            case R.id.option_menu_save_gardu:
                if(textNomor.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nomor gardu tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if(textDaya.getText().toString().trim().isEmpty())
                    Toast.makeText(getApplicationContext(), "Daya tidak boleh kosong", Toast.LENGTH_SHORT).show();
                else{
                    new FormGarduAsync().execute();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendRequest(){
        try{
            String nomor = textNomor.getText().toString();
            String address = textAddress.getText().toString();
            float daya = Float.parseFloat(textDaya.getText().toString());
            String penyulang = textPenyulang.getText().toString();
            gardu.setNomor(nomor);
            gardu.setAddress(address);
            gardu.setDaya(daya);
            gardu.setPenyulang(penyulang);
            gardu.setTiang(tiang);
            /**Mengirimkan POST reques*/
            CommonResponse cr = server.sendPostRequest(gardu, ServerRequest.urlSubmit);
            replyCode = (Integer)cr.getData();
            Log.d("FormGardu", cr.getMessage());
        }catch (Exception ex){
            Log.e("FormGardu", ex.getMessage());
        }
    }

    private class FormGarduAsync extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(FormGardu.this);
            progressDialog.setMessage("saving data...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            sendRequest();
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if(replyCode == HttpStatus.SC_OK){
                goToHomeActivity();
            }else{
                Toast.makeText(getApplicationContext(), "save data problem", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
