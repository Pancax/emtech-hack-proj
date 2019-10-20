package pancax.emtechproj;

import org.json.JSONException;
import org.json.JSONObject;

public class Account {
    private String id;
    private String address;
    private String wallet_provider_name;
    private String wallet_provider_display_name;
    public Account(JSONObject obj){
        try {
            id=obj.get("id").toString();
            address=obj.get("address").toString();
            wallet_provider_name=obj.get("wallet_provider_name").toString();
            wallet_provider_display_name=obj.get("wallet_provider_display_name").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getId(){
        return id;
    }
    public String getAddress(){
        return address;
    }
    public String getWallet_provider_name(){
        return wallet_provider_name;
    }
    public String getWallet_provider_display_name(){
        return wallet_provider_display_name;
    }
}
