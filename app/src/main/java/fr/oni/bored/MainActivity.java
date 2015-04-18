package fr.oni.bored;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.activeandroid.ActiveAndroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.oni.bored.edit.EditActivityFragmentBuilder;
import fr.oni.bored.edit.EditCategoryFragmentBuilder;
import fr.oni.bored.model.Activity;
import fr.oni.bored.model.Category;
import fr.oni.bored.select.SelectActivitiesFragmentBuilder;
import fr.oni.bored.select.SelectCategoriesFragmentBuilder;
import fr.oni.bored.view.ViewActivitiesFragmentBuilder;
import fr.oni.bored.view.ViewActivityFragmentBuilder;
import fr.oni.bored.view.ViewCategoriesFragmentBuilder;


public class MainActivity extends ActionBarActivity implements OnInteractionListener {

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
            ArrayList<Category> categories = getCategories();
            goToFragment(new ViewCategoriesFragmentBuilder(categories).build(), false);
        }
        Log.d(this.getClass().getName(), "onCreate end");
    }

    private void goToFragment(BaseFragment fragment) {
        goToFragment(fragment, true);
    }

    private void goToFragment(BaseFragment fragment, boolean addToBackStack) {
        goToFragment(R.id.main_fragment, fragment, addToBackStack);
    }

    private void goToFragment(@IdRes int id, BaseFragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(id, fragment);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_randomize:
                goToRandomize();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void goToRandomize() {
        ArrayList<Category> categories = new ArrayList<>(Category.loadAll());
        goToFragment(new SelectCategoriesFragmentBuilder(categories).build());
    }

    @Override
    public void onEditCategory(Category category) {
        goToFragment(new EditCategoryFragmentBuilder().category(category).build());
    }

    @Override
    public void onCreateCategory() {
        goToFragment(new EditCategoryFragmentBuilder().build());
    }

    @Override
    public void onViewActivities(Category category) {
        goToFragment(new ViewActivitiesFragmentBuilder(category).build());
    }

    @Override
    public void onCreateActivity(Category category) {
        goToFragment(new EditActivityFragmentBuilder().build());
    }

    @Override
    public void onViewActivity(Activity activity) {
        goToFragment(new ViewActivityFragmentBuilder(activity).build());
    }

    @Override
    public void onEditDone() {
        ArrayList<Category> categories = getCategories();
        goToFragment(new ViewCategoriesFragmentBuilder(categories).build());
    }

    @Override
    public void onEditActivity(Activity activity) {
        goToFragment(new EditActivityFragmentBuilder().activity(activity).build());
    }

    @Override
    public void onRandomizeActivities(List<Activity> activities) {
        goToFragment(new SelectActivitiesFragmentBuilder(new ArrayList<>(activities)).build());
    }
}
