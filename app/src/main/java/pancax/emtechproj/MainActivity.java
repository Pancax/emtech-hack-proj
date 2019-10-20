package pancax.emtechproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements OnTaskDoneListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String urlString = "https://api.zabo.com/sandbox-v0/";

        HttpsURLConnect connect = new HttpsURLConnect(this,urlString,this);
        connect.execute("currencies");
        Intent intent = new Intent(this, web_activity.class);
        startActivity(intent);

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
