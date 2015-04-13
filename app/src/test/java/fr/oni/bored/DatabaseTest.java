package fr.oni.bored;

import android.content.Context;
import android.test.suitebuilder.annotation.SmallTest;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import fr.oni.bored.model.Activity;
import fr.oni.bored.model.Category;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class DatabaseTest {

    @Before
    public void setUp() {
        Context context = ShadowApplication.getInstance().getApplicationContext();
        Configuration configuration = new Configuration.Builder(context)
                .setDatabaseName(null)
                .setDatabaseVersion(1)
                .create();
        ActiveAndroid.initialize(configuration);
    }

    @After
    public void afterTest() {
        ActiveAndroid.dispose();
    }

    @Test
    @SmallTest
    public void insertAnActivity() {
        Activity activity = TestUtils.createActivity(1);
        activity.save();
        Activity activityResult = Activity.load(Activity.class, activity.getId());
        TestUtils.compareActivity(activity, activityResult);
    }

    @Test
    @SmallTest
    public void insertACategory() {
        Category category = TestUtils.createCategory(1);
        category.save();
        Category categoryResult = Category.load(Category.class, category.getId());
        TestUtils.compareCategory(category, categoryResult);
    }

    @Test
    @SmallTest
    public void updateAnActivity() {
        Activity activity = TestUtils.createActivity(1);
        activity.save();
        System.out.println(activity.getId());
        activity.description = "Nope";
        activity.save();
        System.out.println(activity.getId());
        Activity activityResult = Activity.load(Activity.class, activity.getId());
        TestUtils.compareActivity(activity, activityResult);
    }

    @Test
    @SmallTest
    public void updateACategory() {
        Category category = TestUtils.createCategory(1);
        category.save();
        category.description = "Nope";
        category.save();

        Category categoryResult = Category.load(Category.class, category.getId());
        TestUtils.compareCategory(category, categoryResult);
    }

    @Test
    @SmallTest
    public void insertAnActivityAndACategory() {
        Category category = TestUtils.createCategory(1);
        Activity activity = TestUtils.createActivity(1);

        activity.category = category;

        category.save();
        activity.save();

        Activity activityResult = Activity.load(Activity.class, activity.getId());
        Category categoryResult = Category.load(Category.class, category.getId());

        TestUtils.compareActivity(activity, activityResult);
        TestUtils.compareCategory(category, categoryResult);

        TestUtils.compareCategory(category, activityResult.category);
        Assert.assertEquals(1, categoryResult.activities().size());
    }

    @Test
    @SmallTest
    public void insertTwoActivitiesAndACategory() {
        Category category = TestUtils.createCategory(1);
        Activity activity1 = TestUtils.createActivity(1);
        Activity activity2 = TestUtils.createActivity(2);

        activity1.category = category;
        activity2.category = category;

        category.save();
        activity1.save();
        activity2.save();

        Activity activity1Result = Activity.load(Activity.class, activity1.getId());
        Activity activity2Result = Activity.load(Activity.class, activity2.getId());
        Category categoryResult = Category.load(Category.class, category.getId());

        TestUtils.compareActivity(activity1, activity1Result);
        TestUtils.compareActivity(activity2, activity2Result);
        TestUtils.compareCategory(category, categoryResult);

        TestUtils.compareCategory(category, activity1Result.category);
        TestUtils.compareCategory(category, activity2Result.category);
        Assert.assertEquals(2, categoryResult.activities().size());
    }

}
