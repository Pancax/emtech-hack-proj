package pancax.emtechproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnTaskDoneListener {
    LinearLayout navLayout;
    LinearLayout walletLayout;
    Button walletButton;
    ListView listView;
    private final String CLIENT_ID = "J28kJ68mHfiXSYLqyoujgFA4mCrRGxBiUkELZS1YImTyD4DAhNmR9VZqpiFpqRUV";
    private final String API_KEY = "d0e625eb91e9e40f67bf1ae9636d42745be8f37d";
    private final String SECRET_KEY ="f9daeb948eedfc34f657e8cdbebc965635b856bf4977e813f30c90070a6339c4";
    private final String URL ="https://api.zabo.com/sandbox-v0";
    private ArrayList<Account> accounts;
    AccountArrayAdapter listAdapter;
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
        doHttpsThing("/users");
        accounts= new ArrayList<>();
    }
    private void doHttpsThing(String getWay){
        HttpsURLConnect connect = new HttpsURLConnect(this,URL,this);
        connect.execute(getWay,API_KEY,SECRET_KEY, CLIENT_ID);
    }
    private void initializeViews(){
        navLayout = findViewById(R.id.nav_layout);
        walletLayout = findViewById(R.id.wallet_layout);
        walletButton = findViewById(R.id.wallet_button);
        listView = findViewById(R.id.listview);
    }
    private void initializeListView(){
        listAdapter = new AccountArrayAdapter(this,accounts);
        listView.setAdapter(listAdapter);
    }
    @Override
    public void onTaskDone(String responseData) {
        //async task is done do wahtever
        Log.d("Response290","Response \n"+responseData);
        readResponse(responseData);
    }
    private void readResponse(String responseData){
        try {
            JSONObject bob = new JSONObject(responseData);
            JSONArray accountArr = (JSONArray) bob.getJSONArray("data").getJSONObject(0).getJSONArray("accounts");
            for(int i=0;i<accountArr.length();i++){
                accounts.add(new Account(accountArr.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initializeListView();
    }
    @Override
    public void onError() {

    }

    public void walletButtonClicked(View v){
    }
}
