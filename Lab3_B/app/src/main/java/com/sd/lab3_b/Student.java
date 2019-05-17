package com.sd.lab3_b;

class Student {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private long dateAdded;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public long getDateAdded() {
        return dateAdded;
    }
    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Student(int id, String lastName, String firstName, String middleName, long dateAdded) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.dateAdded = dateAdded;
    }

    public Student(String lastName, String firstName, String middleName, long dateAdded) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.dateAdded = dateAdded;
    }


    //Support:
    private String fullName;
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public Student(int id, String fullName, long dateAdded) {
        this.id = id;
        this.fullName = fullName;
        this.dateAdded = dateAdded;
    }
}
