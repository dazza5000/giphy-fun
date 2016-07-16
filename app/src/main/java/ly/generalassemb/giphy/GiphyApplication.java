package ly.generalassemb.giphy;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by darrankelinske on 7/15/16.
 */
public class GiphyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LeakCanary.install(this);

    }
}
