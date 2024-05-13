package com.wm21ltd.wm21.presenters;

import com.wm21ltd.wm21.interfaces.OnTrainingRequestFormRequestComplete;
import com.wm21ltd.wm21.interfaces.OnTrainingRequestFormView;
import com.wm21ltd.wm21.serviceapis.InvokeTrainerRequestApi;

import java.util.HashMap;

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
