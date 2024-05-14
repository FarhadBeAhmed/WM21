package co.wm21.https.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import co.wm21.https.FHelper.networks.Models.TrainingServiceNewsListModel;
import co.wm21.https.R;
import co.wm21.https.interfaces.OnBottomReachedListener;
import co.wm21.https.interfaces.OnTrainingServiceNewsClickListener;

public class TrainingServiceNewsAdapter extends RecyclerView.Adapter<TrainingServiceNewsAdapter.TrainingServiceNewsViewHolder> {
    private Context context;
    private List<TrainingServiceNewsListModel> trainingServiceNewsList;
    private OnTrainingServiceNewsClickListener onTrainingServiceNewsClickListener;
    private OnBottomReachedListener onBottomReachedListener;

    public TrainingServiceNewsAdapter(Context context, List<TrainingServiceNewsListModel> trainingServiceNewsList, OnTrainingServiceNewsClickListener onTrainingServiceNewsClickListener) {
        this.context = context;
        this.trainingServiceNewsList = trainingServiceNewsList;
        this.onTrainingServiceNewsClickListener = onTrainingServiceNewsClickListener;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }

    @NonNull
    @Override
    public TrainingServiceNewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.training_service_news_layout,viewGroup,false);
        return new TrainingServiceNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingServiceNewsViewHolder holder, int position) {
        if (position == trainingServiceNewsList.size() -1){
            onBottomReachedListener.onBottomReached(position);
        }
        final TrainingServiceNewsListModel trainingServiceNewsListModel = trainingServiceNewsList.get(position);
        holder.categoryName.setText(trainingServiceNewsListModel.getCategory());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTrainingServiceNewsClickListener.onTrainingServiceNewsCategoryName(trainingServiceNewsListModel.getCategory(),trainingServiceNewsListModel.getSubCategory());
            }
        });
    }

    @Override
    public int getItemCount() {
        return trainingServiceNewsList.size();
    }


    public class TrainingServiceNewsViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;
        public TrainingServiceNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.trainingServiceNewsCategory);
        }
    }
}
