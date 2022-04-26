package xyz.flevarakis.arubatranquility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import xyz.flevarakis.arubatranquility.core.Constants;
import xyz.flevarakis.arubatranquility.models.Instance;
import xyz.flevarakis.arubatranquility.models.atq.Metrics;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Instance currentInstance;
    private SwipeRefreshLayout swipeContainer;
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            currentInstance = (Instance) extras.getSerializable("instance");
            //savedInstanceState.putSerializable("currentInstance", currentInstance);
            Log.d(TAG, "Received instance " + currentInstance.getHostname());
        }
        else {
            Log.e(TAG, "Did not receive instance");
            finish();
        }
        updateData();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData();
            }
        });
        swipeContainer.setRefreshing(true);
    }

    private void updateData() {
        Gson gson = new Gson();
        client.get(Constants.API_METRICS_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                Metrics metrics = gson.fromJson(json, Metrics.class);
                updateDisplay(metrics);
                swipeContainer.setRefreshing(false);
                Log.i(TAG, "Received metrics form API: " + json);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, error.toString());
            }
        });
    }

    private void updateDisplay(Metrics metrics)
    {
        TextView locationsMonitoredCount = (TextView) findViewById(R.id.locations_monitored_count);
        TextView connectedClientsCount = (TextView) findViewById(R.id.connected_clients_count);
        TextView devicesUpCount = (TextView) findViewById(R.id.devices_up_count);
        TextView healthySitesCount = (TextView) findViewById(R.id.healthy_sites_count);
        TextView sitesWithWarningCount = (TextView) findViewById(R.id.sites_with_warning_count);
        TextView criticalSitesCount = (TextView) findViewById(R.id.critical_sites_count);

        locationsMonitoredCount.setText(String.valueOf(metrics.getTotalSites()));
        connectedClientsCount.setText(String.valueOf(metrics.getConnectedCount()));
        devicesUpCount.setText(String.valueOf(metrics.getDeviceUp()));
        healthySitesCount.setText(String.valueOf(metrics.getNormal()));
        sitesWithWarningCount.setText(String.valueOf(metrics.getWarning()));
        criticalSitesCount.setText(String.valueOf(metrics.getCritical()));
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