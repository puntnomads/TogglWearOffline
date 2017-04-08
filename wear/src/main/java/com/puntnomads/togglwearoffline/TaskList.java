package com.puntnomads.togglwearoffline;

import android.app.ListActivity;
import android.content.Intent;
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

public class TaskList extends ListActivity {

    public final static String EXTRA_TASK = "task";

    public final static String EXTRA_PROJECT = "project";

    String project;

    private static String[] items;

    private static final String[] Full_Stack_items={"HennaDot"};

    private static final String[] Android_items={"TogglWearOffline", "NeverMissAPrayer", "MyCalendar"};

    private static final String[] Body_items={"Morning Routine", "Breakfast", "Lunch", "Dinner"};

    private static final String[] Household_items={"Chores", "Grooming"};

    private static final String[] Islam_items={"Fajr Prayer", "Duhr Prayer", "Asr Prayer", "Maghreb Prayer", "Isha Prayer"};

    private static final String[] Mind_items={"Planning"};

    private static final String[] NodeJS_items={"NodeJS Course"};

    private static final String[] Web_Front_End_items={"Javascript30", "VideoPlayer"};

    private static final String[] PHP_items={"CodeAcademy", "PHP Tutorials"};

    private static final String[] Computer_Science_items={"CS50", "Algorithms", "Algorithms Design"};

    private static final String[] Programming_items={"Resources"};

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.list_tasks);

        Intent intent = getIntent();
        project = intent.getStringExtra(ProjectsList.EXTRA_PROJECT);
        switch(project) {
            case "Full Stack":
                items = Full_Stack_items;
                break;
            case "Android":
                items = Android_items;
                break;
            case "Body":
                items = Body_items;
                break;
            case "Household":
                items = Household_items;
                break;
            case "Islam":
                items = Islam_items;
                break;
            case "Mind":
                items = Mind_items;
                break;
            case "NodeJS":
                items = NodeJS_items;
                break;
            case "Web Front End":
                items = Web_Front_End_items;
                break;
            case "PHP":
                items = PHP_items;
                break;
            case "Computer Science":
                items = Computer_Science_items;
                break;
            case "Programming":
                items = Programming_items;
                break;
        }

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
        //selection.setText(items[position]);
        Intent intent = new Intent(this, ProjectActivity.class);
        String task = items[position];
        intent.putExtra(EXTRA_PROJECT, project);
        intent.putExtra(EXTRA_TASK, task);
        startActivity(intent);

    }
}