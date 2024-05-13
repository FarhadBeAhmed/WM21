package com.wm21ltd.wm21.networks.Models;

import com.google.gson.annotations.SerializedName;

public class RewardAchievementDataListModel {
    @SerializedName("SL")
    private String sL;
    @SerializedName("Rank")
    private String rank;
    @SerializedName("Date")
    private String date;
    @SerializedName("Status")
    private String status;

    public String getSL() {
        return sL;
    }

    public void setSL(String sL) {
        this.sL = sL;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
