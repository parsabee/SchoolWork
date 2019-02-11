package edu.uoregon.parsab.piggamevol3;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Fragment1 extends Fragment{

// Instantiating widgets
    private TextView player1;
    private TextView player2;
    private TextView about_game;
    private EditText player1nameEditText;
    private EditText player2nameEditText;
    private Button new_game;

//  Instantiating the activity and the layouts
    private boolean dual_pane;
    private boolean large_screen;
    private MainActivity activity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Creating the first fragment

        View view = inflater.inflate(R.layout.fragment_1, container, false);

        // Creating the new game button to launch the second fragment/activity

        new_game = (Button) view.findViewById(R.id.new_game);

        //Getting size information

        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        if(screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE)
            large_screen = true;
        else
            large_screen = false;

        //Getting the orientation information
        int screenOrientation = getResources().getConfiguration().orientation;


        if(screenOrientation == Configuration.ORIENTATION_LANDSCAPE)
            dual_pane = true;
        else
            dual_pane = false;

        new_game.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        NewGame(v);
                    }
                }
        );

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get a reference from the host activity
        activity = (MainActivity)getActivity();

        // Creating the widgets for this fragment
        player1 = (TextView) activity.findViewById(R.id.player1);
        player2 = (TextView) activity.findViewById(R.id.player2);
        about_game = (TextView) activity.findViewById(R.id.about_game);
        player1nameEditText = (EditText) activity.findViewById(R.id.player1name);
        player2nameEditText = (EditText) activity.findViewById(R.id.player2name);

    }

    public void NewGame(View view){

        String player1name = player1nameEditText.getText().toString();
        String player2name = player2nameEditText.getText().toString();


        //check to see if the the MainActivity has loaded a single or dual pane layout
        if(!dual_pane && !large_screen){
            Intent intent = new Intent(getActivity(), SecondActivity.class);

            intent.putExtra("player1name", player1name);
            intent.putExtra("player2name", player2name);

            startActivity(intent);
        }
        else{
            // Creating a new game object
            Pig pig_game = new Pig(player1name, player2name);

            // Getting a reference from the second fragment
            Fragment2 fragment2 = (Fragment2)getFragmentManager().findFragmentById(R.id.fragment_2);
            fragment2.action(pig_game, dual_pane, large_screen);
        }
    }
}
