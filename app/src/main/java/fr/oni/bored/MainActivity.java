package fr.oni.bored;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.oni.bored.data.Activity;
import fr.oni.bored.data.Category;
import fr.oni.bored.data.DatabaseHelper;
import fr.oni.bored.view.ViewActivitiesFragment;
import fr.oni.bored.view.ViewActivitiesFragmentBuilder;
import fr.oni.bored.view.ViewActivityFragment;
import fr.oni.bored.view.ViewActivityFragmentBuilder;
import fr.oni.bored.view.ViewCategoriesFragment;
import fr.oni.bored.view.ViewCategoriesFragmentBuilder;


public class MainActivity extends ActionBarActivity
        implements ViewCategoriesFragment.OnViewCategoriesInteractionListener,
        ViewActivitiesFragment.OnViewActivitiesInteractionListener,
        ViewActivityFragment.OnViewActivityInteractionListener {

    private DatabaseHelper dbHelper;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(this.getClass().getName(), "onCreate begin");
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }

        try {
            createSampleCategories();
        } catch (SQLException e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
        }

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            ArrayList<fr.oni.bored.model.Category> categories = null;
            try {
                categories = getCategories();
            } catch (SQLException e) {
                Log.e(this.getClass().getName(), e.getMessage(), e);
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ViewCategoriesFragment fragment = new ViewCategoriesFragmentBuilder(categories)
                    .build();
            transaction.replace(R.id.main_fragment, fragment);
            transaction.commit();
        }
        Log.d(this.getClass().getName(), "onCreate end");
    }

    private ArrayList<fr.oni.bored.model.Category> getCategories() throws SQLException {
        Dao<Category, Integer> categoriesDao = getDbHelper().getCategoryDao();
        List<Category> data = categoriesDao.queryForAll();
        ArrayList<fr.oni.bored.model.Category> categories = new ArrayList<>();
        for (Category category : data) {
            categories.add(new fr.oni.bored.model.Category(category));
        }
        return categories;
    }

    private void createSampleCategories() throws SQLException {
        Dao<Category, Integer> categoriesDao = getDbHelper().getCategoryDao();
        Category category1 = new Category("Category 1", "Description 1");
        Category category2 = new Category("Category 2", "Description 2");
        categoriesDao.create(category1);
        categoriesDao.create(category2);
        Dao<Activity, Integer> activitiesDao = getDbHelper().getActivityDao();
        Activity activity1 = new Activity("Activity 1", "Description 1", category1);
        Activity activity2 = new Activity("Activity 2", "Description 2", category1);
        Activity activity3 = new Activity("Activity 3", "Description 3", category2);
        Activity activity4 = new Activity("Activity 4", "Description 4", category2);
        activitiesDao.create(activity1);
        activitiesDao.create(activity2);
        activitiesDao.create(activity3);
        activitiesDao.create(activity4);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEditCategory(fr.oni.bored.model.Category category) {
        Toast.makeText(this, "onEditCategory", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewActivities(fr.oni.bored.model.Category category) {
        Log.d(this.getClass().getName(), "onViewActivities begin");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ViewActivitiesFragment fragment = new ViewActivitiesFragmentBuilder(category).build();
        transaction.replace(R.id.main_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        Log.d(this.getClass().getName(), "onViewActivities end");
    }

    @Override
    public void onEditActivity(fr.oni.bored.model.Activity activity) {
        Toast.makeText(this, "onEditActivity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewActivity(fr.oni.bored.model.Activity activity) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ViewActivityFragment fragment = new ViewActivityFragmentBuilder(activity).build();
        transaction.replace(R.id.main_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public DatabaseHelper getDbHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return dbHelper;
    }
}
