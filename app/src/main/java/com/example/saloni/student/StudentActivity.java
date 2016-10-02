package com.example.saloni.student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class StudentActivity extends AppCompatActivity {


    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        myDb = new DatabaseHelper(this);

    }

    /*
       *Function Called When the User Clicks the Login Buttton
       * Checks if the Login details are Valid or not
       * Lets the Student login if the details are valid
     */
    public void Student_Login(View view) {
        TextView mRoll_number = (TextView) findViewById(R.id.user);
        TextView mPassword = (TextView) findViewById(R.id.pass);
        String uname = mRoll_number.getText().toString();
        String upass = mPassword.getText().toString();
        Cursor res = myDb.getRegisteredStudent(uname);
        if (res.getCount() == 0)
            Toast.makeText(StudentActivity.this, "Enter Valid details", Toast.LENGTH_SHORT).show();
        else {
            StringBuffer buffer = new StringBuffer();
            String sname = null;
            String spass = null;
            String rno = null;

            while (res.moveToNext()) {
                rno = res.getString(0);
                sname = res.getString(1);
                spass = res.getString(2);
                buffer.append("Roll_Number : " + rno + "\n");
                buffer.append("Name : " + sname + "\n");
                buffer.append("Password: " + spass + "\n\n");
            }

            if (uname.equals(rno) && upass.equals(spass)) {
                Intent i = new Intent(this, Feedback.class);
                i.putExtra("StudentRollNo", rno);
                i.putExtra("StudentName", sname);
                startActivity(i);
                mRoll_number.setText("");
                mPassword.setText("");

            } else
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show();


        }


    }


}
