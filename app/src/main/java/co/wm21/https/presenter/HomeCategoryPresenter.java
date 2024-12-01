package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.view.adapters.category.CategoryView;
import co.wm21.https.presenter.interfaces.OnHomeCategoryView;
import co.wm21.https.serviceapis.InvokeHomeCategoryApi;

public class HomeCategoryPresenter {
    private OnHomeCategoryView onHomeCategoryView;

    public HomeCategoryPresenter(OnHomeCategoryView onHomeCategoryView) {
        this.onHomeCategoryView = onHomeCategoryView;
    }
    public void getCategoryDataResponse(String limit){
        onHomeCategoryView.onHomeCategoryDataStartLoading();
        new InvokeHomeCategoryApi(limit, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                onHomeCategoryView.onHomeCategoryDataStopLoading();
                onHomeCategoryView.onHomeCategoryDataLoaded((List<CategoryView>)obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                onHomeCategoryView.onHomeCategoryDataStopLoading();
                onHomeCategoryView.onHomeCategoryDataShowMessage(errMsg);
            }
        });

    }

}
