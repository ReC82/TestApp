package com.bit.blmt.testapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bit.blmt.testapp.JIRA.JIRA_Activity;
import com.bit.blmt.testapp.JIRA.JIRA_Activity_View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import it.sauronsoftware.ftp4j.FTPClient;

/**
 * Created by blmt on 13/10/2017.
 */

public class FTP_cmd extends AppCompatActivity{

    private File fileToSend;
    private File fileToGet;
    private String fileContent;
    private String status = "notReady";
    private static String serverHost;
    private static String serverPass;
    private static int serverPort;
    private static String serverLogin;
    public static final int ID_NOTIFICATION = 1988;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BIT/";

    public Crypt myCommand = new Crypt();
    public FTPClient client = new FTPClient();

    public FTP_cmd() {
        serverHost = "ftp.quizart.be";
        serverPass = "d3h2dz9x";
        serverPort = 21;
        serverLogin = "quizart.be";
        fileToGet = null;
    }

    public void setFile(String Filename) {
        File file = new File(path + Filename + ".bit");
        fileToSend = file;
    }

    public File getFile() {
        return this.fileToSend;
    }

    public File getFiletoGet() {
        return this.fileToGet;
    }

    public boolean putFileOnFtp() {

        try {
            AsyncRunner SendFile = new AsyncRunner();
            SendFile.setDirection("UP");
            SendFile.execute(this.getFiletoGet());
            this.setStatus("isOk");
            return true;
        }
        catch (Exception e)
        {
            this.setStatus(e.toString());
            return false;
        }
    }

    public boolean getFileOnFtp() {

        try {
            AsyncRunner GetFile = new AsyncRunner();
            GetFile.setDirection("DOWN");
            GetFile.execute(this.getFile());
            this.setStatus("isOk");
            return true;
        }
        catch (Exception e)
        {
            this.setStatus(e.toString());
            return false;
        }

    }

    public void PutFile() {
        try {
            client.connect(this.serverHost, this.serverPort);
            client.login(this.serverLogin, this.serverPass);
            client.setType(FTPClient.TYPE_BINARY);
            client.changeDirectory("/BIT/");
            client.upload(this.getFile());

        } catch (Exception e) {
            e.printStackTrace();
            try {
                client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void GetFile() {
        if(this.fileToGet != null) {
            System.out.println("OK : a filename hase been set");
            try {
                client.connect(this.serverHost, this.serverPort);
                client.login(this.serverLogin, this.serverPass);
                client.setType(FTPClient.TYPE_BINARY);
                client.changeDirectory("/BIT/");
                client.download(this.fileToGet.toString(), new java.io.File(path + this.fileToGet.toString()));
                client.deleteFile(this.fileToGet.toString());
                System.out.println("The file should be on the phone ! Try again");


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Getting an Exception while trying to get the file !");
                try {
                    client.disconnect(true);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        else
        {
            System.out.println("No Filename set to get the file !");
        }
    }


    public void setFileCommand(String command)
    {
        Crypt cryptedCommand = new Crypt();
        cryptedCommand.setKeyString("cryptMe");
        command = cryptedCommand.CaesarCipherString(command);

        BufferedWriter writer = null;
        try {
            //create a temporary file
            // This will output the full path where the file will be written to...
            writer = new BufferedWriter(new FileWriter(this.getFile()));
            writer.write(command);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }

    }

    public File getFileToGet() {
        return fileToGet;
    }

    public void setFileToGet(File fileToGet) {
        System.out.println("File to get set to : " + fileToGet.getName());
        this.fileToGet = fileToGet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static String getServerHost() {
        return serverHost;
    }

    public static void setServerHost(String serverHost) {
        FTP_cmd.serverHost = serverHost;
    }

    public static String getServerPass() {
        return serverPass;
    }

    public static void setServerPass(String serverPass) {
        FTP_cmd.serverPass = serverPass;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static void setServerPort(int serverPort) {
        FTP_cmd.serverPort = serverPort;
    }

    public static String getServerLogin() {
        return serverLogin;
    }

    public static void setServerLogin(String serverLogin) {
        FTP_cmd.serverLogin = serverLogin;
    }

    private class AsyncRunner extends AsyncTask<File, Integer, Void>
    {
        public String direction = "UP";

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

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
            if(this.direction == "UP") {
                PutFile();
            }
            else if(this.direction == "DOWN")
            {
                GetFile();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //Toast.makeText(getApplicationContext(), "Le traitement asynchrone est terminé", Toast.LENGTH_LONG).show();
        }
    }
}


