package co.wm21.https.presenter;



import java.util.HashMap;

import co.wm21.https.interfaces.OnFranchiseApplicationRequestComplete;
import co.wm21.https.interfaces.OnFranchiseApplicationView;
import co.wm21.https.serviceapis.InvokeFranchiseApplicationApi;

public class FranchiseApplicationPresenter {
    OnFranchiseApplicationView mView;

    public FranchiseApplicationPresenter(OnFranchiseApplicationView mView) {
        this.mView = mView;
    }

    public void onFranchiseApplicationResponseData(String serviceID, String userID, String formType, String divisionID,
                                                   String districtID, String thanaID, String applicantName, String applicantAddress,
                                                   String applicantLicense, String category){
        mView.onFranchiseApplicationStartLoading();
        new InvokeFranchiseApplicationApi(serviceID, userID, formType, divisionID, districtID, thanaID, applicantName, applicantAddress, applicantLicense, category, new OnFranchiseApplicationRequestComplete() {
            @Override
            public void onFranchiseApplicationRequestSuccess(Object obj) {
                mView.onFranchiseApplicationStopLoading();
                mView.onFranchiseApplicationData((HashMap) obj);
            }

            @Override
            public void onFranchiseApplicationRequestError(String errMsg) {
                mView.onFranchiseApplicationStopLoading();
                mView.onFranchiseApplicationShowMessage(errMsg);
            }
        });
    }
}
