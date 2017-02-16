package com.example.myles.testingwaters;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class EntryActivity extends AppCompatActivity {

    ImageDB imageDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        openDb();

        Button signupBtn = (Button)findViewById(R.id.signup_btn);
        Button loginBtn = (Button)findViewById(R.id.login_btn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        Cursor cursor = imageDB.getAllRows();
        // If there isn't data in database save images
        if( !(cursor.moveToFirst()) ) {
            saveImage("grand_canyon", R.drawable.grand_canyon);
            saveImage("kokanee_cabin", R.drawable.kokanee_glacier_cabin);
            saveImage("tulum_ruins", R.drawable.tulum_mayan_ruins);
            saveImage("yellowstone_park", R.drawable.yellowstone_national_park);
        }

        closeDb();
    }

    private void saveImage(String imageName, int drawableId) {
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                drawableId);
        byte[] iconByte = DbBitmapUtility.getBytes(icon);
        imageDB.insertRow(imageName, iconByte);
        Log.i("log", "saved:" + imageName +" into db");
    }

    private void openDb() {
        imageDB = new ImageDB(this);
        imageDB.open();
    }

    private void closeDb() {
        imageDB.close();
    }
}
