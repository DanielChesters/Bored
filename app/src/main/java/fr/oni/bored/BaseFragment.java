package fr.oni.bored;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hannesdorfmann.fragmentargs.FragmentArgs;

public class BaseFragment extends Fragment {
    protected OnInteractionListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.listener = (OnInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }
}
