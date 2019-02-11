package edu.uoregon.parsab.tideappvol4;


import java.util.ArrayList;

public class TideItems extends ArrayList<TideItem> {
    // Extending ArrayList to facilitate possible future features

    // Default Serial ID
    private static final long serialVersionUID = 1L;

    // Info that applies to the whole forecast
    private String zip = "";
    private String city = "";
    private String lat = "";
    private String lon = "";

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLat() {
        return lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLon() {
        return lon;
    }
}
