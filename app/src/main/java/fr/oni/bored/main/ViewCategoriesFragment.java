package fr.oni.bored.main;

import android.app.Activity;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fr.oni.bored.R;
import fr.oni.bored.data.Category;
import fr.oni.bored.data.DatabaseHelper;
import fr.oni.bored.main.adapter.CategoryAdapter;


public class ViewCategoriesFragment extends Fragment {

    @InjectView(R.id.main_categories_recyclerView)
    protected RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<fr.oni.bored.model.Category> categories;
    private CategoryAdapter adapter;
    private OnViewCategoriesInteractionListener listener;

    public ViewCategoriesFragment() throws SQLException {
        Log.d(this.getClass().getName(), "ViewCategoriesFragment new instance begin");
        if (categories == null) {
            DatabaseHelper dbHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
            Dao<Category, Integer> categoriesDao = dbHelper.getCategoryDao();
            List<Category> data = categoriesDao.queryForAll();
            Bundle b = new Bundle();
            ArrayList<fr.oni.bored.model.Category> categories = new ArrayList<>();
            for (Category category : data) {
                categories.add(new fr.oni.bored.model.Category(category));
            }
            b.putParcelableArrayList("CATEGORIES", categories);
            setArguments(b);
        }
        Log.d(this.getClass().getName(), "ViewCategoriesFragment new instance end");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(this.getClass().getName(), "onCreate new instance begin");
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        this.categories = b.getParcelableArrayList("CATEGORIES");
        Log.d(this.getClass().getName(), "onCreate new instance end");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(this.getClass().getName(), "onCreateView begin");
        final View view = inflater.inflate(R.layout.fragment_view_categories, container, false);
        ButterKnife.inject(this, view);

        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new CategoryAdapter(categories, listener);
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
