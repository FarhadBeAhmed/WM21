package co.wm21.https.presenter.interfaces;

public interface OnDistrictDataRequestComplete {
    void onDistrictListRequestSuccess(Object obj);

    void onDistrictListRequestError(String errMsg);
}
