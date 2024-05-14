package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.UpdateQty;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnAddToCartView;
import co.wm21.https.interfaces.OnUpdateQtyRequestComplete;
import co.wm21.https.interfaces.OnUpdateQtyView;
import co.wm21.https.serviceapis.InvokeAddToCartApi;
import co.wm21.https.serviceapis.InvokeUpdateQtyApi;

public class UpdateQtyPresenter {
    OnUpdateQtyView updateQtyView;

    public UpdateQtyPresenter(OnUpdateQtyView updateQtyView) {
        this.updateQtyView = updateQtyView;
    }

    public void UpdateQtyDataLoad(String userId,String pId,String color,String size,int qty,int type) {
        updateQtyView.onUpdateQtyStartLoading();
        new InvokeUpdateQtyApi(userId,pId,color,size,qty,type, new OnUpdateQtyRequestComplete() {
            @Override
            public void onUpdateQtyRequestComplete(Object obj) {
                updateQtyView.onUpdateQtyStopLoading();
                updateQtyView.onUpdateQtyDataLoad((UpdateQty) obj);
            }

            @Override
            public void onUpdateQtyRequestError(String errMsg) {
                updateQtyView.onUpdateQtyStopLoading();
                updateQtyView.onUpdateQtyShowMessage(errMsg);
            }
        });
    }
}
