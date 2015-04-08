package fr.oni.bored.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import fr.oni.bored.R;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "activities.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Activity, Integer> activityDao;
    private Dao<Category, Integer> categoryDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Activity.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create databases", e);
        }
    }

    @Override
    //TODO Add a real upgrade mechanism
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, Activity.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVersion + " to new "
                    + newVersion, e);
        }
    }

    public Dao<Activity, Integer> getActivityDao() throws SQLException {
        if (activityDao == null) {
            activityDao = getDao(Activity.class);
        }
        return activityDao;
    }

    public Dao<Category, Integer> getCategoryDao() throws SQLException {
        if (categoryDao == null) {
            categoryDao = getDao(Category.class);
        }
        return categoryDao;
    }
}
