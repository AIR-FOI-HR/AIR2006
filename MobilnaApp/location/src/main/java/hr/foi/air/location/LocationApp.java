package hr.foi.air.location;

import android.app.Application;
import android.content.res.Resources;

public class LocationApp extends Application {
    private static Resources resources;

    @Override
    public void onCreate() {
        super.onCreate();

        resources = getResources();
    }

    public static Resources getAppResources() {
        return resources;
    }
}
