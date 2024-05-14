package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RewardAchievementDataModel {
    @SerializedName("error")
    private Integer error;
    @SerializedName("error_report")
    private String errorReport;
    @SerializedName("service_training")
    private List<RewardAchievementDataListModel> serviceTraining = null;

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

    public List<RewardAchievementDataListModel> getServiceTraining() {
        return serviceTraining;
    }

    public void setServiceTraining(List<RewardAchievementDataListModel> serviceTraining) {
        this.serviceTraining = serviceTraining;
    }
}
