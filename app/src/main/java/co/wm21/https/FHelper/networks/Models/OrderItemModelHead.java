package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderItemModelHead {
    @SerializedName("myPoint")
    @Expose
    private String myPoint;
    @SerializedName("rp")
    @Expose
    private String rp;
    @SerializedName("total_rp")
    @Expose
    private String totalRp;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("payable")
    @Expose
    private Integer payable;
    @SerializedName("adj_amount")
    @Expose
    private String adjAmount;
    @SerializedName("adj_rp")
    @Expose
    private String adjRp;
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("error_report")
    @Expose
    private String errorReport;
    @SerializedName("data")
    @Expose
    private List<OrderItemModel> data;

    public String getMyPoint() {
        return myPoint;
    }

    public void setMyPoint(String myPoint) {
        this.myPoint = myPoint;
    }

    public String getRp() {
        return rp;
    }

    public void setRp(String rp) {
        this.rp = rp;
    }

    public String getTotalRp() {
        return totalRp;
    }

    public void setTotalRp(String totalRp) {
        this.totalRp = totalRp;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getPayable() {
        return payable;
    }

    public void setPayable(Integer payable) {
        this.payable = payable;
    }

    public String getAdjAmount() {
        return adjAmount;
    }

    public void setAdjAmount(String adjAmount) {
        this.adjAmount = adjAmount;
    }

    public String getAdjRp() {
        return adjRp;
    }

    public void setAdjRp(String adjRp) {
        this.adjRp = adjRp;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getErrorReport() {
        return errorReport;
    }

    public void setErrorReport(String errorReport) {
        this.errorReport = errorReport;
    }

    public List<OrderItemModel> getData() {
        return data;
    }

    public void setData(List<OrderItemModel> data) {
        this.data = data;
    }
}
