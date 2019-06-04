package se.maj7.fx;

import android.app.Application;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Load price data before FXListActivity
        FXDatabase.shared.getPrices(getApplicationContext());
    }
}
