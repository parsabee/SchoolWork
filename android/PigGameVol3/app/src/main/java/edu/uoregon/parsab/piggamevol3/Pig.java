package edu.uoregon.parsab.piggamevol3;
import java.util.Random;

public class Pig {

    //Instantiating players names
    String player1name;
    String player2name;


    int plyrCount = 0; //players count for the round
    int number=0; //Number showing on the die

    dice D;

    //Setting constructors
    public Pig(){}
    public Pig(String name1, String name2){
        player1name = name1;
        player2name = name2;
    }

    public void die(){
        Random rnd = new Random();
        number = 1+rnd.nextInt(6);
        if(number == 1)
            D= dice.one;
        else if(number == 2)
            D= dice.two;
        else if(number == 3)
            D= dice.three;
        else if(number == 4)
            D= dice.four;
        else if(number == 5)
            D= dice.five;
        else
            D= dice.six;
    }
    public String play(){
        plyrCount = 0;
        die();
        if(D!= dice.one){
            plyrCount+=number;
            return "NOT_ONE";
        }
        else{
            plyrCount =0;
            return "ONE";
        }
    }
    public int getPoints(){
        return plyrCount;
    }
    public dice getDie(){return D; }

    public String getPlayer1name(){
        return player1name;
    }
    public String getPlayer2name(){
        return player2name;
    }

    public void setPlayer1name(String name){
        player1name = name;
    }

    public void setPlayer2name(String name){
        player2name = name;
    }
}
