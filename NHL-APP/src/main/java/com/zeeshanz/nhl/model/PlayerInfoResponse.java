package com.zeeshanz.nhl.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlayerInfoResponse {

    @SerializedName("people")
    private List<PlayerInfo> players;

    public List<PlayerInfo> getPlayerInfo() {
        return players;
    }
}
