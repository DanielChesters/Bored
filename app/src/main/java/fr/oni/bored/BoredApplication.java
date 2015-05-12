package fr.oni.bored;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class BoredApplication extends Application {
    RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        BoredApplication application = (BoredApplication) context.getApplicationContext();
        return application.refWatcher;
    }
}
