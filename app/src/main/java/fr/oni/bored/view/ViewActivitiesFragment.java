package fr.oni.bored.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.melnykov.fab.FloatingActionButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fr.oni.bored.BaseFragment;
import fr.oni.bored.R;
import fr.oni.bored.model.Category;
import fr.oni.bored.view.adapter.ActivityAdapter;

public class ViewActivitiesFragment extends BaseFragment {
    @InjectView(R.id.view_activities_recyclerView)
    protected RecyclerView recyclerView;
    @InjectView(R.id.view_activities_add_button)
    protected FloatingActionButton addActivityButton;

    @Arg
    Category category;
    private OnViewActivitiesInteractionListener listener;

    public ViewActivitiesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_view_activities, container, false);
        ButterKnife.inject(this, view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ActivityAdapter adapter = new ActivityAdapter(category.activities(), listener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        addActivityButton.attachToRecyclerView(recyclerView);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.listener = (OnViewActivitiesInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnViewActivitiesInteractionListener");
        }
    }

    @OnClick(R.id.view_activities_add_button)
    public void addActivity() {
        listener.onEditActivity(null);
    }


    public interface OnViewActivitiesInteractionListener {
        void onEditActivity(fr.oni.bored.model.Activity activity);

        void onViewActivity(fr.oni.bored.model.Activity activity);
    }

}
