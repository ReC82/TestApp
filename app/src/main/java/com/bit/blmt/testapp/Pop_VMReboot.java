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

public class Pop_VMReboot extends Activity {
    Button RebootVM;

    public EditText VMName;
    public EditText VMServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_vm_reboot);

        VMName = (EditText) findViewById(R.id.et_vmName);
        VMServer = (EditText) findViewById(R.id.et_VMServer);
    }

    public void RebootVM(View v)
    {
        String vmname = this.VMName.getText().toString();
        String server = this.VMServer.getText().toString();
        if(server.startsWith("HV"))
        {
            System.out.println("Server Name set to " + server);
        }
        else
        {
            System.out.println("No Server set.  Choose Default.");
            server = "default";
        }
        Indentity ThisUser = new Indentity();

        FTP_cmd testFTP = new FTP_cmd();
        testFTP.setFile("Command");
        testFTP.setFileCommand("RebootVm;" + ThisUser.getId_name() + ";" + vmname + "#" + server);
        if(testFTP.putFileOnFtp())
        {
            System.out.println("The command has been sent !");
            Toast.makeText(getApplicationContext(), "The command has been sent !", Toast.LENGTH_LONG).show();
            finish();
        }
        else
        {
            System.out.println("Something went wrong with the FTP transfer");
            Toast.makeText(getApplicationContext(), "Something went wrong with the FTP transfer", Toast.LENGTH_LONG).show();
        }
    }

}
