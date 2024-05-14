package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.SignupModel;
import co.wm21.https.FHelper.networks.Models.SignupNumberVerifyModel;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnAddToCartView;
import co.wm21.https.interfaces.OnSignupNumberVerifyRequestComplete;
import co.wm21.https.interfaces.OnSignupNumberVerifyView;
import co.wm21.https.serviceapis.InvokeAddToCartApi;
import co.wm21.https.serviceapis.InvokeSignupNumberVerifyApi;

public class SignupNumberVerifyPresenter {
    OnSignupNumberVerifyView signupNumberVerifyView;

    public SignupNumberVerifyPresenter(OnSignupNumberVerifyView signupNumberVerifyView) {
        this.signupNumberVerifyView = signupNumberVerifyView;
    }

    public void signupNumberVerifyDataLoad(String mobile,String country,String code) {
        signupNumberVerifyView.onSignupNumberVerifyStartLoading();
        new InvokeSignupNumberVerifyApi(mobile,country,code, new OnSignupNumberVerifyRequestComplete() {
            @Override
            public void onSignupNumberVerifyRequestComplete(Object obj) {
                signupNumberVerifyView.onSignupNumberVerifyStopLoading();
                signupNumberVerifyView.onSignupNumberVerifyDataLoad((SignupNumberVerifyModel) obj);
            }

            @Override
            public void onSignupNumberVerifyRequestError(String errMsg) {
                signupNumberVerifyView.onSignupNumberVerifyStopLoading();
                signupNumberVerifyView.onSignupNumberVerifyShowMessage(errMsg);
            }
        });
    }
}
