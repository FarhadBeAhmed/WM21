package co.wm21.https;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;

public class ProjectApp extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() { return context; }

    public static Drawable getIntResDrawable(@DrawableRes int drawable) {
        return ResourcesCompat.getDrawable(getContext().getResources(), drawable, getContext().getTheme());
    }
}