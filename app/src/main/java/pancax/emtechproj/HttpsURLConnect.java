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
            connection.setDoInput(true);

            //add the request properties
            //curl "https://api.zabo.com/sandbox-v0/exchange-rates" \
            //  -H "X-Zabo-Key: adminApiKey-established-in-dash" \
            //  -H "X-Zabo-Sig: signature-of-request-using-paired-secret-key" \
            //  -H "X-Zabo-Timestamp: 1420069800000"
            String b = System.currentTimeMillis()+"";
            String s = calcHmacSha256("f9daeb948eedfc34f657e8cdbebc965635b856bf4977e813f30c90070a6339c4", b+xc.toString()+"");
            connection.addRequestProperty("X-Zabo-Key","d0e625eb91e9e40f67bf1ae9636d42745be8f37d");
            connection.addRequestProperty("X-Zabo-Sig",s);
            connection.addRequestProperty("X-Zabo-Timestamp",b);
            result+="Encode\n"+s+"\n"+b+"\n";

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
            result+=connection.getResponseCode()+" "+connection.getResponseMessage();
            result+=" "+b+xc.toString()+"\'\'";
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

    private String encode(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return bytesToHex(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
    }
    public static String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
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
