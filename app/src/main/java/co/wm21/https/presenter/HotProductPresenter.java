package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.adapters.category.CategoryView;
import co.wm21.https.adapters.product.ProductView;
import co.wm21.https.interfaces.OnHomeCategoryRequestComplete;
import co.wm21.https.interfaces.OnHomeCategoryView;
import co.wm21.https.interfaces.OnHomePopularProductComplete;
import co.wm21.https.interfaces.OnHomePopularProductView;
import co.wm21.https.serviceapis.InvokeHomeCategoryApi;
import co.wm21.https.serviceapis.InvokeHomePopularProductApi;

public class HomePopularProductPresenter {
    private OnHomePopularProductView onHomePopularProductView;

    public HomePopularProductPresenter(OnHomePopularProductView onHomePopularProductView) {
        this.onHomePopularProductView = onHomePopularProductView;
    }
    public void getHomePopularProduct(String value){
        onHomePopularProductView.onHomePopularProductStartLoading();
        new InvokeHomePopularProductApi(value, new OnHomePopularProductComplete() {
            @Override
            public void onHomePopularProductSuccess(Object obj) {
                onHomePopularProductView.onHomePopularProductStopLoading();
                onHomePopularProductView.onHomePopularProductLoaded((List<ProductModel>)obj);
            }

            @Override
            public void onHomePopularProductError(String errMsg) {
                onHomePopularProductView.onHomePopularProductStopLoading();
                onHomePopularProductView.onHomePopularProductShowMessage(errMsg);
            }
        });

    }

}
