package co.wm21.https.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.TopSellingProModel;
import co.wm21.https.databinding.LayoutItemTopSelBinding;


public class TopSellingAdapter extends RecyclerView.Adapter<TopSellingAdapter.viewHolder> {
    Context context;
    ArrayList<TopSellingProModel>topSellingProducts;
    int layout;
    LayoutItemTopSelBinding binding;
    public ItemClickListener listener;

    public TopSellingAdapter(Context context, ArrayList<TopSellingProModel> topSellingProducts, int layout) {
        this.context = context;
        this.topSellingProducts = topSellingProducts;
        this.layout = layout;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=LayoutItemTopSelBinding.inflate(LayoutInflater.from(context),parent,false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        TopSellingProModel product=topSellingProducts.get(position);
        Picasso.get().load(ConstantValues.web_url + "image/product/small/" + product.getImg()).into(holder.prImage);

    }

    @Override
    public int getItemCount() {
        return topSellingProducts.size();
    }
    public void setOnClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public TopSellingAdapter addOnClickListener(ItemClickListener listener) {
        this.listener = listener;
        return this;
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView prImage;
        public viewHolder(@NonNull LayoutItemTopSelBinding itemView) {
            super(itemView.getRoot());
            prImage=itemView.productImage;
            if (listener != null)
                itemView.getRoot().setOnClickListener(v -> listener.OnClick(v, getAbsoluteAdapterPosition()));


        }
    }
}
