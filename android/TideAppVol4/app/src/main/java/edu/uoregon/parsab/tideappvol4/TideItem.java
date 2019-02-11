package edu.uoregon.parsab.tideappvol4;


import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class TideItem {

    private String date = null;
    private String day = null;
    private String time = null;
    private String pred = null;
    private String highlow = null;


    public void setDate(String pubDate) {
        this.date = pubDate;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day+"day";
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPred() {
        return pred;
    }

    public void setPred(String pred) {
        double ft = Double.parseDouble(pred);
        double cm = ft*(30.48);
        this.pred = String.format("%.2f ft., %.2f cm", ft, cm);
    }

    public String getHighlow() {
        return highlow;
    }

    public void setHighlow(String highlow) {
        if(highlow.equals("H"))
            this.highlow = "High: ";
        else if(highlow.equals("L"))
            this.highlow = "Low: ";
    }
}