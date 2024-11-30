package co.wm21.https.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.transaction.WithdrawalHistoryData;
import co.wm21.https.databinding.WithdrawalHistorySigleItemBinding;

public class WithdrawalHistoryAdapter extends RecyclerView.Adapter<WithdrawalHistoryAdapter.MyViewHolder> {

    private List<WithdrawalHistoryData> iList;
    private Context context;
    private OnItemClickListener listener;
    WithdrawalHistorySigleItemBinding binding;


    public WithdrawalHistoryAdapter(List<WithdrawalHistoryData> iList, Context context, OnItemClickListener listener) {
        this.iList = iList;
        this.context = context;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(WithdrawalHistoryData item);
    }

    @NonNull
    @Override
    public WithdrawalHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = WithdrawalHistorySigleItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new WithdrawalHistoryAdapter.MyViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull WithdrawalHistoryAdapter.MyViewHolder holder, int position) {
        WithdrawalHistoryData iModel = iList.get(position);

        if (iModel != null) {
            holder.gateway.setText(iModel.getGateway() != null ? iModel.getGateway() : "N/A");
            holder.gatewayNumber.setText(iModel.getAccNumber() != null ? iModel.getAccNumber().toString() : "N/A");
            holder.franchise.setText("Franchise: " + (iModel.getFranchise() != null ? iModel.getFranchise() : "N/A"));
            holder.date.setText(iModel.getDate() != null ? iModel.getDate().toString() : "N/A");
            holder.time.setText(iModel.getTime() != null ? iModel.getTime().toString() : "N/A");
            holder.withdrawAmount.setText("Withdraw: " + (iModel.getWithdrawl() != null ? iModel.getWithdrawl() : "N/A"));
            holder.receiveAmount.setText("Receive: " + (iModel.getReceived() != null ? iModel.getReceived() : "N/A"));
            holder.info.setText("Info: " + (iModel.getStatus() != null ? iModel.getStatus() : "N/A"));
            holder.action.setText("Action: " + (iModel.getAcStatus() != null ? iModel.getAcStatus() : "N/A"));
            holder.acc.setText(iModel.getTransId() != null ? iModel.getTransId().toString() : "N/A");
        }
    }


    @Override
    public int getItemCount() {
        return iList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView gateway;
        TextView gatewayNumber;
        TextView franchise;
        TextView date;
        TextView time;
        TextView withdrawAmount;
        TextView receiveAmount;
        TextView info;
        TextView action;
        TextView acc;


        public MyViewHolder(@NonNull WithdrawalHistorySigleItemBinding itemView) {
            super(itemView.getRoot());
            gateway=itemView.gatewayId;
            gatewayNumber=itemView.gatewayNumberId;
            franchise=itemView.franchiseId;
            date=itemView.dateId;
            time= itemView.timeId;
            withdrawAmount=itemView.withdrawAmountId;
            receiveAmount=itemView.receiveAmountId;
            info=itemView.infoId;
            action=itemView.actionId;
            acc=itemView.acId;


        }
    }
}
