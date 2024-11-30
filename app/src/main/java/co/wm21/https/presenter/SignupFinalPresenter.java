package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.SignupModel;
import co.wm21.https.presenter.interfaces.OnSignupFinalRequestComplete;
import co.wm21.https.presenter.interfaces.OnSignupFinalView;
import co.wm21.https.serviceapis.InvokeSignupFinalApi;

public class SignupFinalPresenter {
    OnSignupFinalView signupFinalView;

    public SignupFinalPresenter(OnSignupFinalView signupFinalView) {
        this.signupFinalView = signupFinalView;
    }

    public void signupFinalDataLoad(String mobile,String name,String refer,String email,String division,String country) {
        signupFinalView.onSignupFinalStartLoading();
        new InvokeSignupFinalApi(mobile,name,refer,email,division,country, new OnSignupFinalRequestComplete() {
            @Override
            public void onSignupFinalRequestComplete(Object obj) {
                signupFinalView.onSignupFinalStopLoading();
                signupFinalView.onSignupFinalDataLoad((SignupModel) obj);
            }

            @Override
            public void onSignupFinalRequestError(String errMsg) {
                signupFinalView.onSignupFinalStopLoading();
                signupFinalView.onSignupFinalShowMessage(errMsg);
            }
        });
    }
}
