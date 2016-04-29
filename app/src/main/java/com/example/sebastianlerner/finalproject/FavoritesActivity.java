package com.example.sebastianlerner.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.Firebase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FavoritesActivity extends AppCompatActivity {

    public String[] values = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        try {
            FileInputStream fis = getApplicationContext().openFileInput("myfile");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line = "l";
            int k = 0;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);

                if (k < values.length) {
                   /*
                    int atLocation = line.indexOf("@");
                    String tempSub = line.substring(atLocation);
                    int commaLocation = tempSub.indexOf(",");
                    String longitude = tempSub.substring(0, commaLocation);
                    String latitude = tempSub.substring(commaLocation);

                    LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                    Location location = null;
                    requestPermissions(LOCATION_PERMS, 1337+3);
                    if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                    double longit, latit;
                    if(location != null) {
                        longit = location.getLongitude();
                        latit = location.getLatitude();

                    }
                    else {
                        longit = 0.0;
                        latit = 0.0;

                    }

                    double distance = Math.sqrt(Math.pow((longit-Double.parseDouble(longitude)), 2) - Math.pow((latit-Double.parseDouble(latitude)), 2));
                    distances.add(distance);
*/

                    //     Firebase myFirebaseRef = new Firebase("https://webmobile1295.firebaseio.com/");
                    //    String mod = "";
                    //   String[] split = line.split(" ");
                    //   if(split[0].charAt(0)== '-'){
                    //      mod = "Driving/";
                    //     line = line.substring(1, line.length()-1);
                    // }
                    // if(split[0].charAt(0) == '+'){
                    //    mod = "Carpooling/";
                    //   line = line.substring(1, line.length()-1);
                    // }
                    // System.out.println("LINE IS HERE:    " + line);
                    /// myFirebaseRef.child(mod + MainActivity.username).setValue(line);
                    if (k < values.length)
                        values[k] = line.substring(0, line.indexOf("@"));

                    k++;
                }
                Log.d("line", sb.toString());

            }
        } catch (FileNotFoundException e) {
            Log.d("not found", "not found");
        } catch (IOException e) {
            Log.d("io", "exception");
        }

        ListView listView = (ListView) findViewById(R.id.favorites);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                test(view, position);
            }


        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);
    }

    public void test(View v, int pos) {
        /**submit request here
         *
         */
        Firebase myFirebaseRef = new Firebase("https://webmobile1295.firebaseio.com/");
        String hold = "";
        String[] split = values[pos].split(" ");
        if(split[2].equals("driving")){
            hold = "Driving/";
        } else {
            hold = "Carpooling/";
        }
        myFirebaseRef.child(hold+MainActivity.username).setValue(values[pos]);
        Intent intent = new Intent(this, RequestDetail.class);
        intent.putExtra("pos", pos);
        intent.putExtra("values", values[pos]);

    }
}
