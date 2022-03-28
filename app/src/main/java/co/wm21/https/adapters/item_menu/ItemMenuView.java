package co.wm21.https.adapters.item_menu;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;

import co.wm21.https.helpers.Constant;
import co.wm21.https.ProjectApp;

public class ItemMenuView {
    String title;
    Drawable image;
    String color;

    public ItemMenuView(String title, @DrawableRes int image) {
        setTitle(title);
        setImage(image);
    }

    public ItemMenuView(String title, @DrawableRes int image, String color) {
        setTitle(title);
        setImage(image);
        setColor(color);
    }

    public ItemMenuView(String title, String... image) {
        setTitle(title);
        setImage(image);
    }

    public ItemMenuView(String title) {
        setTitle(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public void setImage(@DrawableRes int image) {
        this.image = ProjectApp.getIntResDrawable(image);
    }

    public void setImage(String... image) {
        this.image = Constant.getDrawableFromUrl(image);
    }

    public String getTitle() {
        return title;
    }

    public Drawable getImage() {
        return image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
