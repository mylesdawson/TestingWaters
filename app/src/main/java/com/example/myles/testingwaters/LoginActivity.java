package com.example.myles.testingwaters;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private UserInfoDB myDb;

    private EditText firstNameText;
    private EditText lastNameText;
    private EditText studentIdText;
    private EditText passwordText;
    private Button signInBtn;
    private TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firstNameText = (EditText) findViewById(R.id.login_first_name);
        lastNameText = (EditText) findViewById(R.id.login_last_name);
        studentIdText = (EditText) findViewById(R.id.login_student_id);
        passwordText = (EditText) findViewById(R.id.login_password);
        errorMsg = (TextView) findViewById(R.id.warning_message);

        openDb();

        signInBtn = (Button) findViewById(R.id.sign_in_btn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameText.getText().toString();
                String lastName = lastNameText.getText().toString();
                String id = studentIdText.getText().toString();
                String pass = passwordText.getText().toString();

                UserInputTester inputTester = new UserInputTester(firstName, lastName, id, pass, errorMsg);
                if (inputTester.testInput()) {
                    int parseId = Integer.parseInt(id);
                    inputTester.displayError("");
                    if (matchLoginCredentials(firstName, lastName, parseId, pass)) {
                        login();
                    }
                }
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void openDb() {
        myDb = new UserInfoDB(this);
        myDb.open();
    }

    private void closeDB() {
        myDb.close();
    }

    private boolean matchLoginCredentials(String firstNameInput, String lastNameInput, int idInput,
                                          String passInput) {
        Cursor cursor = myDb.getAllRows();
        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                String firstName = cursor.getString(UserInfoDB.COL_FIRSTNAME);
                String lastName = cursor.getString(UserInfoDB.COL_LASTNAME);
                int studentNumber = cursor.getInt(UserInfoDB.COL_STUDENTNUM);
                String password = cursor.getString(UserInfoDB.COL_PASSWORD);

                if (firstNameInput.equalsIgnoreCase(firstName) && lastNameInput.equalsIgnoreCase(lastName)
                        && idInput == studentNumber && passInput.equalsIgnoreCase(password)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        displayText("Login credentials incorrect");
        return false;
    }

    private void login() {
        Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), FlowActivity.class);
        startActivity(intent);
    }

    public void displayText(String message) {
        errorMsg.setVisibility(View.VISIBLE);
        errorMsg.setText(message);
        errorMsg.postDelayed(new Runnable() {
            @Override
            public void run() {
                errorMsg.setVisibility(View.INVISIBLE);
            }
        }, 5000);
    }
}
