package com.puntnomads.togglwearoffline;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RemoveActivity extends Activity {

    Button confirm;
    String name;
    String list;
    private static String[] items;
    List<String> lists = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);
        confirm = (Button) findViewById(R.id.confirm_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = getIntent().getStringExtra("name");
                SharedPreferences prefs = getSharedPreferences(name, 0);
                String projectsString = prefs.getString(name, "");
                items = projectsString.split(",");
                list = getIntent().getStringExtra("list");
                List<String> arrayList = new ArrayList<>();
                Collections.addAll(arrayList, items);
                arrayList.remove(arrayList.indexOf(list));
                items = arrayList.toArray(new String[0]);
                for (int i = 0; i < items.length; i++){
                    lists.add(items[i]);
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (String s : lists){
                    stringBuilder.append(s);
                    stringBuilder.append(",");
                }
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(name, stringBuilder.toString());
                editor.apply();
            }
        });
    }
}
