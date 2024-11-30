package co.wm21.https.view.adapters.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.application.EShopStatusData;
import co.wm21.https.databinding.RowEShopStatusBinding;

public class EShopStatusAdapter extends RecyclerView.Adapter<EShopStatusAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(EShopStatusData item);
    }

    private List<EShopStatusData> iList;
    private Context context;
    private OnItemClickListener listener;

    RowEShopStatusBinding binding;

    public EShopStatusAdapter(List<EShopStatusData> iList, Context context, OnItemClickListener listener) {
        this.iList = iList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EShopStatusAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowEShopStatusBinding.inflate(LayoutInflater.from(context), parent, false);
        return new EShopStatusAdapter.MyViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EShopStatusAdapter.MyViewHolder holder, int position) {
        EShopStatusData iModel = iList.get(position);

        holder.userId.setText(iModel.getUserName()+" ("+iModel.getId()+")");
        holder.name.setText(iModel.getOwner());
        holder.date.setText(iModel.getJoin());
        holder.shopType.setText("Type: "+iModel.getTypeName());
        holder.shopName.setText("Shop Name: "+iModel.getName());
        holder.shopContactNumber.setText("Contact"+iModel.getMobile());
        holder.bankAccName.setText(iModel.getBankAccname());
        holder.bankName.setText(iModel.getBank());
        holder.bankAccNumber.setText(iModel.getBankAcc());
        holder.bkashNumber.setText("BK-"+iModel.getBankBkash());
        holder.rocketNumber.setText("Na-"+iModel.getBankDbbl());
        holder.nagadNumber.setText("Ro-"+iModel.getBankUcash());
        if (iModel.getActive().equals('1')){
            holder.actionBtn.setText("Action");
        }
        if (iModel.getActive().equals('0')){
            holder.actionBtn.setText("Delete");
            holder.actionBtn.setBackgroundColor(Color.RED);
        }

        //shop physical address should add in item layout (in shop info)
        //shop trade_lisence_number should add in item layout (Doc)
        //shop trade_lisence_expire should add in item layout (Doc)


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
        TextView name;
        TextView date;
        TextView shopType;
        TextView shopName;
        TextView shopContactNumber;
        TextView bankAccName;
        TextView bankName;
        TextView bankAccNumber;
        TextView bkashNumber;
        TextView nagadNumber;
        TextView rocketNumber;
        TextView address;
        TextView location;
        AppCompatButton actionBtn;

        public MyViewHolder(@NonNull RowEShopStatusBinding itemView) {
            super(itemView.getRoot());
            userId = itemView.userId;
            name = itemView.nameId;
            date = itemView.dateId;
            shopType = itemView.shopTypeId;
            shopName = itemView.shopNameId;
            shopContactNumber = itemView.shopContactId;
            bankAccName = itemView.accNameId;
            bankName = itemView.bankNameId;
            bankAccNumber = itemView.accNumId;
            bkashNumber = itemView.bkashNumId;
            nagadNumber = itemView.nagadNumId;
            rocketNumber = itemView.rocketNumId;
            address = itemView.addressId;
            location = itemView.locationId;
            actionBtn = itemView.actionId;
        }

//        public void bind(EShopStatusData item, OnItemClickListener listener) {
//            fullItemLayout.setOnClickListener(v -> listener.onItemClick(item));
//        }
    }
}
