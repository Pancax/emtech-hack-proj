package pancax.emtechproj;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements OnTaskDoneListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String urlString = "https://api.zabo.com/sandbox-v/";

        HttpsURLConnect connect = new HttpsURLConnect(this,urlString,this);
        connect.execute("users");
    }

    @Override
    public void onTaskDone(String responseData) {
        //async task is done do wahtever
        Log.d("Response290","Response \n"+responseData);
    }

    @Override
    public void onError() {

    }
}
