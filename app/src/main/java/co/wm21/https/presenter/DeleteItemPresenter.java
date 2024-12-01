package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.DeleteItem;
import co.wm21.https.presenter.interfaces.OnDeleteItemView;
import co.wm21.https.serviceapis.InvokeDeleteItemApi;

public class DeleteItemPresenter {
    OnDeleteItemView deleteItemView;

    public DeleteItemPresenter(OnDeleteItemView deleteItemView) {
        this.deleteItemView = deleteItemView;
    }

    public void deleteItemDataLoad(String pId,int type) {
        deleteItemView.onDeleteItemStartLoading();
        new InvokeDeleteItemApi(pId,type, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                deleteItemView.onDeleteItemStopLoading();
                deleteItemView.onDeleteItemDataLoad((DeleteItem) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                deleteItemView.onDeleteItemStopLoading();
                deleteItemView.onDeleteItemShowMessage(errMsg);
            }
        });
    }
}
