package com.zeeshanz.nhl.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zeeshanz.nhl.R;
import com.zeeshanz.nhl.model.Player;
import com.zeeshanz.nhl.utils.SORT_TYPE;
import com.zeeshanz.nhl.utils.Utils;
import com.zeeshanz.nhl.view.MainActivity;

import java.util.List;

@SuppressWarnings("all")
public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.MyViewHolder> {

    private MainActivity mainActivity;
    private List<Player> playersList, originalPlayersList;

    public interface PlayerClickListener {
        void onPlayerClick(int position);
    }

    @NonNull
    private final PlayersAdapter.PlayerClickListener playerClickListener;

    public PlayersAdapter(MainActivity mainActivity, List<Player> players, @NonNull PlayerClickListener listener) {
        this.mainActivity = mainActivity;
        this.playersList = players;
        this.originalPlayersList = players;
        this.playerClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Player player = playersList.get(position);
        String fullName = player.getPerson().getFullName();
        String positionName = player.getPosition().getName();
        String positionType = player.getPosition().getType();
        String positionDetail = (positionName.equals(positionType) ? positionType : positionName + " " + positionType); // Added this check to remove duplication where name and type is the same.
        String listing = "#" + String.valueOf(player.getJerseyNumber());

        holder.tvPlayerName.setText(fullName);
        holder.tvListing.setText(listing);
        holder.tvPosition.setText(positionDetail);
        holder.itemView.setOnClickListener(view -> mainActivity.onPlayerClick(position));
    }

    @Override
    public int getItemCount() {
        return playersList.size();
    }

    /**
     * This function will set parameters for Filter
     *
     * @param sortType  - either by name or by jersey number
     * @param ascending - if true, sort ascending, otherwise descending
     */
    public void sortPlayers(SORT_TYPE sortType, boolean ascending) {
        List<Player> sortedPlayers = Utils.sortPlayers(sortType, playersList, ascending);
        playersList = sortedPlayers;
        PlayersAdapter.this.notifyDataSetChanged();
    }

    public void filterPlayers(String filter) {
        List<Player> filteredPlayers = Utils.filterPlayers(originalPlayersList, filter);
        playersList = filteredPlayers;
        PlayersAdapter.this.notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvPlayerName;
        TextView tvListing;
        TextView tvPosition;

        MyViewHolder(View itemView) {
            super(itemView);
            tvPlayerName = itemView.findViewById(R.id.tv_player_name);
            tvPosition = itemView.findViewById(R.id.tv_position);
            tvListing = itemView.findViewById(R.id.tv_listing);

            tvPlayerName.setOnClickListener(v -> {
                Player player = playersList.get(getAdapterPosition());
                int playerId = player.getPerson().getId();
                playerClickListener.onPlayerClick(playerId);
            });
        }
    }
}
