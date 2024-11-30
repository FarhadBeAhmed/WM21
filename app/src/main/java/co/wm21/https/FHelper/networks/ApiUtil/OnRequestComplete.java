package co.wm21.https.FHelper.networks.ApiUtil;

public interface OnRequestComplete {
    void onRequestSuccess(Object obj);

    void onRequestError(String errMsg);
}
