package com.example.myles.testingwaters;

import android.content.Intent;
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
            Intent intent = new Intent(this, AboutActivity.class);
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

    // Saves user login info to internal storage (filename: saved_credentials)
    // This is not recommended in general
    public void saveLoginInfo(View view){

        String message = firstNameText.getText().toString() + "\n" + lastNameText.getText().toString() + "\n" + studentIdText.getText().toString();
        String fileName = "saved_credentials";
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            fileOutputStream.write(message.getBytes());
            fileOutputStream.close();
            hideText(firstNameText, lastNameText, studentIdText);
            Toast.makeText(getApplicationContext(), "Credentials saved successfully!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Some advanced wizardry going on here
    public void loadLoginInfo(View view){
        try {
            int currentLine = 0;
            int bufferLength;
            int bufferDifference = 0;
            String message;

            FileInputStream fileInputStream = openFileInput("saved_credentials");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while((message = bufferedReader.readLine()) != null){
                stringBuilder.append(message);
                bufferLength = stringBuilder.length();
                if(currentLine == 0) {
                    firstNameText.setText(stringBuilder.toString());
                    currentLine ++;
                    bufferDifference = bufferLength;
                } else if( currentLine == 1){
                    stringBuilder.delete(0,bufferDifference);
                    lastNameText.setText(stringBuilder.toString());
                    currentLine++;
                    bufferDifference = bufferLength -bufferDifference;
                } else {
                    stringBuilder.delete(0,bufferDifference);
                    studentIdText.setText(stringBuilder.toString());
                }
            }
            Toast.makeText(getApplicationContext(), "Credentials loaded successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hideText(EditText firstName, EditText lastName, EditText studentId){
        firstName.setText("");
        lastName.setText("");
        studentId.setText("");
    }

}
