package co.wm21.https.FHelper.networks.Models;

import com.example.example.SlideImage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class HomeTopSliderImageModelHead {
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("error_report")
    @Expose
    private String errorReport;
    @SerializedName("data")
    @Expose
    private List<SlideImage> sliderItems = null;
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

    public List<SlideImage> getSliderItems() {
        return sliderItems;
    }

    public void setSliderItems(List<SlideImage> sliderItems) {
        this.sliderItems = sliderItems;
    }
}
