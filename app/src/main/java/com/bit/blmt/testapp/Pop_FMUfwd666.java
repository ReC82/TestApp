package com.bit.blmt.testapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import it.sauronsoftware.ftp4j.FTPClient;

import static com.bit.blmt.testapp.MainActivity.address;

/**
 * Created by blmt on 29/09/2017.
 */

public class Pop_FMUfwd666 extends Activity{

    Button FMUfwd666;

    Spinner selectedUser;
    private String[] arrayUser;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BIT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_fmu_fwd666);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8) , (int)(height*.8));
        FMUfwd666 = (Button) findViewById(R.id.btn_FMUfwd666);
        this.arrayUser = new String[] {
                "blmt", "gda", "bjde", "gbr", "bpvz", "bfkh", "cancel"
        };
        selectedUser = (Spinner) findViewById(R.id.sp_666user);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayUser);
        selectedUser.setAdapter(adapter);
    }
    public void FMUfwd666(View v){
        String to = selectedUser.getSelectedItem().toString();
        Indentity ThisUser = new Indentity();

        FTP_cmd testFTP = new FTP_cmd();
        testFTP.setFile("Command");
        testFTP.setFileCommand("FMU666Forward;" + ThisUser.getId_name() + ";" + to);
        if(testFTP.putFileOnFtp())
        {
            Toast.makeText(getApplicationContext(), "Your FMU(666) Command has been sent !", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Issue with FMU(666) command please ask an Administrator ! error : " + testFTP.getStatus(), Toast.LENGTH_LONG).show();
        }

        finish();
    }

}
