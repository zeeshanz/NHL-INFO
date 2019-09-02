package com.zeeshanz.nhl.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Team {

    private int id;

    @SerializedName("name")
    private String name;

    public Team(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    @Override
    @NonNull
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name+ '\'' +
                '}';
    }
}
