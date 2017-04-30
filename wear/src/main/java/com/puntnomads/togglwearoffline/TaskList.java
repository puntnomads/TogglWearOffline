package com.puntnomads.togglwearoffline;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by puntnomads on 14/01/2017.
 */

public class TaskList extends ListActivity {

    String project;

    private static String[] items;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.list_tasks);

        project = getIntent().getStringExtra("list");
        SharedPreferences prefs = getSharedPreferences(project, 0);
        String projectsString = prefs.getString(project, "");
        items = projectsString.split(",");

        Arrays.sort(items);

        // Create a List from String Array elements
        final List<String> items_list = new ArrayList<String>(Arrays.asList(items));

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, items_list){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);

                // Get the Layout Parameters for ListView Current Item View
                ViewGroup.LayoutParams params = view.getLayoutParams();

                // Set the height of the Item View
                params.height = 120;
                view.setLayoutParams(params);

                return view;
            }
        };

        setListAdapter(arrayAdapter);
    }

    @Override
    public void onListItemClick(ListView parent, View v, int position,
                                long id) {
        String task = items[position];
        Intent intent;
        if(getIntent().getIntExtra("num",5)==2){
            intent = new Intent(TaskList.this, RemoveActivity.class);
            intent.putExtra("name", project);
            intent.putExtra("list", task);
        } else {
            intent = new Intent(TaskList.this, TaskActivity.class);
            intent.putExtra("project", project);
            intent.putExtra("task", task);
        }
        startActivity(intent);

    }
}