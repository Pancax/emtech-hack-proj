package pancax.emtechproj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AccountArrayAdapter extends ArrayAdapter<Account> {
    ArrayList<Account> curList;

    public AccountArrayAdapter(@NonNull Context context, ArrayList<Account> list) {
        super(context,0, list);
        curList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Account account = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.account_adapter_layout, parent, false);
        }
        // Lookup view for data population
        TextView id= (TextView) convertView.findViewById(R.id.id);
        TextView address = (TextView) convertView.findViewById(R.id.address);
        TextView wallet_provider_name = convertView.findViewById(R.id.wallet_provider_name);
        TextView wallet_provider_display_name = convertView.findViewById(R.id.wallet_provider_display_name);

        // Populate the data into the template view using the data object
        id.setText(account.getId());
        address.setText(account.getAddress());
        wallet_provider_name.setText(account.getWallet_provider_name());
        wallet_provider_display_name.setText(account.getWallet_provider_display_name());
        // Return the completed view to render on screen
        return convertView;

    }
}
