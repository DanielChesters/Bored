package fr.oni.bored.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hannesdorfmann.fragmentargs.annotation.Arg;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fr.oni.bored.BaseFragment;
import fr.oni.bored.R;
import fr.oni.bored.model.Category;

public class EditCategoryFragment extends BaseFragment {
    @InjectView(R.id.edit_category_title)
    protected EditText titleText;

    @InjectView(R.id.edit_category_description)
    protected EditText descriptionText;

    @Arg(required = false)
    Category category;


    public EditCategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_category, container, false);
        ButterKnife.inject(this, view);
        if (category != null) {
            titleText.setText(category.title);
            descriptionText.setText(category.description);
        }
        return view;
    }

    @OnClick(R.id.edit_category_done)
    public void saveCategory() {
        if (category == null) {
            category = new Category();
        }
        category.title = titleText.getText().toString();
        category.description = descriptionText.getText().toString();
        category.save();
        listener.onEditCategoryDone();
    }
}
