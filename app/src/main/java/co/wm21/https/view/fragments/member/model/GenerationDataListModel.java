package co.wm21.https.view.fragments.member.model;

import com.google.gson.annotations.SerializedName;

public class GenerationDataListModel {
    @SerializedName("Serial")
    private String serial;
    @SerializedName("Generation")
    private String generation;
    @SerializedName("Member")
    private String member;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }
}
