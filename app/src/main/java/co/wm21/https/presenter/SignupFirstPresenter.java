package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.SignupModel;
import co.wm21.https.presenter.interfaces.OnSignupFirstRequestComplete;
import co.wm21.https.presenter.interfaces.OnSignupFirstView;
import co.wm21.https.serviceapis.InvokeSignupFirstApi;

public class SignupFirstPresenter {
    OnSignupFirstView signupFirstView;

    public SignupFirstPresenter(OnSignupFirstView signupFirstView) {
        this.signupFirstView = signupFirstView;
    }

    public void signupFirstDataLoad(String mobile,String userId) {
        signupFirstView.onSignupFirstStartLoading();
        new InvokeSignupFirstApi(mobile,userId, new OnSignupFirstRequestComplete() {
            @Override
            public void onSignupFirstRequestComplete(Object obj) {
                signupFirstView.onSignupFirstStopLoading();
                signupFirstView.onSignupFirstDataLoad((SignupModel) obj);
            }

            @Override
            public void onSignupFirstRequestError(String errMsg) {
                signupFirstView.onSignupFirstStopLoading();
                signupFirstView.onSignupFirstShowMessage(errMsg);
            }
        });
    }
}
