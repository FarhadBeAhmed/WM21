package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.view.adapters.category.CategoryView;
import co.wm21.https.presenter.interfaces.OnHomeCategoryRequestComplete;
import co.wm21.https.presenter.interfaces.OnHomeCategoryView;
import co.wm21.https.serviceapis.InvokeHomeCategoryApi;

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
