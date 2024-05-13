package com.wm21ltd.wm21.networks.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccountIncomeDataModel {
    @SerializedName("error")
    private Integer error;
    @SerializedName("error_report")
    private String errorReport;
    @SerializedName("incomes")
    private List<IncomeBalaceReportDataListModel> incomes = null;

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

    public List<IncomeBalaceReportDataListModel> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<IncomeBalaceReportDataListModel> incomes) {
        this.incomes = incomes;
    }
}
