package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.interfaces.OnHomePopularCatProductComplete;
import co.wm21.https.interfaces.OnHomePopularCatProductView;
import co.wm21.https.interfaces.OnHomePopularProductComplete;
import co.wm21.https.interfaces.OnHomePopularProductView;
import co.wm21.https.serviceapis.InvokeHomePopularCatProductApi;
import co.wm21.https.serviceapis.InvokeHomePopularProductApi;

public class HomePopularCatProductPresenter {
    private OnHomePopularCatProductView onHomePopularCatProductView;

    public HomePopularCatProductPresenter(OnHomePopularCatProductView onHomePopularCatProductView) {
        this.onHomePopularCatProductView = onHomePopularCatProductView;
    }
    public void getHomePopularProduct(String value1, String value2, String value3, String value4 ){
        onHomePopularCatProductView.onHomePopularCatProductStartLoading();
        new InvokeHomePopularCatProductApi(value1,value2,value3,value4, new OnHomePopularCatProductComplete() {
            @Override
            public void onHomePopularCatProductSuccess(Object obj) {
                onHomePopularCatProductView.onHomePopularCatProductStopLoading();
                onHomePopularCatProductView.onHomePopularCatProductLoaded((List<ProductModel>)obj);
            }

            @Override
            public void onHomePopularCatProductError(String errMsg) {
                onHomePopularCatProductView.onHomePopularCatProductStopLoading();
                onHomePopularCatProductView.onHomePopularCatProductShowMessage(errMsg);
            }
        });

    }

}
