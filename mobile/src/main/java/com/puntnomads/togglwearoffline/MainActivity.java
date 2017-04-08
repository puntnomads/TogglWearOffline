package com.puntnomads.togglwearoffline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.StrictMode;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.puntnomads.togglwearoffline.Constants.FIRST_COLUMN;
import static com.puntnomads.togglwearoffline.Constants.SECOND_COLUMN;
import static com.puntnomads.togglwearoffline.Constants.THIRD_COLUMN;
import static com.puntnomads.togglwearoffline.Constants.FOURTH_COLUMN;
import static com.puntnomads.togglwearoffline.Constants.FIFTH_COLUMN;

public class MainActivity extends AppCompatActivity {

    // improve the UI to be a table format
    private ArrayList<HashMap<String, String>> list;
    ListView listView;
    Button send;
    ArrayList<String> projectNames = new ArrayList<String>();
    ArrayList<String> taskNames = new ArrayList<String>();
    ArrayList<String> startNames = new ArrayList<String>();
    ArrayList<Integer> startTimes = new ArrayList<Integer>();
    ArrayList<Integer> stopTimes = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView =(ListView)findViewById(R.id.listView1);
        send = (Button) findViewById(R.id.button);

        // Register the local broadcast receiver
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAllData();
            }
        });
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle data = intent.getBundleExtra("datamap");
            long numbers = data.getLong("numbers");
            for(int x = 0; x < numbers; x++){
                projectNames.add(data.getString("projectname"+x));
                taskNames.add(data.getString("taskname"+x));
                startNames.add(data.getString("startname"+x));
                startTimes.add(Integer.parseInt(data.getString("starttime"+x)));
                stopTimes.add(Integer.parseInt(data.getString("stoptime"+x)));
            }
            if(projectNames.size()==numbers){
                displayInfo();
            }
        }
    }

    public void sendAllData(){
        for(int x = 0; x < projectNames.size(); x++){
            sendData(projectNames.get(x), taskNames.get(x), startNames.get(x), stopTimes.get(x) - startTimes.get(x));
        }
    }

    public void displayInfo(){

        list=new ArrayList<HashMap<String,String>>();

        for(int x = 0; x < projectNames.size(); x++){
            HashMap<String,String> temp=new HashMap<String, String>();
            temp.put(FIRST_COLUMN, Integer.toString(x+1));
            temp.put(SECOND_COLUMN, projectNames.get(x));
            temp.put(THIRD_COLUMN, taskNames.get(x));
            temp.put(FOURTH_COLUMN, startNames.get(x));
            temp.put(FIFTH_COLUMN, Integer.toString(stopTimes.get(x) - startTimes.get(x)));
            list.add(temp);
        }

        ListViewAdapter adapter=new ListViewAdapter(this, list);
        listView.setAdapter(adapter);
    }

    public void sendData(String projectName, String taskName, String startName, int duration){
        // use Asyc Task for the network call
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        int pid = 0;
        switch(projectName) {
            case "Full Stack":
                pid = 35991486;
                break;
            case "Android":
                pid = 28581344;
                break;
            case "Body":
                pid = 28581662;
                break;
            case "Household":
                pid = 28581611;
                break;
            case "Islam":
                pid = 28581672;
                break;
            case "Mind":
                pid = 28581667;
                break;
            case "NodeJS":
                pid = 28581424;
                break;
            case "Web Front End":
                pid = 28581527;
                break;
            case "PHP":
                pid = 35991305;
                break;
            case "Computer Science":
                pid = 28582134;
                break;
        }
        try {
            // JSON data

            JSONObject childData = new JSONObject();
            JSONObject parentData = new JSONObject();
            try {
                childData.put("description", taskName);
                childData.put("duration", duration);
                childData.put("start", startName);
                childData.put("pid", pid);
                childData.put("created_with", "Phone");
                parentData.put("time_entry", childData);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Headers
            ArrayList<String[]> headers = new ArrayList<>();
            headers.add(new String[]{"Content-Type", "application/json"});

            HttpResultHelper httpResult = httpPost("https://www.toggl.com/api/v8/time_entries",
                    "16b34c42c38cd623fdd95fa339edbaaf", "api_token", parentData.toString(), headers, 7000);
            BufferedReader in = new BufferedReader(new InputStreamReader(httpResult.getResponse()));
            String result = "";
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result += inputLine;
            }
            Log.v("Message:",result);
        } catch (Exception e) {
            Log.d("TAG", "Failed to work", e);
        }
    }

    private HttpResultHelper httpPost(String urlStr, String user, String password, String data, ArrayList<String[]> headers, int timeOut) throws IOException
    {
        // Set url
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Use this if you need basic authentication
        if ((user != null) && (password != null)) {
            String userPass = user + ":" + password;
            String basicAuth = "Basic " + Base64.encodeToString(userPass.getBytes(), Base64.DEFAULT);
            conn.setRequestProperty("Authorization", basicAuth);
        }

        // Set Timeout and method
        conn.setReadTimeout(timeOut);
        conn.setConnectTimeout(timeOut);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        if (headers != null) {
            for (int i = 0; i < headers.size(); i++) {
                conn.setRequestProperty(headers.get(i)[0], headers.get(i)[1]);
            }
        }

        if (data != null) {
            conn.setFixedLengthStreamingMode(data.getBytes().length);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            os.close();
        }

        InputStream inputStream = null;
        try
        {
            inputStream = conn.getInputStream();
        }
        catch(IOException exception)
        {
            inputStream = conn.getErrorStream();
        }

        HttpResultHelper result = new HttpResultHelper();
        result.setStatusCode(conn.getResponseCode());
        result.setResponse(inputStream);

        return result;
    }

    public class HttpResultHelper {
        private int statusCode;
        private InputStream response;

        public HttpResultHelper() {
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public InputStream getResponse() {
            return response;
        }

        public void setResponse(InputStream response) {
            this.response = response;
        }
    }
}
