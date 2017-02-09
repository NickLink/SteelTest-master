package link.nick.com.steeltest.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import io.realm.Realm;
import link.nick.com.steeltest.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Nick on 04.02.2017.
 */

public class AppController extends Application{

    public static final String TAG = AppController.class.getSimpleName();
    private static SharedPreferences sPref;
    private Context context;
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();
        sPref = context.getSharedPreferences("myApp", Context.MODE_PRIVATE);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/robotoregular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        Realm.init(this);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static SharedPreferences getSharPref() {
        return sPref;
    }



}
