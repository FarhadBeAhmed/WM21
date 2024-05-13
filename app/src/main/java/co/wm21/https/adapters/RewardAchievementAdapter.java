package com.wm21ltd.wm21.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.networks.Models.RewardAchievementDataListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardAchievementAdapter extends RecyclerView.Adapter<RewardAchievementAdapter.MyViewHolder> {

    private List<RewardAchievementDataListModel> aList;
    private Context context;

    public RewardAchievementAdapter(List<RewardAchievementDataListModel> aList, Context context) {
        this.aList = aList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_row_rewardAchievement_Sl)
        TextView textViewSL;
        @BindView(R.id.txt_row_rewardAchievement_Rank)
        TextView textViewRank;
        @BindView(R.id.txt_row_rewardAchievement_Date)
        TextView textViewDate;
        @BindView(R.id.txt_row_rewardAchievement_Status)
        TextView textViewStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_reward_achievement, viewGroup, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        RewardAchievementDataListModel model = aList.get(i);
        myViewHolder.textViewSL.setText(model.getSL());
        myViewHolder.textViewRank.setText("Rank: " + model.getRank());
        myViewHolder.textViewDate.setText("Date: " + model.getDate());
        myViewHolder.textViewStatus.setText("Status: " + model.getStatus());
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }
}
