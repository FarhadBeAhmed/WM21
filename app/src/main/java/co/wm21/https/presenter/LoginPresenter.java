package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.LoginModel;
import co.wm21.https.presenter.interfaces.OnLoginRequestComplete;
import co.wm21.https.presenter.interfaces.OnLoginView;
import co.wm21.https.serviceapis.InvokeLoginApi;

public class LoginPresenter {
    OnLoginView loginView;

    public LoginPresenter(OnLoginView loginView) {
        this.loginView = loginView;
    }

    public void login(String user,String pass) {
        loginView.onLoginStartLoading();
        new InvokeLoginApi(user,pass, new OnLoginRequestComplete() {
            @Override
            public void onLoginRequestComplete(Object obj) {
                loginView.onLoginStopLoading();
                loginView.onLoginDataLoad((LoginModel) obj);
            }

            @Override
            public void onLoginRequestError(String errMsg) {
                loginView.onLoginStopLoading();
                loginView.onLoginShowMessage(errMsg);
            }
        });
    }
}
