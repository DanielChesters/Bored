package fr.oni.bored.edit;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.oni.bored.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class EditCategoryFragment extends Fragment {

    public EditCategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_category, container, false);
    }
}
