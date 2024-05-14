package co.wm21.https.interfaces;

import java.util.HashMap;

public interface OnTrainingRequestFormView {
    void onTrainingRequestFormData(HashMap hashMap);

    void onTrainingRequestFormStartLoading();

    void onTrainingRequestFormStopLoading();

    void onTrainingRequestFormShowMessage(String errMsg);
}
