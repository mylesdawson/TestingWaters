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
                    String pass = password.getText().toString();
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
        warningMsg.setVisibility(View.VISIBLE);
        warningMsg.setText(errorMsg);
        warningMsg.postDelayed(new Runnable(){
            @Override
            public void run()
            {
                warningMsg.setVisibility(View.INVISIBLE);
            }
        }, 5000);
    }

    // TODO: move this code to separate class!!
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

        // Test password: whitespace
        if(pass.contains(" ")){
            displayError("No whitespace allowed in password");
            return false;
        }

        // Test password: at least 1 uppercase letter, at least 1 number, len = 6
        int passwordLen = 6;
        if(pass.length() < passwordLen || pass.length() > passwordLen){
            displayError("Password must be exactly 6 characters long");
            return false;
        }

        if(!(pass.matches(".*\\d+.*"))) {
            displayError("Password does not contain a number");
            return false;
        }

        // set to false if password does not contain an uppercase letter
        boolean hasUppercase = !pass.equals(pass.toLowerCase());
        if(!hasUppercase){
            displayError("Password does not contain an uppercase letter");
            return false;
        }

        return true;
    }


    private void displayText(String message) {
        final TextView textView = (TextView) findViewById(R.id.display_database);
        textView.setText(message);

    }


    public void addRecord(String firstName, String lastName, int id, String password) {
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
                String password = cursor.getString(DBAdapter.COL_PASSWORD);

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
