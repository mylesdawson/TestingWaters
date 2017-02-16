package com.example.myles.testingwaters;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class TriangleActivity extends AppCompatActivity {

    Spinner spinner;
    Button beginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        spinner = (Spinner) findViewById(R.id.difficulty_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    // Easy selected
                    case 0:
                        //Toast.makeText(getApplicationContext(), "Difficulty is easy", Toast.LENGTH_SHORT).show();
                        break;
                    // Medium selected
                    case 1:
                        //Toast.makeText(getApplicationContext(), "Difficulty is medium", Toast.LENGTH_SHORT).show();
                        break;
                    // Hard selected
                    case 2:
                        //Toast.makeText(getApplicationContext(), "Difficulty is hard", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        beginBtn = (Button) findViewById(R.id.begin_btn);
        beginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginGame();
            }
        });
    }

    private void beginGame() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new TriangleGame(this));
    }


}
