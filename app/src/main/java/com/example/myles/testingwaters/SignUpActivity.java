package com.example.myles.testingwaters;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    DBAdapter myDb;
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

        registerBtn = (Button)findViewById(R.id.register_btn);
        firstNameTxt = (EditText)findViewById(R.id.signup_first_name);
        lastNameTxt = (EditText)findViewById(R.id.signup_last_name);
        studentId = (EditText)findViewById(R.id.signup_student_id);
        password = (EditText)findViewById(R.id.signup_password);
        warningMsg = (TextView)findViewById(R.id.warning_message);
        showDbBtn = (Button)findViewById(R.id.show_database_btn);
        clearDbBtn = (Button)findViewById(R.id.clear_database_btn);

        setupInterface();
        openDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }
    private void closeDB() {
        myDb.close();
    }

    public void setupInterface() {

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean testResult = testInput();
                if(testResult){
                    displayError("");
                    String firstName = firstNameTxt.getText().toString();
                    String lastName = lastNameTxt.getText().toString();
                    int id = Integer.parseInt(studentId.getText().toString());
                    int pass = Integer.parseInt(password.getText().toString());
                    addRecord(firstName, lastName, id, pass);
                }

            }
        });

        showDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayRecords();
            }
        });

        clearDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();
            }
        });
    }

    private void displayError(String errorMsg) {
        warningMsg.setText(errorMsg);
    }

    private boolean testInput() {
        // If any of the input fields are empty return false
        String firstName = firstNameTxt.getText().toString();
        String lastName = lastNameTxt.getText().toString();
        String id = studentId.getText().toString();
        String pass = password.getText().toString();

        if(firstName.equalsIgnoreCase("") || lastName.equalsIgnoreCase("") ||
                id.equalsIgnoreCase("") || pass.equalsIgnoreCase("") ) {
            displayError("One or more fields left blank");
            return false;
        }

        // Test password


        return true;
    }


    private void displayText(String message) {
        TextView textView = (TextView) findViewById(R.id.display_database);
        textView.setText(message);
    }



    public void addRecord(String firstName, String lastName, int id, int password) {
        displayText("Clicked add record!");

        long newId = myDb.insertRow(firstName, lastName, id, password);

        // Query for the record we just added.
        // Use the ID:
        Cursor cursor = myDb.getRow(newId);
        displayRecordSet(cursor);
    }

    public void clearAll() {
        displayText("Clicked clear all!");
        myDb.deleteAll();
    }

    public void displayRecords() {
        displayText("Clicked display record!");

        Cursor cursor = myDb.getAllRows();
        displayRecordSet(cursor);
    }

    // Display an entire recordset to the screen.
    private void displayRecordSet(Cursor cursor) {
        String message = "";
        // populate the message from the cursor

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String firstName = cursor.getString(DBAdapter.COL_FIRSTNAME);
                String lastName = cursor.getString(DBAdapter.COL_LASTNAME);
                int studentNumber = cursor.getInt(DBAdapter.COL_STUDENTNUM);
                int password = cursor.getInt(DBAdapter.COL_PASSWORD);

                // Append data to the message:
                message += "id=" + id
                        +", first name=" + firstName
                        +", last name=" + lastName
                        +", #=" + studentNumber
                        +", password=" + password
                        +"\n";
            } while(cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();

        displayText(message);
    }
}
