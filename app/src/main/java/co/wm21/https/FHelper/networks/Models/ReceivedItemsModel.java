package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReceivedItemsModel {
    @SerializedName("serial")
    @Expose
    private String serial;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("used")
    @Expose
    private String used;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("pin")
    @Expose
    private String pin;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
