package com.example.myles.testingwaters;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity{

    // Sets login credentials
    private String firstName = "myles";
    private String lastName = "dawson";
    private String studentId = "301263491";

    private EditText firstNameText;
    private EditText lastNameText;
    private EditText studentIdText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firstNameText = (EditText) findViewById(R.id.first_name);
        lastNameText = (EditText) findViewById(R.id.last_name);
        studentIdText = (EditText) findViewById(R.id.student_id);

/*        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/
    }

    // Determines if login is success or failure
    public void validateLogin(View view){

        String firstNameInput = firstNameText.getText().toString();
        String lastNameInput = lastNameText.getText().toString();
        String studentIdInput = studentIdText.getText().toString();

        TextView textView = (TextView) findViewById(R.id.result_message);
        Boolean firstNameCheck = checkLogin(firstNameInput, firstName);
        Boolean lastNameCheck = checkLogin(lastNameInput, lastName);
        Boolean studentIdCheck = checkLogin(studentIdInput, studentId);

        if( !firstNameCheck || !lastNameCheck || !studentIdCheck){
            textView.setText(R.string.failure);
            textView.setTextColor(getResources().getColor(R.color.failure));
        } else{
            textView.setText(R.string.success);
            textView.setTextColor(getResources().getColor(R.color.success));
            Toast.makeText(this, "Welcome " + lastNameInput.toLowerCase() + "! Now learning begins", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, FlowActivity.class);
            startActivity(intent);
        }
    }

    // Returns true if input text matches the login credentials
    public boolean checkLogin(String input, String checkAgainst){
        if(input.equalsIgnoreCase(checkAgainst)){
            return true;
        }
        return false;
    }


}
