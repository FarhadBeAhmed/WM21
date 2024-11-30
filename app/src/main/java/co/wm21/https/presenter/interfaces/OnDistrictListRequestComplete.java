package co.wm21.https.presenter.interfaces;

public interface OnDistrictListRequestComplete {
    void onDistrictListRequestSuccess(Object obj);

    void onDistrictListRequestError(String errMsg);
}
