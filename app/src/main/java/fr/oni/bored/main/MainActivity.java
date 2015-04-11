package fr.oni.bored.main;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import fr.oni.bored.BuildConfig;
import fr.oni.bored.R;
import fr.oni.bored.data.Activity;
import fr.oni.bored.data.Category;
import fr.oni.bored.data.DatabaseHelper;


public class MainActivity extends ActionBarActivity implements MainCategoriesFragment.OnViewCategoriesInteractionListener, MainActivitiesFragment.OnViewActivitiesInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(this.getClass().getName(), "onCreate begin");
        super.onCreate(savedInstanceState);

        if(BuildConfig.DEBUG) {
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
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            MainCategoriesFragment fragment = null;
            try {
                fragment = new MainCategoriesFragment();
            } catch (SQLException e) {
                Log.e(this.getClass().getName(), e.getMessage(), e);
            }
            transaction.replace(R.id.main_fragment, fragment);
            transaction.commit();
        }
        Log.d(this.getClass().getName(), "onCreate end");
    }

    private void createSampleCategories() throws SQLException {
        DatabaseHelper dbHelper = OpenHelperManager.getHelper(getApplicationContext(), DatabaseHelper.class);
        Dao<Category, Integer> categoriesDao = dbHelper.getCategoryDao();
        Category category1 = new Category("Title 1", "Description 1");
        Category category2 = new Category("Title 2", "Description 2");
        categoriesDao.create(category1);
        categoriesDao.create(category2);
        Dao<Activity, Integer> activitiesDao = dbHelper.getActivityDao();
        Activity activity1 = new Activity("Title 1", "Description 1", category1);
        Activity activity2 = new Activity("Title 2", "Description 2", category1);
        Activity activity3 = new Activity("Title 3", "Description 3", category2);
        Activity activity4 = new Activity("Title 4", "Description 4", category2);
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

    }

    @Override
    public void onViewActivities(fr.oni.bored.model.Category category) {
        Log.d(this.getClass().getName(), "onViewActivities begin");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MainActivitiesFragment fragment = MainActivitiesFragment.newInstance(category);
        transaction.replace(R.id.main_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        Log.d(this.getClass().getName(), "onViewActivities end");
    }

    @Override
    public void onEditActivity(fr.oni.bored.model.Activity activity) {

    }

    @Override
    public void onViewActivity(fr.oni.bored.model.Activity activity) {

    }
}
