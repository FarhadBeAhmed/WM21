package co.wm21.https.view.adapters.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.wm21.https.R;

public class CheckoutPrAdapter extends RecyclerView.Adapter<CheckoutPrAdapter.CheckoutPrViewHolder> {
    List<CartModel> cartList;
    LayoutInflater layoutInflater;

    Context context;

    public CheckoutPrAdapter(List<CartModel> cartList, Context context) {
        this.cartList = cartList;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CheckoutPrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckoutPrViewHolder(layoutInflater.inflate(R.layout.checkout_pr_single_row, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutPrViewHolder holder, int position) {
        CartModel cartViewsModel= cartList.get(position);
        holder.chPrName.setText(cartViewsModel.getProductName());
        holder.chPrPrice.setText(String.valueOf(cartViewsModel.getPrice()));
        holder.chPrQty.setText(String.valueOf(cartViewsModel.getQty()));
        holder.chPrT.setText(String.valueOf(cartViewsModel.getSubTotal()));

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CheckoutPrViewHolder extends RecyclerView.ViewHolder {

        TextView chPrName,chPrPrice,chPrQty,chPrT;

        public CheckoutPrViewHolder(@NonNull View itemView) {
            super(itemView);
            chPrName=itemView.findViewById(R.id.chPrNameId);
            chPrPrice=itemView.findViewById(R.id.chPrPriceId);
            chPrQty=itemView.findViewById(R.id.chPrQtyId);
            chPrT=itemView.findViewById(R.id.chPrTId);
        }
    }
}
