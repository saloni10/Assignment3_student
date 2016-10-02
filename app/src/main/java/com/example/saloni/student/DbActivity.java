package com.example.saloni.student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DbActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static final String UserName = "name";
    public static final String PassWord = "pass";
    DatabaseHelper myDb;
    EditText editRoll, editName, editCourse, editMarks;
    Button addbtn, viewbtn, updatebtn, deletebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        myDb = new DatabaseHelper(this);
        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String pname = (shared.getString(UserName, ""));

        TextView usernameTF = (TextView) findViewById(R.id.Username);
        usernameTF.setText("Welcome " + pname);
        editRoll = (EditText) findViewById(R.id.rollEt);
        editName = (EditText) findViewById(R.id.nameEt);
        editCourse = (EditText) findViewById(R.id.courseEt);
        editMarks = (EditText) findViewById(R.id.marksEt);
        addbtn = (Button) findViewById(R.id.addbtn);
        viewbtn = (Button) findViewById(R.id.viewbtn);
        updatebtn = (Button) findViewById(R.id.updatebtn);
        deletebtn = (Button) findViewById(R.id.delbtn);
        AddData(); //function call to Save Student data to database
        ViewData();//function call to View Student data from database
        UpdateData();//function call to Update Student data to database
        DeleteData();//function call to Delete Student data from database
    }

    /*
      *Function that gets called on clicking Add Button
      * Saves data to database and displays a toast
     */
    public void AddData() {
        addbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editRoll.getText().toString(), editName.getText().toString(), editCourse.getText().toString(), editMarks.getText().toString());
                        if (isInserted == true) {
                            Toast.makeText(DbActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                            Write_To_External_storage();

                           // Write_To_storage();
                        } else
                            Toast.makeText(DbActivity.this, "Data Already Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    /*
      *Function that writes private files into External Storage
      * Saves student data into External Private Files
     */
    public void Write_To_External_storage() {
        String external_state;
        external_state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(external_state)) {

            File Dir = new File(this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "StudentDetails");
            if (!Dir.exists()) {
                Dir.mkdir();
            }
            String file_name = editRoll.getText().toString() + "Details.txt";
            File detail_file = new File(Dir, file_name);


            try {
                FileOutputStream fos = new FileOutputStream(detail_file);
                fos.write("Name: ".getBytes());
                fos.write(editName.getText().toString().getBytes());
                fos.write("\nRoll Number".getBytes());
                fos.write(editRoll.getText().toString().getBytes());
                fos.write("\nCourse".getBytes());
                fos.write(editCourse.getText().toString().getBytes());
                fos.write("\nMarks".getBytes());
                fos.write(editMarks.getText().toString().getBytes());
                Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else
            Toast.makeText(this, "SD Card Not Found", Toast.LENGTH_SHORT).show();

    }


    public void Write_To_storage() {
        String external_state;
        external_state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(external_state)) {

            File Dir = new File(this.getFilesDir(), "StudentDetails");
            if (!Dir.exists()) {
                Dir.mkdir();
            }
            String file_name = editRoll.getText().toString() + "Details.txt";
            File detail_file = new File(Dir, file_name);


            try {
                FileOutputStream fos = new FileOutputStream(detail_file);
                fos.write("Name: ".getBytes());
                fos.write(editName.getText().toString().getBytes());
                fos.write("\nRoll Number".getBytes());
                fos.write(editRoll.getText().toString().getBytes());
                fos.write("\nCourse".getBytes());
                fos.write(editCourse.getText().toString().getBytes());
                fos.write("\nMarks".getBytes());
                fos.write(editMarks.getText().toString().getBytes());
                Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else
            Toast.makeText(this, "SD Card Not Found", Toast.LENGTH_SHORT).show();

    }

    /*
     *Function that gets called on clicking ViewData Button
     * Retreives the Data and saves it to a buffer
     * Calls ShowData function for displaying data saved in buffer
    */
    public void ViewData() {
        viewbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.ViewAllData();
                        if (res.getCount() == 0)
                            Toast.makeText(DbActivity.this, "No data to display", Toast.LENGTH_SHORT).show();
                        else {
                            StringBuffer buffer = new StringBuffer();
                            while (res.moveToNext()) {
                                String rno = res.getString(0);
                                String name = res.getString(1);
                                String course = res.getString(2);
                                String marks = res.getString(3);
                                buffer.append("Roll_Number : " + rno + "\n");
                                buffer.append("Name : " + name + "\n");
                                buffer.append("Course :" + course + "\n");
                                buffer.append("Marks: " + marks + "\n\n");
                            }
                            ShowData("Student Data", buffer.toString());
                        }

                    }
                }
        );
    }

    /*
      *Function to display the data retrieved in an Alert Dilaog Box
     */
    public void ShowData(String title, String message)

    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    /*
       *Function Called on Clicking update Button
      *Function to update Student Data
     */
    public void UpdateData() {
        updatebtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpated = myDb.UpdateData(editRoll.getText().toString(), editName.getText().toString(), editCourse.getText().toString(), editMarks.getText().toString());
                        if (isUpated == true)
                            Toast.makeText(DbActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(DbActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    /*
     *Function Called on Clicking Delete Button
     *Function to delete Student Data
    */
    public void DeleteData() {
        deletebtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer rowsDeleted = myDb.DeleteData(editRoll.getText().toString());
                        if (rowsDeleted > 0)
                            Toast.makeText(DbActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(DbActivity.this, "Enter valid Roll Number To delete", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    /*
     *Function called on Clicking Logout Button
     * Takes to the Main Activity
    */
    public void logout(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
