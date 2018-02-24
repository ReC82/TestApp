package com.bit.blmt.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by blmt on 25/10/2017.
 */

public class Pop_ADUnlock extends Activity {
    Button UnlockUser;
    EditText usertoUnlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_ad_unlock);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8) , (int)(height*.8));
        UnlockUser = (Button) findViewById(R.id.btnUnlockUser);
        usertoUnlock = (EditText) findViewById(R.id.et_VMServer);

    }
    public void UnlockUser(View v){

        Indentity ThisUser = new Indentity();

        FTP_cmd testFTP = new FTP_cmd();
        testFTP.setFile("Command");
        testFTP.setFileCommand("unlock;" + ThisUser.getId_name() + ";" + usertoUnlock.getText());
        if(testFTP.putFileOnFtp())
        {
            Toast.makeText(getApplicationContext(), "Your Unlock User Command has been sent !", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Issue with Unlock User command please ask an Administrator ! error : " + testFTP.getStatus(), Toast.LENGTH_LONG).show();
        }

        finish();
    }

}
