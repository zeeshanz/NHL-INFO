package com.zeeshanz.nhl.model;

import com.google.gson.annotations.SerializedName;

public class Person {

    @SerializedName("id")
    private int id;

    @SerializedName("fullName")
    private String fullName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

}
