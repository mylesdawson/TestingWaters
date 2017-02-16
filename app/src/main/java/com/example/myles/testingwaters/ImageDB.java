package com.example.myles.testingwaters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ImageDB {

    // For logging
    private static final String TAG = "ImageDB";

    // DB Fields
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image_data";

    public static final int COL_ROWID = 0;
    public static final int COL_NAME= 1;
    public static final int COL_DATA = 2;

    public static final String[] ALL_KEYS = new String[]{KEY_NAME, KEY_IMAGE};

    // Database Name
    private static final String DATABASE_NAME = "image_db";
    private static final String DB_TABLE = "image_table";
    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + DB_TABLE + "(" +
            KEY_NAME + " TEXT," +
            KEY_IMAGE + " BLOB);";

    private static final String DATABASE_CREATE_SQL =
            "create table " + DB_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "
                    // + KEY_{...} + " {type} not null"
                    //	- Key is the column name you created above.
                    //	- {type} is one of: text, integer, real, blob
                    //		(http://www.sqlite.org/datatype3.html)
                    //  - "not null" means it is a required field (must be given a value).
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
                    + KEY_NAME + " text not null, "
                    + KEY_IMAGE + " Blob "

                    // Rest  of creation:
                    + ");";

    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    public ImageDB(Context context) {
        this.context = context;
        myDBHelper = new DatabaseHelper(context);
    }

    public ImageDB open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to the database.
    public long insertRow(String name, byte[] image) {
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_IMAGE, image);

        // Insert it into the database.
        return db.insert(DB_TABLE, null, initialValues);
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        Cursor c = db.query(true, DB_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DB_TABLE, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Get a specific row (by rowId)
    public Cursor getRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = db.query(true, DB_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    public boolean updateRow(long rowId, String name, byte[] image) {
        String where = KEY_ROWID + "=" + rowId;

        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, name);
        newValues.put(KEY_IMAGE, image);

        // Insert it into the database.
        return db.update(DB_TABLE, newValues, where, null) != 0;
    }

    //	Private Helper Class:

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(CREATE_TABLE_IMAGE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

            // Recreate new database:
            onCreate(_db);
        }
    }
}