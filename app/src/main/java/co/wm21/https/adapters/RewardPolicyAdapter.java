package com.wm21ltd.wm21.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.networks.Models.RewardPolicyDataListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardPolicyAdapter extends RecyclerView.Adapter<RewardPolicyAdapter.MyViewHolder> {

    private List<RewardPolicyDataListModel> rList;
    private Context context;


    public RewardPolicyAdapter(List<RewardPolicyDataListModel> rList, Context context) {
        this.rList = rList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_row_rewardPolicy_Sl)
        TextView textViewSL;
        @BindView(R.id.txt_row_rewardPolicy_Rank)
        TextView textViewRank;
        @BindView(R.id.txt_row_rewardPolicy_TeamA)
        TextView textViewTeamA;
        @BindView(R.id.txt_row_rewardPolicy_TeamB)
        TextView textViewTeamB;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_reward_policy, viewGroup, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        RewardPolicyDataListModel model = rList.get(i);
        myViewHolder.textViewSL.setText(model.getSerial());
        myViewHolder.textViewRank.setText("Rank: " + model.getRank());
        myViewHolder.textViewTeamA.setText("Team A: " + model.getTeamA());
        myViewHolder.textViewTeamB.setText("Team B: " + model.getTeamB());
    }

    @Override
    public int getItemCount() {
        return rList.size();
    }
}
