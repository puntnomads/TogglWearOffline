package com.puntnomads.togglwearoffline;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.puntnomads.togglwearoffline.Constants.FIFTH_COLUMN;
import static com.puntnomads.togglwearoffline.Constants.FIRST_COLUMN;
import static com.puntnomads.togglwearoffline.Constants.FOURTH_COLUMN;
import static com.puntnomads.togglwearoffline.Constants.SECOND_COLUMN;
import static com.puntnomads.togglwearoffline.Constants.THIRD_COLUMN;

/**
 * Created by puntnomads on 21/01/2017.
 */

public class ListViewAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;
    TextView txtFourth;
    TextView txtFifth;
    public ListViewAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub



        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.colmn_row, null);

            txtFirst=(TextView) convertView.findViewById(R.id.id);
            txtSecond=(TextView) convertView.findViewById(R.id.project_name);
            txtThird=(TextView) convertView.findViewById(R.id.task_name);
            txtFourth=(TextView) convertView.findViewById(R.id.start_name);
            txtFifth=(TextView) convertView.findViewById(R.id.duration);

        }

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN));
        txtSecond.setText(map.get(SECOND_COLUMN));
        txtThird.setText(map.get(THIRD_COLUMN));
        txtFourth.setText(map.get(FOURTH_COLUMN));
        txtFifth.setText(map.get(FIFTH_COLUMN));

        return convertView;
    }

}
