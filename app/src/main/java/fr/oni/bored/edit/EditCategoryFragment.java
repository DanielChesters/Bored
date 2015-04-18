package fr.oni.bored.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fr.oni.bored.BaseFragment;
import fr.oni.bored.R;
import fr.oni.bored.model.Category;

public class EditCategoryFragment extends BaseFragment {
    @InjectView(R.id.edit_category_title)
    protected MaterialEditText titleText;

    @InjectView(R.id.edit_category_description)
    protected MaterialEditText descriptionText;

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
        final String title = titleText.getText().toString();
        if (title.isEmpty()) {
            titleText.setError("Title is required");
        } else {
            category.title = title;
            category.description = descriptionText.getText().toString();
            category.save();
            listener.onEditDone();
        }

    }
}
