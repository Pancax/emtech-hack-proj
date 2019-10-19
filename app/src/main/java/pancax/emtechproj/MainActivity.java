package pancax.emtechproj;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.liquidplayer.javascript.JSContext;
import org.liquidplayer.javascript.JSValue;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSContext context = new JSContext();
        context.evaluateScript("Zabo.init({\n" +
                "  clientId: 'YourAppKeyFromYourZaboDashboard',\n" +
                "  env: 'sandbox'\n" +
                "}).then(app => {\n" +
                "  console.log(app)\n" +
                "}).catch(err => {\n" +
                "  console.error(err)\n" +
                "})");
    }
}
