package com.bit.blmt.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bit.blmt.testapp.JIRA.Pop_JiraCreation;

public class BACKUP_Activity extends AppCompatActivity {

    Button btnView;
    Button btnCreate;
    Button btnUpdate;

    public String address;


    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jira);

        btnCreate = (Button) findViewById(R.id.jira_create);

    }

    public void btnJiraCreate(View view){
        startActivity(new Intent(BACKUP_Activity.this, Pop_JiraCreation.class));

    }


    public void btnJiraUpdate(View view){
        Toast.makeText(BACKUP_Activity.this, "Update Request Sent",Toast.LENGTH_LONG).show();
    }

    public void btnJiraRefresh(View view){

    }





}
