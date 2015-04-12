package fr.oni.bored.view;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fr.oni.bored.R;


public class ViewActivityFragment extends Fragment {
    private static final String ARG_ACTIVITY = "activity";

    private fr.oni.bored.model.Activity activity;
    private OnViewActivityInteractionListener listener;
    @InjectView(R.id.view_activity_title) protected TextView titleView;
    @InjectView(R.id.view_activity_description) protected TextView descriptionView;

    public static ViewActivityFragment newInstance(fr.oni.bored.model.Activity activity) {
        ViewActivityFragment fragment = new ViewActivityFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ACTIVITY, activity);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            activity = getArguments().getParcelable(ARG_ACTIVITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_view_activity, container, false);
        ButterKnife.inject(this, view);
        titleView.setText(activity.title);
        descriptionView.setText(activity.description);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnViewActivityInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnViewActivityInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnViewActivityInteractionListener {
        void onEditActivity(fr.oni.bored.model.Activity activity);
    }

}
