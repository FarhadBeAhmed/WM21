package co.wm21.https.FHelper.Annotations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorProductModel {
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

}
