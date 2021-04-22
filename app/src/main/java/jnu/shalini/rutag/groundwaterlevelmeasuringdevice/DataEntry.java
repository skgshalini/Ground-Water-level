package jnu.shalini.rutag.groundwaterlevelmeasuringdevice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataEntry extends AppCompatActivity implements LocationListener {

    static String tvCountry,tvState,tvCity;
    static  LocationManager locationManager;
    ImageView speechButton;
    EditText speechText;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Button button;
    static String tvLatitude;
    static String tvLongitude;
    static String user_name;
    static String user_email;
    static String user_phoneNo;
    static String date;
    static String time;
    static String depth;
    static  Geocoder geocoder;
    static List<Address> addresses;
    static int c=0;
    private static final int RECOGNIZER_RESULT=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        speechButton = findViewById(R.id.imageView);
        speechText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        Intent intent = getIntent();
        user_name = intent.getStringExtra("name");
        user_phoneNo = intent.getStringExtra("phoneNo");
        user_email = intent.getStringExtra("email");
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Depths");
        grantPermission();
        checkLocationIsEnabledOrNot();
        speechButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to text");
                startActivityForResult(speechIntent, RECOGNIZER_RESULT);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=1;
                getLocation();
                depth = speechText.getText().toString();
                Date currentTime = Calendar.getInstance().getTime();
                date = DateFormat.getDateInstance().format(currentTime);
                time = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(new Date());
              if(geocoder!=null&&c==1){
                  Upload upload = new Upload(depth, date, time, tvLatitude, tvLongitude, tvCountry, tvState, tvCity, user_email, user_name, user_phoneNo);
                  reference.child(time).setValue(upload);
                  Toast.makeText(DataEntry.this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();
                  c=-1;
              }

            }

        });
    }
    private void getLocation() {
        try{
           // Toast.makeText(DataEntry.this, "Get location called", Toast.LENGTH_SHORT).show();
            locationManager =(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,500,5,(LocationListener)this);
        } catch(SecurityException e){
            e.printStackTrace();
        }
    }

    private void checkLocationIsEnabledOrNot() {
        LocationManager lm=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled=false;
        boolean networkEnabled=false;
        try{
            gpsEnabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch( Exception e){
            e.printStackTrace();
        }
        try {
            networkEnabled =lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!gpsEnabled&&!networkEnabled){
            new AlertDialog.Builder(DataEntry.this)
                    .setTitle("Enable GPS service").setCancelable(false).setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }).setNegativeButton("Cancel",null).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==RECOGNIZER_RESULT && resultCode==RESULT_OK){
            ArrayList<String> matches=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            speechText.setText(matches.get(0).toString());
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        //Toast.makeText(DataEntry.this, "Location changed called", Toast.LENGTH_SHORT).show();
        try {
              geocoder =new Geocoder(getApplicationContext(),Locale.getDefault());
              addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
              tvLatitude = Double.toString(location.getLatitude());
              tvLongitude = Double.toString(location.getLongitude());
              tvCountry=addresses.get(0).getCountryName();
              tvState=addresses.get(0).getAdminArea();
              tvCity=addresses.get(0).getLocality();
              if(c==1) {
                  Upload upload = new Upload(depth, date, time, tvLatitude, tvLongitude, tvCountry, tvState, tvCity, user_email, user_name, user_phoneNo);
                  reference.child(time).setValue(upload);
                  Toast.makeText(DataEntry.this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();
                  c=-1;
              }

          }
          catch (IOException e) {
              e.printStackTrace();
          }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
    private void grantPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)
                        !=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
    }
}