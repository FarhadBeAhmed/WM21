package co.wm21.https.FHelper.networks.Models.application;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BDPStatusResponse {
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("error_report")
    @Expose
    private String errorReport;
    @SerializedName("data")
    @Expose
    private List<BDPStatusData> data;

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

    public List<BDPStatusData> getData() {
        return data;
    }

    public void setData(List<BDPStatusData> data) {
        this.data = data;
    }
}
