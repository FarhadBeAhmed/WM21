package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignupFinalModel {
    @SerializedName("error")
    @Expose
    private int error;
    @SerializedName("error_report")
    @Expose
    private String error_report;
    @SerializedName("data")
    @Expose
    private String data;

    @SerializedName("countries")
    @Expose
    private List<Countries> countries;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getError_report() {
        return error_report;
    }

    public void setError_report(String error_report) {
        this.error_report = error_report;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Countries> getCountries() {
        return countries;
    }

    public void setCountries(List<Countries> countries) {
        this.countries = countries;
    }
}
