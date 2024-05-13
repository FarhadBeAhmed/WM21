package com.wm21ltd.wm21.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.activities.TSNFCategoryDetailsActivity;
import com.wm21ltd.wm21.adapters.TrainingServiceNewsAdapter;
import com.wm21ltd.wm21.interfaces.OnBottomReachedListener;
import com.wm21ltd.wm21.interfaces.OnTrainingServiceNewsClickListener;
import com.wm21ltd.wm21.interfaces.OnTrainingServiceNewsListView;
import com.wm21ltd.wm21.networks.Models.TrainingServiceNewsListModel;
import com.wm21ltd.wm21.presenters.TrainingServiceNewsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainingFragment extends Fragment implements OnTrainingServiceNewsListView, OnTrainingServiceNewsClickListener {
    private RecyclerView trainingServiceNewsRecycler;
    private TrainingServiceNewsPresenter trainingServiceNewsPresenter;
    private TrainingServiceNewsAdapter trainingServiceNewsAdapter;
    private List<TrainingServiceNewsListModel> trainingServiceNewsList = new ArrayList<>();
    private int loadMore = 0;
    private MaterialDialog dialog;
    private ConstraintLayout contextView;


    public TrainingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_training_1, container, false);

        dialog = new MaterialDialog.Builder(getContext()).title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .cancelable(false)
                .build();

        trainingServiceNewsRecycler = view.findViewById(R.id.trainingServiceNewsRecycler);
        contextView = view.findViewById(R.id.training_contextView);


        trainingServiceNewsPresenter = new TrainingServiceNewsPresenter(this);
        initializedRecyclerView();


        return view;
    }

    @Override
    public void onTrainingServiceNewsResponseData(List<TrainingServiceNewsListModel> trainingServiceNewsListModels) {

        trainingServiceNewsList.addAll(trainingServiceNewsListModels);
        trainingServiceNewsAdapter.notifyDataSetChanged();
    }


    @Override
    public void onTrainingServiceNewsStartLoading() {
        dialog.show();
    }

    @Override
    public void onTrainingServiceNewsStopLoading() {
        dialog.dismiss();
    }

    @Override
    public void onTrainingServiceNewsShowMessage(String msg) {
        showSnackBar(msg);
    }

    private void initializedRecyclerView(){
        trainingServiceNewsAdapter = new TrainingServiceNewsAdapter(getContext(), trainingServiceNewsList, this);
        trainingServiceNewsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        trainingServiceNewsRecycler.setAdapter(trainingServiceNewsAdapter);
        parseData(0);
        trainingServiceNewsAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                parseData(loadMore = loadMore + 10);
            }
        });
}

    private void parseData(int loadMoreData) {
        trainingServiceNewsPresenter.TrainingServiceNewsDataResponse("1",loadMoreData + ",10");
    }
    @Override
    public void onTrainingServiceNewsCategoryName(String categoryName, String subCategory) {
        Intent intent = new Intent(getContext(), TSNFCategoryDetailsActivity.class);
        intent.putExtra("categoryName",categoryName);
        intent.putExtra("type","1");
        startActivity(intent);
    }

    private void showSnackBar(String msg) {

        Snackbar snackbar = Snackbar.make(contextView, msg, Snackbar.LENGTH_LONG);

        View view = snackbar.getView();
        TextView textView = view.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

}
