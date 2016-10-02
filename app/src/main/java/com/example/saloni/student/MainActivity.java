package com.example.saloni.student;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/*
*The Starting Activity
 */
public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
    }

    /*
    *Function called when Logging in as Instructor
    * Lets the Instructor login in to the application
    */
    public void InstructorLogin(View view) {
        Intent i = new Intent(this, InstructorActivity.class);
        startActivity(i);
    }

    /*
    *Function called when Logging in as a Student
    * Lets the Student login in to the application
    */
    public void StudentLogin(View view) {
        Intent i = new Intent(this, StudentActivity.class);
        startActivity(i);
    }

    /*
    *Function called when Registering as a Student
    * Lets the Student Register in to the application
    */
    public void RegisterLink(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

}
