package com.home.service.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.home.service.app.Prevalent.Prevalent;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    String RequestedWorker;
    EditText message;
    Button Send, ShareLocation;

    boolean locationShared = false;


    RadioGroup radioGroup;
    RadioButton radioButton;
    DatabaseReference mref;
    private ProgressDialog LoadingBar;

    public static final int RClP = 100;
    String currentLatitude;
    String currenLongitude;
    List<Address> address;
    String currentAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        RequestedWorker=getIntent().getStringExtra("RequestedWorker");

        Send=(Button)findViewById(R.id.send);
        ShareLocation = findViewById(R.id.sharelocation);
        message=(EditText)findViewById(R.id.sms);

        LoadingBar=new ProgressDialog(this);
        mref= FirebaseDatabase.getInstance().getReference();

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);


        ShareLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationShared = true;

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MessageActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RClP);
                } else {
                    getCurentLocation();
                }
            }
        });


        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if ( dataSnapshot.child("Users").child(Prevalent.currentOnlineUser.getPhone()).child("Address").getValue().equals(""))
                {

                }
                else
                {
                    final AlertDialog.Builder builder=new AlertDialog.Builder(MessageActivity.this);
                    builder.setTitle("WUW Assistant!");
                    builder.setMessage("Do You Want To Use Address that you use Last Time");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            mref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    String myadd=dataSnapshot.child("Users").child(Prevalent.currentOnlineUser.getPhone()).child("Address").getValue().toString();
                                    message.setText(myadd);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(TextUtils.isEmpty(message.getText().toString()))
                {
                    Toast.makeText(MessageActivity.this, "Please provide your Address..", Toast.LENGTH_LONG).show();
                }
                else if (!locationShared){
                    Toast.makeText(MessageActivity.this, "Please turn on location first..", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(Prevalent.currentOnlineUser.getPhone().equals("+911111111111"))
                    {
                        Intent i=new Intent(MessageActivity.this,LoginActivity.class);
                        startActivity(i);
                        Toast.makeText(MessageActivity.this, "Login First..", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        int radioId=radioGroup.getCheckedRadioButtonId();
                        radioButton=findViewById(radioId);


                        LoadingBar.setTitle("Please Wait..");
                        LoadingBar.setMessage("Sending message..");
                        LoadingBar.show();

                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                        String dateToStr = format.format(today);
                        //System.out.println(dateToStr);

                        String SMSmessage=message.getText().toString();
                        String send="My name is "+ Prevalent.currentOnlineUser.getName()+" i need a "+RequestedWorker
                                +" at address "+SMSmessage+" and i complete payment by "+radioButton.getText().toString()
                                +" mode and my phone number is "+Prevalent.currentOnlineUser.getPhone()+" :"+currentLatitude+":"+currenLongitude;

                        Map<String,Object> map=new HashMap<String,Object>();
                        map.put(Prevalent.currentOnlineUser.getPhone()+System.currentTimeMillis(),dateToStr+System.currentTimeMillis()+"\n"+send);

                        mref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                dataSnapshot.getRef().child("Users").child(Prevalent.currentOnlineUser.getPhone()).child("Address").setValue(message.getText().toString().trim());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        mref.child("Services").updateChildren(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(MessageActivity.this, "Message Send Successfully..", Toast.LENGTH_SHORT).show();
                                            LoadingBar.dismiss();
                                            Intent i =new Intent(MessageActivity.this,HomeActivity.class);
                                            startActivity(i);

                                        }
                                        else
                                        {
                                            LoadingBar.dismiss();
                                            Toast.makeText(MessageActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                    }

                }

            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RClP && grantResults.length > 0) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getCurentLocation();

            } else {

            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(MessageActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(MessageActivity.this).removeLocationUpdates(this);
                if (locationResult != null && locationResult.getLocations().size() > 0) {
                    int latestLocationIndex = locationResult.getLocations().size() - 1;
                    double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    Geocoder geocoder = new Geocoder(MessageActivity.this, Locale.getDefault());


                    Toast.makeText(MessageActivity.this, "Location Enabled Latitude : "+latitude+" longitude "+longitude, Toast.LENGTH_SHORT).show();

                    Location location = new Location("providerNA");
                    location.setLatitude(latitude);
                    location.setLongitude(longitude);
                    //FetchAddressFromLatLong(location);

                    currentLatitude = ""+latitude;
                    currenLongitude = ""+longitude;

                    try {
                        address = geocoder.getFromLocation(latitude, longitude, 1);
                        setUpdata(address);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, Looper.getMainLooper());

    }
    private void setUpdata(List<Address> addresses) {
        String add = addresses.get(0).getAddressLine(0);
        currentAddress = add;

    }
}
