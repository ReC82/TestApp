package com.bit.blmt.testapp.JIRA;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bit.blmt.testapp.FTP_cmd;
import com.bit.blmt.testapp.Indentity;
import com.bit.blmt.testapp.R;

import java.io.File;

public class JIRA_Activity extends AppCompatActivity {

    Button btnView;
    Button btnCreate;

    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BIT/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jira);

        btnCreate = (Button) findViewById(R.id.jira_create);
        btnView = (Button) findViewById(R.id.jira_view);
        File file = new File(path + "jiralist.bit");
        File fileUpdated = new File(path + "jiralist_updated.bit");

        if(!file.exists() && !fileUpdated.exists())
        {
            //Cannot Find Any File - Start Downloading
            Toast.makeText(JIRA_Activity.this, "No File Found - Try To Download File",Toast.LENGTH_LONG).show();

        }

        //Verify if the last update = actual file
        if(file.exists() && fileUpdated.exists())
        {
            if(file.length() != fileUpdated.length())
            {
                file.delete();
                fileUpdated.renameTo(file);
            }
        }


        if(file.exists() && file.length() != 0)
        {
            btnView.setEnabled(true);
            Toast.makeText(getApplicationContext(), "File Found !", Toast.LENGTH_LONG).show();
        }
        else
        {
            System.out.println("File " + file.getAbsolutePath().toString() + " Not Found !");
            FTP_cmd testFTP = new FTP_cmd();
            File filetoGet = new File("jiralist.bit");
            testFTP.setFileToGet(filetoGet);
            if(testFTP.getFileOnFtp())
            {
                btnView.setEnabled(false);
                System.out.println("Please try again in 5 minutes");
                System.out.println("File is maybe 0 k");
            }
            else
            {
                System.out.println("Issue to get the file from FTP (Maybe there's no file)");
            }

        }
    }

    public void btnJiraCreate(View view){
        startActivity(new Intent(JIRA_Activity.this, Pop_JiraCreation.class));

    }

    public void btnJiraView(View view){
        startActivity(new Intent(JIRA_Activity.this, JIRA_Activity_View.class));
    }

    public void btnJiraUpdate(View view){
        Indentity ThisUser = new Indentity();

        FTP_cmd testFTP = new FTP_cmd();
        testFTP.setFile("Command");
        testFTP.setFileCommand("getjiratickets;" + ThisUser.getId_name() + ";" + ThisUser.getId_name() +"");
        if(testFTP.putFileOnFtp())
        {
            Toast.makeText(JIRA_Activity.this, "Update Request Sent - Wait 5 Minutes",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(JIRA_Activity.this, "Update Request Not Sent",Toast.LENGTH_LONG).show();
        }

        finish();
    }

    public void btnJiraDelCache(View view){
        File file = new File(path + "jiralist.bit");
        boolean deleted = file.delete();
        if(deleted)
        {
            Toast.makeText(JIRA_Activity.this, "Cache Deleted",Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(JIRA_Activity.this, "Problem to delete Cache",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void btnJiraMorning(View view){

    }



}
