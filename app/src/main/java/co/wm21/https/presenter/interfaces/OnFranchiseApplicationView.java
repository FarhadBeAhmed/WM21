package co.wm21.https.presenter.interfaces;

import java.util.HashMap;

public interface OnFranchiseApplicationView {
    void onFranchiseApplicationData(HashMap hashMap);

    void onFranchiseApplicationStartLoading();

    void onFranchiseApplicationStopLoading();

    void onFranchiseApplicationShowMessage(String errMsg);
}
