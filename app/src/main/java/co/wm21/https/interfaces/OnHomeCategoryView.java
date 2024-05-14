package co.wm21.https.interfaces;

import java.util.List;

import co.wm21.https.SliderItem;
import co.wm21.https.adapters.category.CategoryView;

public interface OnHomeCategoryView {
    void onHomeCategoryDataLoaded(List<CategoryView> sliderItem);

    void onHomeCategoryDataStartLoading();

    void onHomeCategoryDataStopLoading();

    void onHomeCategoryDataShowMessage(String errMsg);
}
