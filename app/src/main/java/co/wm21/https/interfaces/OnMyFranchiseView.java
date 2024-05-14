package co.wm21.https.interfaces;

import java.util.HashMap;

public interface OnMyFranchiseView {
    void onResponseData(HashMap hashMap);

    void startLoading();

    void stopLoading();

    void showMessage(String errMsg);
}
