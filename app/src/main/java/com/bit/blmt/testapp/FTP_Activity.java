package com.bit.blmt.testapp;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import it.sauronsoftware.ftp4j.FTPClient;

import static com.bit.blmt.testapp.MainActivity.address;

public class FTP_Activity extends AppCompatActivity {
    public Button btnBBSsftp;
    public EditText et_sftplogin;
    public EditText et_responsables;


    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BIT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftp);
        et_sftplogin = (EditText) findViewById(R.id.et_sftplogin);
        et_responsables = (EditText) findViewById(R.id.et_responsables);
    }

    public void btnBBSsftp (View view){

        File file = new File(path + "/Command.bit");
        String resps = et_responsables.getText().toString();
        String login = et_sftplogin.getText().toString();
        String address = getMacAddr();
        String[] saveText = {"bbsSFTP;" + address + ";" + login + "|" + resps};

        //String[] saveText = String.valueOf(editText.getText()).split(System.getProperty("line separator"));

        Toast.makeText(getApplicationContext(), ((String) file.getAbsolutePath()), Toast.LENGTH_LONG).show();

        Save(file, saveText);

    }

    private class AsyncRunner extends AsyncTask<File, Integer, Void>
    {

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            //Toast.makeText(getApplicationContext(), "Début du traitement asynchrone", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            //super.onProgressUpdate(values);
            // Mise à jour de la ProgressBar

        }

        @Override
        protected Void doInBackground(File... files) {
            PutFile(files[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //Toast.makeText(getApplicationContext(), "Le traitement asynchrone est terminé", Toast.LENGTH_LONG).show();
        }
    }

    public void PutFile (File file)
    {
        FTPClient client = new FTPClient();

        try {

            client.connect("ftp.quizart.be",21);
            client.login("quizart.be", "d3h2dz9x");
            client.setType(FTPClient.TYPE_BINARY);
            client.changeDirectory("/BIT/");

            client.upload(file);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void Save(File file, String[] data)
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
            System.out.printf("!!!E" + fos.toString());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.printf("FILE NOT FOUND EXCEPTION");
        }
        try
        {
            try
            {
                for (int i = 0; i<data.length; i++)
                {
                    fos.write(data[i].getBytes());
                    if (i < data.length-1)
                    {
                        fos.write("\n".getBytes());
                    }
                }
                //RUN ASYNC TASK
                FTP_Activity.AsyncRunner SendFile = new FTP_Activity.AsyncRunner();
                SendFile.execute(file);
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
}
