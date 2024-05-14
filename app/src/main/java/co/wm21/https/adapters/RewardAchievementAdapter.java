package co.wm21.https.adapters;

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
import co.wm21.https.FHelper.networks.Models.RewardAchievementDataListModel;
import co.wm21.https.R;

public class RewardAchievementAdapter extends RecyclerView.Adapter<RewardAchievementAdapter.MyViewHolder> {

    private List<RewardAchievementDataListModel> aList;
    private Context context;

    public RewardAchievementAdapter(List<RewardAchievementDataListModel> aList, Context context) {
        this.aList = aList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSL;
        TextView textViewRank;
        TextView textViewDate;

        TextView textViewStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewStatus=itemView.findViewById(R.id.txt_row_rewardAchievement_Status);
            textViewDate=itemView.findViewById(R.id.txt_row_rewardAchievement_Date);
            textViewRank=itemView.findViewById(R.id.txt_row_rewardAchievement_Rank);
            textViewSL=itemView.findViewById(R.id.txt_row_rewardAchievement_Sl);

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
