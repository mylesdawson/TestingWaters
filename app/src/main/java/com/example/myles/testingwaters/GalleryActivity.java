package com.example.myles.testingwaters;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryActivity extends AppCompatActivity {

    private ImageDB imageDb;

    TextView grandCanyon;
    TextView tulumRuins;
    TextView yellowstonePark;
    TextView kokaneeCabin;

    ImageView grandCanyonImg;
    ImageView tulumRuinsImg;
    ImageView yellowstoneImg;
    ImageView kokaneeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        grandCanyon = (TextView) findViewById(R.id.grand_canyon_txt);
        tulumRuins = (TextView) findViewById(R.id.tulum_ruins_txt);
        yellowstonePark = (TextView) findViewById(R.id.yellowstone_txt);
        kokaneeCabin = (TextView) findViewById(R.id.kokanee_cabin_txt);

        grandCanyonImg = (ImageView) findViewById(R.id.grand_canyon_img);
        tulumRuinsImg = (ImageView) findViewById(R.id.tulum_ruins_img);
        yellowstoneImg = (ImageView) findViewById(R.id.yellowstone_img);
        kokaneeImg = (ImageView) findViewById(R.id.kokanee_cabin_img);

        grandCanyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayImage("grand_canyon", grandCanyonImg);
            }
        });

        tulumRuins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayImage("tulum_ruins", tulumRuinsImg);
            }
        });

        yellowstonePark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayImage("yellowstone_park", yellowstoneImg);
            }
        });

        kokaneeCabin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayImage("kokanee_cabin", kokaneeImg);
            }
        });
    }

    // Retrieve image from database and set to corresponding imageView
    public void displayImage(String imageName, ImageView imageView) {
        openDb();
        // open db
        // try to match imageName to a database name
        // retrieve blob and remake into image
        // set image view as one of: drawable, alpha, bitmap
        Cursor cursor = imageDb.getAllRows();
        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                String name = cursor.getString(0);
                //Log.i("tag", "database image name is: " + name);
                byte[] image = cursor.getBlob(1);
                //Log.i("tag", "image data is: " + image);

                if( name.equals(imageName)){
                    Bitmap bitmap = DbBitmapUtility.getImage(image);
                    imageView.setImageBitmap(bitmap);
                }

            } while (cursor.moveToNext());
        }
        closeDB();
    }

    private void openDb() {
        imageDb = new ImageDB(this);
        imageDb.open();
    }

    private void closeDB() {
        imageDb.close();
    }

}
