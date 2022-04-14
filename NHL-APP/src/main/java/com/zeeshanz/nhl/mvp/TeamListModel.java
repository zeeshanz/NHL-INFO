package com.zeeshanz.nhl.mvp;

import androidx.annotation.NonNull;
import android.util.Log;

import com.zeeshanz.nhl.model.Team;
import com.zeeshanz.nhl.model.TeamListResponse;
import com.zeeshanz.nhl.network.ApiClient;
import com.zeeshanz.nhl.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamListModel implements TeamListContract.Model {

    private final String TAG = "TeamListModel";

    /**
     * This function will fetch teams data
     * @param onFinishedListener on finished
     */
    @Override
    public void getTeamList(final OnFinishedListener onFinishedListener) {

        ApiInterface apiService = ApiClient.getNHLClient().create(ApiInterface.class);

        Call<TeamListResponse> call = apiService.getNHLTeams();
        call.enqueue(new Callback<TeamListResponse>() {
            @Override
            public void onResponse(@NonNull Call<TeamListResponse> call, @NonNull Response<TeamListResponse> response) {
                if (response.body() != null) {
                    List<Team> teams = response.body().getTeams();
                    Log.d(TAG, "Number of teams received: " + teams.size());
                    onFinishedListener.onFinished(teams);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TeamListResponse> call, @NonNull Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }

}
