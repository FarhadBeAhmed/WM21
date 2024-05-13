package com.wm21ltd.wm21.networks.Models;

import com.google.gson.annotations.SerializedName;

public class TeamInfoDataListModel {
    @SerializedName("Serial")
    private String serial;
    @SerializedName("Type")
    private String type;
    @SerializedName("Type_value")
    private String typeValue;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
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
