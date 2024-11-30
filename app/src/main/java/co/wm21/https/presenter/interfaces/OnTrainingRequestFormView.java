package co.wm21.https.presenter.interfaces;

import java.util.HashMap;

public interface OnTrainingRequestFormView {
    void onTrainingRequestFormData(HashMap hashMap);

    void onTrainingRequestFormStartLoading();

    void onTrainingRequestFormStopLoading();

    void onTrainingRequestFormShowMessage(String errMsg);
}
