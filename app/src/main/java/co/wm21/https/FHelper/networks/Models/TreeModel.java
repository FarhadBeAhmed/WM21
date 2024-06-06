package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TreeModel {
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("error_report")
    @Expose
    private String errorReport;
    @SerializedName("tree")
    @Expose
    private List<TreeData> tree;

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

    public List<TreeData> getTree() {
        return tree;
    }

    public void setTree(List<TreeData> tree) {
        this.tree = tree;
    }
}
