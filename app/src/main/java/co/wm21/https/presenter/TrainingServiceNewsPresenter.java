package com.wm21ltd.wm21.presenters;

import com.wm21ltd.wm21.interfaces.OnTrainingServiceNewsListView;
import com.wm21ltd.wm21.interfaces.OnTrainingServiceNewsRequestComplete;
import com.wm21ltd.wm21.networks.Models.TrainingServiceNewsListModel;
import com.wm21ltd.wm21.serviceapis.InvokeTrainingServiceNewsApi;

import java.util.List;

public class TrainingServiceNewsPresenter {
    private OnTrainingServiceNewsListView onTrainingServiceNewsListView;

    public TrainingServiceNewsPresenter(OnTrainingServiceNewsListView onTrainingServiceNewsListView) {
        this.onTrainingServiceNewsListView = onTrainingServiceNewsListView;
    }
    public void TrainingServiceNewsDataResponse (String type, String limit) {
        onTrainingServiceNewsListView.onTrainingServiceNewsStartLoading();
        new InvokeTrainingServiceNewsApi(type, limit, new OnTrainingServiceNewsRequestComplete() {
            @Override
            public void onTrainingServiceNewsRequestSuccess(Object obj) {
                onTrainingServiceNewsListView.onTrainingServiceNewsStopLoading();
                onTrainingServiceNewsListView.onTrainingServiceNewsResponseData((List<TrainingServiceNewsListModel>) obj);
            }

            @Override
            public void onTrainingServiceNewsRequestError(String errMsg) {
                onTrainingServiceNewsListView.onTrainingServiceNewsStopLoading();
                onTrainingServiceNewsListView.onTrainingServiceNewsShowMessage(errMsg);
            }
        });
    }
}
