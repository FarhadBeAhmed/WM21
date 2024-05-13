package co.wm21.https.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.activities.MainActivity;
import co.wm21.https.databinding.ItemAppliedProductSingleRowBinding;
import co.wm21.https.databinding.ItemOrderProductListSingleRowBinding;
import co.wm21.https.helpers.User;
import co.wm21.https.model.AppliedProductModel;

public class AppliedAdapter extends RecyclerView.Adapter<AppliedAdapter.viewHolder> {

    @LayoutRes
    int layout;
    Context context;
    ArrayList<AppliedProductModel> cartList;
    LinearLayout fragmentLayout;
    MainActivity mainActivity;
    User user;

    public AppliedAdapter(int layout, Context context, ArrayList<AppliedProductModel> cartList, MainActivity mainActivity) {
        this.layout = layout;
        this.context = context;
        this.cartList = cartList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAppliedProductSingleRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), layout, parent, false);
        return new viewHolder(binding);
    }

    @SuppressLint({"NotifyDataSetChanged", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        user = new User(context);
        AppliedProductModel appliedModel = cartList.get(position);
        Picasso.get().load(ConstantValues.web_url + "image/product/small/" + appliedModel.getImageUrl()).into(holder.prImg);
        String price = String.valueOf(appliedModel.getPrice());
        String quantity = String.valueOf(appliedModel.getQty());
        String tPrice = String.valueOf(appliedModel.getTotal());
        holder.price.setText(price);
        holder.qty.setText(quantity);
        holder.prName.setText(appliedModel.getProductName());
        holder.prTotalPrice.setText(tPrice);
        if (appliedModel.getAdjust()>0){
            holder.prRP.setText(String.format("%.2f (adjusted)",appliedModel.getAdjust()));
        }else holder.prRP.setText(String.format("%.2f",appliedModel.getPoint()));
        holder.productInvoice.setText(appliedModel.getInvoice());
        holder.mobileNumber.setText(appliedModel.getShopMobile());
        holder.shopName.setText(appliedModel.getShopName());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView prImg;
        TextView prName, qty, prTotalPrice, price,prRP,productInvoice,shopName,mobileNumber;

        public viewHolder(@NonNull ItemAppliedProductSingleRowBinding itemView) {
            super(itemView.getRoot());
            prImg = itemView.productImage;
            prName = itemView.productName;
            qty = itemView.productQuantity;
            prTotalPrice = itemView.singleProductTotalPrice;
            price = itemView.proPrice;
            prRP=itemView.productRP;
            productInvoice=itemView.productInvoice;
            shopName=itemView.shopName;
            mobileNumber=itemView.mobileNumber;
        }
    }

}
