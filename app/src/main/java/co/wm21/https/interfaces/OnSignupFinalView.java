package co.wm21.https.interfaces;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.SignupModel;

public interface OnSignupFinalView {
    void onSignupFinalDataLoad(SignupModel signupModel);

    void onSignupFinalStartLoading();

    void onSignupFinalStopLoading();

    void onSignupFinalShowMessage(String errmsg);
}
