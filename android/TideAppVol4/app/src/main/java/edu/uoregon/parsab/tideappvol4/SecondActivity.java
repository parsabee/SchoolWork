package edu.uoregon.parsab.tideappvol4;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SecondActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Dal dal = new Dal(this);
    Cursor cursor = null;
    private String location = "9411340";
    SimpleCursorAdapter adapter = null;
    private String prediction = null;
    private String dateStart = null;
    private String dateFinish = null;
    private String beginDate;
    private String endDate;
    private String Latitude;
    private String Longitude;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //receiving data from previous activity
        location = getIntent().getExtras().getString("location");
        dateStart = getIntent().getExtras().getString("dateStart");
        dateFinish = getIntent().getExtras().getString("dateFinish");
        beginDate = getIntent().getExtras().getString("beginDate");
        endDate = getIntent().getExtras().getString("endDate");
        Latitude = getIntent().getExtras().getString("latitude");
        Longitude = getIntent().getExtras().getString("longitude");
        city = getIntent().getExtras().getString("city");

        // Initialize the database
        getTideForecast(location, beginDate,endDate, Latitude, Longitude, city);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void getTideForecast(String location, String beginDate, String endDate, String latitude, String longitude, String city){
        cursor = dal.getForcastByDate(location, dateStart, dateFinish);
        if(cursor.getCount()==0){
            Toast.makeText(SecondActivity.this, "No Offline Data Available, Checking Online Resources", Toast.LENGTH_LONG).show();
            new RestTask().execute(location, beginDate, endDate, latitude, longitude, city);
        }
        else{
            displayTide();
        }
    }
    public class RestTask extends AsyncTask<String, Void, TideItems> {
        private String location;
        private String date;
        private String beginDate;
        private String endDate;
        private String  latitude;
        private String longitude;
        private String city;

        @Override
        protected TideItems doInBackground(String... params) {
            location = params[0];
            beginDate = params[1];
            endDate = params[2];
            latitude = params[3];
            longitude = params[4];
            city = params[5];
            String baseUrl = String.format("https://www.tidesandcurrents.noaa.gov/cgi-bin/predictiondownload.cgi?&stnid=%s&threshold=&thresholdDirection=&bdate=%s&edate=%s&units=standard&timezone=LST/LDT&datum=MLLW&interval=hilo&clock=12hour&type=xml&annual=false", this.location, this.beginDate, this.endDate);
            TideItems items = null;

            if(!isNetworkAvailable()){
                Log.d("DoInBackGround:", " NetWork Not Available");
                Toast.makeText(SecondActivity.this, "No Internet Access", Toast.LENGTH_LONG).show();
                return null;
            }
            try{
                URL tideUrl = new URL(baseUrl);
                HttpURLConnection connection = (HttpURLConnection) tideUrl.openConnection();
                connection.setRequestProperty("User-Agent", "");
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                InputStream in = connection.getInputStream();

                if(in!= null){
                    items = dal.parseXmlStream(in);
                    items.setZip(location);
                    items.setLat(latitude);
                    items.setLon(longitude);
                    items.setCity(city);
                }
            }
            catch (Exception e){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SecondActivity.this, "Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
                    }
                });
                Log.e("Tide", "doInBackground error: "+e.getLocalizedMessage());
            }
            return items;
        }

        @Override
        protected void onPostExecute(TideItems tideItems) {
            if(tideItems!=null && tideItems.size()!=0){
                dal.putForecastIntoDb(tideItems);
                cursor = dal.getForcastByDate(location, dateStart, dateFinish);
                displayTide();
            }
        }
    }

    private void displayTide() {
        // Set up the adapter for the ListView to display the tide info
        adapter = new SimpleCursorAdapter(
                this,
                R.layout.listview_items,
                cursor,
                new String[]{"date", "day", "time", "highlow"},
                new int[]{
                        R.id.date,
                        R.id.day,
                        R.id.time,
                        R.id.tide
                },
                0 );

        ListView itemsListView = (ListView)findViewById(R.id.tideForecast);
        itemsListView.setAdapter(adapter);
        itemsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        cursor.moveToPosition(position);
                        prediction = cursor.getString(cursor.getColumnIndex("pred"));
                        String city = cursor.getString(cursor.getColumnIndex("city"));
                        Toast.makeText(SecondActivity.this, city+ "  " +prediction, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    //Checking if the user has internet connection
    private boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
