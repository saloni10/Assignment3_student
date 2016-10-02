package com.example.saloni.student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class InstructorActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static final String UserName = "name";
    public static final String PassWord = "pass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        /*
         *Saving the Instructor's Username and Password in Shared Preferences Object
        */
        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(UserName, "Psingh");
        editor.putString(PassWord, "mobile");
        editor.commit();


    }

    /*
       *Function Called When the User Clicks the Login Buttton
       * Checks if the Login details are Valid or not
       * Lets the Instructor login if the details are valid
     */
    public void login(View view) {
        TextView mUsernameTf = (TextView) findViewById(R.id.user);
        TextView mPasswordTf = (TextView) findViewById(R.id.pass);
        String uname = mUsernameTf.getText().toString();
        String upass = mPasswordTf.getText().toString();
        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String pname = (shared.getString(UserName, ""));
        String ppass = (shared.getString(PassWord, ""));
        if (uname.equals(pname) && upass.equals(ppass)) {
            Intent i = new Intent(this, DbActivity.class);
            i.putExtra("WEL", pname);
            startActivity(i);
            mUsernameTf.setText("");
            mPasswordTf.setText("");
        } else
            Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show();
    }


}
