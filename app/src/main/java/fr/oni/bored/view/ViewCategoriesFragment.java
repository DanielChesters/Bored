package fr.oni.bored.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fr.oni.bored.BaseFragment;
import fr.oni.bored.R;
import fr.oni.bored.model.Category;
import fr.oni.bored.view.adapter.CategoryAdapter;


public class ViewCategoriesFragment extends BaseFragment {

    @InjectView(R.id.main_categories_recyclerView)
    protected RecyclerView recyclerView;

    @Arg
    ArrayList<Category> categories;

    private OnViewCategoriesInteractionListener listener;

    public ViewCategoriesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(this.getClass().getName(), "onCreateView begin");
        final View view = inflater.inflate(R.layout.fragment_view_categories, container, false);
        ButterKnife.inject(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        CategoryAdapter adapter = new CategoryAdapter(categories, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        Log.d(this.getClass().getName(), "onCreateView end");
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.listener = (OnViewCategoriesInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnViewCategoriesInteractionListener");
        }
    }

    public interface OnViewCategoriesInteractionListener {
        void onEditCategory(fr.oni.bored.model.Category category);

        void onViewActivities(fr.oni.bored.model.Category category);
    }
}
