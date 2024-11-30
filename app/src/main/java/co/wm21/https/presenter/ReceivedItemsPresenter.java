package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.ReceivedItemsModelHead;
import co.wm21.https.presenter.interfaces.OnReceivedItemsRequestComplete;
import co.wm21.https.presenter.interfaces.OnReceivedItemsView;
import co.wm21.https.serviceapis.InvokeReceivedItemsApi;

public class ReceivedItemsPresenter {
    OnReceivedItemsView onReceivedItemsView;

    public ReceivedItemsPresenter(OnReceivedItemsView onReceivedItemsView) {
        this.onReceivedItemsView = onReceivedItemsView;
    }

    public void receivedItemsDataLoad(String userId) {
        onReceivedItemsView.onReceivedItemsStartLoading();
        new InvokeReceivedItemsApi(userId, new OnReceivedItemsRequestComplete() {
            @Override
            public void onReceivedItemsRequestComplete(Object obj) {
                onReceivedItemsView.onReceivedItemsStopLoading();
                onReceivedItemsView.onReceivedItemsDataLoad((ReceivedItemsModelHead) obj);
            }

            @Override
            public void onReceivedItemsRequestError(String errMsg) {
                onReceivedItemsView.onReceivedItemsStopLoading();
                onReceivedItemsView.onReceivedItemsShowMessage(errMsg);
            }
        });
    }
}
