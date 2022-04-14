package com.zeeshanz.nhl.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class PlayerInfo {

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("birthCountry")
    private String birthCountry;

    @SerializedName("nationality")
    private String nationality;

    public PlayerInfo(String birthCountry, String nationality) {
        this.birthCountry = birthCountry;
        this.nationality = nationality;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    @NonNull
    public String toString() {
        return "People{" +
                "birthCountry=" + birthCountry +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
