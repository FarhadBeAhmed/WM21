package co.wm21.https.interfaces;



import java.util.List;

import co.wm21.https.FHelper.networks.Models.TrainingServiceNewsListModel;

public interface OnTrainingServiceNewsListView {

    void onTrainingServiceNewsResponseData(List<TrainingServiceNewsListModel> trainingServiceNewsList);

    void onTrainingServiceNewsStartLoading();

    void onTrainingServiceNewsStopLoading();

    void onTrainingServiceNewsShowMessage(String msg);
}
