package fr.oni.bored.select;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fr.oni.bored.BaseFragment;
import fr.oni.bored.R;
import fr.oni.bored.model.Category;
import fr.oni.bored.select.adapter.CategoryAdapter;


public class SelectCategoriesFragment extends BaseFragment {

    @InjectView(R.id.select_categories_recyclerView)
    protected RecyclerView recyclerView;

    @InjectView(R.id.select_categories_select_all_button)
    protected FloatingActionButton selectAllCategoriesButton;

    @InjectView(R.id.select_categories_random_button)
    protected FloatingActionButton randomActivityButton;
    @Arg
    ArrayList<Category> categories;
    Map<Category, Boolean> selectedCategories;
    private OnSelectCategoriesInteractionListener listener;
    private CategoryAdapter adapter;

    public SelectCategoriesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_categories, container, false);
        ButterKnife.inject(this, view);
        selectedCategories = new LinkedHashMap<>();
        for (Category category : categories) {
            selectedCategories.put(category, false);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new CategoryAdapter(categories, listener, selectedCategories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnSelectCategoriesInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSelectCategoriesInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @OnClick(R.id.select_categories_select_all_button)
    public void selectAllCategories() {
        selectedCategories.clear();
        for (Category category : categories) {
            selectedCategories.put(category, true);
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.select_categories_random_button)
    public void randomizeActivities() {
        List<fr.oni.bored.model.Activity> activities = new ArrayList<>();
        for (Map.Entry<Category, Boolean> category : selectedCategories.entrySet()) {
            if (category.getValue()) {
                activities.addAll(category.getKey().activities());
            }
        }
        Collections.shuffle(activities);
        listener.onRandomizeActivities(new HashSet<>(activities.subList(0, 4)));
    }

    public interface OnSelectCategoriesInteractionListener {
        void onRandomizeActivities(Set<fr.oni.bored.model.Activity> activities);

        void onViewActivities(Category category);

        void onEditCategory(Category category);
    }

}
