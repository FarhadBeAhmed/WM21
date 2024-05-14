package co.wm21.https.interfaces;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.SignupModel;
import co.wm21.https.FHelper.networks.Models.SignupNumberVerifyModel;

public interface OnSignupNumberVerifyView {
    void onSignupNumberVerifyDataLoad(SignupNumberVerifyModel signupNumberVerifyModel);

    void onSignupNumberVerifyStartLoading();

    void onSignupNumberVerifyStopLoading();

    void onSignupNumberVerifyShowMessage(String errmsg);
}
