package fr.oni.bored.select;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.oni.bored.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class SelectActivitiesFragment extends Fragment {

    public SelectActivitiesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_activities, container, false);
    }
}
