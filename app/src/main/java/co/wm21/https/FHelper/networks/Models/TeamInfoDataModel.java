package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamInfoDataModel {
    @SerializedName("error")
    private Integer error;
    @SerializedName("error_report")
    private String errorReport;
    @SerializedName("team_info")
    private List<TeamInfoDataListModel> teamInfo = null;

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

    public List<TeamInfoDataListModel> getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(List<TeamInfoDataListModel> teamInfo) {
        this.teamInfo = teamInfo;
    }
}
