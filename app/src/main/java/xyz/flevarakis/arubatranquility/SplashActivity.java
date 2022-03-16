package xyz.flevarakis.arubatranquility;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.flevarakis.arubatranquility.adapters.InstancesAdapter;
import xyz.flevarakis.arubatranquility.core.Constants;
import xyz.flevarakis.arubatranquility.core.InstanceManager;
import xyz.flevarakis.arubatranquility.models.Instance;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private ArrayList<Instance> instances;
    private InstanceManager instanceManager;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d(TAG, "Received result code: " + result.getResultCode());
                    handleActivityResult(result);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        instanceManager = new InstanceManager(getApplicationContext());

        final Button addInstanceButton = (Button) findViewById(R.id.add_instance_button);
        addInstanceButton.setOnClickListener(view -> {
            Log.d(TAG, "Clicking button to add new instance");
            launchNewInstanceActivity();
        });

        final TextView copyrightText = (TextView) findViewById(R.id.contact_us);
        copyrightText.setOnClickListener(view -> {
            contact();
        });


        instanceManager.createFileIfDoesntExist();
        instances = instanceManager.getAllInstances();
        Log.d(TAG, "Loaded " + instances.size() + " instances");

        InstancesAdapter instancesAdapter = new InstancesAdapter(this, instances);

        ListView instancesListView = (ListView) findViewById(R.id.instances_list_view);
        instancesListView.setAdapter(instancesAdapter);

        instancesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Instance clickedInstance = (Instance) parent.getItemAtPosition(position);
                Log.d(TAG, "Clicked item: " + clickedInstance.getEmail());
                launchInstanceViewActivity(clickedInstance);
            }
        });
    }

    private void launchNewInstanceActivity() {
        Intent intent = new Intent(getApplicationContext(), AddInstanceActivity.class);
        activityResultLauncher.launch(intent);
    }

    private void launchInstanceViewActivity(Instance instance) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("instance", instance);
        startActivity(intent);
    }

    private void addNewInstance(Instance instance) {
        Log.d(TAG, "Adding new instance " + instance.getHostname());
        instanceManager.addInstance(instance);
        instances.add(instance);
    }

    private void contact() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, "meletios.flevarakis@hpe.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Aruba Tranquility");
        if (intent.resolveActivity(getPackageManager()) != null) {
            Log.d(TAG, "Starting mail client");
            startActivity(intent);
        }
    }

    private void handleActivityResult(ActivityResult result) {
        if (result.getResultCode() == Constants.returnNewInstanceResultCode) {
            Intent intent = result.getData();
            if (intent != null) {
                addNewInstance((Instance) intent.getSerializableExtra("instance"));
            }
        }
    }
}