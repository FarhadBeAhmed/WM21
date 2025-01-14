package co.wm21.https.presenter.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.home.CategoryView;

public interface OnHomeCategoryView {
    void onHomeCategoryDataLoaded(List<CategoryView> sliderItem);

    void onHomeCategoryDataStartLoading();

    void onHomeCategoryDataStopLoading();

    void onHomeCategoryDataShowMessage(String errMsg);
}
