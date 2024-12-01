package co.wm21.https.presenter;


import java.util.List;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.BrandAmbassadorListModel;
import co.wm21.https.presenter.interfaces.OnBrandAmbassadorListView;
import co.wm21.https.serviceapis.InvokeBrandAmbassadorApi;

public class BrandAmbassadorPresenter {
    private OnBrandAmbassadorListView onBrandAmbassadorListView;

    public BrandAmbassadorPresenter(OnBrandAmbassadorListView onBrandAmbassadorListView) {
        this.onBrandAmbassadorListView = onBrandAmbassadorListView;
    }

    public void brandAmbassadorDataResponse (String limit) {

        onBrandAmbassadorListView.onBrandAmbassadorStartLoading();
        new InvokeBrandAmbassadorApi(limit, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                onBrandAmbassadorListView.onBrandAmbassadorStopLoading();
                onBrandAmbassadorListView.onBrandAmbassadorReqData((List<BrandAmbassadorListModel>) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                onBrandAmbassadorListView.onBrandAmbassadorStopLoading();
                onBrandAmbassadorListView.onBrandAmbassadorShowMessage(errMsg);
            }
        });
    }
}
