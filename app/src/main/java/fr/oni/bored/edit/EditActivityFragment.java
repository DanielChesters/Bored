package fr.oni.bored.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.hannesdorfmann.fragmentargs.annotation.Arg;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fr.oni.bored.BaseFragment;
import fr.oni.bored.R;
import fr.oni.bored.model.Category;

public class EditActivityFragment extends BaseFragment {
    @InjectView(R.id.edit_activity_title)
    protected EditText titleText;

    @InjectView(R.id.edit_activity_description)
    protected EditText descriptionText;

    @InjectView(R.id.edit_activity_category)
    protected Spinner categorySpinner;

    @Arg(required = false)
    fr.oni.bored.model.Activity activity;

    @Arg(required = false)
    Category category;

    public EditActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_activity, container, false);
        ButterKnife.inject(this, view);
        final List<Category> categories = Category.loadAll();
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(this.getActivity(),
                R.layout.edit_activity_category_row, categories);
        categorySpinner.setAdapter(categoryArrayAdapter);

        if (activity != null) {
            titleText.setText(activity.title);
            descriptionText.setText(activity.description);
            categorySpinner.setSelection(categories.indexOf(activity.category));
        } else if (category != null) {
            categorySpinner.setSelection(categories.indexOf(category));
        }
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @OnClick(R.id.edit_activity_done)
    public void saveActivity() {
        if (activity == null) {
            activity = new fr.oni.bored.model.Activity();
        }
        activity.category = (Category) categorySpinner.getSelectedItem();
        activity.title = titleText.getText().toString();
        activity.description = descriptionText.getText().toString();

        activity.save();
        listener.onEditActivityDone();
    }
}
