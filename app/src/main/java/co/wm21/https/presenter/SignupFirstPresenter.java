package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.SignupModel;
import co.wm21.https.presenter.interfaces.OnSignupFirstView;
import co.wm21.https.serviceapis.InvokeSignupFirstApi;

public class SignupFirstPresenter {
    OnSignupFirstView signupFirstView;

    public SignupFirstPresenter(OnSignupFirstView signupFirstView) {
        this.signupFirstView = signupFirstView;
    }

    public void signupFirstDataLoad(String mobile,String userId) {
        signupFirstView.onSignupFirstStartLoading();
        new InvokeSignupFirstApi(mobile,userId, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                signupFirstView.onSignupFirstStopLoading();
                signupFirstView.onSignupFirstDataLoad((SignupModel) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                signupFirstView.onSignupFirstStopLoading();
                signupFirstView.onSignupFirstShowMessage(errMsg);
            }
        });
    }
}
