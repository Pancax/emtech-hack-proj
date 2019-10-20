package pancax.emtechproj;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnect extends AsyncTask<String, Void, String> {
    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;
    private boolean Okay;
    private URL url;
    private OnTaskDoneListener delegate;
    private Context c;
    private String getWay;

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
        String getway = params[0];
        String api_key = params[1];
        String secret_key = params[2];
        String client_id = params[3];
        this.getWay=getway;
        String result="";
        HttpsURLConnection connection = null;
        try{
            URL connectionUrl = new URL(url.toString()+getway);
            connection = (HttpsURLConnection) connectionUrl.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setDoInput(true);
            String currentTime= System.currentTimeMillis()+"";
            String encodedString = calcHmacSha256(secret_key, currentTime+connectionUrl.toString()+"");
            connection.addRequestProperty("X-Zabo-Key", api_key);
            connection.addRequestProperty("X-Zabo-Sig",encodedString);
            connection.addRequestProperty("X-Zabo-Timestamp",currentTime);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if(!getWay.equals("/users"))
            getWay+=" "+connection.getResponseMessage()+" "+ connection.getResponseCode();
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
        delegate.onTaskDone(result,getWay);
    }
    private String calcHmacSha256(String key, String data) {
        byte[] hmacSha256 = null;
        try {
            byte[] secretKey = key.getBytes("UTF-8");
            byte[] message = data.getBytes("UTF-8");

            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "HmacSHA256");
            mac.init(secretKeySpec);
            hmacSha256 = mac.doFinal(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate hmac-sha256", e);
        }

        return String.format("%032x", new BigInteger(1, hmacSha256));
    }
}
