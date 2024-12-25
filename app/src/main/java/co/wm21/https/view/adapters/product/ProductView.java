package co.wm21.https.view.adapters.product;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import co.wm21.https.utils.Constant;
import co.wm21.https.ProjectApp;

public class ProductView implements Parcelable {

    @SerializedName("productName")
    @Expose
    private String productName ;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("image")
    @Expose
    private Drawable image;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("discount")
    @Expose
    private double discount;
    @SerializedName("rating")
    @Expose
    private double rating ;
    @SerializedName("point")
    @Expose
    private double point;
    @SerializedName("product_id")
    @Expose
    private long product_id;
    @SerializedName("cat_id")
    @Expose
    private long cat_id;
    @SerializedName("scat_id")
    @Expose
    private long scat_id;
    @SerializedName("brand_id")
    @Expose
    private long brand_id;
    @SerializedName("upload_by")
    @Expose
    private  String upload_by;
    @SerializedName("offer_date")
    @Expose
    private  String offer_date;

    public ProductView(){}

    public ProductView(String productName, String image, double price, double discount, double rating, long cat_id, long scat_id, long brand_id, long product_id,String offer_date,String upload_by,double point) {
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
        setOffer_date(offer_date);
        setUpload_by(upload_by);
        setPoint(point);
    }


    protected ProductView(Parcel in) {
        productName = in.readString();
        imageUrl = in.readString();
        price = in.readDouble();
        discount = in.readDouble();
        rating = in.readDouble();
        cat_id = in.readLong();
        scat_id = in.readLong();
        brand_id = in.readLong();
        product_id = in.readLong();
        offer_date = in.readString();
        upload_by = in.readString();
        point = in.readDouble();
    }

    public static final Creator<ProductView> CREATOR = new Creator<ProductView>() {
        @Override
        public ProductView createFromParcel(Parcel in) {
            return new ProductView(in);
        }

        @Override
        public ProductView[] newArray(int size) {
            return new ProductView[size];
        }
    };



    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
        this.image = Constant.getDrawableFromUrl("image", "product", "small", image);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getOffer_date() {
        return offer_date;
    }

    public void setOffer_date(String offer_date) {
        this.offer_date = offer_date;
    }

    public String getUpload_by() {
        return upload_by;
    }

    public void setUpload_by(String upload_by) {
        this.upload_by = upload_by;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getCurrentPrice() {
        return price - discount;
    }

    public long getCatId() {
        return cat_id;
    }

    public void setCatId(long cat_id) {
        this.cat_id = cat_id;
    }

    public long getScatId() {
        return scat_id;
    }

    public void setScatId(long scat_id) {
        this.scat_id = scat_id;
    }

    public long getBrandId() {
        return brand_id;
    }

    public void setBrandId(long brand_id) {
        this.brand_id = brand_id;
    }

    public long getProductId() {
        return product_id;
    }

    public void setProductId(long product_id) {
        this.product_id = product_id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productName);
        parcel.writeString(imageUrl);
        parcel.writeDouble(price);
        parcel.writeDouble(discount);
        parcel.writeDouble(rating);
        parcel.writeLong(cat_id);
        parcel.writeLong(scat_id);
        parcel.writeLong(brand_id);
        parcel.writeLong(product_id);
        parcel.writeString(offer_date);
        parcel.writeString(upload_by);
        parcel.writeDouble(point);
    }
}
