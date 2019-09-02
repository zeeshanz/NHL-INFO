package com.zeeshanz.nhl.network;

import com.zeeshanz.nhl.model.PlayerInfoResponse;
import com.zeeshanz.nhl.model.PlayersListResponse;
import com.zeeshanz.nhl.model.TeamListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("teams")
    Call<TeamListResponse> getNHLTeams();

    @GET("teams/{id}/roster")
    Call<PlayersListResponse> getPlayers(@Path("id") int id);

    @GET("people/{id}")
    Call<PlayerInfoResponse> getPlayerInfo(@Path("id") int id);
}
