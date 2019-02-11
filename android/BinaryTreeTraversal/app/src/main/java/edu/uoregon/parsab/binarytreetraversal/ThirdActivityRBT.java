package edu.uoregon.parsab.binarytreetraversal;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivityRBT extends AppCompatActivity {

    //Setting up RBT object;
    RBT<Integer> redBlackTree  = new RBT<Integer>();
    EditText digit;
    TextView traversal;
    TextView showTraverseType;
    String traversal_type = "in_order";
    String tree;
    String traverse = "";
    private boolean checked =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trees);
        tree = getIntent().getExtras().getString("tree");

        //Setting up the up-button to take us back to the main activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Setting up widgets
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
            Toast.makeText(ThirdActivityRBT.this, "This is "+tree+" !\n"+"b: BLACK node, r:RED node", Toast.LENGTH_LONG).show();
        }

        //if settings is selected
        if(item.getItemId()==R.id.settings_id){

            //settings will inflate a dialog box that contains a spinner that lets the user choose the type of traversal
            item.setOnMenuItemClickListener(
                    new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            AlertDialog.Builder a_builder = new AlertDialog.Builder(ThirdActivityRBT.this);
                            View mview = getLayoutInflater().inflate(R.layout.settings_dialog_rbt, null);
                            Spinner traversalSpinner = (Spinner)mview.findViewById(R.id.traversalSpinner);
                            traversalSpinner.setOnItemSelectedListener(
                                    new AdapterView.OnItemSelectedListener() {
                                        // setting up the spinner
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            switch(position){
                                                case 0:
                                                    traversal_type = "in_order";
                                                    Traverse();
                                                    showTraverseType.setText("Traversal ("+traversal_type+"):");
                                                    break;
                                                case 1:
                                                    traversal_type = "pre_order";
                                                    Traverse();
                                                    showTraverseType.setText("Traversal ("+traversal_type+"):");
                                                    break;
                                                case 2:
                                                    traversal_type = "post_order";
                                                    Traverse();
                                                    showTraverseType.setText("Traversal ("+traversal_type+"):");                                                    break;
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                            traversal_type = "in_order";
                                        }
                                    }
                            );
                            final CheckBox showColorCheckBox = (CheckBox)mview.findViewById(R.id.showColor);
                            showColorCheckBox.setChecked(checked);
                            showColorCheckBox.setOnCheckedChangeListener(
                                    new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if (showColorCheckBox.isChecked()){
                                                checked = true;
                                                Traverse();
                                            }
                                            else{
                                                checked = false;
                                                Traverse();
                                            }
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
        //making sure the user has entered an integer
        try {
            Integer inNum = Integer.parseInt(number);
            redBlackTree.insert(inNum);
            Traverse();
        }
        catch(Exception e)
        {
            Log.e("RBT", " Error in Insert:"+e.getLocalizedMessage());
            Toast.makeText(this,"Please Enter an Integer!", Toast.LENGTH_LONG).show();
        }
        digit.setText("");
    }

    //Setting up DELETE method corresponding to DELETE button
    private void DELETE(View v){
        String number = digit.getText().toString();
        try{
            Integer inNum = Integer.parseInt(number);
            redBlackTree.delete(inNum);
            Traverse();
        }
        catch (Exception e)
        {
            Log.e("RBT", " Error in Delete: "+e.getLocalizedMessage());
            Toast.makeText(this,"Please Enter an Integer!", Toast.LENGTH_LONG).show();
        }
        digit.setText("");

    }

    //Setting up RESET method corresponding to RESET button
    private void RESET(View v){
        redBlackTree = new RBT<Integer>();
        traversal.setText("Empty!");
    }

    //Setting up Traverse method that shows the specified traversal of the tree
    public void Traverse (){
        if (traversal_type == "in_order")
            traverse = redBlackTree.inOrder(redBlackTree.getRoot(), checked);
        else if(traversal_type == "pre_order")
            traverse = redBlackTree.preOrder(redBlackTree.getRoot(), checked);
        else if(traversal_type == "post_order")
            traverse = redBlackTree.postOrder(redBlackTree.getRoot(), checked);
        traversal.setText(traverse);
    }

    //Saving all the instances in case of screen rotation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("treeObject", redBlackTree);
        outState.putString("tree_typeSavedInstance", tree);
        outState.putString("traversal_typeSavedInstance", traversal_type);
        outState.putString("traverseSavedInstance", traverse);
        outState.putBoolean("boxCheckedSavedInstance", checked);
        Log.d("RBT", tree + ", "+traversal_type+", "+ traverse+", "+"Instances were saved");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        redBlackTree = savedInstanceState.getParcelable("treeObject");
        tree = savedInstanceState.getString("tree_typeSavedInstance");
        traversal_type = savedInstanceState.getString("traversal_typeSavedInstance");
        traverse = savedInstanceState.getString("traverseSavedInstance");
        traversal.setText(traverse);
        showTraverseType.setText("Traversal ("+traversal_type+"):");
        checked = savedInstanceState.getBoolean("boxCheckedSavedInstance");
        Log.d("RBT", tree + ", "+traversal_type+", "+ traverse+", "+"Instances were restored");
    }
}