package co.wm21.https.adapters.product;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;

import java.io.Serializable;
import java.util.ArrayList;

import co.wm21.https.helpers.Constant;
import co.wm21.https.ProjectApp;

public class ProductView {
    private String productName, imageUrl;
    private Drawable image;
    private double price, discount, rating;
    private long cat_id, scat_id, brand_id, product_id;

    public ProductView() {}

    public ProductView(String productName, String image, double price, double discount, double rating, long cat_id, long scat_id, long brand_id, long product_id) {
        this.imageUrl = image;
        setProductName(productName);
        setImage(image);
        setPrice(price);
        setDiscount(discount);
        setRating(rating);
        setCatId(cat_id);
        setScatId(scat_id);
        setBrandId(brand_id);
        setProductId(product_id);
    }

    public String getProductName() { return productName; }

    public void setProductName(String productName) { this.productName = productName; }

    public String getImageUrl() { return imageUrl; }
    public Drawable getImage() { return image; }

    public void setImage(Drawable image) { this.image = image; }

    public void setImage(@DrawableRes int image) { this.image = ResourcesCompat.getDrawable(ProjectApp.getContext().getResources(), image, ProjectApp.getContext().getTheme()); }

    public void setImage(String image) { this.image = Constant.getDrawableFromUrl("image", "product", "small", image); }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public double getDiscount() { return discount; }

    public void setDiscount(double discount) { this.discount = discount; }

    public double getRating() { return rating; }

    public void setRating(double rating) { this.rating = rating; }

    public double getCurrentPrice() { return price - discount; }

    public long getCatId() { return cat_id; }

    public void setCatId(long cat_id) { this.cat_id = cat_id; }

    public long getScatId() { return scat_id; }

    public void setScatId(long scat_id) { this.scat_id = scat_id; }

    public long getBrandId() { return brand_id; }

    public void setBrandId(long brand_id) { this.brand_id = brand_id; }

    public long getProductId() { return product_id; }

    public void setProductId(long product_id) { this.product_id = product_id; }

}
