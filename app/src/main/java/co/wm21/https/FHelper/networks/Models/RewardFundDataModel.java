package com.wm21ltd.wm21.networks.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RewardFundDataModel {
    @SerializedName("error")
    private Integer error;
    @SerializedName("error_report")
    private String errorReport;
    @SerializedName("team_info")
    private List<RewardFundDataListModel> teamInfo = null;

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

    public List<RewardFundDataListModel> getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(List<RewardFundDataListModel> teamInfo) {
        this.teamInfo = teamInfo;
    }
}
