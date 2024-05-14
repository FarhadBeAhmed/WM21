package co.wm21.https.model;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;

import co.wm21.https.ProjectApp;
import co.wm21.https.helpers.Constant;

public class DeliveryProductModel {

    private String serial,productID,productName, imageUrl,invoice,paid,received;
    private String shopName,shopMobile;
    private Drawable image;
    private double price, total, point,adjust;
    int qty;

    public DeliveryProductModel(String serial, String productName, String imageUrl, double price, double total, double point, int qty, String productID, String invoice, double adjust, String shopName, String shopMobile, String paid, String received) {
        this.serial = serial;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.image = image;
        this.price = price;
        this.total = total;
        this.point = point;
        this.qty = qty;
        this.productID = productID;
        this.invoice = invoice;
        this.adjust = adjust;
        this.shopName = shopName;
        this.shopMobile = shopMobile;
        this.paid = paid;
        this.received = received;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

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



    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public double getAdjust() {
        return adjust;
    }

    public void setAdjust(double adjust) {
        this.adjust = adjust;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopMobile() {
        return shopMobile;
    }

    public void setShopMobile(String shopMobile) {
        this.shopMobile = shopMobile;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }
}
