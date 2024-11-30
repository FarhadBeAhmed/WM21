package co.wm21.https.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import co.wm21.https.R;
import co.wm21.https.view.fragments.member.model.RewardPolicyDataListModel;

public class RewardMyAchievementAdapter extends RecyclerView.Adapter<RewardMyAchievementAdapter.MyViewHolder> {

     List<RewardPolicyDataListModel> rList;
     Context context;


    public RewardMyAchievementAdapter(List<RewardPolicyDataListModel> rList, Context context) {
        this.rList = rList;
        this.context = context;
    }
    @SuppressLint("NonConstantResourceId")

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSL;
        TextView textViewRank;

        TextView notFoundTxt;
        ImageView status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            notFoundTxt=itemView.findViewById(R.id.notFoundTxt);

            textViewRank=itemView.findViewById(R.id.txt_row_rewardPolicy_Rank);
            textViewSL=itemView.findViewById(R.id.txt_row_rewardPolicy_Sl);
            status=itemView.findViewById(R.id.rewardPolicy_status);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_reward_my_achievement, viewGroup, false);
        return new MyViewHolder(mView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        if (!rList.isEmpty()) {
            RewardPolicyDataListModel model = rList.get(i);
            myViewHolder.textViewSL.setText(model.getSerial().toString());
            myViewHolder.textViewRank.setText("Rank: " + model.getRank());


            String status= rList.get(i).getStatus();

            if ((status.equals("right"))) {
                Glide.with(context).load(R.drawable.right).into(myViewHolder.status);
            } else  if ((status.equals("load"))){
                Glide.with(context).load(R.drawable.load).into(myViewHolder.status);
            }



        }else{
            myViewHolder.notFoundTxt.setText("No Data Found");
        }
    }

    @Override
    public int getItemCount() {
        return rList.size();
    }
}
