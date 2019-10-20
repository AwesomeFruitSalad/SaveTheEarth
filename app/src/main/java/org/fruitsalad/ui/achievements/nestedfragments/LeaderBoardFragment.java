package org.fruitsalad.ui.achievements.nestedfragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.fruitsalad.R;
import org.fruitsalad.model.SaviourOfEarth;
import org.fruitsalad.ui.achievements.adapters.LeaderBoardRecyclerViewAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderBoardFragment extends Fragment {

    private List<SaviourOfEarth> users;

    public LeaderBoardFragment() {
        // Required empty public constructor
    }

    public LeaderBoardFragment(List<SaviourOfEarth> users) {
        this.users = users;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leader_board, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_leaderboard);
        recyclerView.setAdapter(new LeaderBoardRecyclerViewAdapter(users));
        return view;
    }

}
