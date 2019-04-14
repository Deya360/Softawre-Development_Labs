package com.example.lab2;

import android.content.ContentValues;
import android.content.Context;

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

}

