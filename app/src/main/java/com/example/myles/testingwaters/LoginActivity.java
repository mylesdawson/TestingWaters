package com.example.myles.testingwaters;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity{

    // Sets login credentials
    private String firstName = "myles";
    private String lastName = "dawson";
    private String studentId = "301263491";

    private EditText firstNameText;
    private EditText lastNameText;
    private EditText studentIdText;
    private EditText passwordText;
    private Button signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firstNameText = (EditText) findViewById(R.id.login_first_name);
        lastNameText = (EditText) findViewById(R.id.login_last_name);
        studentIdText = (EditText) findViewById(R.id.login_student_id);
        passwordText = (EditText)findViewById(R.id.login_password);

        signInBtn = (Button)findViewById(R.id.sign_in_btn);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    // Determines if login is success or failure
    public void validateLogin(){

        String firstNameInput = firstNameText.getText().toString();
        String lastNameInput = lastNameText.getText().toString();
        String studentIdInput = studentIdText.getText().toString();

        TextView textView = (TextView) findViewById(R.id.warning_message);
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
