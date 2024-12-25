package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.SignupNumberVerifyModel;
import co.wm21.https.presenter.interfaces.OnSignupNumberVerifyView;
import co.wm21.https.serviceapis.InvokeSignupNumberVerifyApi;

public class SignupNumberVerifyPresenter {
    OnSignupNumberVerifyView signupNumberVerifyView;

    public SignupNumberVerifyPresenter(OnSignupNumberVerifyView signupNumberVerifyView) {
        this.signupNumberVerifyView = signupNumberVerifyView;
    }

    public void signupNumberVerifyDataLoad(String mobile,String country,String code) {
        signupNumberVerifyView.onSignupNumberVerifyStartLoading();
        new InvokeSignupNumberVerifyApi(mobile,country,code, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                signupNumberVerifyView.onSignupNumberVerifyStopLoading();
                signupNumberVerifyView.onSignupNumberVerifyDataLoad((SignupNumberVerifyModel) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                signupNumberVerifyView.onSignupNumberVerifyStopLoading();
                signupNumberVerifyView.onSignupNumberVerifyShowMessage(errMsg);
            }
        });
    }
}
