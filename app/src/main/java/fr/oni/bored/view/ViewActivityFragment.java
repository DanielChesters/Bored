package fr.oni.bored.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.annotation.Arg;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fr.oni.bored.BaseFragment;
import fr.oni.bored.R;


public class ViewActivityFragment extends BaseFragment {
    private static final String ARG_ACTIVITY = "activity";
    @InjectView(R.id.view_activity_title)
    protected TextView titleView;
    @InjectView(R.id.view_activity_description)
    protected TextView descriptionView;
    @Arg
    fr.oni.bored.model.Activity activity;
    private OnViewActivityInteractionListener listener;

    public ViewActivityFragment() {
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

    @OnClick(R.id.view_activity_edit_button)
    public void editActivity() {
        listener.onEditActivity(activity);
    }

    public interface OnViewActivityInteractionListener {
        void onEditActivity(fr.oni.bored.model.Activity activity);
    }

}
