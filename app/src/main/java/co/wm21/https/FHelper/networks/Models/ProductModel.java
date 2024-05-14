package co.wm21.https.FHelper.networks.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductModel implements Parcelable {
    @SerializedName("serial")
    @Expose
    private String serial;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("upload_by")
    @Expose
    private String uploadBy;
    @SerializedName("offer_date")
    @Expose
    private String offerDate;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("sprice")
    @Expose
    private String sprice;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("scat_id")
    @Expose
    private String scatId;
    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @SerializedName("discount_in_percet")
    @Expose
    private String discountInPercet;

    public ProductModel(Parcel in) {
        serial = in.readString();
        name = in.readString();
        uploadBy = in.readString();
        offerDate = in.readString();
        img = in.readString();
        discount = in.readString();
        sprice = in.readString();
        point = in.readString();
        price = in.readString();
        catId = in.readString();
        scatId = in.readString();
        brandId = in.readString();
        discountInPercet = in.readString();
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }

    public String getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(String offerDate) {
        this.offerDate = offerDate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getSprice() {
        return sprice;
    }

    public void setSprice(String sprice) {
        this.sprice = sprice;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getScatId() {
        return scatId;
    }

    public void setScatId(String scatId) {
        this.scatId = scatId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getDiscountInPercet() {
        return discountInPercet;
    }

    public void setDiscountInPercet(String discountInPercet) {
        this.discountInPercet = discountInPercet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(serial);
        parcel.writeString(name);
        parcel.writeString(uploadBy);
        parcel.writeString(offerDate);
        parcel.writeString(img);
        parcel.writeString(discount);
        parcel.writeString(sprice);
        parcel.writeString(point);
        parcel.writeString(price);
        parcel.writeString(catId);
        parcel.writeString(scatId);
        parcel.writeString(brandId);
        parcel.writeString(discountInPercet);
    }
}
