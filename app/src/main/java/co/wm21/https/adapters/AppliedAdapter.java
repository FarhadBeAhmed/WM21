package co.wm21.https.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.R;
import co.wm21.https.activities.MainActivity;
import co.wm21.https.adapters.cart.CartAdapter;
import co.wm21.https.adapters.cart.CartModel;
import co.wm21.https.databinding.ItemOrderProductListSingleRowBinding;
import co.wm21.https.databinding.LayoutItemCartBinding;
import co.wm21.https.fragments.manageOrder.OrderFragment;
import co.wm21.https.helpers.User;
import co.wm21.https.model.OrderProductModel;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.viewHolder> {

    @LayoutRes
    int layout;
    Context context;
    ArrayList<OrderProductModel> cartList;
    TextView totalPrice;
    LinearLayout fragmentLayout;
    int quan=0;
    int totalSelected = 0;
    API api = ConstantValues.getAPI();
    MainActivity mainActivity;
    User user;

    public OrderAdapter(int layout, Context context, ArrayList<OrderProductModel> cartList, TextView totalPrice, LinearLayout fragmentLayout,MainActivity mainActivity) {
        this.layout = layout;
        this.context = context;
        this.cartList = cartList;
        this.totalPrice = totalPrice;
        this.fragmentLayout = fragmentLayout;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderProductListSingleRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), layout, parent, false);
        return new viewHolder(binding);
    }

    @SuppressLint({"NotifyDataSetChanged", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        user=new User(context);
        OrderProductModel cartModel = cartList.get(position);
        Picasso.get().load(ConstantValues.web_url + "image/product/small/" + cartModel.getImageUrl()).into(holder.prImg);
        String price = String.valueOf(cartModel.getPrice());
        String quantity = String.valueOf(cartModel.getQty());
        String tPrice = String.valueOf(cartModel.getTotal());
        holder.price.setText(price);
        holder.qty.setText(quantity);
        holder.prName.setText(cartModel.getProductName());
        holder.prTotalPrice.setText(tPrice);
        String pId = cartList.get(position).getProductID();
        String color = cartList.get(position).getColor();
        String size = cartList.get(position).getSize();
        String uId = user.getUsername();
        holder.qtyAdd.setOnClickListener(view -> {
            String qu=holder.qty.getText().toString();
            quan = Integer.parseInt(qu);
            quan += 1;
            MySingleton.getInstance(context).addToRequestQueue(api.updateOrder(pId, uId, color, size, quan,2, response -> {
                try {
                    showSnackBar( response.getString(ConstantValues.MSG));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }));
            cartList.get(position).setTotal(cartList.get(position).getPrice() * quan);
            cartList.get(position).setQty(quan);
            holder.prTotalPrice.setText(String.format("%.2f", cartList.get(position).getTotal()));
            notifyDataSetChanged();
            update();

        });
        holder.qtyMinus.setOnClickListener(view -> {
            if (cartModel.getQty() > 1) {
                quan = Integer.parseInt(holder.qty.getText().toString());
                quan -= 1;
                MySingleton.getInstance(context).addToRequestQueue(api.updateOrder(pId, uId, color, size, quan,2, response -> {
                    try {
                        showSnackBar( response.getString(ConstantValues.MSG));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }));
                cartList.get(position).setTotal(cartList.get(position).getPrice() * quan);
                cartList.get(position).setQty(quan);
                holder.prTotalPrice.setText(String.format("%.2f", cartList.get(position).getTotal()));
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
                    MySingleton.getInstance(context).addToRequestQueue(api.deleteFromCart(cartModel.getSerial(), "2", response -> {
                        try {
                            if (response.getString("error").equals("0")) {
                                String msg = response.getString("msg");
                                notifyItemRangeChanged(position, cartList.size());
                                alertDialog.dismiss();
                                cartList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemChanged(position);
                                notifyDataSetChanged();
                                update();
                                Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }));
                });
            }
        });

        holder.deleteItem.setOnClickListener(view -> {
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
                MySingleton.getInstance(context).addToRequestQueue(api.deleteFromCart(cartModel.getSerial(), "2", response -> {
                    try {
                        if (response.getString("error").equals("0")) {
                            String msg = response.getString("msg");
                            notifyItemRangeChanged(position, cartList.size());
                            alertDialog.dismiss();
                            cartList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemChanged(position);
                            notifyDataSetChanged();
                            update();
                            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }));
            });
        });
    }


    private void update() {
       /* double sum = 0;
        for (int i = 0; i < cartList.size(); i++) {
            double price = cartList.get(i).getTotal();
            sum += price;
        }*/
      /*  Fragment frg = null;
        frg = ((FragmentActivity)context).getSupportFragmentManager().findFragmentByTag("OrderFragment");
        final FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();*/
        mainActivity.getCartItems();
        switchFragment(new OrderFragment(mainActivity),"OrderFragment");
       /* totalPrice.setText(String.valueOf(sum));*/
    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView prImg, qtyAdd, qtyMinus, deleteItem;
        TextView prName, qty, prTotalPrice, price;
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
        }
    }
    private void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(fragmentLayout, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
