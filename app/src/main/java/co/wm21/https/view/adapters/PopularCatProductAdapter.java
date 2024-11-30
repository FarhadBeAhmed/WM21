package co.wm21.https.view.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Paint;
import android.provider.Settings;
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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.R;
import co.wm21.https.view.activities.HomeMoreProductActivity;
import co.wm21.https.view.activities.MainActivity;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.view.fragments.CartFragment;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.presenter.interfaces.OnAddToCartView;
import co.wm21.https.presenter.AddToCartPresenter;


public class PopularCatProductAdapter extends RecyclerView.Adapter<PopularCatProductAdapter.ProductViewHolder>implements OnAddToCartView {
    private ArrayList<ProductModel> productList;
    public ItemClickListener listener;
    private Context context;
    private ViewPager2 viewPager2;

    SessionHandler sessionHandler;
    private  String selectedColor = "", selectedSize = "";
    @LayoutRes
    int layout = 0;
    int size;
    View clickView;
    private AddToCartPresenter addToCartPresenter;

    LoadingDialog loadingDialog;



    int temp=0;

    public PopularCatProductAdapter(ArrayList<ProductModel> productList, Context context, int layout,int size) {
        this.productList = productList;
        this.context = context;
        this.layout = layout;
        this.size = size;
    }

    public PopularCatProductAdapter(ArrayList<ProductModel> productList, Context context, int layout,int size,int temp) {
        this.productList = productList;
        this.context = context;
        this.layout = layout;
        this.size = size;
        this.temp=temp;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModel product = productList.get(position);
        sessionHandler = new SessionHandler(context);
        if (sessionHandler.isLoggedIn()) {
            holder.rpLayout.setVisibility(View.VISIBLE);
            holder.eshopTV.setVisibility(View.GONE);
            holder.productRPTV.setText(String.format("%s", product.getPoint()));
        } else {
            holder.rpLayout.setVisibility(View.GONE);
            holder.eshopTV.setVisibility(View.VISIBLE);
        }
        holder.shopName.setText("(" + product.getUploadBy() + ")");
        holder.productName.setText(product.getName());
        holder.previousPrice.setText(String.format("৳ %s", product.getPrice()));
        holder.productPrice.setText(String.format("৳ %s", product.getSprice()));
        // holder.productImage.setImageDrawable(product.getImage());
        Picasso.get().load(ConstantValues.URL+"image/product/small/"+product.getImg()).into(holder.productImage);

        holder.btnAddToCart.setOnClickListener(view -> {
            addToCartPresenter=new AddToCartPresenter(this);
            if(temp==1){
                loadingDialog=new LoadingDialog((HomeMoreProductActivity) context);
            }else loadingDialog=new LoadingDialog((Activity) context);
            @SuppressLint("HardwareIds") String val = Settings.Secure.getString(((FragmentActivity) context).getContentResolver(), Settings.Secure.ANDROID_ID);
            String uId = val.replaceAll("[\\D]", "");
            int qty = 1;
            clickView=view;
            addToCartPresenter.AddToCartDataLoad(String.valueOf(product.getSerial()), uId,selectedColor,selectedSize,qty);
           // addToCart(view, String.valueOf(product.getSerial()));
        });
    }

    @Override
    public int getItemCount() {
        if (productList.size()<size){
            return productList.size();
        }else return size;
    }
    public void setOnClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public PopularCatProductAdapter addOnClickListener(ItemClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void onAddToCartDataLoad(AddToCartModel addToCartModel) {

        String status = addToCartModel.getData();
        String error = String.valueOf(addToCartModel.getError());
        if (error.equals("0") || error.equals("2")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            ViewGroup viewGroup = clickView.findViewById(android.R.id.content);
            View dialogView = ((FragmentActivity) context).getLayoutInflater().inflate(R.layout.layout_add_to_cart_item, viewGroup, false);
            RelativeLayout ok = dialogView.findViewById(R.id.okBtnId);
            RelativeLayout goToCart = dialogView.findViewById(R.id.goBtnId);
            TextView msg = dialogView.findViewById(R.id.textMsg);
            msg.setText(addToCartModel.getData());
            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            ok.setOnClickListener(view1 -> {
                alertDialog.dismiss();
            });
            goToCart.setOnClickListener(view1 -> {
                alertDialog.dismiss();

                ((MainActivity) context).switchFragment(new CartFragment(), "CartFragment");
            });

            ((MainActivity) context).getCartItems();

        }
        Snackbar.make(((MainActivity)context).findViewById(R.id.fragmentContainer), status, Snackbar.LENGTH_SHORT).show();


    }

    @Override
    public void onAddToCartStartLoading() {
       loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onAddToCartStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onAddToCartShowMessage(String errmsg) {
        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, previousPrice, btnAddToCart, productRPTV, shopName,eshopTV;
        LinearLayout productRPLayout,rpLayout;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            eshopTV = itemView.findViewById(R.id.eshopTV);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            previousPrice = itemView.findViewById(R.id.previousPrice);
            btnAddToCart = itemView.findViewById(R.id.addToCart);
            productRPTV = itemView.findViewById(R.id.productRP);
            productRPLayout = itemView.findViewById(R.id.productRPLayout);
            rpLayout = itemView.findViewById(R.id.rpLayout);
            shopName = itemView.findViewById(R.id.shopNameTV);
            previousPrice.setPaintFlags(previousPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            // to remove "previousPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG)"
            if (listener != null)
                itemView.setOnClickListener(v -> listener.OnClick(v, getAdapterPosition()));
        }
    }


}
