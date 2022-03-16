package xyz.flevarakis.arubatranquility;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import xyz.flevarakis.arubatranquility.models.Instance;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Instance currentInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            currentInstance = (Instance) extras.getSerializable("instance");
            Log.d(TAG, "Received instance " + currentInstance.getHostname());
        }
        else {
            Log.e(TAG, "Did not receive instance");
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d(TAG, "Going back to home");
            finish();
            return true;
        }
        Log.d(TAG, "Received unknown press");
        return false;
    }
}