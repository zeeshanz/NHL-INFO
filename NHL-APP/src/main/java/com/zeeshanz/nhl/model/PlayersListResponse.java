package com.zeeshanz.nhl.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlayersListResponse {

    @SerializedName("roster")
    private List<Player> players;

    public List<Player> getPlayers() {
        return players;
    }
}
