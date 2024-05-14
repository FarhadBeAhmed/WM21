package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.VerificationModel;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnAddToCartView;
import co.wm21.https.interfaces.OnVerificationRequestComplete;
import co.wm21.https.interfaces.OnVerificationView;
import co.wm21.https.serviceapis.InvokeAddToCartApi;
import co.wm21.https.serviceapis.InvokeVerificationApi;

public class VerificationPresenter {
    OnVerificationView verificationView;

    public VerificationPresenter(OnVerificationView verificationView) {
        this.verificationView = verificationView;
    }

    public void verificationDataLoad(String user_id) {
        verificationView.onVerificationStartLoading();
        new InvokeVerificationApi(user_id, new OnVerificationRequestComplete() {
            @Override
            public void onVerificationRequestComplete(Object obj) {
                verificationView.onVerificationStopLoading();
                verificationView.onVerificationDataLoad((VerificationModel) obj);
            }

            @Override
            public void onVerificationRequestError(String errMsg) {
                verificationView.onVerificationStopLoading();
                verificationView.onVerificationShowMessage(errMsg);
            }
        });
    }
}
