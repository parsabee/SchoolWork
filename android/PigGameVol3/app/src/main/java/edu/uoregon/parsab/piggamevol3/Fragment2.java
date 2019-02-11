package edu.uoregon.parsab.piggamevol3;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Fragment2 extends Fragment{

    private TextView player1name;
    private TextView player2name;
    private TextView player1score;
    private TextView player2score;
    private TextView whose_turn;
    private TextView turn_points;
    private ImageView die;
    private Button roll_die;
    private Button end_turn;
    private ImageView img;

    private Pig pig_game;

    //to determine whose turn it is
    boolean plyr1trn= true;

    //to determine if both players played the round
    boolean both_went = true;

    int plyr1pts = 0;
    String plyr1pts_str;

    int plyr2pts = 0;
    String plyr2pts_str;

    int turnpts = 0;
    String turnpts_str;

    String name1;
    String name2;
    String temp_turn;

    // Creating the second fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflating the new fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        player1name = (TextView) view.findViewById(R.id.player1name);
        player2name = (TextView) view.findViewById(R.id.player2name);
        player1name.setText(name1);
        player2name.setText(name2);


        //Setting up widgets
        whose_turn = (TextView) view.findViewById(R.id.whose_turn);
        whose_turn.setText(temp_turn);

        player1score= (TextView) view.findViewById(R.id.player1score);
        player2score= (TextView) view.findViewById(R.id.player2score);
        player1score.setText(plyr1pts_str);
        player2score.setText(plyr2pts_str);

        turn_points= (TextView) view.findViewById(R.id.turn_points);
        turn_points.setText(turnpts_str);

        roll_die = (Button) view.findViewById(R.id.roll_die);
        end_turn = (Button) view.findViewById(R.id.end_turn);
        img = (ImageView) view.findViewById(R.id.die);


        if(savedInstanceState != null){
            plyr1pts = savedInstanceState.getInt("cur_plyr1pts");
            plyr2pts = savedInstanceState.getInt("cur_plyr2pts");
            turnpts = savedInstanceState.getInt("cur_turnpts");
            plyr1trn = savedInstanceState.getBoolean("cur_plyr1trn");
            both_went = savedInstanceState.getBoolean("cur_both_went");
            name1 = savedInstanceState.getString("cur_name1");
            name2 = savedInstanceState.getString("cur_name2");

            player1score.setText(Integer.toString(plyr1pts));
            player2score.setText(Integer.toString(plyr2pts));
            turn_points.setText(Integer.toString(turnpts));
            player1name.setText(name1);
            player2name.setText(name2);
            whose_turn.setText(savedInstanceState.getString("cur_turns"));
        }


        // Setting up onClickListeners
        roll_die.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        RollDie(v);
                    }
                }
        );

        end_turn.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        EndTurn(v);
                    }
                }
        );

        return view;
    }


    public void action(Pig game, boolean dualPane, boolean largeScreen){
        if(dualPane || largeScreen){
            Activity activity = getActivity();

            name1 = game.getPlayer1name();
            name2 = game.getPlayer2name();

            //Since player 1 plays first, upon initialization, it's player 1's turn
            temp_turn = name1+"'s turn";

            //Initializing the scores
            plyr1pts_str = Integer.toString(plyr1pts);
            plyr2pts_str = Integer.toString(plyr2pts);
            turnpts_str = Integer.toString(turnpts);

            //Initializing pig_game
            pig_game = game;

            player1name.setText(name1);
            player2name.setText(name2);
            whose_turn.setText(temp_turn);
            player1score.setText(plyr1pts_str);
            player2score.setText(plyr2pts_str);
            turn_points.setText(turnpts_str);

            getView().invalidate();
        }
        else{
            name1 = game.getPlayer1name();
            name2 = game.getPlayer2name();

            //Since player 1 plays first, upon initialization, it's player 1's turn
            temp_turn = name1+"'s turn";

            //Initializing the scores
            plyr1pts_str = Integer.toString(plyr1pts);
            plyr2pts_str = Integer.toString(plyr2pts);
            turnpts_str = Integer.toString(turnpts);

            //Initializing pig_game
            pig_game = game;
        }
    }

    private void showDie (dice die){
        int id = 0;
        switch(die){
            case one:
                id = R.drawable.die1;
                break;
            case two:
                id = R.drawable.die2;
                break;
            case three:
                id = R.drawable.die3;
                break;
            case four:
                id = R.drawable.die4;
                break;
            case five:
                id = R.drawable.die5;
                break;
            case six:
                id = R.drawable.die6;
                break;
        }
        img.setImageResource(id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("cur_plyr1pts", plyr1pts);
        outState.putInt("cur_plyr2pts", plyr2pts);
        outState.putInt("cur_turnpts", turnpts);
        outState.putBoolean("cur_plyr1trn", plyr1trn);
        outState.putBoolean("cur_both_went", both_went);
        outState.putString("cur_turns", whose_turn.getText().toString());
        outState.putString("cur_name1", name1);
        outState.putString("cur_name2", name2);
    }

    //Upon Clicking "Roll Die" following function will execute
    public void RollDie(View view){
        String result = pig_game.play();
        showDie(pig_game.getDie());
        if(result == "NOT_ONE"){
            turnpts+=pig_game.getPoints();
            turn_points.setText(Integer.toString(turnpts));
            if(plyr1trn){
                whose_turn.setText(name1+"'s turn");
                plyr1pts+=pig_game.getPoints();
                player1score.setText(Integer.toString(plyr1pts));
                both_went =false;
            }
            else{
                whose_turn.setText(name2+"'s turn");
                plyr2pts+=pig_game.getPoints();
                player2score.setText(Integer.toString(plyr2pts));
                both_went =true;
            }
        }
        else{
            turnpts=0;
            turn_points.setText(Integer.toString(turnpts));
            if(plyr1trn){
                whose_turn.setText(name2+"'s turn");
                plyr1pts = 0;
                player1score.setText(Integer.toString(plyr1pts));
                plyr1trn =false;
                both_went =false;

            }
            else{
                whose_turn.setText(name1+"'s turn");
                plyr2pts = 0;
                player2score.setText(Integer.toString(plyr2pts));
                plyr1trn = true;
                both_went =true;
            }
        }
        if(plyr1pts >= 100 && both_went && plyr1pts > plyr2pts){
            whose_turn.setText(name1+" won");
            plyr1pts = 0;
            plyr2pts = 0;
            plyr1trn = true;
            player1score.setText(Integer.toString(plyr1pts));
            player2score.setText(Integer.toString(plyr2pts));
        }
        if(plyr2pts >= 100 && both_went && plyr1pts < plyr2pts){
            whose_turn.setText(name2+" won");
            plyr1pts = 0;
            plyr2pts = 0;
            plyr1trn = true;
            player1score.setText(Integer.toString(plyr1pts));
            player2score.setText(Integer.toString(plyr2pts));
        }
    }

    //Upon clicking End Turn following function will execute
    public void EndTurn(View view){
        turnpts=0;
        turn_points.setText(Integer.toString(turnpts));
        if(plyr1trn) {
            whose_turn.setText(name2+"'s turn");
            plyr1trn = false;
            both_went = false;
        }
        else {
            whose_turn.setText(name1+"'s turn");
            plyr1trn = true;
            both_went = true;
        }
    }
}
