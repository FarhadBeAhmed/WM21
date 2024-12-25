package co.wm21.https.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.ProductReviewModel;
import co.wm21.https.databinding.ItemProductReviewBinding;

public class ProductReviewAdapter extends RecyclerView.Adapter<ProductReviewAdapter.reviewViewHolder> {
    Context context;
    ArrayList<ProductReviewModel> productReviews;
    int layout;
    ItemProductReviewBinding binding;

    public ProductReviewAdapter(Context context, ArrayList<ProductReviewModel> productReviews, int layout) {
        this.context = context;
        this.productReviews = productReviews;
        this.layout = layout;
    }

    @NonNull
    @Override
    public reviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemProductReviewBinding.inflate(LayoutInflater.from(context), parent, false);
        return new reviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull reviewViewHolder holder, int position) {
        ProductReviewModel productReview = productReviews.get(position);
        holder.name.setText(productReview.getName());
        holder.review.setText(productReview.getReview());
        //Picasso.get().load(ConstantValues.web_url + "new/sys/stores/" + productReview.getImg()).into(holder.image);

        Glide.with(context)
                .asDrawable()
                .load(ConstantValues.URL + "image/product/small/" + productReview.getImg())  // SVG URL
                .transition(DrawableTransitionOptions.withCrossFade()) // Optional: Fade transition
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // Cache strategy
                .into(holder.image);

      /*  ImageLoaderHelper.loadImage(
                context,                 // Context (Activity or Application)
                holder.productImage,            // ImageView where the image will be loaded
                ConstantValues.URL + "image/product/small/" + product.getImg(),             // Image URL
                R.drawable.ic_image_temp,     // Placeholder image
                R.drawable.ic_information           // Error image
        );*/
        holder.date.setText(productReview.getDate());
        holder.ratingBar.setRating((float) productReview.getRating());
    }

    @Override
    public int getItemCount() {
        return productReviews.size();
    }

    public class reviewViewHolder extends RecyclerView.ViewHolder {
        TextView review, name, date;
        ImageView image;
        RatingBar ratingBar;

        public reviewViewHolder(@NonNull ItemProductReviewBinding itemView) {
            super(itemView.getRoot());
            review = itemView.review;
            name = itemView.profileName;
            date = itemView.date;
            image = itemView.profileImage;
            ratingBar = itemView.ratingBar;
        }
    }
}
