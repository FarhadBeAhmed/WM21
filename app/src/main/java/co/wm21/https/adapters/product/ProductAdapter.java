package co.wm21.https.adapters.product;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wm21.https.adapters.ItemClickListener;;
import co.wm21.https.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<ProductView> productList;
    public ItemClickListener listener;
    private Context context;

    public ProductAdapter(Context context, ArrayList<ProductView> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductView product = productList.get(position);
        holder.productName.setText(product.getProductName());
        holder.previousPrice.setText(String.format("৳ %s", product.getPrice()));
        holder.productPrice.setText(String.format("৳ %s", product.getCurrentPrice()));
        holder.productImage.setImageDrawable(product.getImage());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setOnClickListener(ItemClickListener listener) { this.listener = listener; }

    public ProductAdapter addOnClickListener(ItemClickListener listener) {
        this.listener = listener;
        return this;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, previousPrice, btnAddToCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            previousPrice = itemView.findViewById(R.id.previousPrice);
            btnAddToCart = itemView.findViewById(R.id.addToCart);
            previousPrice.setPaintFlags(previousPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            // to remove "previousPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG)"
            if (listener != null) itemView.setOnClickListener(v -> listener.OnClick(v, getAdapterPosition()));
        }
    }
}
