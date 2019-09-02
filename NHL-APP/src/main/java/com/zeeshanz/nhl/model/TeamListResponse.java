package com.zeeshanz.nhl.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamListResponse {

    @SerializedName("teams")
    private List<Team> teams;

    public List<Team> getTeams() {
        return teams;
    }
}
