package co.wm21.https.view.adapters.cart;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class CartModel{
    private String  productName, imageUrl,u_id,productID;
    private String  color, size,serial;
    private double price,subTotal,point,direct,singlePoint;
    private long qty;


    public CartModel(String u_id,String productName, String imageUrl, String color, String size, String serial, double price,  double subTotal, double point, double direct, long qty, String productID,double singlePoint) {
        this.u_id = u_id;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.color = color;
        this.size = size;
        this.serial = serial;
        this.price = price;
        this.subTotal = subTotal;
        this.point = point;
        this.direct = direct;
        this.qty = qty;
        this.productID = productID;
        this.singlePoint = singlePoint;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public double getDirect() {
        return direct;
    }

    public void setDirect(double direct) {
        this.direct = direct;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public double getSinglePoint() {
        return singlePoint;
    }

    public void setSinglePoint(double singlePoint) {
        this.singlePoint = singlePoint;
    }
}
