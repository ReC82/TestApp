package com.bit.blmt.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Test_Activity extends AppCompatActivity {

    public Crypt MyCrypt = new Crypt();
    public TextView testText;
    public EditText et_TestText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        testText = (TextView) findViewById(R.id.tv_TestResult);
    }

    public void btnTest(View view){
        String toCrypt = et_TestText.getText().toString();
        testText.setText(et_TestText.getText().toString());

        FTP_cmd testFTP = new FTP_cmd();
        testFTP.setFile("test_crypt_string");
        testFTP.setFileCommand(toCrypt);
        if(testFTP.putFileOnFtp())
        {
            Toast.makeText(getApplicationContext(), "Your Command has been sent !", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Issue with command please ask an Administrator ! error : " + testFTP.getStatus(), Toast.LENGTH_LONG).show();
        }
    }

    public void emptyText(View view){
        et_TestText = (EditText) findViewById(R.id.et_TestText);
        et_TestText.setText("");
    }
}
