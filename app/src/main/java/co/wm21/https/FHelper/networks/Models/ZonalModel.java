package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZonalModel {
    @SerializedName("search_found")
    @Expose
    private Integer searchFound;
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("error_report")
    @Expose
    private String errorReport;
    @SerializedName("zonal_list")
    @Expose
    private List<ZonalListModel> zonalList = null;
    private final static long serialVersionUID = -5190889175219871654L;

    public Integer getSearchFound() {
        return searchFound;
    }

    public void setSearchFound(Integer searchFound) {
        this.searchFound = searchFound;
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

    public List<ZonalListModel> getZonalList() {
        return zonalList;
    }

    public void setZonalList(List<ZonalListModel> zonalList) {
        this.zonalList = zonalList;
    }
}
