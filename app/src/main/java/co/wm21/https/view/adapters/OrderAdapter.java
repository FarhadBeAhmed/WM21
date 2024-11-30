package co.wm21.https.view.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.DeleteItem;
import co.wm21.https.FHelper.networks.Models.OrderItemModel;
import co.wm21.https.FHelper.networks.Models.UpdateQty;
import co.wm21.https.R;
import co.wm21.https.view.activities.MainActivity;
import co.wm21.https.databinding.ItemOrderProductListSingleRowBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.view.fragments.manageOrder.OrderFragment;
import co.wm21.https.helpers.User;
import co.wm21.https.presenter.interfaces.OnDeleteItemView;
import co.wm21.https.presenter.interfaces.OnUpdateQtyView;
import co.wm21.https.presenter.DeleteItemPresenter;
import co.wm21.https.presenter.UpdateQtyPresenter;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.viewHolder>implements OnUpdateQtyView, OnDeleteItemView {

    @LayoutRes
    int layout;
    Context context;
    ArrayList<OrderItemModel> cartList;
    TextView totalPrice, totalRP, adjRP, adjTotalPrice, payable;
    LinearLayout fragmentLayout;
    int quan = 0;
    int totalSelected = 0;
    API api = ConstantValues.getAPI();
    // MainActivity mainActivity;
    User user;
    LinearLayout usingRP;
    UpdateQtyPresenter updateQtyPresenter;
    DeleteItemPresenter deleteItemPresenter;
    LoadingDialog loadingDialog;
    View delBtnView;


    public OrderAdapter(int layout, Context context, ArrayList<OrderItemModel> cartList, TextView totalPrice, LinearLayout fragmentLayout, TextView totalRP, TextView adjRP, TextView adjTotalPrice, TextView payable, LinearLayout usingRP) {
        this.layout = layout;
        this.context = context;
        this.cartList = cartList;
        this.totalPrice = totalPrice;
        this.fragmentLayout = fragmentLayout;
        this.totalRP = totalRP;
        this.adjRP = adjRP;
        this.adjTotalPrice = adjTotalPrice;
        this.payable = payable;
        this.usingRP = usingRP;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderProductListSingleRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), layout, parent, false);
        return new viewHolder(binding);
    }

    @SuppressLint({"NotifyDataSetChanged", "DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        updateQtyPresenter= new UpdateQtyPresenter(this);
        deleteItemPresenter=new DeleteItemPresenter(this);
        user = new User(context);
        OrderItemModel cartModel = cartList.get(position);
        Picasso.get().load(ConstantValues.web_url + "image/product/small/" + cartModel.getImg()).into(holder.prImg);
        String price = String.valueOf(cartModel.getPrice());
        String quantity = String.valueOf(cartModel.getQty());
        String tPrice = String.valueOf(cartModel.getTotal());
        holder.price.setText(price);
        holder.qty.setText(quantity);
        holder.prName.setText(cartModel.getName());
        holder.prTotalPrice.setText(tPrice);
        holder.sPoint.setText(cartModel.getSpoint() + "");
        String pId = cartList.get(position).getpId();
        String color = cartList.get(position).getColor();
        String size = cartList.get(position).getSize();
        String uId = user.getUsername();
        holder.qtyAdd.setOnClickListener(view -> {

            loadingDialog=new LoadingDialog((Activity) context);
            quan = Integer.parseInt(holder.qty.getText().toString());
            quan += 1;
            updateQtyPresenter.UpdateQtyDataLoad(uId,pId, color, size, quan, 2);

            cartList.get(position).setTotal(String.valueOf(Double.parseDouble(cartList.get(position).getPrice()) * quan));
            cartList.get(position).setPoint(String.valueOf(Double.parseDouble(cartList.get(position).getSpoint()) * quan));
            cartList.get(position).setQty(String.valueOf(quan));
            holder.prTotalPrice.setText(String.format("%.2s", cartList.get(position).getTotal()));
            notifyDataSetChanged();
            update();

        });
        holder.qtyMinus.setOnClickListener(view -> {
            loadingDialog=new LoadingDialog((Activity) context);
            if (Double.parseDouble(cartModel.getQty()) > 1) {
                quan = Integer.parseInt(holder.qty.getText().toString());
                quan -= 1;
                updateQtyPresenter.UpdateQtyDataLoad(uId,pId, color, size, quan, 2);
                cartList.get(position).setTotal(String.valueOf(Double.parseDouble(cartList.get(position).getPrice()) * quan));
                cartList.get(position).setPoint(String.valueOf(Double.parseDouble(cartList.get(position).getSpoint()) * quan));
                cartList.get(position).setQty(String.valueOf(quan));
                holder.prTotalPrice.setText(String.format("%.2s", cartList.get(position).getTotal()));
                notifyDataSetChanged();
                update();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                ViewGroup viewGroup = view.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.layout_confirm_delete_cart_item, viewGroup, false);
                RelativeLayout closeBtn = dialogView.findViewById(R.id.closeId);
                RelativeLayout deleteBtn = dialogView.findViewById(R.id.deleteId);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                closeBtn.setOnClickListener(view1 -> {
                    alertDialog.dismiss();
                });
                deleteBtn.setOnClickListener(view1 -> {
                    delBtnView=view1;
                    loadingDialog=new LoadingDialog((Activity) context);
                    deleteItemPresenter.deleteItemDataLoad(cartModel.getSerial(), 2);
                    notifyItemRangeChanged(position, cartList.size());
                    cartList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemChanged(position);
                    alertDialog.dismiss();
                    update();

                });
            }
        });

        holder.deleteItem.setOnClickListener(view -> {
            delBtnView=view;
            loadingDialog=new LoadingDialog((Activity) context);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            ViewGroup viewGroup = view.findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.layout_confirm_delete_cart_item, viewGroup, false);
            RelativeLayout closeBtn = dialogView.findViewById(R.id.closeId);
            RelativeLayout deleteBtn = dialogView.findViewById(R.id.deleteId);
            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            closeBtn.setOnClickListener(view1 -> {
                alertDialog.dismiss();
            });
            deleteBtn.setOnClickListener(view1 -> {
                deleteItemPresenter.deleteItemDataLoad(cartModel.getSerial(), 2);
                notifyItemRangeChanged(position, cartList.size());
                alertDialog.dismiss();
                cartList.remove(position);
                notifyItemRemoved(position);
                notifyItemChanged(position);
                notifyDataSetChanged();
                update();

            });
        });
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void update() {
        double sum = 0;
        double rp = 0;
        for (int i = 0; i < cartList.size(); i++) {
            double price = Double.parseDouble(cartList.get(i).getTotal());
            sum += price;
            rp += Double.parseDouble(cartList.get(i).getPoint());
        }
        OrderFragment.adjustedRP = rp;
        OrderFragment.adjustedPrice = rp * 60;
        OrderFragment.payablePrice = sum - OrderFragment.adjustedPrice;
        totalRP.setText(String.format("%.02f", rp));
        adjRP.setText("RP " + String.format("%.02f", OrderFragment.adjustedRP));
        ((MainActivity) context).getCartItems();
        totalPrice.setText(String.format("%.02f", sum));
        adjTotalPrice.setText(String.format("%.02f", OrderFragment.adjustedPrice));
        payable.setText(String.format("%.02f", OrderFragment.payablePrice));
        if (OrderFragment.myPoint < OrderFragment.adjustedRP) {
            usingRP.setVisibility(View.GONE);
            OrderFragment.ifAdjust = 0;
        } else {
            usingRP.setVisibility(View.VISIBLE);
            //OrderFragment.ifAdjust = 1;
        }
    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }


    @Override
    public void onUpdateQtyDataLoad(UpdateQty updateQty) {
        showSnackBar(updateQty.getData());

    }

    @Override
    public void onUpdateQtyStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onUpdateQtyStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onUpdateQtyShowMessage(String errmsg) {
        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteItemDataLoad(DeleteItem deleteItem) {
        notifyDataSetChanged();
        Snackbar.make(delBtnView, deleteItem.getData(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteItemStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onDeleteItemStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onDeleteItemShowMessage(String errmsg) {
        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView prImg, qtyAdd, qtyMinus, deleteItem;
        TextView prName, qty, prTotalPrice, price, sPoint;

        public viewHolder(@NonNull ItemOrderProductListSingleRowBinding itemView) {
            super(itemView.getRoot());
            prImg = itemView.productImage;
            qtyAdd = itemView.qtyIncrement;
            qtyMinus = itemView.qtyDecrement;
            prName = itemView.productName;
            qty = itemView.productQuantity;
            deleteItem = itemView.deleteItem;
            prTotalPrice = itemView.singleProductTotalPrice;
            price = itemView.productPrice;
            sPoint = itemView.productRP;
        }
    }

    private void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(fragmentLayout, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
