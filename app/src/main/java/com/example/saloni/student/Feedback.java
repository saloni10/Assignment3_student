package com.example.saloni.student;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Feedback extends AppCompatActivity {


    DatabaseHelper myDb;
    String srno = null;
    String sname=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        myDb = new DatabaseHelper(this);
        Intent i = getIntent();
        srno = i.getStringExtra("StudentRollNo");
        sname = i.getStringExtra("StudentName");
        TextView displayName = (TextView) findViewById(R.id.displayNameTF);
        displayName.setText("Welcome " + sname);
    }


    /*
       *Function Called on Clickinng ViewMarks Button
       * Displays Marks And details Entered By Instructor
     */
    public void view_details(View view) {
        Cursor res = myDb.ViewRequiredData(srno);
        if (res.getCount() == 0)
            Toast.makeText(Feedback.this, "No marks to display", Toast.LENGTH_SHORT).show();
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
            ShowData("Your Marks", buffer.toString());
        }

    }

    public void ShowData(String title, String message)

    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    /*
       *Function Called on Clicking GiveFeedback Button
       * Takes to the FeedBackActivity
     */
    public void feedback(View view) {
        Intent i = new Intent(this, GivefeedbackActivity.class);
        i.putExtra("Roll", sname);
        startActivity(i);

    }

    /*
       *Function Called on Clicking AskQuery Button
       * Lets the student send Email to the instructor
     */
    public void askQuery(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Query: Student Name " + sname);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"psingh@iiitd.ac.in"});
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);

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
