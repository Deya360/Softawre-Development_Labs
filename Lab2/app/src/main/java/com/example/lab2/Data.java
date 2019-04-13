package com.example.lab2;

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
    public static Item getItem(int pos) {
        return itemArr.get(pos);
    }

    public static int getSize() {return itemArr.size();}
}
