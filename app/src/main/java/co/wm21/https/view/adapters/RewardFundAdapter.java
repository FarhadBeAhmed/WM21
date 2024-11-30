package co.wm21.https.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import butterknife.ButterKnife;
import co.wm21.https.FHelper.networks.Models.RewardFundDataListModel;
import co.wm21.https.R;

public class RewardFundAdapter extends RecyclerView.Adapter<RewardFundAdapter.MyViewHolder> {

    private List<RewardFundDataListModel> rList;
    private Context context;

    public RewardFundAdapter(List<RewardFundDataListModel> rList, Context context) {
        this.rList = rList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSL;
        TextView textViewRank;
        TextView textViewBudget;
        TextView textViewStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            textViewStatus= itemView.findViewById(R.id.txt_row_rewardFund_Status);
            textViewSL= itemView.findViewById(R.id.txt_row_rewardFund_Sl);
            textViewRank= itemView.findViewById(R.id.txt_row_rewardFund_Rank);
            textViewBudget= itemView.findViewById(R.id.txt_row_rewardFund_Budget);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_reward_fund, viewGroup, false);
        return new MyViewHolder(mView);
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
