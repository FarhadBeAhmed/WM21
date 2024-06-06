package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TreeData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sub_id")
    @Expose
    private String subId;
    @SerializedName("hand")
    @Expose
    private String hand;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getHand() {
        return hand;
    }

    public void setHand(String hand) {
        this.hand = hand;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
