package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TreesResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("join")
    @Expose
    private String join;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("refer")
    @Expose
    private String refer;
    @SerializedName("A_Team_Member")
    @Expose
    private String aTeamMember;
    @SerializedName("B_Team_Member")
    @Expose
    private String bTeamMember;
    @SerializedName("A_team_carry")
    @Expose
    private String aTeamCarry;
    @SerializedName("B_team_carry")
    @Expose
    private String bTeamCarry;
    @SerializedName("A_Team_Point")
    @Expose
    private String aTeamPoint;
    @SerializedName("B_Team_Point")
    @Expose
    private String bTeamPoint;
    @SerializedName("A_Team_spot")
    @Expose
    private String aTeamSpot;
    @SerializedName("B_Team_spot")
    @Expose
    private String bTeamSpot;
    @SerializedName("photo")
    @Expose
    private String photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getATeamMember() {
        return aTeamMember;
    }

    public void setATeamMember(String aTeamMember) {
        this.aTeamMember = aTeamMember;
    }

    public String getBTeamMember() {
        return bTeamMember;
    }

    public void setBTeamMember(String bTeamMember) {
        this.bTeamMember = bTeamMember;
    }

    public String getATeamCarry() {
        return aTeamCarry;
    }

    public void setATeamCarry(String aTeamCarry) {
        this.aTeamCarry = aTeamCarry;
    }

    public String getBTeamCarry() {
        return bTeamCarry;
    }

    public void setBTeamCarry(String bTeamCarry) {
        this.bTeamCarry = bTeamCarry;
    }

    public String getATeamPoint() {
        return aTeamPoint;
    }

    public void setATeamPoint(String aTeamPoint) {
        this.aTeamPoint = aTeamPoint;
    }

    public String getBTeamPoint() {
        return bTeamPoint;
    }

    public void setBTeamPoint(String bTeamPoint) {
        this.bTeamPoint = bTeamPoint;
    }

    public String getATeamSpot() {
        return aTeamSpot;
    }

    public void setATeamSpot(String aTeamSpot) {
        this.aTeamSpot = aTeamSpot;
    }

    public String getBTeamSpot() {
        return bTeamSpot;
    }

    public void setBTeamSpot(String bTeamSpot) {
        this.bTeamSpot = bTeamSpot;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
