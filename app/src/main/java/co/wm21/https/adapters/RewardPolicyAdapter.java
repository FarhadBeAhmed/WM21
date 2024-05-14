package co.wm21.https.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wm21.https.R;
import co.wm21.https.fragments.member.model.RewardPolicyDataListModel;

public class RewardPolicyAdapter extends RecyclerView.Adapter<RewardPolicyAdapter.MyViewHolder> {

     List<RewardPolicyDataListModel> rList;
     Context context;


    public RewardPolicyAdapter(List<RewardPolicyDataListModel> rList, Context context) {
        this.rList = rList;
        this.context = context;
    }
    @SuppressLint("NonConstantResourceId")

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSL;
        TextView textViewRank;
        TextView textViewTeamA;

        TextView textViewTeamB;
        TextView notFoundTxt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            notFoundTxt=itemView.findViewById(R.id.notFoundTxt);
            textViewTeamB=itemView.findViewById(R.id.txt_row_rewardPolicy_TeamB);
            textViewTeamA=itemView.findViewById(R.id.txt_row_rewardPolicy_TeamA);
            textViewRank=itemView.findViewById(R.id.txt_row_rewardPolicy_Rank);
            textViewSL=itemView.findViewById(R.id.txt_row_rewardPolicy_Sl);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_reward_policy, viewGroup, false);
        return new MyViewHolder(mView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        if (!rList.isEmpty()) {
            RewardPolicyDataListModel model = rList.get(i);
            myViewHolder.textViewSL.setText(model.getSerial());
            myViewHolder.textViewRank.setText("Rank: " + model.getRank());
            myViewHolder.textViewTeamA.setText("Team A: " + model.getTeamA());
            myViewHolder.textViewTeamB.setText("Team B: " + model.getTeamB());
        }else{
            myViewHolder.notFoundTxt.setText("No Data Found");
        }
    }

    @Override
    public int getItemCount() {
        return rList.size();
    }
}
