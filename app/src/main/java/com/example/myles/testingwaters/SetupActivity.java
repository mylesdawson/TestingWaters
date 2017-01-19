package com.example.myles.testingwaters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
    }

    public void resourceLaunch(View view){
        Intent intent = new Intent(this, ResourceActivity.class);
        startActivity(intent);
    }
}
