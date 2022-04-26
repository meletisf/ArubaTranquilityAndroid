package xyz.flevarakis.arubatranquility;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import xyz.flevarakis.arubatranquility.core.Constants;
import xyz.flevarakis.arubatranquility.models.Instance;

public class AddInstanceActivity extends AppCompatActivity {

    private static final String TAG = "AddInstanceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instance);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button saveInstanceButton = (Button) findViewById(R.id.save_instance_button);
        saveInstanceButton.setOnClickListener(view -> {
            returnInstance();
        });
    }

    private void returnInstance() {
        final EditText hostnameEditText = (EditText) findViewById(R.id.instance_hostname);
        final EditText emailEditText = (EditText) findViewById(R.id.instance_email);
        final EditText passwordEditText = (EditText) findViewById(R.id.instance_password);

        Instance i = new Instance(
                hostnameEditText.getText().toString(),
                emailEditText.getText().toString(),
                passwordEditText.getText().toString()
        );

        Intent intent = new Intent();
        intent.putExtra("instance", i);
        setResult(Constants.RETURN_NEW_INSTANCE_RESULT_CODE, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d(TAG, "Going back to home");
                finish();
                return true;
            default:
                Log.d(TAG, "Received unknown press");
                return false;
        }
    }
}