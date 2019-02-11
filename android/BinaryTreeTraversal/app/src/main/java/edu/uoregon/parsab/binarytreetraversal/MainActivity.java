package edu.uoregon.parsab.binarytreetraversal;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button BST_BUTTON = (Button)findViewById(R.id.BST_Button);
        BST_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BST(v);
                    }
                }
        );

        Button RBT_BUTTON = (Button)findViewById(R.id.RBT_Button);
        RBT_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RBT(v);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.about_dialog, null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        return super.onOptionsItemSelected(item);
    }

    //Setting up the transition to the second activity (Binary search tree)
    public void BST(View view){
        String tree = "Binary-Search Tree";
        Intent intent = new Intent(getApplication(), SecondActivity.class);
        intent.putExtra("tree", tree);
        startActivity(intent);
    }

    //Setting up the transition to the third activity (Red black tree)
    public void RBT(View view){
        String tree = "Red-Black Tree";
        Intent intent = new Intent(getApplication(), ThirdActivityRBT.class);
        intent.putExtra("tree", tree);
        startActivity(intent);
    }
}
