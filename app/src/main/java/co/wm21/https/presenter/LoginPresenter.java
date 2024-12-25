package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.LoginModel;
import co.wm21.https.presenter.interfaces.OnLoginView;
import co.wm21.https.serviceapis.InvokeLoginApi;

public class LoginPresenter {
    OnLoginView loginView;

    public LoginPresenter(OnLoginView loginView) {
        this.loginView = loginView;
    }

    public void login(String user,String pass) {
        loginView.onLoginStartLoading();
        new InvokeLoginApi(user,pass, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                loginView.onLoginStopLoading();
                loginView.onLoginDataLoad((LoginModel) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                loginView.onLoginStopLoading();
                loginView.onLoginShowMessage(errMsg);
            }
        });
    }
}
