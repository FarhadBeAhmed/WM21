package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppliedProductModelHead {
    @SerializedName("error")
    @Expose
    private int error;
    @SerializedName("error_report")
    @Expose
    private String error_report;
    @SerializedName("data")
    @Expose
    private List<AppliedProductModel> data;
    @SerializedName("subTotal")
    @Expose
    private double subTotal;
    @SerializedName("myPoint")
    @Expose
    private String myPoint;
    @SerializedName("count")
    @Expose
    private String count;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getError_report() {
        return error_report;
    }

    public void setError_report(String error_report) {
        this.error_report = error_report;
    }

    public List<AppliedProductModel> getData() {
        return data;
    }

    public void setData(List<AppliedProductModel> data) {
        this.data = data;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getMyPoint() {
        return myPoint;
    }

    public void setMyPoint(String myPoint) {
        this.myPoint = myPoint;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
