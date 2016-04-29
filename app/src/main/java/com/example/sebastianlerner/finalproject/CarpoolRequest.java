package com.example.sebastianlerner.finalproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

import java.util.List;


/**
 * Created by Michael on 4/4/2016.
 */
public class CarpoolRequest extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private String imageFile;
    private Uri fileUri;
    private String LOCATION = "1337+3";
    private LocationManager mLocationManager;
    //private Location myLocation = getLastKnownLocation();

    private ArrayList<Request> requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        EditText slocation = (EditText) findViewById(R.id.slocation);
    }

    public void photo(View v) throws IOException {

        Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

//folder stuff
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
        imagesFolder.mkdirs();

        File image = new File(imagesFolder, "QR_" + timeStamp + ".png");
        Uri uriSavedImage = Uri.fromFile(image);

        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        imageIntent.putExtra("file", uriSavedImage);
        startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Image saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
                String mCurrentPhotoPath = getIntent().getStringExtra("file");

                Log.d("mCurrentPhotoPath", mCurrentPhotoPath);
                /*
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File f = new File(mCurrentPhotoPath);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                */



            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }

    public void submission(View v) {


        EditText slocationE = (EditText) findViewById(R.id.slocation);
        EditText elocationE = (EditText) findViewById(R.id.elocation);
        // EditText dateE = (EditText) findViewById(R.id.date);
        EditText ridersE = (EditText) findViewById(R.id.riders);
        EditText timeE = (EditText) findViewById(R.id.time);

        if (!timeE.getText().toString().equals("") && !ridersE.getText().toString().equals("") && !slocationE.equals("") && !elocationE.equals("")) {

            String slocation = slocationE.getText().toString();
            String elocation = elocationE.getText().toString();
            // String date = dateE.getText().toString().replace("/", "");
            int riders = Integer.parseInt(ridersE.getText().toString());
            String timeS = "";
            int time = 0;
            if(timeE.getText().toString().contains(":")) {
                 timeS = timeE.getText().toString().replace(":", "");
                 time = Integer.parseInt(timeS);
            }
            else
            {
                timeS = timeE.getText().toString();
                time = Integer.parseInt(timeS.toString());
            }

            boolean drive = false;
            CheckBox checked = ((CheckBox) findViewById(R.id.driving));
            if (checked.isChecked()) {
                drive = true;
            }
            Request r = new Request(slocation.replace(" ", "_"), elocation.replace(" ", "_"), riders, drive, time);

            //LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

            Location location = null;
            requestPermissions(LOCATION_PERMS, 1337 + 3);


            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            double longitude, latitude;
            if (location != null) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            } else {
                longitude = 38.031674 + (Math.random() * 000001);
                latitude = -78.510939 + (Math.random() * .000001);
            }
            String input = r.toString() + "@" + longitude + "," + latitude + "\n";
            String line = r.toString() + "@" + longitude + "," + latitude;
            Firebase myFirebaseRef = new Firebase("https://webmobile1295.firebaseio.com/");
            String mod = "";
            String[] split = line.split(" ");
            if (split[0].charAt(0) == '-') {
                mod = "Driving/";
                line = line.substring(1, line.length() - 1);
                line = line.trim();
            }
            if (split[0].charAt(0) == '+') {
                mod = "Carpooling/";
                line = line.substring(1, line.length() - 1);
                line = line.trim();
            }
            myFirebaseRef.child(mod + MainActivity.username).setValue(line);
            System.out.println(input);
            BufferedWriter bw = null;
            Log.i("ffff", "test");

            String filename = "myfile";
            String filename2 = "name";
            String string = "Hello world!";
            FileOutputStream outputStream;
            FileOutputStream outputStream2;



            try {
                // FileOutputStream fileOutputStream = openFileOutput("storage.txt", Context.MODE_PRIVATE);
                outputStream = openFileOutput(filename, Context.MODE_APPEND);
                outputStream2 = openFileOutput(filename2, Context.MODE_APPEND);
                outputStream.write(input.getBytes());
                outputStream2.write(MainActivity.username.getBytes());
                outputStream.close();
                outputStream2.close();

                // bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                // bw.write(input);
                for (int k = 0; k < fileList().length; k++) {
                    Log.d("fam", fileList()[k]);
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }//finally{
            //  try{
            //   bw.close();
            // } catch (IOException e){
            //   e.printStackTrace();
            //}
            //}
            if (timeS != null || riders != 0 || !slocation.equals("") || !elocation.equals("")) {
                Intent intent = new Intent(this, CarpoolActivity.class);

                startActivity(intent);
            }
        }
    }

    private Location getLastKnownLocation() {


        Location bestLocation = null;
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
            List<String> providers = mLocationManager.getProviders(true);
            for (String provider : providers) {
                Location l = mLocationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }
            }
        }

            return bestLocation;
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

//    private void galleryAddPic() {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(mCurrentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        this.sendBroadcast(mediaScanIntent);
//    }




}


