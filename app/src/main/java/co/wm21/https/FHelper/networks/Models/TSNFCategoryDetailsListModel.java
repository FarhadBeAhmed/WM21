package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TSNFCategoryDetailsListModel implements Serializable {
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Details")
    @Expose
    private String details;
    @SerializedName("Last_Date")
    @Expose
    private String lastDate;
    @SerializedName("Fee")
    @Expose
    private String fee;
    @SerializedName("Support")
    @Expose
    private String support;
    @SerializedName("Vanue")
    @Expose
    private String vanue;
    @SerializedName("Division")
    @Expose
    private String Division;
    @SerializedName("District")
    @Expose
    private String District;
    @SerializedName("Thana")
    @Expose
    private String Thana;

    private boolean expanded;

    private final static long serialVersionUID = 4631473171184030056L;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }



    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getThana() {
        return Thana;
    }

    public void setThana(String thana) {
        Thana = thana;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getVanue() {
        return vanue;
    }

    public void setVanue(String vanue) {
        this.vanue = vanue;
    }
}
