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
import fr.oni.bored.data.DatabaseHelper;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class DatabaseTest {
    private DatabaseHelper dbHelper;
    private Dao<Activity, Integer> activityDao;

    @Before
    public void setUp() throws Exception {
        Context context = ShadowApplication.getInstance().getApplicationContext();
        dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        activityDao = dbHelper.getDao(Activity.class);
    }

    @Test
    @SmallTest
    public void addActivity() throws SQLException {
        Activity activity = new Activity();
        activity.setTitle("Title");
        activity.setDescription("Description");
        activityDao.create(activity);

        Activity activityResult = activityDao.queryForId(activity.getId());
        Assert.assertEquals("ID not equal", activity.getId(), activityResult.getId());
        Assert.assertEquals("Title not equal", activity.getTitle(), activityResult.getTitle());
        Assert.assertEquals("Description not equal", activity.getDescription(), activityResult.getDescription());
    }

    @After
    public void tearDown() throws Exception {
        OpenHelperManager.releaseHelper();
    }
}
