package edu.uoregon.parsab.binarytreetraversal;

import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    //Setting up BST object;
    BST<Integer> binarySearchTree = new BST<Integer>();

//    BST<Integer> binarySearchTree;
    EditText digit;
    TextView traversal;
    Spinner traversalSpinner;
    String traversal_type = "in_order";
    String tree;
    TextView showTraverseType;
    String traverse = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        binarySearchTree = new BST<Integer>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trees);
        tree = getIntent().getExtras().getString("tree");
        //Setting up the up-button to take us back to the main activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Setting up all the widgets
        digit = (EditText)findViewById(R.id.data);
        showTraverseType = (TextView)findViewById(R.id.trvTextView);
        showTraverseType.setText("Traversal ("+traversal_type+"):");
        traversal = (TextView)findViewById(R.id.traversal);

        //Setting up ADD button
        Button ADD_BUTTON = (Button)findViewById(R.id.AddButton);
        ADD_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ADD(v);
                    }
                }
        );

        //Setting up Delete button
        Button DELETE_BUTTON = (Button)findViewById(R.id.DeleteButton);
        DELETE_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DELETE(v);
                    }
                }
        );

        //Setting up Reset Button
        Button RESET_BUTTON = (Button)findViewById(R.id.ResetButton);
        RESET_BUTTON.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RESET(v);
                    }
                }
        );

    }


    //Setting up the menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        //if about option is selected
        if(item.getItemId()== R.id.about_id){
            Toast.makeText(SecondActivity.this, "This is "+tree+" !", Toast.LENGTH_LONG).show();
        }

        //if settings is selected
        if(item.getItemId()==R.id.settings_id){

            //settings will inflate a dialog box that contains a spinner that lets the user choose the type of traversal
            MenuItem menuItem = item.setOnMenuItemClickListener(
                    new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            AlertDialog.Builder a_builder = new AlertDialog.Builder(SecondActivity.this);
                            View mview = getLayoutInflater().inflate(R.layout.settings_dialog_bst, null);
                            traversalSpinner = (Spinner) mview.findViewById(R.id.traversalSpinner);
                            traversalSpinner.setOnItemSelectedListener(
                                    new AdapterView.OnItemSelectedListener() {
                                        // setting up the spinner
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            switch (position) {
                                                case 0:
                                                    traversal_type = "in_order";
                                                    Traverse();
                                                    showTraverseType.setText("Traversal (" + traversal_type + "):");
                                                    break;
                                                case 1:
                                                    traversal_type = "pre_order";
                                                    Traverse();
                                                    showTraverseType.setText("Traversal (" + traversal_type + "):");
                                                    break;
                                                case 2:
                                                    traversal_type = "post_order";
                                                    Traverse();
                                                    showTraverseType.setText("Traversal (" + traversal_type + "):");

                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                                traversal_type = "in_order";
                                        }
                                    }
                            );
                            a_builder.setView(mview);
                            AlertDialog dialog = a_builder.create();
                            dialog.show();
                            return false;
                        }
                    }
            );
        }
        return super.onOptionsItemSelected(item);
    }

    //Setting up ADD method corresponding to ADD button
    private void ADD(View v){

        String number = digit.getText().toString();

        //making sure the user has entered a digit
        try{
            Integer inNum = Integer.parseInt(number);
            binarySearchTree.insert(inNum);
            Traverse();
        }
        catch (Exception e){
            Toast.makeText(this,"Please Enter an Integer!", Toast.LENGTH_LONG).show();
            Log.e("BST", " Error in ADD: "+e.getLocalizedMessage());
        }
        digit.setText("");
    }

    //Setting up DELETE method corresponding to DELETE button
    private void DELETE(View v){

        String number = digit.getText().toString();

        //making sure the user has entered a digit
        try{
            Integer inNum = Integer.parseInt(number);
            binarySearchTree.delete(inNum);
            Traverse();
        }
        catch (Exception e){
            Toast.makeText(this,"Please Enter an Integer!", Toast.LENGTH_LONG).show();
            Log.e("BST", " Error in DELETE: "+e.getLocalizedMessage());
        }
        digit.setText("");
    }

    //Setting up RESET method corresponding to RESET button
    private void RESET(View v){
        binarySearchTree = new BST<Integer>();
        traversal.setText("Empty!");
    }

    //Setting up Traverse method that shows the specified traversal of the tree
    public void Traverse (){
        if (traversal_type == "in_order")
            traverse = binarySearchTree.inOrder(binarySearchTree.getRoot());
        else if(traversal_type == "pre_order")
            traverse = binarySearchTree.preOrder(binarySearchTree.getRoot());
        else if(traversal_type == "post_order")
            traverse = binarySearchTree.postOrder(binarySearchTree.getRoot());
        traversal.setText(traverse);
    }

    //Saving all the instances in case of screen rotation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("treeObject", binarySearchTree);
        outState.putString("tree_typeSavedInstance", tree);
        outState.putString("traversal_typeSavedInstance", traversal_type);
        outState.putString("traverseSavedInstance", traverse);
        Log.d("BST", tree + ", "+traversal_type+", "+ traverse+", "+"Instances were saved");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        binarySearchTree = savedInstanceState.getParcelable("treeObject");
        tree = savedInstanceState.getString("tree_typeSavedInstance");
        traversal_type = savedInstanceState.getString("traversal_typeSavedInstance");
        traverse = savedInstanceState.getString("traverseSavedInstance");
        traversal.setText(traverse);
        showTraverseType.setText("Traversal ("+traversal_type+"):");
        Log.d("BST", tree + ", "+traversal_type+", "+ traverse+", "+"Instances were restored");
    }
}
