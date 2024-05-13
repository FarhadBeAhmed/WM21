package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZonalListModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("User")
    @Expose
    private String user;
    private final static long serialVersionUID = 5264107184960485207L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
