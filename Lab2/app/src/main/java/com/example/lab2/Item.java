package com.example.lab2;

public class Item {
    public String getName() {
        return name;
    }

    public String getHelptext() {
        return helptext;
    }

    public String getImageURLSuffix() {
        return imageURLSuffix;
    }

    private String name;
    private String helptext;
    private String imageURLSuffix;

    public Item(String name, String helptext, String imageURLSuffix) {
        this.name = name;
        this.helptext = helptext;
        this.imageURLSuffix = imageURLSuffix;
    }




}
