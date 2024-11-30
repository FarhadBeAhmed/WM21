package co.wm21.https.presenter.interfaces;

import java.util.HashMap;

public interface OnFranchiseInfoView {
    void onFranchiseInfoData(HashMap hashMap);

    void onFranchiseInfoStartLoading();

    void onFranchiseInfoStopLoading();

    void onFranchiseInfoShowMessage(String errMsg);
}
