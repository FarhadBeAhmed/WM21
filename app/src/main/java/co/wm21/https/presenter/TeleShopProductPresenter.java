package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.interfaces.OnHomePopularProductComplete;
import co.wm21.https.interfaces.OnHomePopularProductView;
import co.wm21.https.interfaces.OnHotProductRequestComplete;
import co.wm21.https.interfaces.OnHotProductView;
import co.wm21.https.serviceapis.InvokeHomePopularProductApi;
import co.wm21.https.serviceapis.InvokeHotProductApi;

public class HotProductPresenter {
    private OnHotProductView onHotProductView;

    public HotProductPresenter(OnHotProductView onHotProductView) {
        this.onHotProductView = onHotProductView;
    }
    public void getHotProduct(int value){
        onHotProductView.onHotProductStartLoading();
        new InvokeHotProductApi(value, new OnHotProductRequestComplete() {
            @Override
            public void onHotProductSuccess(Object obj) {
                onHotProductView.onHotProductStopLoading();
                onHotProductView.onHotProductDataLoaded((List<ProductModel>)obj);
            }

            @Override
            public void onHotProductError(String errMsg) {
                onHotProductView.onHotProductStopLoading();
                onHotProductView.onHotProductShowMessage(errMsg);
            }
        });

    }

}
