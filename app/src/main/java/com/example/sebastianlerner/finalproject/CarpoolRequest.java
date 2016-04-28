package com.example.sebastianlerner.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;



/**
 * Created by Michael on 4/4/2016.
 */
public class CarpoolRequest extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    private ArrayList<Request> requests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        EditText slocation = (EditText) findViewById(R.id.slocation);
    }

    public void photo(View v)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Image saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }
    public void submission(View v){


        EditText slocationE = (EditText) findViewById(R.id.slocation);
        EditText elocationE = (EditText) findViewById(R.id.elocation);
        EditText dateE = (EditText) findViewById(R.id.date);
        EditText ridersE = (EditText) findViewById(R.id.riders);
        EditText timeE = (EditText) findViewById(R.id.time);

        String slocation = slocationE.getText().toString();
        String elocation = elocationE.getText().toString();
        String date = dateE.getText().toString().replace("/", "");
        int riders = Integer.parseInt(ridersE.getText().toString());
        String timeS = timeE.getText().toString().replace(":", "");
        int time = Integer.parseInt(timeS);
        boolean drive = false;
        CheckBox checked = ((CheckBox) findViewById(R.id.driving));
        if (checked.isChecked()) {
            drive = true;
        }
        Request r = new Request(slocation, elocation, date, riders, drive, time);

        String input = r.toString()+"\n";
        System.out.println(input);
        BufferedWriter bw = null;
        Log.i("ffff", "test");

        String filename = "myfile";
        String string = "Hello world!";
        FileOutputStream outputStream;


        try{
            // FileOutputStream fileOutputStream = openFileOutput("storage.txt", Context.MODE_PRIVATE);
            outputStream = openFileOutput(filename, Context.MODE_APPEND);
            outputStream.write(input.getBytes());
            outputStream.close();
            // bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            // bw.write(input);
            for(int k = 0; k < fileList().length; k++) {
                Log.d("fam", fileList()[k]);
            }



        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }//finally{
        //  try{
        //   bw.close();
        // } catch (IOException e){
        //   e.printStackTrace();
        //}
        //}
        Intent intent = new Intent(this, CarpoolActivity.class);
        startActivity(intent);
    }




}


