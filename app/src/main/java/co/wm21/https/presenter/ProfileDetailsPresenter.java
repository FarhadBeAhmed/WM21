package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.ProfileDetailsHead;
import co.wm21.https.presenter.interfaces.OnProfileDetailsRequestComplete;
import co.wm21.https.presenter.interfaces.OnProfileDetailsView;
import co.wm21.https.serviceapis.InvokeProfileDetailsApi;

public class ProfileDetailsPresenter {
    OnProfileDetailsView profileDetailsView;

    public ProfileDetailsPresenter(OnProfileDetailsView profileDetailsView) {
        this.profileDetailsView = profileDetailsView;
    }

    public void profileDetailsDataLoad(String userId) {
        profileDetailsView.onProfileDetailsStartLoading();
        new InvokeProfileDetailsApi(userId, new OnProfileDetailsRequestComplete() {
            @Override
            public void onProfileDetailsRequestComplete(Object obj) {
                profileDetailsView.onProfileDetailsStopLoading();
                profileDetailsView.onProfileDetailsDataLoad((ProfileDetailsHead) obj);
            }

            @Override
            public void onProfileDetailsRequestError(String errMsg) {
                profileDetailsView.onProfileDetailsStopLoading();
                profileDetailsView.onProfileDetailsShowMessage(errMsg);
            }
        });
    }
}
