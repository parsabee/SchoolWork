package edu.uoregon.parsab.tideappvol4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

// Data Access Layer

public class Dal {
    private Context context = null;

    public Dal(Context context)
    {
        this.context = context;
    }


    // Parse the XML files and put the data in the db
    public void putForecastIntoDb(TideItems items) {

        // Initialize database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Put weather forecast in the database
        ContentValues cv = new ContentValues();

        for(TideItem item : items)
        {
            cv.put("lat", items.getLat());
            cv.put("lon", items.getLon());
            cv.put("date", item.getDate());
            cv.put("zip", items.getZip());				// stored in items, not item
            cv.put("city", items.getCity());			// stored in items, not item
            cv.put("day", item.getDay());
            cv.put("time", item.getTime());
            cv.put("pred", item.getPred());
            cv.put("highlow", item.getHighlow());
            db.insert("Tide", null, cv);
        }
        db.close();
    }


    public Cursor getForcastByDate(String location, String beginDate, String endDate){

        // Initialize the database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Get tide forecast for one location
        String query = "SELECT * FROM Tide WHERE zip = ? AND date BETWEEN ? AND ?";
        String[] variables = new String[]{location, beginDate, endDate};    // rawQuery must not include a trailing ';'
        return db.rawQuery(query, variables);
    }


    public TideItems parseXmlStream(InputStream in) {
        TideItems items = null;
        if(in != null){
            try {
                // get the XML reader
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                XMLReader xmlreader = parser.getXMLReader();

                // set content handler
                ParseHandler handler = new ParseHandler();
                xmlreader.setContentHandler(handler);

                // parse the data
                InputSource is = new InputSource(in);
                xmlreader.parse(is);

                // set the feed in the activity
                items = handler.getItems();
            }
            catch (Exception e) {
                Log.e("News reader", e.toString());
            }
        }
        return items;
    }
}
