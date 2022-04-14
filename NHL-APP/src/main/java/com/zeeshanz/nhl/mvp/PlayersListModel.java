package com.zeeshanz.nhl.mvp;

import androidx.annotation.NonNull;
import android.util.Log;

import com.zeeshanz.nhl.model.Player;
import com.zeeshanz.nhl.model.PlayerInfo;
import com.zeeshanz.nhl.model.PlayerInfoResponse;
import com.zeeshanz.nhl.model.PlayersListResponse;
import com.zeeshanz.nhl.network.ApiClient;
import com.zeeshanz.nhl.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayersListModel implements PlayersListContract.Model {

    private final String TAG = "PlayersListModel";

    /**
     * This function will fetch players data
     * @param onFinishedListener on finished
     */
    @Override
    public void getPlayersData(final OnFinishedListener onFinishedListener, int teamId, final String teamName) {

        ApiInterface apiService = ApiClient.getNHLClient().create(ApiInterface.class);

        Call<PlayersListResponse> call = apiService.getPlayers(teamId);
        call.enqueue(new Callback<PlayersListResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlayersListResponse> call, @NonNull Response<PlayersListResponse> response) {
                if (response.body() != null) {
                    List<Player> players = response.body().getPlayers();
                    Log.d(TAG, "Number of players received: " + players.size());
                    onFinishedListener.onFinished(players, teamName);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlayersListResponse> call, @NonNull Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }
    /**
     * This function will fetch a players info
     * @param onFinishedListener on finished
     */
    @Override
    public void getPlayerInfo(final OnFinishedListener onFinishedListener, int playerId) {

        ApiInterface apiService = ApiClient.getNHLClient().create(ApiInterface.class);

        Call<PlayerInfoResponse> call = apiService.getPlayerInfo(playerId);
        call.enqueue(new Callback<PlayerInfoResponse>() {
            @Override
            public void onResponse(@NonNull Call<PlayerInfoResponse> call, @NonNull Response<PlayerInfoResponse> response) {
                if (response.body() != null) {
                    PlayerInfo playerInfo = response.body().getPlayerInfo().get(0); // Since there will be only one object
                    onFinishedListener.onFinished(playerInfo);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlayerInfoResponse> call, @NonNull Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }

}
