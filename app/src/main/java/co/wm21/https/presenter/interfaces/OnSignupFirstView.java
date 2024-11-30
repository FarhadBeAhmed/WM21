package co.wm21.https.presenter.interfaces;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.SignupModel;

public interface OnSignupFirstView {
    void onSignupFirstDataLoad(SignupModel signupModel);

    void onSignupFirstStartLoading();

    void onSignupFirstStopLoading();

    void onSignupFirstShowMessage(String errmsg);
}
