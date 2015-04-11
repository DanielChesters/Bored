package fr.oni.bored.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fr.oni.bored.R;
import fr.oni.bored.main.adapter.ActivityAdapter;
import fr.oni.bored.model.Category;

public class ViewActivitiesFragment extends Fragment {
    private static final String ARG_CATEGORY = "category";
    @InjectView(R.id.view_activities_recyclerView)
    protected RecyclerView recyclerView;
    private Category category;
    private OnViewActivitiesInteractionListener listener;

    public ViewActivitiesFragment() {

    }

    public static ViewActivitiesFragment newInstance(Category category) {
        ViewActivitiesFragment fragment = new ViewActivitiesFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getParcelable(ARG_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_view_activities, container, false);
        ButterKnife.inject(this, view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ActivityAdapter adapter = new ActivityAdapter(category.activities, listener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
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


    public interface OnViewActivitiesInteractionListener {
        void onEditActivity(fr.oni.bored.model.Activity activity);

        void onViewActivity(fr.oni.bored.model.Activity activity);
    }

}
