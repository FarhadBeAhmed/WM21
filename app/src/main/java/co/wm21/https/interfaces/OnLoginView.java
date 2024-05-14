package co.wm21.https.interfaces;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.LoginModel;

public interface OnLoginView {
    void onLoginDataLoad(LoginModel loginModel);

    void onLoginStartLoading();

    void onLoginStopLoading();

    void onLoginShowMessage(String errmsg);
}
