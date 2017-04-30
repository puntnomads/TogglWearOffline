package com.puntnomads.togglwearoffline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeleteActivity extends Activity {

    private Button projectButton;
    private Button taskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        projectButton = (Button) findViewById(R.id.project_button);
        taskButton = (Button) findViewById(R.id.task_button);
        projectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a task list and depending on selection, send string extra and from there go to keyboard.
                Intent intent = new Intent(DeleteActivity.this, ProjectsList.class);
                intent.putExtra("num", 1);
                startActivity(intent);
            }
        });
        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeleteActivity.this, ProjectsList.class);
                intent.putExtra("num", 2);
                startActivity(intent);
            }
        });
    }
}
