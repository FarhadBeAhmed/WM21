package co.wm21.https.helpers;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.chaos.view.PinView;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

import co.wm21.https.ProjectApp;
import co.wm21.https.annotations.*;

/**
 * Requires a {@link Constant} and other things and api.
 * @see ConstantValues
 */
public final class Constant implements ConstantValues {

    public static String FACEBOOK_URL = "https://www.facebook.com/groups/262949877574547/";
    public static String YOUTUBE_URL = "https://www.youtube.com/hashtag/wm21";

    public static void addMultipleClickListener(View.OnClickListener v, @NonNull View... views) {
        for (View b : views) b.setOnClickListener(v);
    }

    /**
     * If you write blank string, It can return only <b>website</b>.<br>
     * We've create a only website value, So use that.
     * @param strings Use ("api", "*.php") instead of ("api/*.php") in this inner method.
     * @return Get File Name Shortcut.
     */
    @NotNull
    public static String getFileName(@NonNull String... strings) {
        return getFixedImageUrl(new Joiner<>("/", URL, "", strings).toString());
    }

    /**
     * If you write blank string, It can return only <b>website</b>.<br>
     * We've create a only website value, So use that.
     * @param strings Use ("PHP", "api", "*") instead of ("PHP", "api/*") in this inner method.
     * @return Get File Name Shortcut.
     */
    @NotNull
    public static String getFileName(FileType extension, @NonNull String... strings) {
        return getFixedUrl(new Joiner<>("/", URL, "." + extension.getFileType(), strings).toString());
    }

    public static Drawable getDrawableFromUrl(@NonNull String... strings) {

        AsyncTask<String, Void, Drawable> asyncTask = new AsyncTask<String, Void, Drawable>() {
            @Override
            protected Drawable doInBackground(String... strings) {
                Drawable drawable = null;
                try {
                    drawable = Drawable.createFromStream(new URL(getFileName(strings)).openStream(), "");
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                return drawable;
            }
        };
        asyncTask.execute(strings);
        try {
            return asyncTask.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFixedUrl(String url) {
        try {
            return new URI(URLDecoder.decode(url, "UTF-8")).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return url;
        }
    }

    public static String getFixedImageUrl(String url) {
        try {
            return url.replaceAll(" ", "%20");
        }catch(Exception e){
            e.printStackTrace();
            return url;
        }
    }

    public static void setImageToImageView(ImageView imageView, @NonNull String... strings) {
        imageView.setImageDrawable(getDrawableFromUrl(strings));
    }

    /**
     * Requires a {@link TextInputLayout} is completed in submit
     * @param textInputLayouts enter the all {@link TextInputLayout}
     * @return is {@link TextInputLayout}(s) text completed or not.
     */
    public static boolean validation(TextInputLayout... textInputLayouts) {
        boolean result = true;
        for (TextInputLayout til : textInputLayouts) {
            if (!til.getEditText().getText().toString().isEmpty()) til.setError("");
            if (til.getEditText().getText().toString().isEmpty()) {
                til.setError("Field can't be empty.");
                result = false;
            }
        }
        return result;
    }

    public static boolean validation2(PinView... textInputLayouts) {
        boolean result = true;
        for (PinView til : textInputLayouts) {
            if (!til.getText().toString().isEmpty()) til.setError("");
            if (til.getText().toString().isEmpty()) {
                til.setError("Field can't be empty.");
                result = false;
            }
        }
        return result;
    }

    @NonNull
    public static String getTextFromTIL(@NonNull TextInputLayout textInputLayout) {
        return textInputLayout.getEditText().getText().toString();
    }

    @NonNull
    public static String getRealStringEscape(String s) {
        String result = s;
        result = (result == null) ? "" : result.replace("`", "")
                .replace("'", "").replace("\"", "")
                .replace("null", "").replace("$", "");
        return result;
    }

    /**
     * You get the url with submit value.
     * @param variable enter your variable name on _String ... [0] <br>
     *                 Then enter your variable on _String ... [1]
     * @param strings enter strings
     * @return you get the url with submit value.
     */
    @NonNull
    public static String GetMethodURL(@NonNull HashMap<String, Object> variable, String... strings) {
        Joiner<String> result = new Joiner<>("&", getFileName(strings) + "?", "", variable.keySet() + "=" + variable.get(variable.keySet()));
        for (String string : variable.keySet())
            result.add(string + "=" + variable.get(string));
        return result.toString();
    }

    /**
     * Requires this method for using {@link API}.
     */
  /*  @NonNull
    @SuppressWarnings("unchecked")
    public static API getAPI() {
        return (API) Proxy.newProxyInstance(API.class.getClassLoader(), new Class<?>[]{ API.class }, (proxy, method, args) -> {
            if (args == null) args = new Object[] {}; JSONObject jo = new JSONObject();
            for (int i = 0; i < args.length; i++) jo.put(((Field) method.getParameterAnnotations()[i][0]).value(), args[i]);
            int methodType = method.getAnnotation(SendMethod.class).value();
            String url = getFileName(FileType.PHP, method.getAnnotation(RequestUrl.class).value());
            return method.getReturnType() == JsonObjectResponse.class ? new JsonObjectResponse(methodType, url, jo)
                 : method.getReturnType() == JsonArrayResponse.class ? new JsonArrayResponse(methodType, url, jo)
                 : new StringResponse(methodType, url, jo);
        });
    }*/

    public static Drawable getDFR(@DrawableRes int drawable) {
        return ResourcesCompat.getDrawable(ProjectApp.getContext().getResources(), drawable, ProjectApp.getContext().getTheme());
    }

    public static int getICFR(@ColorRes int color) {
        return ResourcesCompat.getColor(ProjectApp.getContext().getResources(), color, ProjectApp.getContext().getTheme());
    }

    /**
     * This is short api for density-independent pixel to pixel.
     * @param dp set density-independent pixel value for convert.
     * @return pixel Value from density-independent pixel value.
     */
    public static int dp(double dp) {
        DisplayMetrics displayMetrics = ProjectApp.getContext().getResources().getDisplayMetrics();
        return (int) Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /**
     * This is short api for scale-independent pixel to pixel.
     * @param sp set scale-independent pixel value for convert.
     * @return pixel Value from scale-independent pixel value.
     */
    public static int sp(double sp) {
        return dp(sp);
    }

    /**
     * This is short api for inch to pixel.
     * @param inch set inch value for convert.
     * @return pixel Value from inch pixel value.
     */
    public static int inch(int inch) { return inch * 96; }

    public static String getBoldedSpannedString(CharSequence text) {
        return Html.fromHtml("<b>" + text + "</b>").toString();
    }

    public static String getBoldedSpannedString(String text) {
        return Html.fromHtml("<b>" + text + "</b>").toString();
    }

    public static String getUnderlinedSpannedString(CharSequence text) {
        return Html.fromHtml("<u>" + text + "</u>").toString();
    }

    public static String getUnderlinedSpannedString(String text) {
        return Html.fromHtml("<u>" + text + "</u>").toString();
    }

    public static String getStrikethroughSpannedString(CharSequence text) {
        return Html.fromHtml("<del>" + text + "</del>").toString();
    }

    public static String getStrikethroughSpannedString(String text) {
        return Html.fromHtml("<del>" + text + "</del>").toString();
    }
}