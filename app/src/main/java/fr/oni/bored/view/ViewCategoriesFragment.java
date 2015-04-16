package fr.oni.bored.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fr.oni.bored.BaseFragment;
import fr.oni.bored.R;
import fr.oni.bored.model.Category;
import fr.oni.bored.view.adapter.CategoryAdapter;


public class ViewCategoriesFragment extends BaseFragment {

    @InjectView(R.id.view_categories_recyclerView)
    protected RecyclerView recyclerView;

    @InjectView(R.id.view_categories_add_button)
    protected FloatingActionButton addCategoryButton;

    @Arg
    ArrayList<Category> categories;


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

        addCategoryButton.attachToRecyclerView(recyclerView);

        Log.d(this.getClass().getName(), "onCreateView end");
        return view;
    }

    @OnClick(R.id.view_categories_add_button)
    public void addCategory() {
        listener.onEditCategory(null);
    }

}
