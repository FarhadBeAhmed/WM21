package co.wm21.https.adapters.category;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;

import co.wm21.https.ProjectApp;
import co.wm21.https.helpers.Constant;

public class SubCatModel {

    String subCatId;
    String catId;
    String name;
    String imageUrl;
    Drawable image;

    public SubCatModel(String subCatId, String name, String image,String catId) {
        this.subCatId = subCatId;
        this.catId = catId;
        this.name = name;
        imageUrl = image;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public void setImage(@DrawableRes int image) {
        this.image = ResourcesCompat.getDrawable(ProjectApp.getContext().getResources(), image, ProjectApp.getContext().getTheme());
    }

    public void setImage(String image) {
        this.image = Constant.getDrawableFromUrl("image", "scat", image);
    }
    public String getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(String subCatId) {
        this.subCatId = subCatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
