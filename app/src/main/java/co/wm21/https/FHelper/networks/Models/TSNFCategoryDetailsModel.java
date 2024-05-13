package com.wm21ltd.wm21.networks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TSNFCategoryDetailsModel implements Serializable {
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("error_report")
    @Expose
    private String errorReport;
    @SerializedName("service_training")
    @Expose
    private List<TSNFCategoryDetailsListModel> serviceTraining = null;
    private final static long serialVersionUID = -6893925550743969923L;

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

    public List<TSNFCategoryDetailsListModel> getServiceTraining() {
        return serviceTraining;
    }

    public void setServiceTraining(List<TSNFCategoryDetailsListModel> serviceTraining) {
        this.serviceTraining = serviceTraining;
    }
}
