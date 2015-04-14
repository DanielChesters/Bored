package fr.oni.bored;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import fr.oni.bored.edit.EditActivityFragment;
import fr.oni.bored.edit.EditActivityFragmentBuilder;
import fr.oni.bored.edit.EditCategoryFragment;
import fr.oni.bored.edit.EditCategoryFragmentBuilder;
import fr.oni.bored.model.Activity;
import fr.oni.bored.model.Category;
import fr.oni.bored.view.ViewActivitiesFragment;
import fr.oni.bored.view.ViewActivitiesFragmentBuilder;
import fr.oni.bored.view.ViewActivityFragment;
import fr.oni.bored.view.ViewActivityFragmentBuilder;
import fr.oni.bored.view.ViewCategoriesFragment;
import fr.oni.bored.view.ViewCategoriesFragmentBuilder;


public class MainActivity extends ActionBarActivity
        implements ViewCategoriesFragment.OnViewCategoriesInteractionListener,
        ViewActivitiesFragment.OnViewActivitiesInteractionListener,
        ViewActivityFragment.OnViewActivityInteractionListener,
        EditCategoryFragment.OnEditCategoryInteractionListener,
        EditActivityFragment.OnEditActivityInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(this.getClass().getName(), "onCreate begin");
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
        ActiveAndroid.initialize(this);
        createSampleCategories();

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            goToViewCategoriesFragment(false);
        }
        Log.d(this.getClass().getName(), "onCreate end");
    }

    private void goToViewCategoriesFragment(boolean addToBackStack) {
        ArrayList<Category> categories = getCategories();
        ;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ViewCategoriesFragment fragment = new ViewCategoriesFragmentBuilder(categories)
                .build();
        transaction.replace(R.id.main_fragment, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    private ArrayList<Category> getCategories() {
        List<Category> categories = Category.loadAll();
        return new ArrayList<>(categories);
    }

    private void createSampleCategories() {
        Category category1 = new Category("Category 1", "Description 1");
        Category category2 = new Category("Category 2", "Description 2");
        category1.save();
        category2.save();
        Activity activity1 = new Activity("Activity 1", "Description 1", category1);
        Activity activity2 = new Activity("Activity 2", "Description 2", category1);
        Activity activity3 = new Activity("Activity 3", "Description 3", category2);
        Activity activity4 = new Activity("Activity 4", "Description 4", category2);
        activity1.save();
        activity2.save();
        activity3.save();
        activity4.save();
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
    public void onEditCategory(Category category) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        EditCategoryFragment fragment = new EditCategoryFragmentBuilder()
                .category(category).build();
        transaction.replace(R.id.main_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onCreateCategory() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        EditCategoryFragment fragment = new EditCategoryFragmentBuilder().build();
        transaction.replace(R.id.main_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onViewActivities(Category category) {
        Log.d(this.getClass().getName(), "onViewActivities begin");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ViewActivitiesFragment fragment = new ViewActivitiesFragmentBuilder(category).build();
        transaction.replace(R.id.main_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        Log.d(this.getClass().getName(), "onViewActivities end");
    }

    @Override
    public void onCreateActivity(Category category) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        EditActivityFragment fragment = new EditActivityFragmentBuilder().category(category).build();
        transaction.replace(R.id.main_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onViewActivity(Activity activity) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ViewActivityFragment fragment = new ViewActivityFragmentBuilder(activity).build();
        transaction.replace(R.id.main_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onEditCategoryDone() {
        goToViewCategoriesFragment(true);
    }

    @Override
    public void onEditActivityDone() {
        goToViewCategoriesFragment(true);
    }

    @Override
    public void onEditActivity(Activity activity) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        EditActivityFragment fragment = new EditActivityFragmentBuilder().activity(activity).build();
        transaction.replace(R.id.main_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
