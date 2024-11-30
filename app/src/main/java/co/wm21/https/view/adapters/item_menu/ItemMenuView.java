package co.wm21.https.view.adapters.item_menu;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;

import co.wm21.https.helpers.Constant;
import co.wm21.https.ProjectApp;

public class ItemMenuView {
    String title;
    Drawable image;
    String color;
    String subTitle;
    String text;

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
    public ItemMenuView(String title,String color) {
        setTitle(title);
        setColor(color);
    }

    public ItemMenuView(String title,@DrawableRes int image, String subTitle,String text ) {
        setTitle(title);
        setImage(image);
        setSubTitle(subTitle);
        setSubTitle(subTitle);
        setText(text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
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
