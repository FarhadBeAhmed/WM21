package co.wm21.https.view.adapters;

import static java.lang.Math.round;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.R;
import co.wm21.https.view.activities.MainActivity;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.view.fragments.CartFragment;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.presenter.interfaces.OnAddToCartView;
import co.wm21.https.presenter.AddToCartPresenter;

public class PopularProductAdapter extends RecyclerView.Adapter<PopularProductAdapter.ProductViewHolder>implements OnAddToCartView {
    private ArrayList<ProductModel> productList;
    public ItemClickListener listener;
    private Context context;
    SessionHandler sessionHandler;
    private AddToCartPresenter addToCartPresenter;
    View clickView;
    //private ProgressDialog progressDialog;
    LoadingDialog loadingDialog;
    ProgressDialog progressBar;
    private  String selectedColor = "", selectedSize = "";

    public PopularProductAdapter(ArrayList<ProductModel> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_product, parent, false));
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModel product = productList.get(position);
        sessionHandler = new SessionHandler(context);
        holder.disLayout.setVisibility(View.GONE);
        if (sessionHandler.isLoggedIn()) {
            holder.rpLayout.setVisibility(View.VISIBLE);
            holder.eshopTV.setVisibility(View.GONE);
            holder.productRPTV.setText(String.format("%s", product.getPoint()));
        } else {
            holder.rpLayout.setVisibility(View.GONE);
            holder.eshopTV.setVisibility(View.VISIBLE);
        }
        double discount=Double.parseDouble(product.getDiscount());
        double sPrice=Double.parseDouble(product.getSprice());

        if(round(discount/sPrice*100)>.49){
            holder.disLayout.setVisibility(View.VISIBLE);
            holder.discountText.setText(round( discount / sPrice * 100) + "% OFF");
        }

        holder.shopName.setText("(" + product.getUploadBy() + ")");
        holder.productName.setText(product.getName());
        holder.previousPrice.setText(String.format("৳ %s", product.getPrice()));
        holder.productPrice.setText(String.format("৳ %s", product.getSprice()));
        // holder.productImage.setImageDrawable(product.getImage());
        Picasso.get().load(ConstantValues.URL+"image/product/small/"+product.getImg()).into(holder.productImage);

        holder.btnAddToCart.setOnClickListener(view -> {
            loadingDialog=new LoadingDialog((Activity) context);
            addToCartPresenter=new AddToCartPresenter(this);
            @SuppressLint("HardwareIds") String val = Settings.Secure.getString(((FragmentActivity) context).getContentResolver(), Settings.Secure.ANDROID_ID);
            String uId = val.replaceAll("[\\D]", "");
            int qty = 1;
            clickView=view;
            addToCartPresenter.AddToCartDataLoad(String.valueOf(product.getSerial()), uId,selectedColor,selectedSize,qty);


        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public void setOnClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public PopularProductAdapter addOnClickListener(ItemClickListener listener) {
        this.listener = listener;
        return this;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, previousPrice, btnAddToCart, productRPTV, shopName,eshopTV;
        LinearLayout productRPLayout,rpLayout;
        TextView discountText;
        RelativeLayout disLayout;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            disLayout = itemView.findViewById(R.id.disLayout);
            discountText = itemView.findViewById(R.id.disText);
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
}
