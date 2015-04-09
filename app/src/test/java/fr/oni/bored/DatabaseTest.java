package fr.oni.bored;

import android.content.Context;
import android.test.suitebuilder.annotation.SmallTest;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.sql.SQLException;

import fr.oni.bored.data.Activity;
import fr.oni.bored.data.Category;
import fr.oni.bored.data.DatabaseHelper;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class DatabaseTest {
    private DatabaseHelper dbHelper;
    private Dao<Activity, Integer> activityDao;
    private Dao<Category, Integer> categoryDao;

    @Before
    public void setUp() throws Exception {
        Context context = ShadowApplication.getInstance().getApplicationContext();
        dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        activityDao = dbHelper.getDao(Activity.class);
        categoryDao = dbHelper.getDao(Category.class);
    }

    @Test
    @SmallTest
    public void insertAnActivity() throws SQLException {
        Activity activity = createActivity(1);
        activityDao.create(activity);

        Activity activityResult = activityDao.queryForId(activity.getId());
        compareActivity(activity, activityResult);
    }

    @Test
    @SmallTest
    public void insertACategory() throws SQLException {
        Category category = createCategory(1);
        categoryDao.create(category);

        Category categoryResult = categoryDao.queryForId(category.getId());
        compareCategory(category, categoryResult);
    }

    @Test
    @SmallTest
    public void updateAnActivity() throws SQLException {
        Activity activity = createActivity(1);
        activityDao.create(activity);
        activity.setDescription("Nope");
        activityDao.update(activity);

        Activity activityResult = activityDao.queryForId(activity.getId());
        compareActivity(activity, activityResult);
    }

    @Test
    @SmallTest
    public void updateACategory() throws SQLException {
        Category category = createCategory(1);
        categoryDao.create(category);
        category.setDescription("Nope");
        categoryDao.update(category);

        Category categoryResult = categoryDao.queryForId(category.getId());
        compareCategory(category, categoryResult);
    }

    @Test
    @SmallTest
    public void insertAnActivityAndACategory() throws SQLException {
        Category category = createCategory(1);
        Activity activity = createActivity(1);

        activity.setCategory(category);

        activityDao.create(activity);
        
        Activity activityResult = activityDao.queryForId(activity.getId());
        Category categoryResult = categoryDao.queryForId(category.getId());

        compareActivity(activity, activityResult);
        compareCategory(category, categoryResult);

        compareCategory(category, activityResult.getCategory());
        Assert.assertEquals(1, categoryResult.getActivities().size());
    }

    @Test
    @SmallTest
    public void insertTwoActivitiesAndACategory() throws SQLException {
        Category category = createCategory(1);
        Activity activity1 = createActivity(1);
        Activity activity2 = createActivity(2);

        activity1.setCategory(category);
        activity2.setCategory(category);

        activityDao.create(activity1);
        activityDao.create(activity2);

        Activity activity1Result = activityDao.queryForId(activity1.getId());
        Activity activity2Result = activityDao.queryForId(activity2.getId());
        Category categoryResult = categoryDao.queryForId(category.getId());

        compareActivity(activity1, activity1Result);
        compareActivity(activity2, activity2Result);
        compareCategory(category, categoryResult);

        compareCategory(category, activity1Result.getCategory());
        compareCategory(category, activity2Result.getCategory());
        Assert.assertEquals(2, categoryResult.getActivities().size());
    }

    @After
    public void tearDown() throws Exception {
        OpenHelperManager.releaseHelper();
    }

    private void compareActivity(Activity activity, Activity activityResult) {
        Assert.assertEquals("ID not equal", activity.getId(), activityResult.getId());
        Assert.assertEquals("Title not equal", activity.getTitle(), activityResult.getTitle());
        Assert.assertEquals("Description not equal", activity.getDescription(), activityResult.getDescription());
    }

    private void compareCategory(Category category, Category categoryResult) {
        Assert.assertEquals("ID not equal", category.getId(), categoryResult.getId());
        Assert.assertEquals("Title not equal", category.getTitle(), categoryResult.getTitle());
        Assert.assertEquals("Description not equal", category.getDescription(), categoryResult.getDescription());
    }

    private Category createCategory(int i) {
        Category category = new Category();
        category.setTitle(String.format("Title %d", i));
        category.setDescription(String.format("Description %d", i));
        return category;
    }

    private Activity createActivity(int i) {
        Activity activity = new Activity();
        activity.setTitle(String.format("Title %d", i));
        activity.setDescription(String.format("Description %d", i));
        return activity;
    }
}
