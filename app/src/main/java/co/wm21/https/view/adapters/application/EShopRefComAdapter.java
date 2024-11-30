package co.wm21.https.view.adapters.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.application.EShopRefComData;
import co.wm21.https.databinding.RowEShopRefComBinding;

public class EShopRefComAdapter extends RecyclerView.Adapter<EShopRefComAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(EShopRefComData item);
    }

    private List<EShopRefComData> iList;
    private Context context;
    private OnItemClickListener listener;

    RowEShopRefComBinding binding;

    public EShopRefComAdapter(List<EShopRefComData> iList, Context context, OnItemClickListener listener) {
        this.iList = iList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EShopRefComAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowEShopRefComBinding.inflate(LayoutInflater.from(context), parent, false);
        return new EShopRefComAdapter.MyViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EShopRefComAdapter.MyViewHolder holder, int position) {
        EShopRefComData iModel = iList.get(position);

        holder.shopId.setText(iModel.getId());
        holder.shopOwnerId.setText(iModel.getOwner());
        holder.shopNameId.setText(iModel.getName());
        holder.shopTypeId.setText(iModel.getTypeName());
        holder.shopContactId.setText(iModel.getMobile());
        holder.addressId.setText(iModel.getAddress());
        holder.salesRpId.setText(iModel.getPoi());
        holder.commissionId.setText(iModel.getPoi());



        // Bind the click listener
       // holder.bind(iModel, listener);
    }

    @Override
    public int getItemCount() {
        return iList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout fullItemLayout;
        TextView shopId;
        TextView shopOwnerId;
        TextView shopNameId;
        TextView shopTypeId;
        TextView shopContactId;
        TextView addressId;
        TextView salesRpId;
        TextView commissionId;


        public MyViewHolder(@NonNull RowEShopRefComBinding itemView) {
            super(itemView.getRoot());
            shopId = itemView.shopId;
            shopOwnerId = itemView.shopOwnerId;
            shopNameId = itemView.shopNameId;
            shopTypeId = itemView.shopTypeId;
            shopContactId = itemView.shopContactId;
            addressId = itemView.addressId;
            salesRpId = itemView.salesRpId;
            commissionId = itemView.commissionId;

        }

        public void bind(EShopRefComData item, OnItemClickListener listener) {
            fullItemLayout.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}
