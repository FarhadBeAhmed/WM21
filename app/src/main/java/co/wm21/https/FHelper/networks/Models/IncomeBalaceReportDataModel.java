package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import co.wm21.https.model.IncomeBalaceReportDataListModel;

public class IncomeBalaceReportDataModel {
    @SerializedName("error")
    private Integer error;
    @SerializedName("error_report")
    private String errorReport;
    @SerializedName("balance")
    private List<IncomeBalaceReportDataListModel> balance = null;

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

    public List<IncomeBalaceReportDataListModel> getBalance() {
        return balance;
    }

    public void setBalance(List<IncomeBalaceReportDataListModel> balance) {
        this.balance = balance;
    }
}
