package com.zeeshanz.nhl.model;

import com.google.gson.annotations.SerializedName;

public class Position {

    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("abbreviation")
    private String abbreviation;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

}
