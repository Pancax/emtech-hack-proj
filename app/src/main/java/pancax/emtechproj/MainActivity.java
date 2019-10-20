package pancax.emtechproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements OnTaskDoneListener {
    LinearLayout navLayout;
    LinearLayout walletLayout;
    Button walletButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*String urlString = "https://api.zabo.com/sandbox-v0/";
        HttpsURLConnect connect = new HttpsURLConnect(this,urlString,this);
        connect.execute("currencies");
        Intent intent = new Intent(this, web_activity.class);
        startActivity(intent);*/
        initializeViews();

    }
    private void initializeViews(){
        navLayout = findViewById(R.id.nav_layout);
        walletLayout = findViewById(R.id.wallet_layout);
        walletButton = findViewById(R.id.wallet_button);
    }
    @Override
    public void onTaskDone(String responseData) {
        //async task is done do wahtever
        Log.d("Response290","Response \n"+responseData);
    }

    @Override
    public void onError() {

    }

    public void walletButtonClicked(View v){
    }
}
