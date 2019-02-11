package edu.uoregon.parsab.tideappvol4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TideSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tideForecast.sqlite";
    private static final int DATABASE_VERSION = 4;
    private Context context = null;

    public TideSQLiteHelper(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Tide"
                + "( _id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "lat TEXT,"           //column for latitude
                + "lon TEXT,"           //column for longitude
                + "date TEXT,"
                + "zip INTEGER,"
                + "city TEXT,"
                + "day TEXT,"
                + "time INTEGER,"
                + "pred INTEGER,"
                + "highlow INTEGER"
                + ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        if(newVersion > oldVersion){
            db.execSQL("ALTER TABLE Tide ADD COLUMN lat AND lon INTEGER DEFAULT 0");
        }
    }
}
