package com.wm21ltd.wm21.networks.Models;

import com.google.gson.annotations.SerializedName;

public class RewardPolicyDataListModel {
    @SerializedName("Serial")
    private String serial;
    @SerializedName("Rank")
    private String rank;
    @SerializedName("Team_A")
    private String teamA;
    @SerializedName("Team_B")
    private String teamB;

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
}
