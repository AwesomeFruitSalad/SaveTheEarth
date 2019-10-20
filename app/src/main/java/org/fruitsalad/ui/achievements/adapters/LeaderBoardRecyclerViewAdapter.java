package org.fruitsalad.ui.achievements.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.fruitsalad.R;
import org.fruitsalad.model.SaviourOfEarth;

public class LeaderBoardRecyclerViewAdapter
        extends RecyclerView.Adapter<LeaderBoardRecyclerViewAdapter.LeaderBoardViewHolder> {

    private List<SaviourOfEarth> users;

    public LeaderBoardRecyclerViewAdapter(List<SaviourOfEarth> users) {
        this.users = users;
        Collections.sort(
                users,
                new Comparator<SaviourOfEarth>() {
                    @Override
                    public int compare(SaviourOfEarth userOne, SaviourOfEarth userTwo) {
                        return userTwo.getScore() - userOne.getScore();
                    }
                });
        Log.i("LEADERBOARD", users.toString());
    }

    @NonNull
    @Override
    public LeaderBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard, parent, false);

        return new LeaderBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardViewHolder holder, int position) {
        holder.saviourOfEarthName.setText(users.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class LeaderBoardViewHolder extends RecyclerView.ViewHolder {

        public TextView saviourOfEarthName;

        public LeaderBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            saviourOfEarthName = itemView.findViewById(R.id.textView_item_leaderboard);
        }
    }
}
