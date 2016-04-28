package com.example.sebastianlerner.finalproject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import com.firebase.client.Firebase;

import java.io.IOException;
import java.nio.channels.Channels;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Collections.copy;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        new ServletPostAsyncTask().execute(new Pair<Context, String>(this, "Michael"));

/**
 Button carpoolButton = (Button) findViewById(R.id.carpool);
 carpoolButton.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View view){
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


    }

    public void carpoolClick(View v){
        System.out.println("HELLO!!!");
        Firebase myFirebaseRef = new Firebase("https://webmobile1295.firebaseio.com/");
        myFirebaseRef.child("message").setValue("Do you have data? You'll love Firebase.");
        Intent intent = new Intent(this, CarpoolActivity.class);
        startActivity(intent);
    }

    public void mapClick(View v){
        goToMaps();
    }

    public void goToMaps() {

        Intent intent = new Intent(this, MapsActivity.class);


        startActivity(intent);
    }


}
