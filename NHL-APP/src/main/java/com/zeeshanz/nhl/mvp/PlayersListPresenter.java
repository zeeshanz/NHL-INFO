package com.zeeshanz.nhl.mvp;

import com.zeeshanz.nhl.model.Player;
import com.zeeshanz.nhl.model.PlayerInfo;
import com.zeeshanz.nhl.model.Position;
import com.zeeshanz.nhl.utils.Utils;

import java.util.List;
import java.util.Locale;
import java.util.ArrayList;

public class PlayersListPresenter implements PlayersListContract.Presenter, PlayersListContract.Model.OnFinishedListener {

    private PlayersListContract.View playersListView;

    private PlayersListContract.Model playersListModel;

    public PlayersListPresenter(PlayersListContract.View playersListView) {
        this.playersListView = playersListView;
        playersListModel = new PlayersListModel();
    }

    @Override
    public void onDestroy() {
        this.playersListView = null;
    }

    @Override
    public void requestPlayersListFromServer(int teamId, String teamName) {
        if (playersListView != null) {
            playersListView.showProgress();
        }
        playersListModel.getPlayersData(this, teamId, teamName);
    }

    @Override
    public void requestPlayerInfoFromServer(int playerId) {
        playersListView.showProgress();
        playersListModel.getPlayerInfo(this, playerId);
    }

    @Override
    public void onFinished(List<Player> playerList, String teamName) {
        List<String> filterItems = makeListOfFilters(playerList);
        playersListView.populatePlayersList(playerList, teamName);
        playersListView.populateSpinner(filterItems);
        if (playersListView != null) {
            playersListView.hideProgress();
        }
    }

    @Override
    public void onFinished(PlayerInfo playerInfo) {
        playersListView.hideProgress();
        Locale locale = Utils.getLocaleFromNationality(playerInfo.getNationality());
        playersListView.showPlayerInfoDialog(playerInfo, locale);
    }

    @Override
    public void onFailure(Throwable t) {
        playersListView.onResponseFailure(t);
        if (playersListView != null) {
            playersListView.hideProgress();
        }
    }

    /**
     * This method creates a unique list of players positions which is used in the spinner on the
     * app's main screen to filter the list of players.
     *
     * @param playerList    the players list
     * @return              the list of players positions
     */
    private List<String> makeListOfFilters(List<Player> playerList) {
        List<String> list = new ArrayList<>();
        list.add("All Positions");
        for (Player player : playerList) {
            Position position = player.getPosition();
            String abbreviation = position.getAbbreviation();
            if (!list.contains(abbreviation)) {
                list.add(abbreviation);
            }
        }

        return list;
    }
}
