package com.nielsero.driverslicenseexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Helps with database operations.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "candidates.db";
    private static final int DB_VERSION = 2;
    private static final String TABLE_NAME = "candidates";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String BI = "bi";
    private static final String AGE = "age";
    private static final String CONTACT = "contact";
    private static final String EXAM_TYPE = "exam_type";
    private static final String LICENSE_CATEGORY = "license_category";

    // DDL statement to create the candidates table
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NAME + " TEXT NOT NULL, "
                    + BI + " TEXT UNIQUE NOT NULL, "
                    + AGE + " INTEGER NOT NULL, "
                    + CONTACT + " TEXT NOT NULL, "
                    + EXAM_TYPE + " TEXT NOT NULL, "
                    + LICENSE_CATEGORY + " TEXT NOT NULL"
                    + ");";

    /**
     * Creates a new DBHelper
     * @param context application context
     */
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(CREATE_TABLE);
    }

    /**
     * Gets a list of all candidate from table.
     * @return candidates list
     */
    public List<Candidate> getAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {ID, NAME, BI, AGE, CONTACT, EXAM_TYPE, LICENSE_CATEGORY};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String bi = cursor.getString(2);
                int age = cursor.getInt(3);
                String contact = cursor.getString(4);
                String examType = cursor.getString(5);
                String licenseCategory = cursor.getString(6);

                Candidate c = new Candidate(id, name, bi, age, contact, examType, licenseCategory);
                candidates.add(c);
            } while(cursor.moveToNext());
        }

        db.close();
        return candidates;
    }

    /**
     * Inserts a candidate in the table.
     * @param name candidate name
     * @param bi candidate bi
     * @param age candidate age
     * @param contact candidate contact
     * @param examType type of exam candidate is applying for (theory/practice)
     * @param licenseCategory category of the license (heavy/light)
     * @return row ID of the newly created candidate or -1 in case of error
     */
    public long insertCandidate(String name, String bi, int age, String contact, String examType, String licenseCategory) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, name);
        values.put(BI, bi);
        values.put(AGE, age);
        values.put(CONTACT, contact);
        values.put(EXAM_TYPE, examType);
        values.put(LICENSE_CATEGORY, licenseCategory);

        long insert = db.insert(TABLE_NAME, null, values);
        db.close();
        return insert;
    }

    /**
     * Deletes a candidate from table.
     * @param id candidate id
     */
    public void deleteCandidate(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = " + id, null);
        db.close();
    }
}
