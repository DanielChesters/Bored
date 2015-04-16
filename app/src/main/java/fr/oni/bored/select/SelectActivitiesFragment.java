package fr.oni.bored.select;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fr.oni.bored.BaseFragment;
import fr.oni.bored.R;
import fr.oni.bored.model.Activity;
import fr.oni.bored.view.adapter.ActivityAdapter;


public class SelectActivitiesFragment extends BaseFragment {

    @InjectView(R.id.select_activities_recyclerView)
    protected RecyclerView recyclerView;

    @Arg
    ArrayList<Activity> activities;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.action_randomize);
        item.setVisible(false);
        getActivity().invalidateOptionsMenu();
    }

    public SelectActivitiesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_select_activities, container, false);
        ButterKnife.inject(this, view);
        ActivityAdapter adapter = new ActivityAdapter(activities, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        setHasOptionsMenu(true);
        return view;
    }
}
