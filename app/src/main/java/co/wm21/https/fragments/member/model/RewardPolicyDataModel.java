package co.wm21.https.fragments.member.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RewardPolicyDataModel {
    @SerializedName("error")
    private Integer error;
    @SerializedName("error_report")
    private String errorReport;
    @SerializedName("team_info")
    private List<RewardPolicyDataListModel> teamInfo;

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

    public List<RewardPolicyDataListModel> getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(List<RewardPolicyDataListModel> teamInfo) {
        this.teamInfo = teamInfo;
    }

}
