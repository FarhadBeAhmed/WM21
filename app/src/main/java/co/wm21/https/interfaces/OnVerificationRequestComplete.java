package co.wm21.https.interfaces;

public interface OnVerificationRequestComplete {
    void onVerificationRequestComplete(Object obj);

    void onVerificationRequestError(String errMsg);
}
