package com.bit.blmt.testapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.blmt.testapp.JIRA.JIRA_Activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import it.sauronsoftware.ftp4j.FTPClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public int requestID = 100;

    public EditText Params;
    public TextView Parameters;
    public Button UnlockUser;
    public Button RebootVM;
    public Button CustomCommand;
    public static String address;
    ArrayList<String> Auth_Macs = new ArrayList<String>();
    String Authorized_Mac[] = {"4C:66:41:FA:9A:0E", "02:00:00:44:55:66"};
    String Authorized_Users[] = {"blmt", "blmt_dev"};

    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Indentity CurrentUser = new Indentity();
        CurrentUser.ShowObject();
        if(CurrentUser.isAuthorized())
        {
            Toast.makeText(MainActivity.this, CurrentUser.getId_mac() + " : Authorized.",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(MainActivity.this, CurrentUser.getId_mac() + " : Not Authorized ! Closing App.",Toast.LENGTH_LONG).show();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    // this code will be executed after 2 seconds
                    finish();
                    System.exit(0);
                }
            }, 2000);
        }
//        if(Arrays.asList(Authorized_Mac).contains(address))
//        {
//            //Toast.makeText()
//            //Toast.makeText(MainActivity.this, address + " : Authorized.",Toast.LENGTH_LONG).show();
//        }
//        else
//        {
//            Toast.makeText(MainActivity.this, address + " : Not Authorized ! Closing App.",Toast.LENGTH_LONG).show();
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    // this code will be executed after 2 seconds
//                    finish();
//                    System.exit(0);
//                }
//            }, 2000);
//
//        }

        File dir = new File(path);

        dir.mkdir();

    }

    private void setRequestID(int value)
    {
        this.requestID = value;
    }

    private int getRequestID()
    {
        return this.requestID;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_fmu) {
            Intent nextAction = new Intent(this,FMU_Activity.class);
            startActivity(nextAction);
        } else if (id == R.id.nav_ad) {
            Intent nextAction = new Intent(this,AD_Activity.class);
            startActivity(nextAction);
        } else if (id == R.id.nav_vm) {
            Intent nextAction = new Intent(this, VM_Activity.class);
            startActivity(nextAction);
//        } else if (id == R.id.nav_ftp) {
//            Intent nextAction = new Intent(this,FTP_Activity.class);
//            startActivity(nextAction);
//        }
        }else if (id == R.id.nav_jira) {
            Intent nextAction = new Intent(this,JIRA_Activity.class);
            startActivity(nextAction);
        }else if (id == R.id.nav_backup) {
            Intent nextAction = new Intent(this,BACKUP_Activity.class);
            startActivity(nextAction);
        }
        else if (id == R.id.nav_tests) {
            Intent nextAction = new Intent(this,Test_Activity.class);
            startActivity(nextAction);
        }
//        else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                AsyncRunner SendFile = new AsyncRunner();
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

}
