package com.example.lab2;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class Data {
    private static final Data ourInstance = new Data(new ArrayList<Item>());
    private static ArrayList<Item> itemArr;

    private Data(ArrayList<Item> data) {
        setData(data);
    }
    public static Data getInstance() {
        return ourInstance;
    }

    public static ArrayList<Item> getData() {
        return itemArr;
    }
    public static void setData(ArrayList<Item> data) {
        itemArr = data;
    }

    public static void addItem(Item it) {
        itemArr.add(it);
    }
    public static void addItem(Item it, Context context) {
        addItem(it);
        ContentValues values = new ContentValues();
        values.put(TechnologyProvider.COLUMN_NAME,it.getName());
        values.put(TechnologyProvider.COLUMN_HELPTEXT,it.getHelptext());
        values.put(TechnologyProvider.COLUMN_GRAPHIC,it.getImageURLSuffix());

        context.getContentResolver().insert(TechnologyProvider.CONTENT_URI, values);
    }
    public static Item getItem(int pos) {
        return itemArr.get(pos);
    }

    public static int getSize() {return itemArr.size();}

    public static void clear() {
        itemArr.clear();
    }
    public static void clear(Context context) {
        clear();
        context.getContentResolver().delete(TechnologyProvider.CONTENT_URI, null, null);
    }

    public static void saveToFile(Context context, String textToWrite) {
        class OneShotTask implements Runnable {
            WeakReference<Context> context;
            String data;
            OneShotTask(Context context, String textToWrite) {
                this.context = new WeakReference<>(context);
                this.data = textToWrite;
            }
            public void run() {
                //Checking the availability state of the External Storage.
                String state = Environment.getExternalStorageState();
                if (!Environment.MEDIA_MOUNTED.equals(state)) {
                    return;
                }

                //Check whether write permissions are granted.
                if (ContextCompat.checkSelfPermission(context.get(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                //Create a new file that points to the root directory, with the given name:
                String filename = "technologiesFile.txt";
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Duo App/";
                File file = new File(path, filename);

                FileOutputStream outputStream = null;
                try {
                    //if the folder doesn't exist, create it
                    if (!file.getParentFile().exists())
                        file.getParentFile().mkdirs();
                    file.createNewFile();
                    outputStream = new FileOutputStream(file, false);

                    outputStream.write(data.getBytes());
                    outputStream.flush();
                    outputStream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Thread writeFile = new Thread(new OneShotTask(context,textToWrite));
        writeFile.start();
    }
}

