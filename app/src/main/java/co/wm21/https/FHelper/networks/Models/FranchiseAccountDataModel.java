package com.wm21ltd.wm21.networks.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FranchiseAccountDataModel {
    @SerializedName("error")
    private Integer error;
    @SerializedName("error_report")
    private String errorReport;
    @SerializedName("service_training")
    private List<FranchiseAccountDataListModel> serviceTraining = null;

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

    public List<FranchiseAccountDataListModel> getServiceTraining() {
        return serviceTraining;
    }

    public void setServiceTraining(List<FranchiseAccountDataListModel> serviceTraining) {
        this.serviceTraining = serviceTraining;
    }
}
