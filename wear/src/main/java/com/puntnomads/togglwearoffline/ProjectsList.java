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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by puntnomads on 14/01/2017.
 */

public class ProjectsList extends ListActivity {

    private static String[] items;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.list_projects);

        SharedPreferences prefs = getSharedPreferences("projects", 0);
        String projectsString = prefs.getString("projects", "");
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
        Intent intent;
        String project = items[position];
        int num = getIntent().getIntExtra("num",5);
        switch (num){
            case 0:
                intent = new Intent(ProjectsList.this, KeyboardActivity.class);
                break;
            case 1:
                intent = new Intent(ProjectsList.this, RemoveActivity.class);
                intent.putExtra("name", "projects");
                break;
            case 2:
                intent = new Intent(ProjectsList.this, TaskList.class);
                intent.putExtra("num", 2);
                break;
            default:
                intent = new Intent(ProjectsList.this, TaskList.class);
        }
        intent.putExtra("list", project);
        startActivity(intent);

    }
}
