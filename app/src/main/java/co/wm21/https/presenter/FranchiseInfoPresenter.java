package co.wm21.https.presenter;



import java.util.HashMap;

import co.wm21.https.presenter.interfaces.OnFranchiseInfoRequestComplete;
import co.wm21.https.presenter.interfaces.OnFranchiseInfoView;
import co.wm21.https.serviceapis.InvokeFranchiseInfoApi;

public class FranchiseInfoPresenter {
    OnFranchiseInfoView mView;

    public FranchiseInfoPresenter(OnFranchiseInfoView mView) {
        this.mView = mView;
    }

    public void onFranchiseInfoDataResponse(String divisionID, String districtID, String thanaID){
        mView.onFranchiseInfoStartLoading();
        new InvokeFranchiseInfoApi(divisionID, districtID, thanaID, new OnFranchiseInfoRequestComplete() {
            @Override
            public void onFranchiseInfoRequestSuccess(Object obj) {
                mView.onFranchiseInfoStopLoading();
                mView.onFranchiseInfoData((HashMap) obj);
            }

            @Override
            public void onFranchiseInfoRequestError(String errMsg) {
                mView.onFranchiseInfoStopLoading();
                mView.onFranchiseInfoShowMessage(errMsg);
            }
        });
    }
}
