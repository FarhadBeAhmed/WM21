package co.wm21.https;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public class PanelView extends LinearLayout {
    int deviceWidth;
    ViewGroup viewGroup;
    public static TextView textView;
    TypedArray a;

    public void setTitle(String title) {
        textView.setText((a.getDrawable(R.styleable.PanelView_titleIcon) != null ? "   " : "") + title);
        invalidate();
        requestLayout();
    }

    public void setTitleIcon(Drawable icon) {
        textView.setCompoundDrawables(icon, null, null, null);
        invalidate();
        requestLayout();
    }

    public void setTitleIcon(@DrawableRes int icon) {
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, 0, 0, 0);
        invalidate();
        requestLayout();
    }
   /* public void setTitleColor(int titleColor) {
        textView.setTextColor(titleColor);
        invalidate();
        requestLayout();
    }*/

    public String getTitle() {
        return textView.getText().toString();
    }

    public Drawable getTitleIcon() {
        return textView.getCompoundDrawables()[0];
    }

    private void myDo(Context context, AttributeSet attrs) {
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.nightDayTextColor, typedValue, true);
        int panelColor = typedValue.data;

        a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PanelView, 0, 0);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(dpFormat(2), dpFormat(2), dpFormat(2), dpFormat(2));
        textView = new TextView(context);
        textView.setText((a.getDrawable(R.styleable.PanelView_titleIcon) != null ? "   " : "") + a.getString(R.styleable.PanelView_title));
        textView.setCompoundDrawablesWithIntrinsicBounds(a.getResourceId(R.styleable.PanelView_titleIcon, 0), 0, 0, 0);
        textView.setPaddingRelative(dpFormat(10), dpFormat(10), dpFormat(10), dpFormat(10));
        try {
            textView.setCompoundDrawables(a.getDrawable(R.styleable.PanelView_titleIcon), null,null,null);
        } catch (Exception e) {}
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(dpFormat(2), dpFormat(2), dpFormat(2), dpFormat(10));
        textView.setLayoutParams(params);
        this.setOrientation(VERTICAL);
        this.addView(textView, 0);
        this.setPaddingRelative(0, 0, 0, dpFormat(10));
        textView.setTextColor(a.getColor(R.styleable.PanelView_textColor,0xFF000000));
        makeRoundCorner(panelColor, getResources().getDimensionPixelSize(R.dimen.radius), this, dpFormat(2), a.getColor(R.styleable.PanelView_baseColor, 0xFF000000));
        makeTopRoundCorner(a.getColor(R.styleable.PanelView_baseColor, 0xFF000000), dpFormat(0), dpFormat(0), textView, dpFormat(5), a.getColor(R.styleable.PanelView_baseColor, 0xFF000000));
    }

    public PanelView(Context context) {
        super(context, null, 0);
        this.viewGroup = new LinearLayout(context);
    }

    public PanelView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.viewGroup = new LinearLayout(context);
        myDo(context, attrs);
    }

    public PanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        this.viewGroup = new LinearLayout(context);
        myDo(context, attrs);
    }

    private void init(Context context) {
        final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point deviceDisplay = new Point();
        display.getSize(deviceDisplay);
        deviceWidth = deviceDisplay.x;
        this.setMinimumWidth(deviceWidth);
    }

    public int dpFormat(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static void makeRoundCorner(int bgcolor, float radius, @NonNull View v, int strokeWidth, int strokeColor)
    {
        GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setColor(bgcolor);

        gdDefault.setCornerRadius(radius);
        gdDefault.setStroke(strokeWidth, strokeColor);
        v.setBackgroundDrawable(gdDefault);
    }

    public static void makeTopRoundCorner(int bgcolor, float topLeftRadius, float topRightRadius, @NonNull View v, int strokeWidth, int strokeColor)
    {
        GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setColor(bgcolor);
        gdDefault.setCornerRadii(edgesOfCorner(topLeftRadius, topRightRadius, 0, 0));
        gdDefault.setStroke(strokeWidth, strokeColor);
        v.setBackgroundDrawable(gdDefault);
    }

    public static float[] edgesOfCorner(float topLeft, float topRight, float bottomLeft, float bottomRight) {
        return new float[] { topLeft, topLeft,
                             topRight, topRight,
                             bottomLeft, bottomLeft,
                             bottomRight, bottomRight };
    }

    public static float[] edgesOfCorner(float CoordinatorX, float CoordinatorY) {
        return new float[] { CoordinatorX, CoordinatorX,
                             CoordinatorY, CoordinatorY,
                             CoordinatorY, CoordinatorY,
                             CoordinatorX, CoordinatorX };
    }
}
