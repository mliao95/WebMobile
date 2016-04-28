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
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.IOException;
import java.nio.channels.Channels;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Collections.copy;


public class MainActivity extends AppCompatActivity{
    String userid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        Firebase myFirebaseRef = new Firebase("https://webmobile1295.firebaseio.com/");
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

    public void submitName(final View v){
        Firebase.setAndroidContext(this);
        final Firebase myFirebaseRef = new Firebase("https://webmobile1295.firebaseio.com/");
        final EditText etname = (EditText)findViewById(R.id.userid);
        final TextView notify = (TextView)findViewById(R.id.notify);
        final String name = etname.getText().toString();
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
                    ViewGroup parent = (ViewGroup) v.getParent();
                    parent.removeView(v);
                }
            }

            @Override public void onCancelled(FirebaseError error) { }

        });
    }


}
