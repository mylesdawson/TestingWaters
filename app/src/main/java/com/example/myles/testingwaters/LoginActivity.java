package com.example.myles.testingwaters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private String firstName = "myles";
    private String lastName = "dawson";
    private String studentId = "301263491";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void validateLogin(View view){

        EditText firstNameText = (EditText) findViewById(R.id.first_name);
        EditText lastNameText = (EditText) findViewById(R.id.last_name);
        EditText studentIdText = (EditText) findViewById(R.id.student_id);

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
            Toast.makeText(this, "Welcome" + lastNameInput + "! Now learning begins", Toast.LENGTH_LONG).show();
        }

    }

    public boolean checkLogin(String input, String checkAgainst){
        Log.d("", "input is:" + input + "checked against:" + checkAgainst);
        if(input.equalsIgnoreCase(checkAgainst)){
            return true;
        }
        return false;
    }
}
