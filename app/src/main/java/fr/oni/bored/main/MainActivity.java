package fr.oni.bored.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import fr.oni.bored.R;
import fr.oni.bored.data.Category;
import fr.oni.bored.data.DatabaseHelper;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(this.getClass().getName(), "onCreate begin");
        super.onCreate(savedInstanceState);

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
}
