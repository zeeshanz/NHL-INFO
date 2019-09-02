package com.zeeshanz.nhl.mvp;

import com.zeeshanz.nhl.model.Player;
import com.zeeshanz.nhl.model.PlayerInfo;

import java.util.List;
import java.util.Locale;

public interface PlayersListContract {

    interface Model {

        interface OnFinishedListener {
            void onFinished(List<Player> playerList, String teamName);

            void onFinished(PlayerInfo playerInfo);

            void onFailure(Throwable t);
        }

        void getPlayersData(OnFinishedListener onFinishedListener, int teamId, String teamName);

        void getPlayerInfo(OnFinishedListener onFinishedListener, int playerId);

    }

    interface View {

        void showProgress();

        void hideProgress();

        void populatePlayersList(List<Player> playerList, String teamName);

        void populateSpinner(List<String> filters);

        void showPlayerInfoDialog(PlayerInfo playerInfo, Locale locale);

        void onResponseFailure(Throwable throwable);

    }

    interface Presenter {

        void onDestroy();

        void requestPlayersListFromServer(int teamId, String teamName);

        void requestPlayerInfoFromServer(int playerId);

    }
}
