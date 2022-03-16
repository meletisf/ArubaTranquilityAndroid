package xyz.flevarakis.arubatranquility.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import xyz.flevarakis.arubatranquility.R;
import xyz.flevarakis.arubatranquility.models.Instance;

public class InstancesAdapter extends ArrayAdapter<Instance> {

    public InstancesAdapter(Context context, ArrayList<Instance> instances) {
        super(context, 0, instances);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Instance instance = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.instance, parent, false);
        }

        TextView hostnameTextView = (TextView) convertView.findViewById(R.id.instance_hostname_text);
        TextView emailTextView = (TextView) convertView.findViewById(R.id.instance_email_text);

        hostnameTextView.setText(instance.getHostname());
        emailTextView.setText(instance.getEmail());

        return convertView;
    }
}
