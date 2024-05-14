package co.wm21.https.adapters.cart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.FHelper.networks.Models.CartItems;
import co.wm21.https.FHelper.networks.Models.DeleteItem;
import co.wm21.https.FHelper.networks.Models.UpdateQty;
import co.wm21.https.R;
import co.wm21.https.activities.MainActivity;
import co.wm21.https.databinding.LayoutItemCartBinding;
import co.wm21.https.dialog.LoadingDialog;
import co.wm21.https.fragments.CartFragment;
import co.wm21.https.interfaces.OnDeleteItemView;
import co.wm21.https.interfaces.OnUpdateQtyView;
import co.wm21.https.presenter.DeleteItemPresenter;
import co.wm21.https.presenter.UpdateQtyPresenter;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> implements OnUpdateQtyView, OnDeleteItemView {
    @LayoutRes
    int layout;
    Context context;
    ArrayList<CartItems> cartList;
    TextView totalPrice, totalRp;
    RelativeLayout cartFragmentLayout;
    int quan;
    int totalSelected = 0;
    UpdateQtyPresenter updateQtyPresenter;
    DeleteItemPresenter deleteItemPresenter;
    LoadingDialog loadingDialog;
    View delBtnView;


    API api = ConstantValues.getAPI();
    //  MainActivity mainActivity;

    public CartAdapter(int layout, Context context, ArrayList<CartItems> cartList, TextView totalPrice, TextView totalRp, RelativeLayout cartFragmentLayout) {
        this.layout = layout;
        this.context = context;
        this.cartList = cartList;
        this.totalPrice = totalPrice;
        this.totalRp = totalRp;
        this.cartFragmentLayout = cartFragmentLayout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemCartBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), layout, parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint({"NotifyDataSetChanged", "DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CartItems cartModel = cartList.get(position);
        Picasso.get().load(ConstantValues.web_url + "image/product/small/" + cartModel.getImg()).into(holder.prImg);
        String price = String.valueOf(cartModel.getPrice());
        String quantity = String.valueOf(cartModel.getQty());
        String tPrice = String.valueOf(cartModel.getTprice());
        holder.price.setText(price);
        holder.qty.setText(quantity);
        holder.rp.setText(String.format("%.1s",cartModel.getPoint()));
        holder.prName.setText(cartModel.getName());
        holder.prTotalPrice.setText(tPrice);
        String pId = cartList.get(position).getpId();
        String color = cartList.get(position).getColor();
        String size = cartList.get(position).getSize();
        String uId = cartList.get(position).getuId();

        updateQtyPresenter= new UpdateQtyPresenter(this);
        deleteItemPresenter=new DeleteItemPresenter(this);

        holder.qtyAdd.setOnClickListener(view -> {
            loadingDialog=new LoadingDialog((Activity) context);
            quan = Integer.parseInt(holder.qty.getText().toString());
            quan += 1;
            updateQtyPresenter.UpdateQtyDataLoad(uId,pId, color, size, quan, 1);

            cartList.get(position).setTprice(String.valueOf(Double.parseDouble(cartList.get(position).getPrice()) * quan));
            cartList.get(position).setPoint(String.valueOf(Double.parseDouble(cartList.get(position).getSpoint()) * quan));
            cartList.get(position).setQty(String.valueOf(quan));
            holder.prTotalPrice.setText(String.format("%.2s", cartList.get(position).getTprice()));
            notifyDataSetChanged();
            update();

        });
        holder.qtyMinus.setOnClickListener(view -> {
            loadingDialog=new LoadingDialog((Activity) context);
            if (Double.parseDouble(cartModel.getQty()) > 1) {
                quan = Integer.parseInt(holder.qty.getText().toString());
                quan -= 1;
                updateQtyPresenter.UpdateQtyDataLoad(uId,pId, color, size, quan, 1);
                cartList.get(position).setTprice(String.valueOf(Double.parseDouble(cartList.get(position).getPrice()) * quan));
                cartList.get(position).setPoint(String.valueOf(Double.parseDouble(cartList.get(position).getSpoint()) * quan));
                cartList.get(position).setQty(String.valueOf(quan));
                holder.prTotalPrice.setText(String.format("%.2s", cartList.get(position).getTprice()));
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
                    deleteItemPresenter.deleteItemDataLoad(cartModel.getSerial(), 1);
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
                deleteItemPresenter.deleteItemDataLoad(cartModel.getSerial(), 1);
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

    @SuppressLint("SetTextI18n")
    private void update() {
        double sum = 0;
        double rp = 0;
        for (int i = 0; i < cartList.size(); i++) {
            double price = Double.parseDouble(cartList.get(i).getTprice());
            sum += price;

            rp+= Double.parseDouble(cartList.get(i).getPoint());
        }
        totalRp.setText("RP " + rp);
        ((MainActivity) context).getCartItems();
        totalPrice.setText("TK " + sum);
    }
    private void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(cartFragmentLayout, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView prImg, qtyAdd, qtyMinus, deleteItem;
        TextView prName, qty, prTotalPrice, price, rp;


        public ViewHolder(@NonNull LayoutItemCartBinding itemView) {
            super(itemView.getRoot());

            prImg = itemView.productImage;
            qtyAdd = itemView.qtyIncrement;
            qtyMinus = itemView.qtyDecrement;
            prName = itemView.productName;
            qty = itemView.productQuantity;
            deleteItem = itemView.deleteItem;
            prTotalPrice = itemView.singleProductTotalPrice;
            price = itemView.productPrice;
            rp = itemView.singleRp;

        }


    }
}
