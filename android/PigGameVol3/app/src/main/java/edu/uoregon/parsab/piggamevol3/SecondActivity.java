package edu.uoregon.parsab.piggamevol3;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private Pig pig_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //setting up the up-Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // receiving the names from the previous activity
        String name_one = getIntent().getExtras().getString("player1name");
        String name_two = getIntent().getExtras().getString("player2name");

        // Creating a new game object
        pig_game = new Pig(name_one, name_two);

        // Initializing the second fragment
        Fragment2 fragment2 = new Fragment2();

        // initializing the game on the fragment
        fragment2.action(pig_game, false, false);

        //showing the second fragment on the screen
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(android.R.id.content,fragment2);
        ft.commit();
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
            Toast.makeText(SecondActivity.this, "This is PigGame Vol 3!", Toast.LENGTH_LONG).show();
        }
        if(item.getItemId()==R.id.settings_id){
            Toast.makeText(SecondActivity.this, "This app doesn't have settings!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
