package com.zeeshanz.nhl.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zeeshanz.nhl.R;
import com.zeeshanz.nhl.adapter.PlayersAdapter;
import com.zeeshanz.nhl.adapter.TeamsAdapter;
import com.zeeshanz.nhl.model.Player;
import com.zeeshanz.nhl.model.PlayerInfo;
import com.zeeshanz.nhl.model.Team;
import com.zeeshanz.nhl.mvp.PlayersListContract;
import com.zeeshanz.nhl.mvp.PlayersListPresenter;
import com.zeeshanz.nhl.mvp.TeamListContract;
import com.zeeshanz.nhl.mvp.TeamListPresenter;
import com.zeeshanz.nhl.utils.GridSpacingItemDecoration;
import com.zeeshanz.nhl.utils.SORT_TYPE;
import com.zeeshanz.nhl.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.zeeshanz.nhl.utils.GridSpacingItemDecoration.dpToPx;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TeamListContract.View, PlayersListContract.View, TeamsAdapter.OnTeamClickListener, PlayersAdapter.PlayerClickListener {

    private static final String TAG = "MainActivity";
    TeamListPresenter teamListPresenter;
    PlayersListPresenter playersListPresenter;
    RecyclerView rvTeamsList, rvPlayersList;
    LinearLayout llSpinner;
    LinearLayoutManager llmTeamsList, llmPlayersList;
    TextView tvSelectTeam;
    ImageView ivLogo, ivTeamLogo;
    DrawerLayout drawer;
    List<Player> playersList;
    PlayersAdapter playersAdapter;
    ProgressBar pbLoading;
    Spinner spinner;
    boolean ascending = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        initUI();

        // Tried to use Dagger to inject the presenters. But due to some conflict with AndroidX, it was not working.
        teamListPresenter = new TeamListPresenter(this);
        playersListPresenter = new PlayersListPresenter(this);
        teamListPresenter.requestTeamListFromServer();
    }

    private void initUI() {

        rvTeamsList = findViewById(R.id.list_slidermenu);
        llmTeamsList = new LinearLayoutManager(this);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        llSpinner = findViewById(R.id.ll_spinner);
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filter = parent.getItemAtPosition(position).toString();
                playersAdapter.filterPlayers(filter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ivLogo = findViewById(R.id.app_logo);
        ivTeamLogo = findViewById(R.id.iv_team_logo);
        tvSelectTeam = findViewById(R.id.select_team_tv);
        rvPlayersList = findViewById(R.id.rv_players_list);

        playersList = new ArrayList<>();
        playersAdapter = new PlayersAdapter(this, playersList, this);

        llmPlayersList = new GridLayoutManager(this, 1);
        rvPlayersList.setLayoutManager(llmPlayersList);
        rvPlayersList.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(this, 10), true));
        rvPlayersList.setItemAnimator(new DefaultItemAnimator());
        rvPlayersList.setAdapter(playersAdapter);

        pbLoading = findViewById(R.id.pb_loading);
    }

    @Override
    public void populateDrawerViewList(List<Team> teamList) {
        TeamsAdapter adapter = new TeamsAdapter(this, teamList, this);
        rvTeamsList.setLayoutManager(llmTeamsList);
        rvTeamsList.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort_alpha) {
            playersAdapter.sortPlayers(SORT_TYPE.NAME, ascending = !ascending);
            return true;
        }
        if (id == R.id.action_sort_numeric) {
            playersAdapter.sortPlayers(SORT_TYPE.JERSEY, ascending = !ascending);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
        tvSelectTeam.setVisibility(View.GONE);
        ivLogo.setVisibility(View.GONE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @Override
    public void populatePlayersList(List<Player> playerList, String teamName) {
        drawer.closeDrawer(GravityCompat.START);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(teamName);
            Drawable drawable = Utils.getDrawable(this, teamName);
            ivTeamLogo.setImageDrawable(drawable);
        }
        playersList.clear();
        playersList.addAll(playerList);
        playersAdapter.notifyDataSetChanged();
    }

    @Override
    public void populateSpinner(List<String> filters) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filters);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        llSpinner.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTeamClick(int teamId, String teamName) {
        playersListPresenter.requestPlayersListFromServer(teamId, teamName);
    }

    @Override
    public void onPlayerClick(int playerId) {
        Log.v("TAG", "Clicked " + playerId);
        playersListPresenter.requestPlayerInfoFromServer(playerId);
    }

    @Override
    public void showPlayerInfoDialog(PlayerInfo playerInfo, Locale locale) {
        PlayerInfoDialog dialog = new PlayerInfoDialog(this, playerInfo, locale);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.show();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
        Toast.makeText(this, getString(R.string.communication_error), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        teamListPresenter.onDestroy();
        playersListPresenter.onDestroy();
    }
}
