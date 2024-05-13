package com.wm21ltd.wm21.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.networks.Models.RewardFundDataListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardFundAdapter extends RecyclerView.Adapter<RewardFundAdapter.MyViewHolder> {

    private List<RewardFundDataListModel> rList;
    private Context context;

    public RewardFundAdapter(List<RewardFundDataListModel> rList, Context context) {
        this.rList = rList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_row_rewardFund_Sl)
        TextView textViewSL;
        @BindView(R.id.txt_row_rewardFund_Rank)
        TextView textViewRank;
        @BindView(R.id.txt_row_rewardFund_Budget)
        TextView textViewBudget;
        @BindView(R.id.txt_row_rewardFund_Status)
        TextView textViewStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_reward_fund, viewGroup, false);
        return new RewardFundAdapter.MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        RewardFundDataListModel model = rList.get(position);
        myViewHolder.textViewSL.setText(model.getSerial());
        myViewHolder.textViewRank.setText("Rank: " + model.getRank());
        myViewHolder.textViewBudget.setText("Budget: " + model.getBudget());
        myViewHolder.textViewStatus.setText("Status: " + model.getStatus());
    }

    @Override
    public int getItemCount() {
        return  rList.size();
    }
}
