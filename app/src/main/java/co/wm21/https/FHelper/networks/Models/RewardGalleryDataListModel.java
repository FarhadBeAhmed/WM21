package com.wm21ltd.wm21.networks.Models;

import com.google.gson.annotations.SerializedName;

public class RewardGalleryDataListModel {
    @SerializedName("SL")
    private String sL;
    @SerializedName("Rank")
    private String rank;
    @SerializedName("Name")
    private String name;
    @SerializedName("Image")
    private String image;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
