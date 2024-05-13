package com.wm21ltd.wm21.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.interfaces.OnBottomReachedListener;
import com.wm21ltd.wm21.networks.Models.FranchiseAccountDataListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FranchiseAccountCommissionAdapter extends RecyclerView.Adapter<FranchiseAccountCommissionAdapter.MyViewHolder> {

    private List<FranchiseAccountDataListModel> faList;
    private Context context;
    OnBottomReachedListener onBottomReachedListener;

    public FranchiseAccountCommissionAdapter(List<FranchiseAccountDataListModel> faList, Context context) {
        this.faList = faList;
        this.context = context;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
        this.onBottomReachedListener = onBottomReachedListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_rowFranchiseCommission_SL)TextView textViewSerial;
        @BindView(R.id.tv_rowFranchiseCommission_User)TextView textViewUser;
        @BindView(R.id.tv_rowFranchiseCommission_Date)TextView textViewDate;
        @BindView(R.id.tv_rowFranchiseCommission_Unit)TextView textViewUnit;
        @BindView(R.id.tv_rowFranchiseCommission_Amount)TextView textViewAmount;
        @BindView(R.id.tv_rowFranchiseCommission_Status)TextView textViewStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_franchise_account_commission, viewGroup, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        if (position == faList.size() - 1) {
            onBottomReachedListener.onBottomReached(position);
        }
        FranchiseAccountDataListModel model = faList.get(position);
        myViewHolder.textViewSerial.setText(model.getSerial());
        myViewHolder.textViewUser.setText("User: "+model.getUser());
        myViewHolder.textViewDate.setText("Date: "+model.getDate());
        myViewHolder.textViewUnit.setText("Unit: "+model.getUnit());
        myViewHolder.textViewAmount.setText("Amount: "+model.getAmount());
        myViewHolder.textViewStatus.setText("Status: "+model.getStatus());
    }

    @Override
    public int getItemCount() {
        return faList.size();
    }
}
