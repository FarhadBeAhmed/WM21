package co.wm21.https.presenter;


import java.util.HashMap;

import co.wm21.https.interfaces.OnTrainingRequestFormRequestComplete;
import co.wm21.https.interfaces.OnTrainingRequestFormView;
import co.wm21.https.serviceapis.InvokeTrainerRequestApi;

public class TrainerRequestPresenter {
    OnTrainingRequestFormView mView;

    public TrainerRequestPresenter(OnTrainingRequestFormView mView) {
        this.mView = mView;
    }

    public void onTrainerRequestDataResponse(String userID, String traningCategory, String title, String details,
                                             String trainer,String charge, String duration, String venue, String seats,
                                             String targetDate, String division, String district, String thana){
        mView.onTrainingRequestFormStartLoading();

        new InvokeTrainerRequestApi(userID, traningCategory, title, details, trainer, charge, duration,
                venue, seats, targetDate,division,district,thana, new OnTrainingRequestFormRequestComplete() {
            @Override
            public void onTrainingRequestFormRequestSuccess(Object obj) {
                mView.onTrainingRequestFormStopLoading();
                mView.onTrainingRequestFormData((HashMap) obj);
            }

            @Override
            public void onTrainingRequestFormRequestError(String errMsg) {
                mView.onTrainingRequestFormStopLoading();
                mView.onTrainingRequestFormShowMessage(errMsg);
            }
        });
    }
}
