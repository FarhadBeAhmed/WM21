package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReceivedItemsModelHead {
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("error_report")
    @Expose
    private String errorReport;
    @SerializedName("sponsor_name")
    @Expose
    private String sponsor_name;
    @SerializedName("sponsor_mobile")
    @Expose
    private String sponsor_mobile;
    @SerializedName("data")
    @Expose
    private List<ReceivedItemsModel> data;

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

    public String getSponsor_name() {
        return sponsor_name;
    }

    public void setSponsor_name(String sponsor_name) {
        this.sponsor_name = sponsor_name;
    }

    public String getSponsor_mobile() {
        return sponsor_mobile;
    }

    public void setSponsor_mobile(String sponsor_mobile) {
        this.sponsor_mobile = sponsor_mobile;
    }

    public List<ReceivedItemsModel> getData() {
        return data;
    }

    public void setData(List<ReceivedItemsModel> data) {
        this.data = data;
    }
}
