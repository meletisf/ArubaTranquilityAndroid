package xyz.flevarakis.arubatranquility.core;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import xyz.flevarakis.arubatranquility.models.Instance;

public class InstanceManager {
    private static final String TAG = "InstanceManager";

    private Context context;
    private Instance[] instances;
    private Gson gson = new Gson();

    public InstanceManager(Context context) {
        this.context = context;
    }

    public ArrayList<Instance> getAllInstances() {
        String content;
        content = getFileContent();

        Type instancesListType = new TypeToken<ArrayList<Instance>>(){}.getType();

        return gson.fromJson(content, instancesListType);
    }

    public void addInstance(Instance instance) {
        ArrayList<Instance> instances = getAllInstances();
        instances.add(instance);

        writeToFile(gson.toJson(instances));
    }

    private String getFileContent() {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(Constants.INSTANCES_FILE_NAME);
        } catch (FileNotFoundException e) {
            Log.i(TAG, "Instances file didn't exist. Creating now");
            createFileIfDoesntExist();
        }

        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            Log.e(TAG, "Something went terribly wrong");
        }
        return stringBuilder.toString();
    }

    public void createFileIfDoesntExist() {
        File file = new File(context.getFilesDir(), Constants.INSTANCES_FILE_NAME);
        if(! file.exists()) {
            writeToFile("[]");
        }
    }

    private void writeToFile(String content) {
        try (FileOutputStream fos = context.openFileOutput(Constants.INSTANCES_FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
