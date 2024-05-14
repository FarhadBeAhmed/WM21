package co.wm21.https.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
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
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.FHelper.networks.Models.DeliveryItemsModel;
import co.wm21.https.FHelper.networks.Models.DeliveryReceiveModel;
import co.wm21.https.R;
import co.wm21.https.activities.MainActivity;
import co.wm21.https.databinding.ItemDeliveryProductSingleRowBinding;
import co.wm21.https.dialog.LoadingDialog;
import co.wm21.https.fragments.HomeFragment;
import co.wm21.https.helpers.User;
import co.wm21.https.interfaces.OnDeliveryItemsView;
import co.wm21.https.interfaces.OnDeliveryReceiveView;
import co.wm21.https.model.DeliveryProductModel;
import co.wm21.https.presenter.DeliveryItemsPresenter;
import co.wm21.https.presenter.DeliveryReceivePresenter;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.viewHolder> implements OnDeliveryReceiveView {

    @LayoutRes
    int layout;
    Context context;
    ArrayList<DeliveryItemsModel> cartList;
    LinearLayout fragmentLayout;
    // MainActivity mainActivity;
    ItemClickListener clickListener;
    User user;
    API api;
    DeliveryItemsModel deliveryModel;
    ItemDeliveryProductSingleRowBinding binding;
    int pos;
    String action;
    DeliveryReceivePresenter deliveryReceivePresenter;
    LoadingDialog loadingDialog;
    AlertDialog alertDialog;
    public DeliveryAdapter(int layout, Context context, ArrayList<DeliveryItemsModel> cartList) {
        this.layout = layout;
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layout, parent, false);
        return new viewHolder(binding);
    }

    @SuppressLint({"NotifyDataSetChanged", "DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        user = new User(context);
        api = ConstantValues.getAPI();
        pos = holder.getBindingAdapterPosition();
        deliveryModel = cartList.get(position);
        Picasso.get().load(ConstantValues.web_url + "image/product/small/" + deliveryModel.getImg()).into(holder.prImg);
        String price = String.valueOf(deliveryModel.getPrice());
        String quantity = String.valueOf(deliveryModel.getQty());
        String tPrice = String.valueOf(deliveryModel.getTotal());
        holder.price.setText(price);
        holder.qty.setText(quantity);
        //holder.received_btn.setOnClickListener(this::submit);
        holder.received_btn.setOnClickListener(view -> {
            loadingDialog=new LoadingDialog((Activity) context);
            deliveryReceivePresenter=new DeliveryReceivePresenter(this);
            submit(view, holder);
        });
        if (Double.parseDouble(deliveryModel.getAdjust()) == 0) {
            if (deliveryModel.getReceived().equals("0")) {
                if (deliveryModel.getPaid().equals("1")) {
                    holder.receivedCard.setVisibility(View.VISIBLE);
                    holder.actionText.setVisibility(View.GONE);
                    holder.received_btn.setBackgroundColor(Color.BLACK);
                    holder.acText.setText("submit");
                    holder.acText.setTextColor(Color.WHITE);
                } else if (deliveryModel.getPaid().equals("2")) {
                    holder.receivedCard.setVisibility(View.GONE);
                    holder.actionText.setVisibility(View.VISIBLE);
                    holder.actionText.setTextColor(Color.RED);
                    holder.actionText.setText("canceled");
                }
            } else if (deliveryModel.getReceived().equals("1")) {
                holder.receivedCard.setVisibility(View.GONE);
                holder.actionText.setVisibility(View.VISIBLE);
                holder.actionText.setTextColor(Color.GREEN);
                holder.actionText.setText("received");
            }
        } else {
            holder.receivedCard.setVisibility(View.GONE);
            holder.actionText.setVisibility(View.VISIBLE);
            holder.actionText.setTextColor(Color.BLACK);
            holder.actionText.setText("Adjusted");
        }
        holder.prName.setText(deliveryModel.getName());
        holder.prTotalPrice.setText(tPrice);
        if (Double.parseDouble(deliveryModel.getAdjust()) > 0) {
            holder.prRP.setText(String.format("%.2s (adjusted)", deliveryModel.getAdjust()));
        } else holder.prRP.setText(String.format("%.2s", deliveryModel.getPoint()));
        holder.productInvoice.setText(deliveryModel.getInvoice());
        holder.mobileNumber.setText(deliveryModel.getShop_mobile());
        holder.shopName.setText(deliveryModel.getShop_name());

    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void submit(View view, viewHolder holder) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_submit_receive_action_item, viewGroup, false);
        RelativeLayout receiveBtn = dialogView.findViewById(R.id.receiveId);
        RelativeLayout returnBtn = dialogView.findViewById(R.id.returnId);
        TextInputLayout pinCode = dialogView.findViewById(R.id.pinCode);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
        receiveBtn.setOnClickListener(view1 -> {
            if (ConstantValues.validation(pinCode)) {
                action=ConstantValues.applied.RECEIVED;
                String pin = pinCode.getEditText().getText().toString();
                deliveryReceivePresenter.deliveryReceiveDataLoad(user.getUsername(), pin, ConstantValues.applied.RECEIVED, cartList.get(holder.getAbsoluteAdapterPosition()).getSerial());
            /*    MySingleton.getInstance(context).addToRequestQueue(api.deliveryReceive(user.getUsername(), user.getPassword(), pin, ConstantValues.applied.RECEIVED, cartList.get(holder.getAbsoluteAdapterPosition()).getSerial(), response -> {
                    try {
                        if (response.getString(ConstantValues.ERROR).equals("0")) {
                            binding.receivedCard.setVisibility(View.GONE);
                            binding.actionText.setVisibility(View.VISIBLE);
                            binding.actionText.setTextColor(Color.GREEN);
                            binding.actionText.setText("received");
                            alertDialog.dismiss();
                            switchFragment(new HomeFragment(), "HomeFragment");
                            notifyDataSetChanged();
                        }
                        if (response.getString(ConstantValues.ERROR).equals("2")) {
                            binding.receivedCard.setVisibility(View.GONE);
                            binding.actionText.setVisibility(View.VISIBLE);
                            binding.actionText.setTextColor(Color.RED);
                            binding.actionText.setText("Returned");
                            alertDialog.dismiss();
                            switchFragment(new HomeFragment(), "HomeFragment");
                            notifyDataSetChanged();
                        }
                        showSnackBar(response.getString(ConstantValues.MSG));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }));
*/
            }
        });
        returnBtn.setOnClickListener(view1 -> {
            action=ConstantValues.applied.RETURN;
            deliveryReceivePresenter.deliveryReceiveDataLoad(user.getUsername(), "", ConstantValues.applied.RETURN, cartList.get(holder.getAbsoluteAdapterPosition()).getSerial());
           /* MySingleton.getInstance(context).addToRequestQueue(api.deliveryReturn(user.getUsername(), user.getPassword(), ConstantValues.applied.RETURN, cartList.get(holder.getAbsoluteAdapterPosition()).getSerial(), response -> {
                try {
                    if (response.getString(ConstantValues.ERROR).equals("0")) {
                        alertDialog.dismiss();
                        binding.receivedCard.setVisibility(View.GONE);
                        binding.actionText.setVisibility(View.VISIBLE);
                        binding.actionText.setTextColor(Color.RED);
                        binding.actionText.setText("canceled");
                        switchFragment(new HomeFragment(), "HomeFragment");
                        notifyDataSetChanged();
                    }
                    showSnackBar(response.getString(ConstantValues.MSG));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }));*/
        });


    }

    private void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(((MainActivity) context).binding.getRoot(), msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void setOnClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
    }


    public DeliveryAdapter addOnClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onDeliveryReceiveDataLoad(DeliveryReceiveModel deliveryReceiveModel) {
        if (action.equals(ConstantValues.applied.RECEIVED)){
            if (deliveryReceiveModel.getError()==0) {
                binding.receivedCard.setVisibility(View.GONE);
                binding.actionText.setVisibility(View.VISIBLE);
                binding.actionText.setTextColor(Color.GREEN);
                binding.actionText.setText("received");
                alertDialog.dismiss();
                switchFragment(new HomeFragment(), "HomeFragment");
                notifyDataSetChanged();
            }
            if (deliveryReceiveModel.getError()==2) {
                binding.receivedCard.setVisibility(View.GONE);
                binding.actionText.setVisibility(View.VISIBLE);
                binding.actionText.setTextColor(Color.RED);
                binding.actionText.setText("Returned");
                alertDialog.dismiss();
                switchFragment(new HomeFragment(), "HomeFragment");
                notifyDataSetChanged();
            }

        }
        if (action.equals(ConstantValues.applied.RETURN)){
            alertDialog.dismiss();
            binding.receivedCard.setVisibility(View.GONE);
            binding.actionText.setVisibility(View.VISIBLE);
            binding.actionText.setTextColor(Color.RED);
            binding.actionText.setText("canceled");
            switchFragment(new HomeFragment(), "HomeFragment");
            notifyDataSetChanged();
        }
        showSnackBar(deliveryReceiveModel.getData());
    }

    @Override
    public void onDeliveryReceiveStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onDeliveryReceiveStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onDeliveryReceiveShowMessage(String errmsg) {
        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView prImg;
        TextView prName, qty, prTotalPrice, price, prRP, productInvoice, shopName, mobileNumber, acText, actionText;
        LinearLayout received_btn;
        CardView receivedCard;

        public viewHolder(@NonNull ItemDeliveryProductSingleRowBinding itemView) {
            super(itemView.getRoot());
            prImg = itemView.productImage;
            prName = itemView.productName;
            qty = itemView.productQuantity;
            prTotalPrice = itemView.singleProductTotalPrice;
            price = itemView.proPrice;
            prRP = itemView.productRP;
            productInvoice = itemView.productInvoice;
            shopName = itemView.shopName;
            mobileNumber = itemView.mobileNumber;
            received_btn = itemView.receivedBtn;
            acText = itemView.acText;
            actionText = itemView.actionText;
            receivedCard = itemView.receivedCard;
            if (clickListener != null)
                itemView.getRoot().setOnClickListener(view -> {
                    clickListener.OnClick(view, getAbsoluteAdapterPosition());
                });
        }
    }


}
