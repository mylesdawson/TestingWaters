package com.example.myles.testingwaters;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        linkButtons();
    }

    public void linkButtons(){
        Button squareBtn = (Button)findViewById(R.id.square_btn);
        Button triangleBtn = (Button)findViewById(R.id.triangle_btn);

        squareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SquareActivity.class);
                startActivity(intent);
            }
        });

        triangleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TriangleActivity.class);
                startActivity(intent);
            }
        });
    }
}
