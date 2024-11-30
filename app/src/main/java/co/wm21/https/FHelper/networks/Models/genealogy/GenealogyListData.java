package co.wm21.https.FHelper.networks.Models.genealogy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenealogyListData {
    @SerializedName("tre_id")
    @Expose
    private Integer treId;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("rp")
    @Expose
    private Integer rp;
    @SerializedName("left_member")
    @Expose
    private Integer leftMember;
    @SerializedName("left_point")
    @Expose
    private Integer leftPoint;
    @SerializedName("right_member")
    @Expose
    private Integer rightMember;
    @SerializedName("right_point")
    @Expose
    private Integer rightPoint;

    public Integer getTreId() {
        return treId;
    }

    public void setTreId(Integer treId) {
        this.treId = treId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRp() {
        return rp;
    }

    public void setRp(Integer rp) {
        this.rp = rp;
    }

    public Integer getLeftMember() {
        return leftMember;
    }

    public void setLeftMember(Integer leftMember) {
        this.leftMember = leftMember;
    }

    public Integer getLeftPoint() {
        return leftPoint;
    }

    public void setLeftPoint(Integer leftPoint) {
        this.leftPoint = leftPoint;
    }

    public Integer getRightMember() {
        return rightMember;
    }

    public void setRightMember(Integer rightMember) {
        this.rightMember = rightMember;
    }

    public Integer getRightPoint() {
        return rightPoint;
    }

    public void setRightPoint(Integer rightPoint) {
        this.rightPoint = rightPoint;
    }


}
