
package com.example.saloni.student;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
   * References: https://youtu.be/kerqarY7_wQ
*/

public class GivefeedbackActivity extends AppCompatActivity {

    String roll_no = null;
    EditText feedbck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_givefeedback);
        Intent i = getIntent();
        roll_no = i.getStringExtra("Roll");

    }

    /*
      *Function Called on Clicking Submit Button
      * File gets Saved in  External Public Directory
     */

    public void submit(View view) {
        String external_state;
        external_state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(external_state)) {
            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File Dir = new File(root.getAbsolutePath() + "/StudentApp");
            if (!Dir.exists()) {
                Dir.mkdir();
            }
            String file_name = roll_no + "Feedback.txt";
            File feedback_file = new File(Dir, file_name);
            feedbck = (EditText) findViewById(R.id.feedbck);
            String feedback_msg = feedbck.getText().toString();
            try {
                FileOutputStream fos = new FileOutputStream(feedback_file);
                fos.write(feedback_msg.getBytes());
                feedbck.setText("");
                Toast.makeText(this, "Feedback submitted", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else
            Toast.makeText(this, "SD Card Not Found", Toast.LENGTH_SHORT).show();

    }
}
