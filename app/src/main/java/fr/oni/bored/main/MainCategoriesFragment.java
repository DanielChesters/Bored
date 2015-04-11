package fr.oni.bored.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fr.oni.bored.R;
import fr.oni.bored.data.Category;
import fr.oni.bored.data.DatabaseHelper;
import fr.oni.bored.main.adapter.CategoryAdapter;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainCategoriesFragment extends Fragment {

    @InjectView(R.id.main_categories_recyclerView) protected RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Category> categories;
    private CategoryAdapter adapter;

    public MainCategoriesFragment() throws SQLException {
        Log.d(this.getClass().getName(), "MainCategoriesFragment new instance begin");
        if (categories == null) {
            DatabaseHelper dbHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
            Dao<Category, Integer> categoriesDao = dbHelper.getCategoryDao();
            categories = categoriesDao.queryForAll();
        }
        Log.d(this.getClass().getName(), "MainCategoriesFragment new instance end");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(this.getClass().getName(), "onCreateView begin");
        final View view = inflater.inflate(R.layout.fragment_main_categories, container, false);
        ButterKnife.inject(this, view);

        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new CategoryAdapter(categories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        Log.d(this.getClass().getName(), "onCreateView end");
        return view;
    }
}
