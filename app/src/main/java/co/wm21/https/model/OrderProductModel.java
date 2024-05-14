package co.wm21.https.model;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;

import co.wm21.https.ProjectApp;
import co.wm21.https.helpers.Constant;

public class OrderProductModel {

    private String serial,productID,productName, imageUrl,color,size,model;
    private Drawable image;
    private double price, total, point,singlePoint;
    int qty;

    public OrderProductModel(String serial,String productName, String imageUrl, String color, String size, String model, double price, double total, double point, int qty,String productID, double singlePoint) {
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
        this.singlePoint = singlePoint;
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

    public double getSinglePoint() {
        return singlePoint;
    }

    public void setSinglePoint(double singlePoint) {
        this.singlePoint = singlePoint;
    }
}
