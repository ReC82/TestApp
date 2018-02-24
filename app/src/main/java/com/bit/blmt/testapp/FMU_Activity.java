package com.bit.blmt.testapp;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class FMU_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmu);
    }

    public void btnFMUfwd666(View view){
        startActivity(new Intent(FMU_Activity.this, Pop_FMUfwd666.class));

    }

    public void btnFMUfwdStock(View view){
        startActivity(new Intent(FMU_Activity.this, Pop_FMUfwdStock.class));

    }

}
