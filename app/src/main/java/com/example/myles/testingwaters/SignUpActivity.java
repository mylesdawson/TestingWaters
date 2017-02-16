package com.example.myles.testingwaters;

import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    UserInfoDB myDb;
    Button registerBtn;
    EditText firstNameTxt;
    EditText lastNameTxt;
    EditText studentId;
    EditText password;
    TextView warningMsg;
    Button showDbBtn;
    Button clearDbBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        registerBtn = (Button) findViewById(R.id.register_btn);
        firstNameTxt = (EditText) findViewById(R.id.signup_first_name);
        lastNameTxt = (EditText) findViewById(R.id.signup_last_name);
        studentId = (EditText) findViewById(R.id.signup_student_id);
        password = (EditText) findViewById(R.id.signup_password);
        warningMsg = (TextView) findViewById(R.id.warning_message);

        setupInterface();
        openDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void openDB() {
        myDb = new UserInfoDB(this);
        myDb.open();
    }

    private void closeDB() {
        myDb.close();
    }

    public void setupInterface() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameTxt.getText().toString();
                String lastName = lastNameTxt.getText().toString();
                String id = studentId.getText().toString();
                String pass = password.getText().toString();

                UserInputTester inputTester = new UserInputTester(firstName, lastName, id, pass, warningMsg);
                if (inputTester.testInput()) {
                    Toast.makeText(getApplicationContext(), "Login info saved!", Toast.LENGTH_LONG).show();
                    inputTester.displayError("");
                    int parseId = Integer.parseInt(id);
                    addRecord(firstName, lastName, parseId, pass);
                }
            }
        });

    }


    public void addRecord(String firstName, String lastName, int id, String password) {
        Toast.makeText(this, "Signed up!", Toast.LENGTH_SHORT).show();
        long newId = myDb.insertRow(firstName, lastName, id, password);

        Cursor cursor = myDb.getRow(newId);
        //displayRecordSet(cursor);
    }

    /*public void clearAll() {
        displayText("Clicked clear all!");
        myDb.deleteAll();
    }*/

   /* public void displayRecords() {
        displayText("Clicked display record!");

        Cursor cursor = myDb.getAllRows();
        //displayRecordSet(cursor);
    }*/

    // Display an entire recordset to the screen.
    /*private void displayRecordSet(Cursor cursor) {
        String message = "";
        // populate the message from the cursor

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(UserInfoDB.COL_ROWID);
                String firstName = cursor.getString(UserInfoDB.COL_FIRSTNAME);
                String lastName = cursor.getString(UserInfoDB.COL_LASTNAME);
                int studentNumber = cursor.getInt(UserInfoDB.COL_STUDENTNUM);
                String password = cursor.getString(UserInfoDB.COL_PASSWORD);

                // Append data to the message:
                message += "id=" + id
                        + ", first name=" + firstName
                        + ", last name=" + lastName
                        + ", #=" + studentNumber
                        + ", password=" + password
                        + "\n";
            } while (cursor.moveToNext());
        }
        // Close the cursor to avoid a resource leak.
        cursor.close();
        displayText(message);
    }*/
}
