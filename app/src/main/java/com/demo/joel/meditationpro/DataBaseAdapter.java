
package com.demo.joel.meditationpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class DataBaseAdapter
{
    DataBaseHelper helper;
    File currCSVFile;

    public DataBaseAdapter(Context context)
    {
        helper = new DataBaseHelper(context);
    }


    public File createCSV(String filename) {
        return currCSVFile = new File(helper.context.getFilesDir(), filename);
    }

    public void setCurrCSVFile(File currCSVFile) {
        this.currCSVFile = currCSVFile;
    }

    public void addSingleDataPoint(int time, int device_val) {
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(currCSVFile);
            outputStream.write(time);
            outputStream.write(":".getBytes());
            outputStream.write(device_val);
            outputStream.write(",".getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Insert a Single Meditation Value
    public long insert_MedVal(int medVal)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.MEDITATION_VALUE,medVal);

        return db.insert(DataBaseHelper.TABLE_TEST, null, cv);
    }

    public int count()
    {
        SQLiteDatabase db = helper.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + DataBaseHelper.TABLE_TEST;
        Cursor cursor = db.rawQuery(countQuery, null);
        int i = cursor.getCount();
        helper.close();

        return i;
    }



    static class DataBaseHelper extends SQLiteOpenHelper
    {
        //Name of the Database
        private static final String DATABASE_NAME = "MeditationPro.db";
        //Database Version Number
        private static final  int DATABASE_VERSION=1;
        //Table Name(s)
        private static final String TABLE_TEST ="Test_Table";
        //Row ID
        private static final String UID = "_id";
        //Column(s)
        private static final String MEDITATION_VALUE = "meditationValue";
        //Create Statement for TABLE_TEST
        private static final String CREATE_TABLE = "CREATE TABLE "
                + TABLE_TEST + " " +
                "("+ UID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                 + MEDITATION_VALUE + " INTEGER);";
        //Drop Statement for Table_TEST
        private static final String DROP_TABLE_TEST
                = "DROP TABLE IF EXISTS "+ TABLE_TEST;
        private Context context;


        public DataBaseHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
            this.context=context;
            Message.message(context, "Inside the DB Helper Constructor");
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try
            {
                db.execSQL(CREATE_TABLE);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion)
        {
            try
            {
                db.execSQL(DROP_TABLE_TEST);
                onCreate(db);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

        }
    }

}
