package co.wm21.https.presenter.interfaces;

public interface OnVerificationRequestComplete {
    void onVerificationRequestComplete(Object obj);

    void onVerificationRequestError(String errMsg);
}
