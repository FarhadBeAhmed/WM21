package co.wm21.https.view.fragments.member.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RewardPolicyDataListModel {
    @SerializedName("Serial")
    @Expose
    private String serial;
    @SerializedName("Rank")
    @Expose
    private String rank;
    @SerializedName("Team_A")
    @Expose
    private String teamA;
    @SerializedName("Team_B")
    @Expose
    private String teamB;
    @SerializedName("status")
    @Expose
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

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
