package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.SerializedName;

public class FranchiseInfoSearchDataModel {
    @SerializedName("title")
    private String title;
    @SerializedName("name")
    private Object name;
    @SerializedName("mobile")
    private Object mobile;
    @SerializedName("email")
    private Object email;
    @SerializedName("addrss")
    private Object addrss;
    @SerializedName("error")
    private Integer error;
    @SerializedName("error_report")
    private String errorReport;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getAddrss() {
        return addrss;
    }

    public void setAddrss(Object addrss) {
        this.addrss = addrss;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getErrorReport() {
        return errorReport;
    }

    public void setErrorReport(String errorReport) {
        this.errorReport = errorReport;
    }

}
