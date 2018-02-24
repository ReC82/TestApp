package com.bit.blmt.testapp.JIRA;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bit.blmt.testapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import it.sauronsoftware.ftp4j.FTPClient;

import static com.bit.blmt.testapp.MainActivity.address;

/**
 * Created by blmt on 29/09/2017.
 */

public class Pop_JiraCreation extends Activity {

    Button jiraCreation;

    EditText et_title;
    EditText et_asker;

    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BIT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_jira_creation);

        et_title = (EditText) findViewById(R.id.et_title);
        et_asker = (EditText) findViewById(R.id.et_asker);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8) , (int)(height*.8));
        jiraCreation = (Button) findViewById(R.id.btn_jiracreation);
    }

    public void btnJiraCreation(View v){
        //Send File to FTP
        File file = new File(path + "/Command.bit");

        String title = et_title.getText().toString();
        String asker = et_asker.getText().toString();

        //String[] saveText = String.valueOf(editText.getText()).split(System.getProperty("line separator"));
        String[] saveText = {"JiraCreation;" + address + ";" + title + "|" + asker};


        Toast.makeText(getApplicationContext(), ((String) file.getAbsolutePath()), Toast.LENGTH_LONG).show();

        Save(file, saveText);

        finish();
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
                Pop_JiraCreation.AsyncRunner SendFile = new Pop_JiraCreation.AsyncRunner();
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
}
