package pancax.emtechproj;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.acl.LastOwnerException;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class HttpsURLConnect extends AsyncTask<String, Void, String> {
    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;
    private boolean Okay;
    private URL url;
    private OnTaskDoneListener delegate;
    private Context c;

    public HttpsURLConnect(Context c, String url, OnTaskDoneListener delegate){
        this.c=c;
        this.delegate=delegate;
        try {
            this.url=new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public void setUrl(String url){
        if(Okay) {
            try {
                this.url=new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    protected String doInBackground(String... params) {
        Okay = false;
        String bob = params[0];
        String result="";
        HttpsURLConnection connection = null;
        try{
            URL xc = new URL(url.toString()+bob);
            connection = (HttpsURLConnection) xc.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                result+=sb.toString();
            }
        }catch(Exception e){
            e.printStackTrace();
            Log.d("ERRORMESSAGE",e.getMessage());
        }finally{
            if(connection!=null)
                connection.disconnect();

        }
        return result;
    }
    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        Okay=true;
        delegate.onTaskDone(result);
    }
}
