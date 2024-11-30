package co.wm21.https.FHelper.networks.Models.transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WithdrawalHistoryData {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("franchise")
    @Expose
    private String franchise;
    @SerializedName("withdrawl")
    @Expose
    private String withdrawl;
    @SerializedName("received")
    @Expose
    private String received;
    @SerializedName("gateway")
    @Expose
    private String gateway;
    @SerializedName("accNumber")
    @Expose
    private String accNumber;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("acStatus")
    @Expose
    private String acStatus;
    @SerializedName("transId")
    @Expose
    private String transId;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    public String getWithdrawl() {
        return withdrawl;
    }

    public void setWithdrawl(String withdrawl) {
        this.withdrawl = withdrawl;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAcStatus() {
        return acStatus;
    }

    public void setAcStatus(String acStatus) {
        this.acStatus = acStatus;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

}
