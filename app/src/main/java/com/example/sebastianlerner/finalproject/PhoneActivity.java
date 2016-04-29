package com.example.sebastianlerner.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

/**
 * Created by Michael on 4/29/2016.
 */
public class PhoneActivity extends AppCompatActivity {
    String key = "";
    String rider = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_activity);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        TextView title = (TextView) findViewById(R.id.phone);
        rider = key;
        title.setText("Phone Number for " + rider);
    }


    public void submitPhone(View v){
        Firebase.setAndroidContext(this);
        Firebase myFirebaseRef = new Firebase("https://webmobile1295.firebaseio.com/");
        EditText phonenumber = (EditText) findViewById(R.id.phonenumber);
        String number = phonenumber.getText().toString();
        myFirebaseRef.child("Numbers/"+rider).setValue(MainActivity.username + ": " + number);
        Intent intent = new Intent(this, CarpoolActivity.class);
        startActivity(intent);
    }
}
