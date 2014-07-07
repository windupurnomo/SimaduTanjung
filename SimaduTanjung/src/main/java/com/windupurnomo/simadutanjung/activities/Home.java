package com.windupurnomo.simadutanjung.activities;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

import com.cengalabs.flatui.FlatUI;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.windupurnomo.simadutanjung.R;
import com.windupurnomo.simadutanjung.entities.Gardu;
import com.windupurnomo.simadutanjung.entities.ListAdapterGardu;
import com.windupurnomo.simadutanjung.server.ServerRequest;
import com.windupurnomo.simadutanjung.util.NetworkUtil;

public class Home extends Activity implements OnQueryTextListener {
    private static final String TAG = "MainActivity";
    private static final String PREFS_NAME = "ListGardu";
    private static final String List_Gardu = "ObjectListGardu";
    private final int APP_THEME = R.array.blood;
    private SharedPreferences sp;
    private ListView listView;
    private ActionMode actionMode;
    private ActionMode.Callback amCallback;
    private ProgressDialog progressDialog;
    private ServerRequest serverRequest;
    private List<Gardu> list;
    private ListAdapterGardu adapter;
    private Gardu selectedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(APP_THEME);
        setContentView(R.layout.activity_home);
        getActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, APP_THEME, false, 2));
        serverRequest = new ServerRequest();
        listView = (ListView) findViewById(R.id.listview_main);
        amCallback = new ActionMode.Callback() {
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                actionMode = null;
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                //getMenuInflater().inflate(R.menu.activity_home_action, menu);
                getMenuInflater().inflate(R.menu.context_menu_gardu, menu);
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_menu_edit:
                        showUpdateForm();
                        break;
                    case R.id.action_menu_delete:
                        delete();
                        break;
                    case R.id.ctx_edit_gardu:
                        showUpdateForm();
                        break;
                    case R.id.ctx_delete_gardu:
                        delete();
                        break;
                    case R.id.ctx_day1_gardu:
                        showMeasurementForm(1);
                        break;
                    case R.id.ctx_day2_gardu:
                        showMeasurementForm(2);
                        break;
                    case R.id.ctx_day3_gardu:
                        showMeasurementForm(3);
                        break;
                    case R.id.ctx_analisis_gardu:
                        Toast.makeText(Home.this, "Analisis", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.ctx_analisis_gardu3:
                        Toast.makeText(Home.this, "Analisis 3 Hari", Toast.LENGTH_LONG).show();
                        break;
                }
                mode.finish();
                return false;
            }
        };
        list = new ArrayList<Gardu>();
        /** melakukan load data melalui AsyncTask */
        new MainActivityAsync().execute("load");
    }

    private void showUpdateForm(){
        Intent in = new Intent(getBaseContext(), FormGardu.class);
        in.putExtra("id", selectedList.getId());
        in.putExtra("nomor", selectedList.getNomor());
        in.putExtra("address", selectedList.getAddress());
        in.putExtra("daya", selectedList.getDaya());
        in.putExtra("penyulang", selectedList.getPenyulang());
        in.putExtra("tiang", selectedList.getTiang());
        startActivity(in);
    }

    private void showMeasurementForm(int number){
        Intent in = new Intent(getBaseContext(), MeasurementActivity.class);
        in.putExtra("theme", APP_THEME);
        in.putExtra("garduNo", selectedList.getNomor());
        in.putExtra("garduId", selectedList.getId());
        in.putExtra("measurementNumber", number);
        startActivity(in);
    }

    private void delete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete "+selectedList.getNomor()+" ?");
        builder.setTitle("Delete");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                new MainActivityAsync().execute("delete");
                list.remove(list.indexOf(selectedList));
                Toast.makeText(getApplicationContext(), "deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.setIcon(android.R.drawable.ic_menu_delete);
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.option_menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Nomor Gardu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.option_menu_new:
                Intent in = new Intent(getApplicationContext(), FormGardu.class);
                startActivity(in);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Gardu> processResponse(String response){
        List<Gardu> list = new ArrayList<Gardu>();
        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONArray jsonArray = jsonObj.getJSONArray("gardu");
            Log.d(TAG, "data lengths: " + jsonArray.length());
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-M-dd hh:mm:ss");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                Gardu gardu = new Gardu();
                gardu.setId(obj.getInt("id"));
                gardu.setNomor(obj.getString("nomor"));
                gardu.setAddress(obj.getString("address"));
                gardu.setDaya(Float.parseFloat(obj.getString("daya")));
                gardu.setPenyulang(obj.getString("penyulang"));
                gardu.setTiang(obj.getInt("tiang"));
                list.add(gardu);
            }
            updateSharedPreference(list);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return list;
    }

    private List<Gardu> processOffLineData(){
        try {
            String gardus = sp.getString(List_Gardu, "");
            Gson gson = new Gson();
            Type type = new TypeToken<List<Gardu>>(){}.getType();
            List<Gardu> garduList = gson.fromJson(gardus, type);
            return garduList;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return new ArrayList<Gardu>();
        }
    }

    private void populateListView(){
        adapter = new ListAdapterGardu(getApplicationContext(), list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View v, int pos, long id) {
                if(actionMode != null){
                    return false;
                }
                actionMode = startActionMode(amCallback);
                v.setSelected(true);
                selectedList = (Gardu) adapter.getItem(pos);
                return true;
            }

        });

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int pos,
                                    long id) {
                selectedList = (Gardu) adapter.getItem(pos);
                Intent in = new Intent(getApplicationContext(), DetailGardu.class);
                in.putExtra("id", selectedList.getId());
                in.putExtra("nomor", selectedList.getNomor());
                in.putExtra("address", selectedList.getAddress());
                in.putExtra("daya", selectedList.getDaya());
                in.putExtra("penyulang", selectedList.getPenyulang());
                in.putExtra("tiang", selectedList.getTiang());
                startActivity(in);
            }
        });*/
    }

    /*@Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_gardu, menu);
    }*/

    private class MainActivityAsync extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(Home.this);
            progressDialog.setMessage("retrieving...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            if(params[0] == "delete"){
                serverRequest.sendGetRequest(ServerRequest.urlDelete+"?id="+ Integer.toString(selectedList.getId()));
            }else{
                if(NetworkUtil.isNetworkAvailable(Home.this)){
                    /** Mengirimkan request ke server dan memproses JSON response */
                    String response = serverRequest.sendGetRequest(ServerRequest.urlSelectAll);
                    list = processResponse(response);
                }else{
                    list = processOffLineData();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    populateListView();
                }
            });
        }

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private void updateSharedPreference(List<Gardu> gardus){
        try {
            Gson gson = new Gson();
            String jsonGardus = gson.toJson(gardus);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(List_Gardu, jsonGardus);
            editor.commit();
            Log.d("TAG","jsonGardus = " + jsonGardus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
