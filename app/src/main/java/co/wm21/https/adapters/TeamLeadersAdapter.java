package co.wm21.https.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.databinding.LayoutItemTeamLeaderBinding;
import co.wm21.https.model.TeamLeadersModel;

public class TeamLeadersAdapter extends RecyclerView.Adapter<TeamLeadersAdapter.viewHolder> {
    Context context;
    ArrayList<TeamLeadersModel>leadersList;
    @LayoutRes
    int layout;
    LayoutItemTeamLeaderBinding binding;

    public TeamLeadersAdapter(Context context, ArrayList<TeamLeadersModel> leadersList, int layout) {
        this.context = context;
        this.leadersList = leadersList;
        this.layout = layout;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=LayoutItemTeamLeaderBinding.inflate(LayoutInflater.from(context),parent,false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        TeamLeadersModel teamLeadersModel=leadersList.get(position);
        holder.name.setText(teamLeadersModel.getName());
        holder.designation.setText(teamLeadersModel.getDesignation());
        Picasso.get().load(ConstantValues.web_url + "assets/images/pages/about_us/" + teamLeadersModel.getImage()).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return leadersList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,designation;
        public viewHolder(@NonNull LayoutItemTeamLeaderBinding itemView) {
            super(itemView.getRoot());
            image=itemView.imageId;
            name=itemView.nameId;
            designation=itemView.designationId;


        }
    }
}
