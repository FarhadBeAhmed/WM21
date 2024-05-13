package com.wm21ltd.wm21.networks.Models;

import com.google.gson.annotations.SerializedName;

public class IncomeBalaceReportDataListModel {
    @SerializedName("Serial")
    private String serial;
    @SerializedName("Bold")
    private String bold;
    @SerializedName("Title")
    private String title;
    @SerializedName("Money")
    private String money;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getBold() {
        return bold;
    }

    public void setBold(String bold) {
        this.bold = bold;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
