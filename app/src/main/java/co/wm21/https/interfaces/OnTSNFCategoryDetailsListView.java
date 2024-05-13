package com.wm21ltd.wm21.interfaces;

import com.wm21ltd.wm21.networks.Models.TSNFCategoryDetailsListModel;

import java.util.List;

public interface OnTSNFCategoryDetailsListView {

    void onTSNFCategoryDetailsReqData(List<TSNFCategoryDetailsListModel> tsnfCategoryDetailsListModels);

    void onTSNFCategoryDetailsStartLoading();

    void onTSNFCategoryDetailsStopLoading();

    void oonTSNFCategoryDetailsShowMessage(String msg);
}
