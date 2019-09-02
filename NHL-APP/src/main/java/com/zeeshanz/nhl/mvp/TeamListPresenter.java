package com.zeeshanz.nhl.mvp;

import com.zeeshanz.nhl.model.Team;

import java.util.List;

public class TeamListPresenter implements TeamListContract.Presenter, TeamListContract.Model.OnFinishedListener {

    private TeamListContract.View teamListView;

    private TeamListContract.Model teamListModel;

    public TeamListPresenter(TeamListContract.View teamListView) {
        this.teamListView = teamListView;
        teamListModel = new TeamListModel();
    }

    @Override
    public void onDestroy() {
        this.teamListView = null;
    }

    @Override
    public void requestTeamListFromServer() {
        teamListModel.getTeamList(this);
    }

    @Override
    public void onFinished(List<Team> teamList) {
        teamListView.populateDrawerViewList(teamList);
    }

    @Override
    public void onFailure(Throwable t) {
        teamListView.onResponseFailure(t);
    }
}
