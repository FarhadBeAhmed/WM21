package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.HomeCategoryHead;
import co.wm21.https.SliderItem;
import co.wm21.https.adapters.category.CategoryView;
import co.wm21.https.interfaces.OnHomeCategoryRequestComplete;
import co.wm21.https.interfaces.OnHomeCategoryView;
import co.wm21.https.serviceapis.InvokeHomeCategoryApi;
import co.wm21.https.serviceapis.InvokeHomeTopSliderImageApi;

public class HomeCategoryPresenter {
    private OnHomeCategoryView onHomeCategoryView;

    public HomeCategoryPresenter(OnHomeCategoryView onHomeCategoryView) {
        this.onHomeCategoryView = onHomeCategoryView;
    }
    public void getCategoryDataResponse(String limit){
        onHomeCategoryView.onHomeCategoryDataStartLoading();
        new InvokeHomeCategoryApi(limit, new OnHomeCategoryRequestComplete() {
            @Override
            public void onHomeCategoryRequestSuccess(Object obj) {
                onHomeCategoryView.onHomeCategoryDataStopLoading();
                onHomeCategoryView.onHomeCategoryDataLoaded((List<CategoryView>)obj);
            }

            @Override
            public void onHomeCategoryRequestError(String errMsg) {
                onHomeCategoryView.onHomeCategoryDataStopLoading();
                onHomeCategoryView.onHomeCategoryDataShowMessage(errMsg);
            }
        });

    }

}
