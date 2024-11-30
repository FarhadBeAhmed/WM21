package co.wm21.https.presenter;


import java.util.List;

import co.wm21.https.FHelper.networks.Models.FranchiseAccountDataListModel;
import co.wm21.https.presenter.interfaces.OnFranchiseAccountRequestComplete;
import co.wm21.https.presenter.interfaces.OnFranchiseAccountView;
import co.wm21.https.serviceapis.InvokeFranchiseAccountApi;

public class FranchiseAccountCommissionPresenter {
    OnFranchiseAccountView mView;

    public FranchiseAccountCommissionPresenter(OnFranchiseAccountView mView) {
        this.mView = mView;
    }

    public void onFranchiseAccountRequestData(String userID, String limitData){
        mView.onFranchiseAccountStartLoading();
        new InvokeFranchiseAccountApi(userID, limitData, new OnFranchiseAccountRequestComplete() {
            @Override
            public void onFranchiseAccountRequestSuccess(Object obj) {
                mView.onFranchiseAccountStopLoading();
                mView.onFranchiseAccountData((List<FranchiseAccountDataListModel>) obj);
            }

            @Override
            public void onFranchiseAccountRequestError(String errMsg) {
                mView.onFranchiseAccountStopLoading();
                mView.onFranchiseAccountShowMessage(errMsg);
            }
        });
    }
}
