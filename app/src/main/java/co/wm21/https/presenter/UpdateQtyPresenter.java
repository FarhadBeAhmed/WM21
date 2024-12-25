package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.UpdateQty;
import co.wm21.https.presenter.interfaces.OnUpdateQtyView;
import co.wm21.https.serviceapis.InvokeUpdateQtyApi;

public class UpdateQtyPresenter {
    OnUpdateQtyView updateQtyView;

    public UpdateQtyPresenter(OnUpdateQtyView updateQtyView) {
        this.updateQtyView = updateQtyView;
    }

    public void UpdateQtyDataLoad(String userId,String pId,String color,String size,int qty,int type) {
        updateQtyView.onUpdateQtyStartLoading();
        new InvokeUpdateQtyApi(userId,pId,color,size,qty,type, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                updateQtyView.onUpdateQtyStopLoading();
                updateQtyView.onUpdateQtyDataLoad((UpdateQty) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                updateQtyView.onUpdateQtyStopLoading();
                updateQtyView.onUpdateQtyShowMessage(errMsg);
            }
        });
    }
}
