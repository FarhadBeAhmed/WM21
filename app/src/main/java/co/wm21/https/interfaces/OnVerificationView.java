package co.wm21.https.interfaces;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.VerificationModel;

public interface OnVerificationView {
    void onVerificationDataLoad(VerificationModel verificationModel);

    void onVerificationStartLoading();

    void onVerificationStopLoading();

    void onVerificationShowMessage(String errmsg);
}
