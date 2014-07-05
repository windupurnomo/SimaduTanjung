package com.windupurnomo.simadutanjung.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import com.windupurnomo.simadutanjung.R;
import com.windupurnomo.simadutanjung.entities.Gardu;
import com.windupurnomo.simadutanjung.entities.ListAdapterGardu;
import com.windupurnomo.simadutanjung.server.ServerRequest;

public class Home extends Activity implements OnQueryTextListener {
    private static final String TAG = "MainActivity";
    private final int APP_THEME = R.array.blood;
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
                        showMeasurementForm();
                        break;
                    case R.id.ctx_day2_gardu:
                        break;
                    case R.id.ctx_day3_gardu:
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

    private void showMeasurementForm(){
        Intent in = new Intent(getBaseContext(), MeasurementActivity.class);
        in.putExtra("theme", APP_THEME);
        in.putExtra("garduNo", selectedList.getNomor());
        in.putExtra("garduId", selectedList.getId());
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
            Gardu mhs = null;
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-M-dd hh:mm:ss");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                mhs = new Gardu();
                mhs.setId(obj.getInt("id"));
                mhs.setNomor(obj.getString("nomor"));
                mhs.setAddress(obj.getString("address"));
                mhs.setDaya(Float.parseFloat(obj.getString("daya")));
                mhs.setPenyulang(obj.getString("penyulang"));
                mhs.setTiang(obj.getInt("tiang"));
                list.add(mhs);
            }
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
        return list;
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_gardu, menu);
    }

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
                /** Mengirimkan request ke server dan memproses JSON response */
                String response = serverRequest.sendGetRequest(ServerRequest.urlSelectAll);
                list = processResponse(response);
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
}
