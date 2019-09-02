package com.zeeshanz.nhl.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeeshanz.nhl.R;
import com.zeeshanz.nhl.model.Team;
import com.zeeshanz.nhl.utils.Utils;

import java.util.Comparator;
import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {
    private List<Team> teamsList;
    private Context context;

    public interface OnTeamClickListener {
        void onTeamClick(int teamId, String teamName);
    }

    @NonNull
    private final TeamsAdapter.OnTeamClickListener teamClickListener;

    public TeamsAdapter(Context context, List<Team> teamsList, @NonNull OnTeamClickListener listener) {
        this.context = context;
        this.teamsList = sortAlphabetically(teamsList);
        this.teamClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nav_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String teamName = teamsList.get(position).getName();
        Drawable drawable = Utils.getDrawable(context, teamName);
        holder.ivTeamLogo.setImageDrawable(drawable);
        holder.tvTeamName.setText(teamName);
    }

    @Override
    public int getItemCount() {
        return teamsList.size();
    }

    private List<Team> sortAlphabetically(List<Team> teamsList) {
        teamsList.sort(Comparator.comparing(Team::getName));
        return teamsList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivTeamLogo;
        TextView tvTeamName;

        ViewHolder(View itemView) {
            super(itemView);
            ivTeamLogo = itemView.findViewById(R.id.iv_team_logo);
            tvTeamName = itemView.findViewById(R.id.tv_team_name);

            tvTeamName.setOnClickListener(v -> {
                Team team = teamsList.get(getAdapterPosition());
                int teamId = team.getId();
                String teamName = team.getName();
                teamClickListener.onTeamClick(teamId, teamName);
            });
        }
    }
}
