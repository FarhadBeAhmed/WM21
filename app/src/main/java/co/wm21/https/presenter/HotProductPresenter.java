package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.presenter.interfaces.OnHotProductView;
import co.wm21.https.serviceapis.InvokeHotProductApi;

public class HotProductPresenter {
    private OnHotProductView onHotProductView;

    public HotProductPresenter(OnHotProductView onHotProductView) {
        this.onHotProductView = onHotProductView;
    }
    public void getHotProduct(int value){
        onHotProductView.onHotProductStartLoading();
        new InvokeHotProductApi(value, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                onHotProductView.onHotProductStopLoading();
                onHotProductView.onHotProductDataLoaded((List<ProductModel>)obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                onHotProductView.onHotProductStopLoading();
                onHotProductView.onHotProductShowMessage(errMsg);
            }
        });

    }

}
