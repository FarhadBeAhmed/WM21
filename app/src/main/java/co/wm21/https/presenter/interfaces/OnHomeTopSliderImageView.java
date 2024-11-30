package co.wm21.https.presenter.interfaces;

import com.google.gson.JsonArray;

import java.util.List;

import co.wm21.https.SliderItem;

public interface OnHomeTopSliderImageView {
    void onHomeSliderDataLoaded(List<SliderItem> sliderItem);

    void onHomeSliderDataStartLoading();

    void onHomeSliderDataStopLoading();

    void onHomeSliderDataShowMessage(String errMsg);
}
