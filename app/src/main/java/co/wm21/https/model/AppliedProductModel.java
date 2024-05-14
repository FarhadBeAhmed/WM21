package co.wm21.https.model;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.ProjectApp;
import co.wm21.https.helpers.Constant;

public class AppliedProductModel {

    private String serial,productID,productName, imageUrl,color,size,model,invoice;
    private String shopName,shopMobile,date;
    private Drawable image;
    private double price, total, point,adjust;
    int qty;

    public AppliedProductModel(String serial, String productName, String imageUrl, String color, String size, String model, double price, double total, double point, int qty, String productID, String invoice, double adjust, String shopName, String shopMobile, String date) {
        this.serial = serial;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.color = color;
        this.size = size;
        this.model = model;
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
        this.date = date;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
