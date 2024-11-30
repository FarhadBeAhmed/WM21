package co.wm21.https.presenter.interfaces;

import java.util.HashMap;

public interface OnMyFranchiseView {
    void onResponseData(HashMap hashMap);

    void startLoading();

    void stopLoading();

    void showMessage(String errMsg);
}
