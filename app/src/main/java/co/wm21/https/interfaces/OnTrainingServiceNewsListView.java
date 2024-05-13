package com.wm21ltd.wm21.interfaces;

import com.wm21ltd.wm21.networks.Models.TrainingServiceNewsListModel;

import java.util.List;

public interface OnTrainingServiceNewsListView {

    void onTrainingServiceNewsResponseData(List<TrainingServiceNewsListModel> trainingServiceNewsList);

    void onTrainingServiceNewsStartLoading();

    void onTrainingServiceNewsStopLoading();

    void onTrainingServiceNewsShowMessage(String msg);
}
