package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.TopSellingProModel;
import co.wm21.https.presenter.interfaces.OnTopSellingProRequestComplete;
import co.wm21.https.presenter.interfaces.OnTopSellingProView;
import co.wm21.https.serviceapis.InvokeTopSellingProApi;

public class TopSellingProPresenter {
    OnTopSellingProView topSellingProView;

    public TopSellingProPresenter(OnTopSellingProView topSellingProView) {
        this.topSellingProView = topSellingProView;
    }

    public void topSellingProDataLoad(int limit) {
        topSellingProView.onTopSellingProStartLoading();
        new InvokeTopSellingProApi(limit, new OnTopSellingProRequestComplete() {
            @Override
            public void onTopSellingProRequestComplete(Object obj) {
                topSellingProView.onTopSellingProStopLoading();
                topSellingProView.onTopSellingProDataLoad((List<TopSellingProModel>) obj);
            }

            @Override
            public void onTopSellingProRequestError(String errMsg) {
                topSellingProView.onTopSellingProStopLoading();
                topSellingProView.onTopSellingProShowMessage(errMsg);
            }
        });
    }
}
