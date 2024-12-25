package co.wm21.https.presenter;



import java.util.List;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.TrainingServiceNewsListModel;
import co.wm21.https.presenter.interfaces.OnTrainingServiceNewsListView;
import co.wm21.https.serviceapis.InvokeTrainingServiceNewsApi;

public class TrainingServiceNewsPresenter {
    private OnTrainingServiceNewsListView onTrainingServiceNewsListView;

    public TrainingServiceNewsPresenter(OnTrainingServiceNewsListView onTrainingServiceNewsListView) {
        this.onTrainingServiceNewsListView = onTrainingServiceNewsListView;
    }
    public void TrainingServiceNewsDataResponse (String type, String limit) {
        onTrainingServiceNewsListView.onTrainingServiceNewsStartLoading();
        new InvokeTrainingServiceNewsApi(type, limit, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                onTrainingServiceNewsListView.onTrainingServiceNewsStopLoading();
                onTrainingServiceNewsListView.onTrainingServiceNewsResponseData((List<TrainingServiceNewsListModel>) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                onTrainingServiceNewsListView.onTrainingServiceNewsStopLoading();
                onTrainingServiceNewsListView.onTrainingServiceNewsShowMessage(errMsg);
            }
        });
    }
}
