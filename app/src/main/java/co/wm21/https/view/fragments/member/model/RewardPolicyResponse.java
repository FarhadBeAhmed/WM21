package co.wm21.https.view.fragments.member.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RewardPolicyResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("error_report")
    @Expose
    private String errorReport;
    @SerializedName("team_info")
    @Expose
    private List<RewardPolicyDataListModel> teamInfo;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
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
