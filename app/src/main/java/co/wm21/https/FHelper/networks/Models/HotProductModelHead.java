package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularProductViewModelHead {
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("error_report")
    @Expose
    private String errorReport;
    @SerializedName("data")
    @Expose
    private List<ProductModel> productViews = null;
    private final static long serialVersionUID = 8482563432836191883L;

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

    public List<ProductModel> getProductViews() {
        return productViews;
    }

    public void setProductViews(List<ProductModel> productViews) {
        this.productViews = productViews;
    }
}
