package com.wm21ltd.wm21.interfaces;

import com.wm21ltd.wm21.networks.Models.BrandAmbassadorDataModel;
import com.wm21ltd.wm21.networks.Models.BrandAmbassadorListModel;

import java.util.List;

public interface OnBrandAmbassadorListView {

    void onBrandAmbassadorReqData (List<BrandAmbassadorListModel> brandAmbassadorList);

    void onBrandAmbassadorStartLoading();

    void onBrandAmbassadorStopLoading();

    void onBrandAmbassadorShowMessage(String msg);
}
