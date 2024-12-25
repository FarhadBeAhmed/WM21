package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.ProfileDetailsHead;
import co.wm21.https.presenter.interfaces.OnProfileDetailsView;
import co.wm21.https.serviceapis.InvokeProfileDetailsApi;

public class ProfileDetailsPresenter {
    OnProfileDetailsView profileDetailsView;

    public ProfileDetailsPresenter(OnProfileDetailsView profileDetailsView) {
        this.profileDetailsView = profileDetailsView;
    }

    public void profileDetailsDataLoad(String userId) {
        profileDetailsView.onProfileDetailsStartLoading();
        new InvokeProfileDetailsApi(userId, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                profileDetailsView.onProfileDetailsStopLoading();
                profileDetailsView.onProfileDetailsDataLoad((ProfileDetailsHead) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                profileDetailsView.onProfileDetailsStopLoading();
                profileDetailsView.onProfileDetailsShowMessage(errMsg);
            }
        });
    }
}
