package com.bit.blmt.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class VM_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm);

    }

    public void RebootVM(View view){
        startActivity(new Intent(VM_Activity.this, Pop_VMReboot.class));
    }
}
