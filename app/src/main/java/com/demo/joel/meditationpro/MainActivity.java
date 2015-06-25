package com.demo.joel.meditationpro;

import android.app.Activity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends Activity
{
    DataBaseAdapter dba;
    File file;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String filename = "myfile";
        file = new File(getApplicationContext().getFilesDir(), filename);

        FileOutputStream outputStream;
        String str = "Hello World";

        try {
            outputStream = openFileOutput(filename, getApplicationContext().MODE_PRIVATE);
            outputStream.write(str.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            String line= "";
            FileInputStream fs = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fs));
            while((line = reader.readLine()) !=null)
            {
                Message.message(getApplicationContext(),line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }





}
