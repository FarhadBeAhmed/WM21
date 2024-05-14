package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.SerializedName;

public class FranchiseAccountDataListModel {
    @SerializedName("Serial")
    private String serial;
    @SerializedName("User")
    private String user;
    @SerializedName("Date")
    private String date;
    @SerializedName("Unit")
    private String unit;
    @SerializedName("Amount")
    private String amount;
    @SerializedName("Status")
    private String status;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
