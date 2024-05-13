package com.wm21ltd.wm21.networks.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenerationDataModel {
    @SerializedName("error")
    private Integer error;
    @SerializedName("error_report")
    private String errorReport;
    @SerializedName("tree_info")
    private List<GenerationDataListModel> treeInfo = null;

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

    public List<GenerationDataListModel> getTreeInfo() {
        return treeInfo;
    }

    public void setTreeInfo(List<GenerationDataListModel> treeInfo) {
        this.treeInfo = treeInfo;
    }
}
