package co.wm21.https.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wm21.https.FHelper.networks.Models.TeamInfoDataListModel;
import co.wm21.https.R;
import co.wm21.https.view.activities.MemberDetailsActivity;

public class TeamInfoAdapter extends RecyclerView.Adapter<TeamInfoAdapter.MyViewHolder> {

    private List<TeamInfoDataListModel> dataModel;
    private Context context;

    public TeamInfoAdapter(List<TeamInfoDataListModel> dataModel, Context context) {
        this.dataModel = dataModel;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(
                R.id.txt_rowTeamInfo_SL)
        TextView textViewSl;
        @BindView(R.id.txt_rowTeamInfo_Name)
        TextView textViewName;
        @BindView(R.id.txt_rowTeamInfo_TeamA)
        TextView textViewTeamA;
        @BindView(R.id.txt_rowTeamInfo_TeamB)
        TextView textViewTeamB;
        @BindView(R.id.txt_rowTeamInfo_Total)
        TextView textViewTotal;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_team_info, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TeamInfoDataListModel dModel = dataModel.get(position);
        holder.textViewSl.setText(dModel.getSerial());
        holder.textViewName.setText(dModel.getType());
        holder.textViewTeamA.setText(dModel.getTeamA());
        holder.textViewTeamB.setText(dModel.getTeamB());
        holder.textViewTotal.setText(dModel.getTypeValue());
        if (dModel.getTeamA() == null || dModel.getTeamA().length() <= 0){
            holder.textViewTeamA.setVisibility(View.GONE);
        } else if (dModel.getTeamB() == null || dModel.getTeamB().length() <= 0){
            holder.textViewTeamB.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MemberDetailsActivity.class);
            intent.putExtra("rank",dModel.getType());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataModel.size();
    }
}
