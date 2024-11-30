package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.presenter.interfaces.OnAddToCartView;
import co.wm21.https.serviceapis.InvokeAddToCartApi;

public class AddToCartPresenter {
    OnAddToCartView addToCartView;

    public AddToCartPresenter(OnAddToCartView addToCartView) {
        this.addToCartView = addToCartView;
    }

    public void AddToCartDataLoad(String pId,String userId,String color,String size,int qty) {
        addToCartView.onAddToCartStartLoading();
        new InvokeAddToCartApi(pId,userId,color,size,qty, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                addToCartView.onAddToCartStopLoading();
                addToCartView.onAddToCartDataLoad((AddToCartModel) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                addToCartView.onAddToCartStopLoading();
                addToCartView.onAddToCartShowMessage(errMsg);
            }
        });
    }
}
