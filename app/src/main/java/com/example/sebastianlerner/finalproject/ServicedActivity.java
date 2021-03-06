package com.example.sebastianlerner.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
public class ServicedActivity extends AppCompatActivity {
    public final ArrayList<String> requests = new ArrayList<String>();


    protected void onCreate(Bundle savedInstanceState) {
        final boolean stop = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_listview);
        final ListView listView = (ListView) findViewById(R.id.requestlist);
        Firebase.setAndroidContext(this);
        final Firebase myFirebaseRef = new Firebase("https://webmobile1295.firebaseio.com/");
        myFirebaseRef.child("Serviced").addValueEventListener(new ValueEventListener() {
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
                    System.out.println("s: " + s);
                    String[] check = s.split(" ");
                    if ((check[0].equals(MainActivity.username))||(check[1].equals(MainActivity.username))) {
                        updateRequests(s);
                    }
                    System.out.println("requests: " + requests);
                }
                updateListView();
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }

        });
        String[] values = requests.toArray(new String[requests.size()]);
        System.out.println("Values: " + requests);
        ArrayAdapter<String> requestadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, requests);
        listView.setAdapter(requestadapter);
        final Intent intent = new Intent(this, PhoneActivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                int iposition = position;
                System.out.println(position);
                final String value = (String) listView.getItemAtPosition(iposition);
                String[] sp = value.split(" ");

                if(sp[0].equals(MainActivity.username)){
                    intent.putExtra("key", sp[1]);
                    startActivity(intent);
                }
                /**
                myFirebaseRef.child("Serviced").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        HashMap h = new HashMap<String, String>();
                        if (snapshot.getValue() instanceof HashMap) {
                            h = (HashMap) snapshot.getValue();
                        }
                        Collection c = h.keySet();
                        Iterator itr = c.iterator();
                        String s = "";
                        String hold = "";
                        String key = "";
                        while (itr.hasNext()) {
                            s = (String) itr.next();
                            String[] check = value.split(" ");
                            String fix = h.get(s).toString().replace("_", " ");
                            System.out.println("FIX: " + fix);
                            System.out.println("value: " + value);
                            System.out.println("Check[0]: " + check[0]);

                            if (fix.equals(value) && check[0].equals(MainActivity.username)) {
                                key = s;
                                intent.putExtra("key", s);
                                startActivity(intent);
                            }

                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError error) {
                    }

                });
                 **/


            }
        });
    }

    public void updateListView()
    {
        ListView listView = (ListView) findViewById(R.id.requestlist);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, requests);

        listView.setAdapter(adapter);

    }


    public void updateRequests(String test) {
        requests.add(test.replace("_", " "));
    }
}
