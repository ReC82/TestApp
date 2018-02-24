package com.bit.blmt.testapp.JIRA;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bit.blmt.testapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class JIRA_Activity_View extends AppCompatActivity {

    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BIT/";
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jira_view);
        items = new ArrayList();
        read(path + "jiralist.bit");
        items.add("Default line");

        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView = (ListView) findViewById(R.id.lv_jiratickets);
        listView.setAdapter(itemsAdapter);
    }

    public void read(String fname) {
        String fpath = fname;
        File file = new File(fpath);
        try {
            Scanner scanner = new Scanner(new FileInputStream(file));
            System.out.println("Scanner : " + scanner.toString());
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] myList = line.split(";");
                try {
                    items.add(myList[0]);
                    items.add(myList[1]);
                }
                catch(Exception e)
                {
                    System.out.println("Error with Array : " + e.toString());
                }

            }
            scanner.close();
        }
        catch(IOException e)
        {
            System.out.println("------------> Error : " + e.toString());
        }
    }

}
