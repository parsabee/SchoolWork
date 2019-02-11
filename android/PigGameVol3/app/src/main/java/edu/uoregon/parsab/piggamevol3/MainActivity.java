package edu.uoregon.parsab.piggamevol3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Created and managed in the first fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Creating Menu with Items Settings and About
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()== R.id.about_id){
            Toast.makeText(MainActivity.this, "This is PigGame Vol 3!", Toast.LENGTH_LONG).show();
        }
        if(item.getItemId()==R.id.settings_id){
            Toast.makeText(MainActivity.this, "This app doesn't have settings!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

}



