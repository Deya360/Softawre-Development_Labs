package com.sd.lab3_a;

class Student {
    private int id;
    private String fullName;
    private long dateAdded;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getDateAdded() {
        return dateAdded;
    }
    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }


    public Student(int id, String fullName, long dateAdded) {
        this.id = id;
        this.fullName = fullName;
        this.dateAdded = dateAdded;
    }

    public Student(String fullName, long dateAdded) {
        this.fullName = fullName;
        this.dateAdded = dateAdded;
    }
}
