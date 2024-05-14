package co.wm21.https.interfaces;

public interface OnDistrictListRequestComplete {
    void onDistrictListRequestSuccess(Object obj);

    void onDistrictListRequestError(String errMsg);
}
