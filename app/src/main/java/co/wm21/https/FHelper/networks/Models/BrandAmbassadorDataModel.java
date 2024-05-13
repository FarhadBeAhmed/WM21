package com.wm21ltd.wm21.networks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BrandAmbassadorDataModel implements Serializable {
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("error_report")
    @Expose
    private String errorReport;
    @SerializedName("ambassador")
    @Expose
    private List<BrandAmbassadorListModel> ambassador = null;
    private final static long serialVersionUID = -8557216822594984903L;

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

    public List<BrandAmbassadorListModel> getAmbassador() {
        return ambassador;
    }

    public void setAmbassador(List<BrandAmbassadorListModel> ambassador) {
        this.ambassador = ambassador;
    }
}
