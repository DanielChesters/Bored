package fr.oni.bored;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import fr.oni.bored.view.ViewActivitiesFragment;
import fr.oni.bored.view.ViewActivityFragment;
import fr.oni.bored.view.ViewCategoriesFragment;
import fr.oni.bored.view.adapter.ActivityAdapter;
import fr.oni.bored.view.adapter.CategoryAdapter;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class ViewTest {
    MainActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @After
    public void end() {
        activity.onDestroy();
    }

    @Test
    @SmallTest
    public void viewCategoriesTest() {
        RecyclerView recyclerView = getCategoriesRecyclerView();
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        Assert.assertTrue(fragment instanceof ViewCategoriesFragment);
        Assert.assertTrue(recyclerView.getAdapter() instanceof CategoryAdapter);
        CategoryAdapter.ViewHolder viewHolder = (CategoryAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(0);
        TextView titleView = viewHolder.getTitleView();
        TextView descriptionView = viewHolder.getDescriptionView();
        Assert.assertEquals("Category 1", titleView.getText());
        Assert.assertEquals("Description 1", descriptionView.getText());
    }

    @Test
    @SmallTest
    public void viewActivitiesTest() {
        RecyclerView recyclerView = getCategoriesRecyclerView();
        CategoryAdapter.ViewHolder viewHolder = (CategoryAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(0);
        View v = viewHolder.itemView;
        View textView = v.findViewById(R.id.view_category_row_text);
        textView.performClick();
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        recyclerView = getActivitiesRecyclerView();
        Assert.assertTrue(fragment instanceof ViewActivitiesFragment);
        Assert.assertTrue(recyclerView.getAdapter() instanceof ActivityAdapter);
        ActivityAdapter.ViewHolder viewHolderActivity = (ActivityAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(0);
        TextView titleView = viewHolderActivity.getTitleView();
        TextView descriptionView = viewHolderActivity.getDescriptionView();
        Assert.assertEquals("Activity 1", titleView.getText());
        Assert.assertEquals("Description 1", descriptionView.getText());
    }

    @Test
    @SmallTest
    public void viewActivityTest() {
        RecyclerView recyclerView = getCategoriesRecyclerView();
        CategoryAdapter.ViewHolder categoryHolder = (CategoryAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(0);
        View v = categoryHolder.itemView;
        View textView = v.findViewById(R.id.view_category_row_text);
        textView.performClick();
        recyclerView = getActivitiesRecyclerView();
        ActivityAdapter.ViewHolder activityHolder = (ActivityAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(0);
        View v2 = activityHolder.itemView;
        View textView2 = v2.findViewById(R.id.view_activity_row_text);
        textView2.performClick();
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        Assert.assertTrue(fragment instanceof ViewActivityFragment);
        final View fragmentView = fragment.getView();
        TextView titleView = (TextView) fragmentView.findViewById(R.id.view_activity_title);
        TextView descriptionView = (TextView) fragmentView.findViewById(R.id.view_activity_description);
        Assert.assertEquals("Activity 1", titleView.getText());
        Assert.assertEquals("Description 1", descriptionView.getText());

    }

    private RecyclerView getCategoriesRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) Shadows.shadowOf(activity).findViewById(R.id.view_categories_recyclerView);
        recyclerView.measure(0, 0);
        recyclerView.layout(0, 0, 100, 10000);
        return recyclerView;
    }

    private RecyclerView getActivitiesRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) Shadows.shadowOf(activity).findViewById(R.id.view_activities_recyclerView);
        recyclerView.measure(0, 0);
        recyclerView.layout(0, 0, 100, 10000);
        return recyclerView;
    }

}
