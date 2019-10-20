package org.fruitsalad.ui.achievements.nestedfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import org.fruitsalad.R;
import org.fruitsalad.model.SaviourOfEarth;
import org.fruitsalad.ui.achievements.adapters.AchievementRecyclerViewAdapter;

/** A simple {@link Fragment} subclass. */
public class AchievementFragment extends Fragment {

    private SaviourOfEarth user;

    public AchievementFragment() {
        // Required empty public constructor
    }

    public AchievementFragment(SaviourOfEarth user) {
        this.user = user;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_achievement, container, false);
        RecyclerView achievementsRecyclerView = view.findViewById(R.id.recycler_achievements);
        achievementsRecyclerView.setAdapter(new AchievementRecyclerViewAdapter(user.getAchievements()));
        return view;
    }
}
