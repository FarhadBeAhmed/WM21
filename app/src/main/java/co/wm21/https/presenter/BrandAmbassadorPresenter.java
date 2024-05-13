package com.wm21ltd.wm21.presenters;

import com.wm21ltd.wm21.interfaces.OnBrandAmbassadorListView;
import com.wm21ltd.wm21.interfaces.OnBrandAmbassadorRequestComplete;
import com.wm21ltd.wm21.networks.Models.BrandAmbassadorDataModel;
import com.wm21ltd.wm21.networks.Models.BrandAmbassadorListModel;
import com.wm21ltd.wm21.serviceapis.InvokeBrandAmbassadorApi;

import java.util.List;

public class BrandAmbassadorPresenter {
    private OnBrandAmbassadorListView onBrandAmbassadorListView;

    public BrandAmbassadorPresenter(OnBrandAmbassadorListView onBrandAmbassadorListView) {
        this.onBrandAmbassadorListView = onBrandAmbassadorListView;
    }

    public void brandAmbassadorDataResponse (String limit) {

        onBrandAmbassadorListView.onBrandAmbassadorStartLoading();
        new InvokeBrandAmbassadorApi(limit, new OnBrandAmbassadorRequestComplete() {
            @Override
            public void onBrandAmbassadorRequestComplete(Object obj) {
                onBrandAmbassadorListView.onBrandAmbassadorStopLoading();
                onBrandAmbassadorListView.onBrandAmbassadorReqData((List<BrandAmbassadorListModel>) obj);
            }

            @Override
            public void onBrandAmbassadorRequestError(String errMsg) {
                onBrandAmbassadorListView.onBrandAmbassadorStopLoading();
                onBrandAmbassadorListView.onBrandAmbassadorShowMessage(errMsg);
            }
        });
    }
}
