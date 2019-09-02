package com.zeeshanz.nhl.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Player {

    @SerializedName("jerseyNumber")
    private String jerseyNumber;

    @SerializedName("person")
    private Person person;

    @SerializedName("position")
    private Position position;

    public Player(String jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public int getJerseyNumber() {
        if (jerseyNumber == null) {return 0;} // Some players have null as jersey number which causes the app to crash, so added this check
        return Integer.parseInt(jerseyNumber);
    }

    public Person getPerson() {
        return person;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    @NonNull
    public String toString() {
        String fullName = person.getFullName();
        String positionName = position.getName();
        String positionType = position.getType();
        String pos = positionName + " " + positionType;
        return "Player{" +
                "jerseyNumber=" + jerseyNumber +
                ", fullName='" + fullName + '\'' +
                ", position='" + pos + '\'' +
                '}';
    }
}
