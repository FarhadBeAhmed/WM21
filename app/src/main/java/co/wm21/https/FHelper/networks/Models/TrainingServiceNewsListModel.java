package com.wm21ltd.wm21.networks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TrainingServiceNewsListModel implements Serializable {
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("SubCategory")
    @Expose
    private String SubCategory;
    private final static long serialVersionUID = 1082674121650668959L;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return SubCategory;
    }

    public void setSubCategory(String subCategory) {
        SubCategory = subCategory;
    }
}
