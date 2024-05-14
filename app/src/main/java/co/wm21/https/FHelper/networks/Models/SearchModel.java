package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SearchModel {

    @SerializedName("search_found")
    private Integer searchFound;
    @SerializedName("error")
    private Integer error;
    @SerializedName("error_report")
    private String errorReport;
    @SerializedName("search_result")
    private List<SearchDataModel> searchResult = null;

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

    public List<SearchDataModel> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<SearchDataModel> searchResult) {
        this.searchResult = searchResult;
    }

}
