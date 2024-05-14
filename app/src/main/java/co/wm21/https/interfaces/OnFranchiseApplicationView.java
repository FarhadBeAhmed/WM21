package co.wm21.https.interfaces;

import java.util.HashMap;

public interface OnFranchiseApplicationView {
    void onFranchiseApplicationData(HashMap hashMap);

    void onFranchiseApplicationStartLoading();

    void onFranchiseApplicationStopLoading();

    void onFranchiseApplicationShowMessage(String errMsg);
}
