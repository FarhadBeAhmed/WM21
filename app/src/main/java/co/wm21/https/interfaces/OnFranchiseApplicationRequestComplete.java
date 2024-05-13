package com.wm21ltd.wm21.interfaces;

public interface OnFranchiseApplicationRequestComplete {
    void onFranchiseApplicationRequestSuccess(Object obj);

    void onFranchiseApplicationRequestError(String errMsg);
}
