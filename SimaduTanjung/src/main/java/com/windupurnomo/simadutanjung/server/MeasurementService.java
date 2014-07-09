package com.windupurnomo.simadutanjung.server;

import android.support.v7.appcompat.R;
import android.util.Log;

import com.windupurnomo.simadutanjung.entities.FragmentDataShare;
import com.windupurnomo.simadutanjung.entities.Gardu;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windu Purnomo on 7/6/14.
 */
public class MeasurementService {
    private final static String TAG         = "ServerRequest";
    private final String serverUri          = "http://simadu.kedainusa.com/php";
    public static final String urlSelectAll = "select_measurement_by_gardu_draft.php";
    public static final String urlSubmit    = "save_measurement.php";

    public MeasurementService() {
        super();
    }

    /* Mengirimkan GET request 	 */
    public String sendGetRequest(String reqUrl){
        HttpClient httpClient;
        HttpGet httpGet = new HttpGet(serverUri+"/"+reqUrl);
        InputStream is = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            HttpParams params = new BasicHttpParams();
            int timeout = 10000;//3000
            HttpConnectionParams.setConnectionTimeout(params, timeout);
            HttpConnectionParams.setSoTimeout(params, timeout);
            httpClient = new DefaultHttpClient(params);
            Log.d(TAG, "executing...");
            HttpResponse httpResponse = httpClient.execute(httpGet);
            StatusLine status = httpResponse.getStatusLine();
            if(status.getStatusCode() == HttpStatus.SC_OK && httpResponse != null){
				/* mengambil response string dari server 	 */
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while((line = reader.readLine()) != null){
                    stringBuilder.append(line+"\n");
                }
                is.close();
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        return stringBuilder.toString();
    }

    /* Mengirimkan POST request 	 */
    public String sendPostRequest(String urlSubmit){
        int replyCode = 99;
        HttpClient httpClient;
        HttpPost post = new HttpPost(this.serverUri+"/"+urlSubmit);
        List<NameValuePair> value = genrateReuqestParam();
        InputStream is = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            HttpParams params = new BasicHttpParams();
            int timeout = 10000;//3000
            HttpConnectionParams.setConnectionTimeout(params, timeout);
            HttpConnectionParams.setSoTimeout(params, timeout);
            httpClient = new DefaultHttpClient(params);
            post.setEntity(new UrlEncodedFormEntity(value));
            Log.d(TAG, "executing post...");
            HttpResponse httpResponse = httpClient.execute(post);
            StatusLine status = httpResponse.getStatusLine();
            if(status.getStatusCode() == HttpStatus.SC_OK){
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while((line = reader.readLine()) != null){
                    stringBuilder.append(line+"\n");
                }
                is.close();

                Log.d(TAG, "submitted sucessfully...");
                replyCode = status.getStatusCode();
            }
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }
        return stringBuilder.toString();
    }

    private List<NameValuePair> genrateReuqestParam (){
        List<NameValuePair> value = new ArrayList<NameValuePair>();
        /* menambahkan parameter ke dalam request */
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        SimpleDateFormat ft2 = new SimpleDateFormat ("yyyy-M-dd hh:mm:ss");
        value.add(new BasicNameValuePair("id", Integer.toString(FragmentDataShare.id)));
        value.add(new BasicNameValuePair("gardu_id", Integer.toString(FragmentDataShare.garduId)));
        value.add(new BasicNameValuePair("batch", Integer.toString(FragmentDataShare.batch)));
        value.add(new BasicNameValuePair("proc_num", Integer.toString(FragmentDataShare.procNum)));
        value.add(new BasicNameValuePair("measurement_date", ft.format(FragmentDataShare.measurementDate)));
        value.add(new BasicNameValuePair("status", Integer.toString(FragmentDataShare.status)));
        value.add(new BasicNameValuePair("phaseR", Float.toString(FragmentDataShare.phaseR)));
        value.add(new BasicNameValuePair("phaseS", Float.toString(FragmentDataShare.phaseS)));
        value.add(new BasicNameValuePair("phaseT", Float.toString(FragmentDataShare.phaseT)));
        value.add(new BasicNameValuePair("netral", Float.toString(FragmentDataShare.netral)));
        value.add(new BasicNameValuePair("phaseRNhA", Float.toString(FragmentDataShare.phaseRNhA)));
        value.add(new BasicNameValuePair("phaseSNhA", Float.toString(FragmentDataShare.phaseSNhA)));
        value.add(new BasicNameValuePair("phaseTNhA", Float.toString(FragmentDataShare.phaseTNhA)));
        value.add(new BasicNameValuePair("netralNhA", Float.toString(FragmentDataShare.netralNhA)));
        value.add(new BasicNameValuePair("phaseRNhB", Float.toString(FragmentDataShare.phaseRNhB)));
        value.add(new BasicNameValuePair("phaseSNhB", Float.toString(FragmentDataShare.phaseSNhB)));
        value.add(new BasicNameValuePair("phaseTNhB", Float.toString(FragmentDataShare.phaseTNhB)));
        value.add(new BasicNameValuePair("netralNhB", Float.toString(FragmentDataShare.netralNhB)));
        value.add(new BasicNameValuePair("phaseRNhC", Float.toString(FragmentDataShare.phaseRNhC)));
        value.add(new BasicNameValuePair("phaseSNhC", Float.toString(FragmentDataShare.phaseSNhC)));
        value.add(new BasicNameValuePair("phaseTNhC", Float.toString(FragmentDataShare.phaseTNhC)));
        value.add(new BasicNameValuePair("netralNhC", Float.toString(FragmentDataShare.netralNhC)));
        value.add(new BasicNameValuePair("phaseRNhD", Float.toString(FragmentDataShare.phaseRNhD)));
        value.add(new BasicNameValuePair("phaseSNhD", Float.toString(FragmentDataShare.phaseSNhD)));
        value.add(new BasicNameValuePair("phaseTNhD", Float.toString(FragmentDataShare.phaseTNhD)));
        value.add(new BasicNameValuePair("netralNhD", Float.toString(FragmentDataShare.netralNhD)));
        value.add(new BasicNameValuePair("phaseRBebanA", Float.toString(FragmentDataShare.phaseRBebanA)));
        value.add(new BasicNameValuePair("phaseSBebanA", Float.toString(FragmentDataShare.phaseSBebanA)));
        value.add(new BasicNameValuePair("phaseTBebanA", Float.toString(FragmentDataShare.phaseTBebanA)));
        value.add(new BasicNameValuePair("netralBebanA", Float.toString(FragmentDataShare.netralBebanA)));
        value.add(new BasicNameValuePair("phaseRBebanB", Float.toString(FragmentDataShare.phaseRBebanB)));
        value.add(new BasicNameValuePair("phaseSBebanB", Float.toString(FragmentDataShare.phaseSBebanB)));
        value.add(new BasicNameValuePair("phaseTBebanB", Float.toString(FragmentDataShare.phaseTBebanB)));
        value.add(new BasicNameValuePair("netralBebanB", Float.toString(FragmentDataShare.netralBebanB)));
        value.add(new BasicNameValuePair("phaseRBebanC", Float.toString(FragmentDataShare.phaseRBebanC)));
        value.add(new BasicNameValuePair("phaseSBebanC", Float.toString(FragmentDataShare.phaseSBebanC)));
        value.add(new BasicNameValuePair("phaseTBebanC", Float.toString(FragmentDataShare.phaseTBebanC)));
        value.add(new BasicNameValuePair("netralBebanC", Float.toString(FragmentDataShare.netralBebanC)));
        value.add(new BasicNameValuePair("phaseRBebanD", Float.toString(FragmentDataShare.phaseRBebanD)));
        value.add(new BasicNameValuePair("phaseSBebanD", Float.toString(FragmentDataShare.phaseSBebanD)));
        value.add(new BasicNameValuePair("phaseTBebanD", Float.toString(FragmentDataShare.phaseTBebanD)));
        value.add(new BasicNameValuePair("netralBebanD", Float.toString(FragmentDataShare.netralBebanD)));

        value.add(new BasicNameValuePair("rn", Float.toString(FragmentDataShare.rn)));
        value.add(new BasicNameValuePair("sn", Float.toString(FragmentDataShare.sn)));
        value.add(new BasicNameValuePair("tn", Float.toString(FragmentDataShare.tn)));
        value.add(new BasicNameValuePair("rs", Float.toString(FragmentDataShare.rs)));
        value.add(new BasicNameValuePair("st", Float.toString(FragmentDataShare.st)));
        value.add(new BasicNameValuePair("rt", Float.toString(FragmentDataShare.rt)));
        value.add(new BasicNameValuePair("rnA", Float.toString(FragmentDataShare.rnA)));
        value.add(new BasicNameValuePair("snA", Float.toString(FragmentDataShare.snA)));
        value.add(new BasicNameValuePair("tnA", Float.toString(FragmentDataShare.tnA)));
        value.add(new BasicNameValuePair("rsA", Float.toString(FragmentDataShare.rsA)));
        value.add(new BasicNameValuePair("stA", Float.toString(FragmentDataShare.stA)));
        value.add(new BasicNameValuePair("rtA", Float.toString(FragmentDataShare.rtA)));
        value.add(new BasicNameValuePair("tiangUjungA", FragmentDataShare.tiangUjungA));
        value.add(new BasicNameValuePair("rnB", Float.toString(FragmentDataShare.rnB)));
        value.add(new BasicNameValuePair("snB", Float.toString(FragmentDataShare.snB)));
        value.add(new BasicNameValuePair("tnB", Float.toString(FragmentDataShare.tnB)));
        value.add(new BasicNameValuePair("rsB", Float.toString(FragmentDataShare.rsB)));
        value.add(new BasicNameValuePair("stB", Float.toString(FragmentDataShare.stB)));
        value.add(new BasicNameValuePair("rtB", Float.toString(FragmentDataShare.rtB)));
        value.add(new BasicNameValuePair("tiangUjungB", FragmentDataShare.tiangUjungB));
        value.add(new BasicNameValuePair("rnC", Float.toString(FragmentDataShare.rnC)));
        value.add(new BasicNameValuePair("snC", Float.toString(FragmentDataShare.snC)));
        value.add(new BasicNameValuePair("tnC", Float.toString(FragmentDataShare.tnC)));
        value.add(new BasicNameValuePair("rsC", Float.toString(FragmentDataShare.rsC)));
        value.add(new BasicNameValuePair("stC", Float.toString(FragmentDataShare.stC)));
        value.add(new BasicNameValuePair("rtC", Float.toString(FragmentDataShare.rtC)));
        value.add(new BasicNameValuePair("tiangUjungC", FragmentDataShare.tiangUjungC));
        value.add(new BasicNameValuePair("rnD", Float.toString(FragmentDataShare.rnD)));
        value.add(new BasicNameValuePair("snD", Float.toString(FragmentDataShare.snD)));
        value.add(new BasicNameValuePair("tnD", Float.toString(FragmentDataShare.tnD)));
        value.add(new BasicNameValuePair("rsD", Float.toString(FragmentDataShare.rsD)));
        value.add(new BasicNameValuePair("stD", Float.toString(FragmentDataShare.stD)));
        value.add(new BasicNameValuePair("rtD", Float.toString(FragmentDataShare.rtD)));
        value.add(new BasicNameValuePair("tiangUjungD", FragmentDataShare.tiangUjungD));
        return value;
    }

}
