package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.DeleteItem;
import co.wm21.https.presenter.interfaces.OnDeleteItemRequestComplete;
import co.wm21.https.presenter.interfaces.OnDeleteItemView;
import co.wm21.https.serviceapis.InvokeDeleteItemApi;

public class DeleteItemPresenter {
    OnDeleteItemView deleteItemView;

    public DeleteItemPresenter(OnDeleteItemView deleteItemView) {
        this.deleteItemView = deleteItemView;
    }

    public void deleteItemDataLoad(String pId,int type) {
        deleteItemView.onDeleteItemStartLoading();
        new InvokeDeleteItemApi(pId,type, new OnDeleteItemRequestComplete() {
            @Override
            public void onDeleteItemRequestComplete(Object obj) {
                deleteItemView.onDeleteItemStopLoading();
                deleteItemView.onDeleteItemDataLoad((DeleteItem) obj);
            }

            @Override
            public void onDeleteItemRequestError(String errMsg) {
                deleteItemView.onDeleteItemStopLoading();
                deleteItemView.onDeleteItemShowMessage(errMsg);
            }
        });
    }
}
