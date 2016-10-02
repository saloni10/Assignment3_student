package com.example.saloni.student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText name, roll_number, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb = new DatabaseHelper(this);

    }

    /*
       *Function called on Clicking Register Button
       * Checks if the student is already registered or not
       * if not, registers the student
     */
    public void register(View view) {
        name = (EditText) findViewById(R.id.name);
        roll_number = (EditText) findViewById(R.id.roll);
        password = (EditText) findViewById(R.id.pass);

        Cursor res = myDb.getRegisteredStudent(roll_number.getText().toString());
        if (res.getCount() == 0) {
            boolean isRegistered = myDb.RegisterStudent(name.getText().toString(), roll_number.getText().toString(), password.getText().toString());

            if (isRegistered == true) {
                Toast.makeText(RegisterActivity.this, "You have been Registered. Login To continue !", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } else
                Toast.makeText(RegisterActivity.this, "Not Registered!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(RegisterActivity.this, "You have registered already! Login to continue", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

    }

}
