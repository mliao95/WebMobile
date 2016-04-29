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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Michael on 4/28/2016.
 */
public class RequestsActivity extends AppCompatActivity {
    public final ArrayList<String> requests = new ArrayList<String>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_listview);
        final ListView listView = (ListView) findViewById(R.id.requestlist);
        Firebase.setAndroidContext(this);
        final Firebase myFirebaseRef = new Firebase("https://webmobile1295.firebaseio.com/");
        myFirebaseRef.child("Carpooling").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                HashMap h = new HashMap<String, String>();
                if (snapshot.getValue() instanceof HashMap) {
                    h = (HashMap) snapshot.getValue();
                }
                Collection c = h.values();
                Iterator itr = c.iterator();
                while (itr.hasNext()) {
                    String s = (String) itr.next();
                    if(s.contains("@")){
                        s = s.substring(0, s.indexOf('@'));
                    }
                    System.out.println("s: " + s);
                    String[] check = s.split(" ");
                    if (!(check[0].equals(MainActivity.username))) {
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
        final Intent intent = new Intent(this, CarpoolActivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                int iposition = position;
                System.out.println(position);
                final String value = (String) listView.getItemAtPosition(iposition);
                myFirebaseRef.child("Carpooling").addValueEventListener(new ValueEventListener() {
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
                        while (itr.hasNext()) {
                            s = (String) itr.next();
                            String[] check = value.split(" ");
                            System.out.println("check: " + check[0]);
                            System.out.println("s: "+ s);
                            if (s.equals(check[0])) {
                                hold = (String) h.get(s);
                                myFirebaseRef.child("Serviced/"+MainActivity.username+" "+s).setValue(hold);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError error) {
                    }

                });
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