package com.zeeshanz.nhl.mvp;

import com.zeeshanz.nhl.model.Team;

import java.util.List;

public interface TeamListContract {

    interface Model {

        interface OnFinishedListener {
            void onFinished(List<Team> teamList);

            void onFailure(Throwable t);
        }

        void getTeamList(OnFinishedListener onFinishedListener);

    }

    interface View {

        void populateDrawerViewList(List<Team> teamList);

        void onResponseFailure(Throwable throwable);

    }

    interface Presenter {

        void onDestroy();

        void requestTeamListFromServer();

    }
}
