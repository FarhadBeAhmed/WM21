package co.wm21.https.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import co.wm21.https.FHelper.networks.Models.FranchiseAccountDataListModel;
import co.wm21.https.R;
import co.wm21.https.presenter.interfaces.OnBottomReachedListener;

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
      TextView textViewSerial;
        TextView textViewUser;
        TextView textViewDate;
        TextView textViewUnit;
        TextView textViewAmount;
      TextView textViewStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSerial=itemView.findViewById(R.id.tv_rowFranchiseCommission_SL);
            textViewUser=itemView.findViewById(R.id.tv_rowFranchiseCommission_User);
            textViewDate=itemView.findViewById(R.id.tv_rowFranchiseCommission_Date);
            textViewUnit=itemView.findViewById(R.id.tv_rowFranchiseCommission_Unit);
            textViewAmount=itemView.findViewById(R.id.tv_rowFranchiseCommission_Amount);
            textViewStatus=itemView.findViewById(R.id.tv_rowFranchiseCommission_Status);
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
