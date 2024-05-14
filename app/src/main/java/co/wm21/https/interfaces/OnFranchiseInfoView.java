package co.wm21.https.interfaces;

import java.util.HashMap;

public interface OnFranchiseInfoView {
    void onFranchiseInfoData(HashMap hashMap);

    void onFranchiseInfoStartLoading();

    void onFranchiseInfoStopLoading();

    void onFranchiseInfoShowMessage(String errMsg);
}
