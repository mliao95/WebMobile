package com.example.sebastianlerner.finalproject;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;







public class MainActivity extends AppCompatActivity{
    static String userid = "";

    final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION


    };
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestPermissions(LOCATION_PERMS, 1337 + 3);
        Log.d("request my permission", "request it");
        Firebase.setAndroidContext(this);


        try {
            FileInputStream fis = getApplicationContext().openFileInput("myfile");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line = "l";
            int k = 0;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            if(sb.length() > 0)
            {
                Intent intent = new Intent(this, CarpoolActivity.class);
              //  intent.putExtra("pos", pos);
              //  intent.putExtra("values", values[pos]);
                startActivity(intent);
            }

        }
        catch (FileNotFoundException e)
        {

        }
        catch (IOException e)
        {

        }
/*
        Bitmap bmp =  BitmapFactory.decodeResource(getResources(), R.mipmap.test_pic);//your image
        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
        bmp.recycle();
        byte[] byteArray = bYtE.toByteArray();
        String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);




       Firebase.setAndroidContext(this);
        Firebase myFirebaseRef = new Firebase("https://webmobile1295.firebaseio.com/");
        myFirebaseRef.child("message").setValue(imageFile);

*/
        //
        // new ServletPostAsyncTask().execute(new Pair<Context, String>(this, "Michael"));

        Firebase myFirebaseRef = new Firebase("https://webmobile1295.firebaseio.com/");
      //  new ServletPostAsyncTask().execute(new Pair<Context, String>(this, "Michael"));

/**
 Button carpoolButton = (Button) findViewById(R.id.carpool);
 carpoolButton.setOnClickListener(new View.OnClickListener(){
@Override public void onClick(View view){
Intent intent = new Intent(this, CarpoolActivity.class);
startActivity(intent);
};
}};
 **/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                goToMaps();

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void carpoolClick(View v) {
        System.out.println("HELLO!!!");
        Firebase myFirebaseRef = new Firebase("https://webmobile1295.firebaseio.com/");
        myFirebaseRef.child("message").setValue("Do you have data? You'll love Firebase.");
        requestPermissions(LOCATION_PERMS, 1337 + 3);

        Intent intent = new Intent(this, CarpoolActivity.class);
        startActivity(intent);
    }



    public void mapClick(View v) {
        goToMaps();
    }

    public void goToMaps() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void submitName(final View v){
        Firebase.setAndroidContext(this);
        final Firebase myFirebaseRef = new Firebase("https://webmobile1295.firebaseio.com/");
        final EditText etname = (EditText)findViewById(R.id.userid);
        final TextView notify = (TextView)findViewById(R.id.notify);
        final String name = etname.getText().toString();
        final Intent intent = new Intent(this, CarpoolActivity.class);
        myFirebaseRef.child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                HashMap h = new HashMap<String, String>();
                if(snapshot.getValue() instanceof HashMap){
                    h = (HashMap) snapshot.getValue();
                }
                boolean unique = true;
                Collection c = h.values();
                Iterator itr = c.iterator();
                while(itr.hasNext()){
                    System.out.println(name);
                    String i = (String) itr.next();
                    if(name.equals(i)){
                        unique = false;
                    }
                }
                if(unique == false){
                    notify.setText("The Name is not unique");
                    unique = true;
                } else {
                    notify.setText("");
                    int a = h.size();
                    String input = "Name/name";
                    input += Integer.toString(a);
                    myFirebaseRef.child(input).setValue(name);
                    userid = name;
                    startActivity(intent);
//                    ViewGroup parent = (ViewGroup) v.getParent();
//                    parent.removeView(v);
//                    parent = (ViewGroup) etname.getParent();
//                    parent.removeView(etname);
//                    notify.setText("Name has been recorded successfuly! Thank you, " + name + ".");
                }
            }

            @Override public void onCancelled(FirebaseError error) { }

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
                "Main Page", // TODO: Define a title for the content shown.
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
                "Main Page", // TODO: Define a title for the content shown.
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
