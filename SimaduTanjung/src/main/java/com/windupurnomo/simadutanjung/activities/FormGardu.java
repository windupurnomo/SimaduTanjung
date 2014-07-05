package com.windupurnomo.simadutanjung.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.windupurnomo.simadutanjung.R;
import com.windupurnomo.simadutanjung.entities.Gardu;
import com.windupurnomo.simadutanjung.server.ServerRequest;

import org.apache.http.HttpStatus;

public class FormGardu extends ActionBarActivity {
    private EditText textNomor, textAddress, textDaya, textPenyulang;
    private RadioGroup radioTiangGroup;
    private RadioButton radioTiang, radio1Tiang, radio2Tiang;
    private ProgressDialog progressDialog;
    private ServerRequest server;
    private int replyCode;
    private Gardu gardu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_gardu);
        initView();
        server = new ServerRequest();
        android.app.ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
            if(tiang == 1) radio1Tiang.setSelected(true);
            else radio2Tiang.setSelected(true);
            gardu.setId(Integer.valueOf(id));
        }else{
            gardu.setId(0);
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
        radio1Tiang.setSelected(true);
        radio2Tiang.setSelected(false);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                goToHomeActivity();
                break;

            case R.id.option_menu_save:
                if(textNomor.getText().toString().trim().isEmpty() || textAddress.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nomor gardu tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else{
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
            int idSelected = radioTiangGroup.getCheckedRadioButtonId();
            int tiang = 1;
            if(idSelected == R.id.radio1Tiang) tiang = 1;
            else if(idSelected == R.id.radio2Tiang) tiang = 2;
            else tiang = 0;
            gardu.setNomor(nomor);
            gardu.setAddress(address);
            gardu.setDaya(daya);
            gardu.setPenyulang(penyulang);
            gardu.setTiang(tiang);
            /**Mengirimkan POST reques*/
            replyCode = server.sendPostRequest(gardu, ServerRequest.urlSubmit);
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
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
