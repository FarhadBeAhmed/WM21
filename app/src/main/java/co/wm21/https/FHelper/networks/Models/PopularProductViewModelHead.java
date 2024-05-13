package co.wm21.https.FHelper.networks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import co.wm21.https.SliderItem;
import co.wm21.https.adapters.product.ProductView;

public class HomeProductViewModelHead {
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("error_report")
    @Expose
    private String errorReport;
    @SerializedName("data")
    @Expose
    private List<ProductView> productViews = null;
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

    public List<ProductView> getProductViews() {
        return productViews;
    }

    public void setProductViews(List<ProductView> productViews) {
        this.productViews = productViews;
    }
}
