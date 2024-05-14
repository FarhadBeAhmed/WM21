package co.wm21.https.interfaces;

public interface OnLoginRequestComplete {
    void onLoginRequestComplete(Object obj);

    void onLoginRequestError(String errMsg);
}
