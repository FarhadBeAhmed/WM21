package co.wm21.https.FHelper.networks.Models.balanceResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceModel {
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("error_report")
    @Expose
    private String errorReport;
    @SerializedName("data")
    @Expose
    private BalanceData balanceData;

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

    public BalanceData getBalanceData() {
        return balanceData;
    }

    public void setBalanceData(BalanceData balanceData) {
        this.balanceData = balanceData;
    }
}
