package com.example.sebastianlerner.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Michael on 4/29/2016.
 */
public class NumbersActivity extends AppCompatActivity {
    public final ArrayList<String> requests = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.numbers_activity);
        final ListView listView = (ListView) findViewById(R.id.numbers);
        Firebase.setAndroidContext(this);
        final Firebase myFirebaseRef = new Firebase("https://webmobile1295.firebaseio.com/");
        ArrayAdapter<String> requestadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, requests);
        listView.setAdapter(requestadapter);
        myFirebaseRef.child("Numbers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                HashMap h = new HashMap<String, String>();
                if (snapshot.getValue() instanceof HashMap) {
                    h = (HashMap) snapshot.getValue();
                }
                Collection c = h.keySet();
                Iterator itr = c.iterator();
                while (itr.hasNext()) {
                    String s = (String) itr.next();
                    if ((s.equals(MainActivity.username))) {
                        updateRequests((String) h.get(s));
                    }
                    System.out.println("requests: " + requests);
                }
                updateListView();
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }

        });
    }


    public void updateListView()
    {
        ListView listView = (ListView) findViewById(R.id.numbers);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, requests);

        listView.setAdapter(adapter);

    }


    public void updateRequests(String test) {
        requests.add(test.replace("_", " "));
    }





}
