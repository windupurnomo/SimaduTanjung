package com.windupurnomo.simadutanjung.server;

import android.util.Log;

import com.windupurnomo.simadutanjung.entities.FragmentDataShare;
import com.windupurnomo.simadutanjung.entities.Gardu;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windu Purnomo on 7/6/14.
 */
public class MeasurementService {
    private final static String TAG = "ServerRequest";
    private final String serverUri = "http://simadu.kedainusa.com";
    public static final String urlSelectAll = "select_all_gardu.php";
    public static final String urlDelete = "delete_gardu.php";
    public static final String urlSubmit = "save_measurement.php";

    public MeasurementService() {
        super();
    }

    /* Mengirimkan POST request 	 */
    public int sendPostRequest(){
        int replyCode = 99;
        HttpClient httpClient;
        HttpPost post = new HttpPost(this.serverUri+"/"+urlSubmit);
        List<NameValuePair> value = new ArrayList<NameValuePair>();
		/* menambahkan parameter ke dalam request */
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-M-dd hh:mm:ss");
        value.add(new BasicNameValuePair("id", Integer.toString(FragmentDataShare.id)));
        value.add(new BasicNameValuePair("gardu_id", Integer.toString(FragmentDataShare.garduId)));
        value.add(new BasicNameValuePair("batch", Integer.toString(FragmentDataShare.batch)));
        value.add(new BasicNameValuePair("proc_num", Integer.toString(FragmentDataShare.procNum)));

        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 3000);
            HttpConnectionParams.setSoTimeout(params, 3000);
            httpClient = new DefaultHttpClient(params);
            post.setEntity(new UrlEncodedFormEntity(value));
            Log.d(TAG, "executing post...");
            HttpResponse httpResponse = httpClient.execute(post);
            StatusLine status = httpResponse.getStatusLine();
            if(status.getStatusCode() == HttpStatus.SC_OK){
                Log.d(TAG, "submitted sucessfully...");
                replyCode = status.getStatusCode();
            }
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }
        return replyCode;
    }
}
