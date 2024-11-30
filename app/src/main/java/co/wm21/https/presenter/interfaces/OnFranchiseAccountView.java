package co.wm21.https.presenter.interfaces;


import java.util.List;

import co.wm21.https.FHelper.networks.Models.FranchiseAccountDataListModel;

public interface OnFranchiseAccountView {
    void onFranchiseAccountData(List<FranchiseAccountDataListModel> dataListModel);

    void onFranchiseAccountStartLoading();

    void onFranchiseAccountStopLoading();

    void onFranchiseAccountShowMessage(String errMsg);
}
