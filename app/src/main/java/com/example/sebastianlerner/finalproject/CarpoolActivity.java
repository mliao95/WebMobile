package com.example.sebastianlerner.finalproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class CarpoolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.carpool_main);
        ListView listView = (ListView) findViewById(R.id.list);
        String[] values = new String[] { "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        };



        try {
            FileInputStream fis = getApplicationContext().openFileInput("myfile");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line="l";
            int k = 0;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                if(k < values.length)
                    values[k] = line;
                k++;
            }
            Log.d("line", sb.toString());
        }
        catch (FileNotFoundException e) {
            Log.d("not found", "not found");
        }
        catch (IOException e)
        {
            Log.d("io", "exception");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);
    }


    public void requestClick(View v){
        Intent intent = new Intent(this, CarpoolRequest.class);
        startActivity(intent);
    }


}


