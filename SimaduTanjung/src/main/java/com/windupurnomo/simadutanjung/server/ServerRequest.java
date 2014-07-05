package com.windupurnomo.simadutanjung.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
import android.util.Log;

import com.windupurnomo.simadutanjung.entities.Gardu;

public class ServerRequest {
    private final static String TAG = "ServerRequest";
    private final String serverUri = "http://simadu.kedainusa.com";
    public static final String urlSelectAll = "select_all_gardu.php";
    public static final String urlDelete = "delete_gardu.php";
    public static final String urlSubmit = "save_gardu.php";

    public ServerRequest() {
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
            HttpConnectionParams.setConnectionTimeout(params, 3000);
            HttpConnectionParams.setSoTimeout(params, 3000);
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
    public int sendPostRequest(Gardu gardu, String url){
        int replyCode = 99;
        HttpClient httpClient;
        HttpPost post = new HttpPost(this.serverUri+"/"+url);
        List<NameValuePair> value = new ArrayList<NameValuePair>();
		/* menambahkan parameter ke dalam request */
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-M-dd hh:mm:ss");
        value.add(new BasicNameValuePair("id", Integer.toString(gardu.getId())));
        value.add(new BasicNameValuePair("nomor", gardu.getNomor()));
        value.add(new BasicNameValuePair("address", gardu.getAddress()));
        value.add(new BasicNameValuePair("daya", Float.toString(gardu.getDaya())));
        value.add(new BasicNameValuePair("penyulang", gardu.getPenyulang()));
        value.add(new BasicNameValuePair("tiang", Integer.toString(gardu.getTiang())));

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