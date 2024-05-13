package com.wm21ltd.wm21.interfaces;

import com.wm21ltd.wm21.networks.Models.FranchiseAccountDataListModel;

import java.util.List;

public interface OnFranchiseAccountView {
    void onFranchiseAccountData(List<FranchiseAccountDataListModel> dataListModel);

    void onFranchiseAccountStartLoading();

    void onFranchiseAccountStopLoading();

    void onFranchiseAccountShowMessage(String errMsg);
}
