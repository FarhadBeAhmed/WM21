package co.wm21.https.presenter.interfaces;

import com.example.example.SlideImage;
import com.google.gson.JsonArray;

import java.util.List;


public interface OnHomeTopSliderImageView {
    void onHomeSliderDataLoaded(List<SlideImage> sliderItem);

    void onHomeSliderDataStartLoading();

    void onHomeSliderDataStopLoading();

    void onHomeSliderDataShowMessage(String errMsg);
}
