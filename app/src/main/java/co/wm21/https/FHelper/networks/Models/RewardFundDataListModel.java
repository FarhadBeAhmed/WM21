package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.SerializedName;

public class RewardFundDataListModel {
    @SerializedName("Serial")
    private String serial;
    @SerializedName("Rank")
    private String rank;
    @SerializedName("Budget")
    private String budget;
    @SerializedName("Status")
    private String status;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
