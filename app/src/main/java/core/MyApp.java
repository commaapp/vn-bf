package core;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


/**
 * Created by d on 11/21/2017.
 */

public class MyApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
