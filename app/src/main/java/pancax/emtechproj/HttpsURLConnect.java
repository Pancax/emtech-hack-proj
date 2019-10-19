package pancax.emtechproj;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class HttpsURLConnect extends AsyncTask<String, Void, String> {
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    private boolean Okay;
    URL url;
    public HttpsURLConnect(URL url){
        this.url=url;
    }
    public void setUrl(URL url){
        if(Okay)
            this.url=url;

    }
    @Override
    protected String doInBackground(String... params) {
        Okay = false;
        String result;
        String inputLine;
        HttpsURLConnection connection = null;
        try{
            connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            connection.connect();

            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());

            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            reader.close();
            streamReader.close();

            result = stringBuilder.toString();

            reader.close();
            streamReader.close();
        }catch(Exception e){
            e.printStackTrace();
            result="";
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

    }
}
