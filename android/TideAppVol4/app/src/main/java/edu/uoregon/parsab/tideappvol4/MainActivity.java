package edu.uoregon.parsab.tideappvol4;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends Activity implements OnItemSelectedListener {
    private static final String TAG = "MainActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    String locationSelection = "9411340";
    String locationLatitude = "";
    String locationLongitude = "";
    String locationName = "";

    //Setting up instances for geo-location
    private double myLatitude;
    private double myLongitude;
    private LocationManager lm;
    private LocationListener ll;


    //this hashMap contains the cities and their coordinates
    private HashMap<String, Double[]> cityCoordinates = new HashMap<>();
    private HashMap<String, String> cityNames = new HashMap<>();
    private HashMap<Double, Double[]> distances = new HashMap<>();

    //The start and finish date format that will be shown on the screen
    final String[] date_to_be_sent = new String[2];

    //The start and finish date format that will be used in the URL
    private String beginDate = "";
    private String endDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //populating the cityCoordinates HashMap
        cityCoordinates.put("9411340", new Double[]{34.4031, -119.6928});
        cityCoordinates.put("9442396", new Double[]{47.9133, -124.6370});
        cityCoordinates.put("9434098", new Double[]{44.0021, -124.1230});
        cityCoordinates.put("9433445", new Double[]{43.6750, -124.1920});
        cityCoordinates.put("9432780", new Double[]{43.3450, -124.3220});
        cityCoordinates.put("9435380", new Double[]{44.6254, -124.0449});
        cityCoordinates.put("9435308", new Double[]{44.5933, -123.9370});
        cityCoordinates.put("9437540", new Double[]{45.5545, -123.9189});
        cityCoordinates.put("9437381", new Double[]{45.4817, -123.9020});
        cityCoordinates.put("9431011", new Double[]{42.4216, -124.4187});
        cityCoordinates.put("9431647", new Double[]{42.7390, -124.4983});
        cityCoordinates.put("9440581", new Double[]{46.2811, -124.0461});
        cityCoordinates.put("9439011", new Double[]{46.2017, -123.9450});
        cityCoordinates.put("9439189", new Double[]{45.6967, -122.8680});

        //populating the cityNames HashMap
        cityNames.put("9411340", "Santa Barbara, CA" );
        cityNames.put("9442396", "La Push, WA");
        cityNames.put("9434098", "Florence, OR");
        cityNames.put("9433445", "Half Moon Bay, OR");
        cityNames.put("9432780", "Charleston, OR");
        cityNames.put("9435380", "South Beach, OR");
        cityNames.put("9435308", "Weiser Point, OR");
        cityNames.put("9437540", "Garibaldi, OR");
        cityNames.put("9437381", "Dick Point, OR");
        cityNames.put("9431011", "Gold Beach, OR");
        cityNames.put("9431647", "Port Orford, OR");
        cityNames.put("9440581", "Cape Disappointment, OR");
        cityNames.put("9439011", "Hammond, OR");
        cityNames.put("9439189", "Rocky Point, OR");
        //Setting up geolocation properties
        //Date Picker on Click
        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = month + "/" + dayOfMonth + "/" + year;
                if(month < 10){
                    if(dayOfMonth<10){
                        date_to_be_sent[0] = year + "/0" + month + "/0" + dayOfMonth;
                        beginDate = year + "0" +month+ "0" +dayOfMonth;
                        if(dayOfMonth<9){
                            endDate = year + "0" +month+ "0" +(dayOfMonth+1);
                            date_to_be_sent[1] = year + "/0" + month + "/0"+ (dayOfMonth+1);
                        }
                        else {
                            endDate = year + "0" + month + "" + (dayOfMonth + 1);
                            date_to_be_sent[1] =  year + "/0" + month + "/" + (dayOfMonth + 1);
                        }
                    }
                    else{
                        date_to_be_sent[0] = year + "/0" + month + "/" + dayOfMonth;
                        beginDate = year + "0" + month + "" + dayOfMonth;
                        if(dayOfMonth>=28){
                            endDate = year + "0" +month+ "" +dayOfMonth;
                            date_to_be_sent[1] = year + "/0" +month+ "/" +dayOfMonth;
                        }
                        else {
                            endDate = year + "0" + month + "" + (dayOfMonth + 1);
                            date_to_be_sent[1] = year + "/0" +month+ "/" +(dayOfMonth + 1);
                        }
                    }

                }

                else {
                    if (dayOfMonth < 10){
                        date_to_be_sent[0] = year + "/" + month + "/0" + dayOfMonth;
                        beginDate = year + "" + month + "0" + dayOfMonth;
                        if(dayOfMonth<9)
                            endDate = year + "" +month+ "0" +(dayOfMonth+1);
                        else
                            endDate = year + "" +month+ "" +(dayOfMonth+1);
                    }

                    else{
                        date_to_be_sent[0] = year + "/" + month + "/" + dayOfMonth;
                        beginDate =year+ ""  + month+ ""  + dayOfMonth ;
                        if(dayOfMonth>=28)
                            endDate = year + "" +month+ "" +dayOfMonth;
                        else
                            endDate = year + "" +month+ "" +(dayOfMonth+1);
                    }

                }
                mDisplayDate.setText(date);
            }
        };

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ll = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                myLatitude = location.getLatitude();
                myLongitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                }, 1);
                return;
            }
        }else{
            configure();
        }

        //Setting up date picker
        mDisplayDate = (TextView)findViewById(R.id.tvDate);

        mDisplayDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(
                                MainActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mDateSetListener,
                                year, month, day
                        );
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                }
        );

        //Setting up the Show Nearest Button
        Button ShowNearestButton = (Button)findViewById(R.id.show_nearest);
        ShowNearestButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowNearest(v);
                    }
                }
        );

        //Setting up the Show Tide button
        Button button = (Button)findViewById(R.id.show_tide);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowTide(v);
                    }
                }
        );

        // Set up location selection spinner
        Spinner locationSpinner = (Spinner)findViewById(R.id.locationSpinner);
        locationSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    configure();
                }
        }
    }

    private void configure(){
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 20, ll);
    }

    //Calculates the distance between user and the stations
    public void CalcDistances(){

        for(String key: cityCoordinates.keySet()){
            double lat = cityCoordinates.get(key)[0];
            double lon = cityCoordinates.get(key)[1];
            lat = myLatitude-lat;
            lon = myLongitude-lon;
            lat = Math.pow(lat, 2);
            lon = Math.pow(lon, 2);
            double distance = Math.sqrt(lat+lon);
            distances.put(distance, cityCoordinates.get(key));
        }
    }

    //Finds the closest station to the user
    public String closestStation(){

        String station = null;
        Double smallest = Double.MAX_VALUE;
        for(Double key: distances.keySet()){
            if(key<smallest)
                smallest = key;
        }
        Double db[] = distances.get(smallest);
        for(String key: cityCoordinates.keySet()){
            if(cityCoordinates.get(key).equals(db))
                station = key;
        }
        return station;
    }

    // the method used for Show Nearest button that will start a new activity with the tide information of the closest station.
    public void ShowNearest(View view) {
        Log.d("Coordinates: ", "Latitude: "+Double.toString(myLatitude)+", Longitude: "+Double.toString(myLongitude));
        CalcDistances();
        String StationID = closestStation();
        locationName = cityNames.get(StationID);
        if(beginDate == ""){
            Toast.makeText(MainActivity.this, "Please Pick A Date", Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(this, SecondActivity.class);

            //sending data to the next activity
            intent.putExtra("location", StationID);
            intent.putExtra("dateStart", date_to_be_sent[0]);
            intent.putExtra("dateFinish", date_to_be_sent[1]);
            intent.putExtra("beginDate", beginDate);
            intent.putExtra("endDate", endDate);
            intent.putExtra("latitude", locationLatitude);
            intent.putExtra("longitude", locationLongitude);
            intent.putExtra("city", locationName);
            startActivity(intent);
        }

    }

    public void ShowTide(View v){
        if(beginDate == ""){
            Toast.makeText(MainActivity.this, "Please Pick A Date", Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(this, SecondActivity.class);

            //sending data to the next activity
            intent.putExtra("location", locationSelection);
            intent.putExtra("dateStart", date_to_be_sent[0]);
            intent.putExtra("dateFinish", date_to_be_sent[1]);
            intent.putExtra("beginDate", beginDate);
            intent.putExtra("endDate", endDate);
            intent.putExtra("latitude", locationLatitude);
            intent.putExtra("longitude", locationLongitude);
            intent.putExtra("city", locationName);
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {

        //14 different stations mentioned in a string array in Values/String.xml
        switch (position){
            case 0:
                locationSelection = "9411340";
                locationLatitude = "+34.4031";
                locationLongitude = "-119.6928";
                locationName = cityNames.get(locationSelection);
                break;
            case 1:
                locationSelection = "9442396";
                locationLatitude = "+47.9133";
                locationLongitude = "-124.6370";
                locationName = cityNames.get(locationSelection);
                break;
            case 2:
                locationSelection = "9434098";
                locationLatitude = "+44.0021";
                locationLongitude = "-124.1230";
                locationName = cityNames.get(locationSelection);
                break;
            case 3:
                locationSelection = "9433445";
                locationLatitude = "+43.6750";
                locationLongitude = "-124.1920";
                locationName = cityNames.get(locationSelection);
                break;
            case 4:
                locationSelection = "9432780";
                locationLatitude = "+43.3450";
                locationLongitude = "-124.3220";
                locationName = cityNames.get(locationSelection);
                break;
            case 5:
                locationSelection = "9435380";
                locationLatitude = "+44.6254";
                locationLongitude = "-124.0449";
                locationName = cityNames.get(locationSelection);
                break;
            case 6:
                locationSelection = "9435308";
                locationLatitude = "+44.5933";
                locationLongitude = "-123.9370";
                locationName = cityNames.get(locationSelection);
                break;
            case 7:
                locationSelection = "9437540";
                locationLatitude = "+45.5545";
                locationLongitude = "-123.9189";
                locationName = cityNames.get(locationSelection);
                break;
            case 8:
                locationSelection = "9437381";
                locationLatitude = "+45.4817";
                locationLongitude = "-123.9020";
                locationName = cityNames.get(locationSelection);
                break;
            case 9:
                locationSelection = "9431011";
                locationLatitude = "+42.4216";
                locationLongitude = "-124.4187";
                locationName = cityNames.get(locationSelection);
                break;
            case 10:
                locationSelection = "9431647";
                locationLatitude = "+42.7390";
                locationLongitude = "-124.4983";
                locationName = cityNames.get(locationSelection);
                break;
            case 11:
                locationSelection = "9440581";
                locationLatitude = "+46.2811";
                locationLongitude = "-124.0461";
                locationName = cityNames.get(locationSelection);
                break;
            case 12:
                locationSelection = "9439011";
                locationLatitude = "+46.2017";
                locationLongitude = "-123.9450";
                locationName = cityNames.get(locationSelection);
                break;
            case 13:
                locationSelection = "9439189";
                locationLatitude = "+45.6967";
                locationLongitude = "-122.8680";
                locationName = cityNames.get(locationSelection);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}
