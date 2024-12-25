package co.wm21.https.view.adapters.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.application.BDPStatusData;
import co.wm21.https.databinding.RowBdpStatusBinding;

public class BDPStatusAdapter extends RecyclerView.Adapter<BDPStatusAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(BDPStatusData item);
    }

    private List<BDPStatusData> iList;
    private Context context;
    private OnItemClickListener listener;

    RowBdpStatusBinding binding;

    public BDPStatusAdapter(List<BDPStatusData> iList, Context context, OnItemClickListener listener) {
        this.iList = iList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BDPStatusAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowBdpStatusBinding.inflate(LayoutInflater.from(context), parent, false);
        return new BDPStatusAdapter.MyViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BDPStatusAdapter.MyViewHolder holder, int position) {
        BDPStatusData iModel = iList.get(position);

        holder.userId.setText(iModel.user +" ("+ iModel.id +")");
        holder.purchaseAmount.setText(iModel.amount);
        holder.unit.setText(iModel.unite);
        holder.date.setText(iModel.date);
        if (iModel.approve.equals("1")){
            holder.approve.setText("Approved");
            holder.approve.setTextColor(Color.GREEN);

        }else {
            holder.approve.setText("Denied");
            holder.approve.setTextColor(Color.RED);
        }
        if (iModel.paid.equals("1")){
            holder.paid.setText("Success");
            holder.paid.setTextColor(Color.GREEN);

        }else {
            holder.paid.setText("Failed");
            holder.paid.setTextColor(Color.RED);
        }



        // Bind the click listener
       // holder.bind(iModel, listener);
    }

    @Override
    public int getItemCount() {
        return iList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout fullItemLayout;
        TextView userId;
        TextView purchaseAmount;
        TextView unit;
        TextView date;
        TextView approve;
        TextView paid;



        public MyViewHolder(@NonNull RowBdpStatusBinding itemView) {
            super(itemView.getRoot());
            userId = itemView.userId;
            purchaseAmount = itemView.purchaseAmount;
            unit = itemView.unit;
            date = itemView.date;
            approve = itemView.approve;
            paid = itemView.paid;


        }

        public void bind(BDPStatusData item, OnItemClickListener listener) {
            fullItemLayout.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}
