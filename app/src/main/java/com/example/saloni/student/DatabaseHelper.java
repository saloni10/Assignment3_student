

package com.example.saloni.student;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 References: https://youtu.be/cp2rL3sAFmI

 */

/**
 * Created by saloni on 30/9/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Studentt.db";
    public static final String TABLE_NAME = "Student_Tb";
    public static final String COL_1 = "Roll_Number";
    public static final String COl_2 = "Name";
    public static final String COL_3 = "Course";
    public static final String COL_4 = "Marks";
    public static final String TABLE2_NAME = "RegisteredStudents";
    public static final String COL2_2 = "Roll_Number";
    public static final String COl2_1 = "Name";
    public static final String COL2_3 = "Password";

    /*
       *Constructor of DatabaseHelper Class
       *Creates the Schema
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("Create Table " + TABLE_NAME + " (Roll_Number TEXT Primary Key, Name TEXT, Course TEXT, Marks TEXT)");
        sqLiteDatabase.execSQL("Create Table " + TABLE2_NAME + " (Roll_Number TEXT Primary Key, Name TEXT, Password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP Table If Exists " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP Table If Exists " + TABLE2_NAME);
        onCreate(sqLiteDatabase);
    }

    /*
      Function Called to Insert Data into Table Student_Tb
      Returns true if data is inserted successfully
     */
    public boolean insertData(String rno, String name, String course, String marks) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, rno);
        contentValues.put(COl_2, name);
        contentValues.put(COL_3, course);
        contentValues.put(COL_4, marks);
        long rslt = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (rslt == -1)
            return false;
        else
            return true;
    }

    /*
      Function Called to View All Data
      Returns all the rows
     */
    public Cursor ViewAllData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor resultt = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME, null);
        return resultt;
    }

    /*
     Function Called to View Specific Data
     Returns required rows
    */
    public Cursor ViewRequiredData(String roll) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor resultt = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME + " where Roll_Number= " + roll + ";", null);
        return resultt;
    }

    /*
     Function Called to Update data
     Returns true if Updated
    */
    public boolean UpdateData(String roll, String name, String course, String marks) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, roll);
        contentValues.put(COl_2, name);
        contentValues.put(COL_3, course);
        contentValues.put(COL_4, marks);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "Roll_Number = ?", new String[]{roll});
        return true;
    }

    /*
     Function Called to Delete data
     Returns number of rows deleted
    */
    public Integer DeleteData(String roll) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int res = sqLiteDatabase.delete(TABLE_NAME, "Roll_Number= ?", new String[]{roll});
        return res;
    }

    /*
      Function Called to Insert Data into Table RegisteredStudents
      Returns true if data is inserted successfully
     */
    public boolean RegisterStudent(String name, String roll, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COl2_1, name);
        contentValues.put(COL2_2, roll);
        contentValues.put(COL2_3, password);
        long rslt = sqLiteDatabase.insert(TABLE2_NAME, null, contentValues);
        if (rslt == -1)
            return false;
        else
            return true;
    }

    /*
    Function Called to View Specific Data
    Returns required rows
   */
    public Cursor getRegisteredStudent(String roll) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor resultt = sqLiteDatabase.rawQuery("Select * from " + TABLE2_NAME + " where Roll_Number= " + roll + ";", null);
        return resultt;
    }
}
