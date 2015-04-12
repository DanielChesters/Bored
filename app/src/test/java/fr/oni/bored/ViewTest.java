package fr.oni.bored;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.SmallTest;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import fr.oni.bored.view.ViewCategoriesFragment;
import fr.oni.bored.view.adapter.CategoryAdapter;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class ViewTest {
    MainActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    @SmallTest
    public void viewCategoriesTest() {
        RecyclerView recyclerView = (RecyclerView) Shadows.shadowOf(activity).findViewById(R.id.main_categories_recyclerView);
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        Assert.assertTrue(fragment instanceof ViewCategoriesFragment);
        Assert.assertTrue(recyclerView.getAdapter() instanceof CategoryAdapter);
    }

}
