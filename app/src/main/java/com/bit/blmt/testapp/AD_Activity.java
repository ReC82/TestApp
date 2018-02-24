package com.bit.blmt.testapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import it.sauronsoftware.ftp4j.FTPClient;

import static android.R.attr.path;
import static com.bit.blmt.testapp.MainActivity.getMacAddr;


public class AD_Activity extends AppCompatActivity {

    public Button UnlockUser;
    public Button ResetPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        //Initialize Buttons
        UnlockUser = (Button) findViewById(R.id.btnUnlockUser);
        ResetPwd = (Button) findViewById(R.id.btn_ResetPwd);
    }

    public void btnUnlockUser (View view){

        startActivity(new Intent(AD_Activity.this, Pop_ADUnlock.class));
//        FTP_cmd myRequest = new FTP_cmd();
//        myRequest.setFile(file);
//        myRequest.putFileOnFtp();

        //String[] saveText = String.valueOf(editText.getText()).split(System.getProperty("line separator"));
        //String[] saveText = String.valueOf("unlock;" + address + ";" + Params.getText()).split(System.getProperty("line.separator"));

        //Toast.makeText(getApplicationContext(), ((String) file.getAbsolutePath()), Toast.LENGTH_LONG).show();

        //Save(file, saveText);

    }

}
