package com.puntnomads.togglwearoffline;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TaskActivity extends Activity {

    private Button startButton;
    private Button stopButton;
    private DatabaseHelper myDatabase;
    private boolean toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        myDatabase = new DatabaseHelper(this);
        Intent intent = getIntent();
        final String project = intent.getStringExtra("project");
        final String task = intent.getStringExtra("task");
        startButton = (Button) findViewById(R.id.start_button);
        stopButton = (Button) findViewById(R.id.stop_button);
        loadPreferences();
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                String timeString = df.format(Calendar.getInstance().getTime());
                int time = (int) (System.currentTimeMillis() / 1000);
                myDatabase.startProject(project, task, timeString, time);
                toggle = true;
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                Log.v("start", Boolean.toString(toggle));
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int time = (int) (System.currentTimeMillis() / 1000);
                myDatabase.stopProject(time);
                toggle = false;
                stopButton.setEnabled(false);
                startButton.setEnabled(true);
                Log.v("stop", Boolean.toString(toggle));
            }
        });
    }

    protected void onPause(){
        super.onPause();
        savePreferences();
    }

    private void savePreferences(){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("toggle", toggle);
        Log.v("save", Boolean.toString(toggle));
        editor.commit();   // I missed to save the data to preference here,.
    }

    private void loadPreferences(){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        toggle = sharedPreferences.getBoolean("toggle", false);
        Log.v("load", Boolean.toString(toggle));
        if(toggle){
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
        if(!toggle){
            stopButton.setEnabled(false);
            startButton.setEnabled(true);
        }
    }
}
