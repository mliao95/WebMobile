package com.example.sebastianlerner.finalproject;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class CarpoolActivity extends AppCompatActivity {
    Firebase myFirebaseRef = new Firebase("https://webmobile1295.firebaseio.com/");
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public String values[];
    ArrayList<Double> distances = new ArrayList<Double>();
    final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carpool_main);
        ListView listView = (ListView) findViewById(R.id.list);
        TextView t1 = (TextView) findViewById(R.id.menutitle);
        t1.setText("Hello, " + MainActivity.username + "!");
        String[] values = new String[] { "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        };



        listView.setOnItemClickListener(new OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                test(view, position);
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

                    Firebase myFirebaseRef = new Firebase("https://webmobile1295.firebaseio.com/");
                    String mod = "";
                    String[] split = line.split(" ");
                    if(split[0].charAt(0)== '-'){
                        mod = "Driving/";
                        line = line.substring(1, line.length()-1);
                    }
                    if(split[0].charAt(0) == '+'){
                        mod = "Carpooling/";
                        line = line.substring(1, line.length()-1);
                    }
                    System.out.println("LINE IS HERE:    " + line);
                    myFirebaseRef.child(mod + MainActivity.username).setValue(line);
                    if (k < values.length)
                        values[k] = line;

                    k++;
                }
                Log.d("line", sb.toString());

            }
        }catch (FileNotFoundException e) {
            Log.d("not found", "not found");
        } catch (IOException e) {
            Log.d("io", "exception");
        }

        int[] tempArray = new int[distances.size()];

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void test(View v, int pos)
    {
        Intent intent = new Intent(this, RequestDetail.class);
        intent.putExtra("pos", pos);
        intent.putExtra("values", values[pos]);
        startActivity(intent);
    }

    public void requestClick(View v) {
        Intent intent = new Intent(this, CarpoolRequest.class);
       // intent.putExtra("position:", pos);
        startActivity(intent);

    }

    public void drivingClick(View v){
        Intent intent = new Intent(this, DrivingActivity.class );
        startActivity(intent);
    }

    public void viewRequestClick(View v){
        Intent intent = new Intent(this, RequestsActivity.class);
        startActivity(intent);
    }

    public void readClick(View v){
        myFirebaseRef.child("request1").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }

        });
    }





    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Carpool Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sebastianlerner.finalproject/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Carpool Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sebastianlerner.finalproject/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}


