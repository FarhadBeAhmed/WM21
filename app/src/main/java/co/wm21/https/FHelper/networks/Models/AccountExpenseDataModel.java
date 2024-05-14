package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import co.wm21.https.model.IncomeBalaceReportDataListModel;

public class AccountExpenseDataModel {
    @SerializedName("error")
    private Integer error;
    @SerializedName("error_report")
    private String errorReport;
    @SerializedName("expense")
    private List<IncomeBalaceReportDataListModel> expense = null;

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

    public List<IncomeBalaceReportDataListModel> getExpense() {
        return expense;
    }

    public void setExpense(List<IncomeBalaceReportDataListModel> expense) {
        this.expense = expense;
    }
}
