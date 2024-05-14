package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RewardGalleryDataModel {
    @SerializedName("error")
    private Integer error;
    @SerializedName("error_report")
    private String errorReport;
    @SerializedName("service_training")
    private List<RewardGalleryDataListModel> serviceTraining = null;

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

    public List<RewardGalleryDataListModel> getServiceTraining() {
        return serviceTraining;
    }

    public void setServiceTraining(List<RewardGalleryDataListModel> serviceTraining) {
        this.serviceTraining = serviceTraining;
    }
}
