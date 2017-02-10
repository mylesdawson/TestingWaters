package com.example.myles.testingwaters;


import android.view.View;
import android.widget.TextView;

public class UserInputTester {

    private final int PASSWORD_LENGTH = 6;

    private String mFirstName;
    private String mLastName;
    private String mStudentId;
    private String mPassword;
    private TextView mErrorView;

    public UserInputTester(String firstName, String lastName, String studentId, String password, TextView errorView) {
        mFirstName = firstName;
        mLastName = lastName;
        mStudentId = studentId;
        mPassword = password;
        mErrorView = errorView;
    }

    public void displayError(String errorMsg) {
        mErrorView.setVisibility(View.VISIBLE);
        mErrorView.setText(errorMsg);
        mErrorView.postDelayed(new Runnable(){
            @Override
            public void run()
            {
                mErrorView.setVisibility(View.INVISIBLE);
            }
        }, 5000);
    }


    // Returns true if any of the input field are empty
    private boolean inputFieldIsEmpty(){
        if( mFirstName.equalsIgnoreCase("") || mLastName.equalsIgnoreCase("") ||
                mStudentId.equalsIgnoreCase("") || mPassword.equalsIgnoreCase("") ) {
            return true;
        }
        return false;
    }

    // returns true if the password contains whitespace
    private boolean passwordHasWhitespace() {
        if(mPassword.contains(" ")){
            return true;
        }
        return false;
    }

    // Returns true if password matches PASSWORD_LENGTH
    private boolean passwordCorrectLength() {
        if( mPassword.length() < PASSWORD_LENGTH || mPassword.length() > PASSWORD_LENGTH ){
            return false;
        }
        return true;
    }

    // Returns true if password contains an uppercase letter
    private boolean passwordHasUppercase() {
        // set to false if password does not contain an uppercase letter
        boolean hasUppercase = !mPassword.equals(mPassword.toLowerCase());
        if(!hasUppercase){
            return false;
        }
        return true;
    }

    // Returns true if password contains a number
    private boolean passwordHasNumber() {
        if( !(mPassword.matches(".*\\d+.*")) ) {
            return false;
        }
        return true;
    }

    // Returns true if all test cases pass
    public boolean testInput() {
        if( inputFieldIsEmpty() ){
            displayError("One or more fields left blank");
            return false;
        } else if( passwordHasWhitespace() ){
            displayError("No whitespace allowed in password");
            return false;
        } else if( !passwordCorrectLength() ){
            displayError("Password must be exactly " + PASSWORD_LENGTH + " characters long");
            return false;
        } else if( !passwordHasUppercase() ){
            displayError("Password does not contain an uppercase letter");
            return false;
        } else if( !passwordHasNumber() ) {
            displayError("Password does not contain a number");
            return  false;
        }
        return true;
    }
}
