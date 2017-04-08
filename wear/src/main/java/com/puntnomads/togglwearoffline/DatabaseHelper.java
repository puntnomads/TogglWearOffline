package com.puntnomads.togglwearoffline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by puntnomads on 14/01/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="projects.db";
    private static final String TABLE_PROJECTS="projects";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_PROJECTNAME="projectname";
    private static final String COLUMN_TASKNAME="taskname";
    private static final String COLUMN_STARTNAME="startname";
    private static final String COLUMN_STARTTIME="starttime";
    private static final String COLUMN_STOPTIME="stoptime";
    ArrayList<String> projectNames = new ArrayList<String>();
    ArrayList<String> taskNames = new ArrayList<String>();
    ArrayList<String> startNames = new ArrayList<String>();
    ArrayList<Integer> startTimes = new ArrayList<Integer>();
    ArrayList<Integer> stopTimes = new ArrayList<Integer>();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PROJECTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PROJECTNAME + " TEXT," +
                COLUMN_TASKNAME + " TEXT," +
                COLUMN_STARTNAME + " TEXT," +
                COLUMN_STARTTIME + " INTEGER," +
                COLUMN_STOPTIME + " INTEGER" +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
        onCreate(db);
    }

    public void startProject(String projectname, String taskname, String timeString, int time){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROJECTNAME, projectname);
        values.put(COLUMN_TASKNAME, taskname);
        values.put(COLUMN_STARTNAME, timeString);
        values.put(COLUMN_STARTTIME, time);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PROJECTS, null, values);
        db.close();
    }

    public void stopProject(int time){
        ContentValues valuesUpdate = new ContentValues();
        valuesUpdate.put(COLUMN_STOPTIME, time);
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_PROJECTS, valuesUpdate, COLUMN_ID+" = (SELECT max("+COLUMN_ID+") FROM "+TABLE_PROJECTS+")", null);
        db.close();
    }

    public ArrayList<ArrayList> getProjects(){
        SQLiteDatabase db = getWritableDatabase();
        String [] columns = new String[]{ COLUMN_PROJECTNAME, COLUMN_TASKNAME, COLUMN_STARTNAME, COLUMN_STARTTIME, COLUMN_STOPTIME};
        Cursor c = db.query(TABLE_PROJECTS, columns, null, null, null, null, null);

        int iProjectName = c.getColumnIndex(COLUMN_PROJECTNAME);
        int iTaskName = c.getColumnIndex(COLUMN_TASKNAME);
        int iStartName = c.getColumnIndex(COLUMN_STARTNAME);
        int iStartTime = c.getColumnIndex(COLUMN_STARTTIME);
        int iStopTime = c.getColumnIndex(COLUMN_STOPTIME);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            projectNames.add(c.getString(iProjectName));
            taskNames.add(c.getString(iTaskName));
            startNames.add(c.getString(iStartName));
            startTimes.add(c.getInt(iStartTime));
            stopTimes.add(c.getInt(iStopTime));
        }

        c.close();
        db.close();
        ArrayList<ArrayList> arrayList = new ArrayList<ArrayList>();
        arrayList.add(projectNames);
        arrayList.add(taskNames);
        arrayList.add(startNames);
        arrayList.add(startTimes);
        arrayList.add(stopTimes);
        return arrayList;
    }

    public void deleteProjects(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PROJECTS, null, null);
        db.close();
    }

}
