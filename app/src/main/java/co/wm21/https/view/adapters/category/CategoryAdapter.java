package co.wm21.https.view.adapters.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wm21.https.*;
import co.wm21.https.view.adapters.ItemClickListener;
import co.wm21.https.utils.Constant;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final ArrayList<CategoryView> categoryList;
    private final LayoutInflater mInflater;
    public ItemClickListener listener;
    private final String layoutType;

    public CategoryAdapter(Context context, ArrayList<CategoryView> categoryList, String layoutType) {
        this.categoryList = categoryList;
        this.mInflater = LayoutInflater.from(context);
        this.layoutType = layoutType;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(mInflater.inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryView category = categoryList.get(position);
        holder.image.setImageDrawable(Constant.getDrawableFromUrl("image", "cat", category.getCategoryImageUrl()));
        holder.text.setText(category.getCategoryName());
    }
    

    @Override
    public int getItemViewType(int position) {
        if(Constant.GRID_LAYOUT.equals(layoutType))
            return R.layout.layout_item_category;
        return R.layout.layout_item_category;
    }

    @Override
    public int getItemCount() { return categoryList.size(); }

    public void setOnClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public CategoryAdapter addOnClickListener(ItemClickListener listener) {
        this.listener = listener;
        return this;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.category_icon);
            text = itemView.findViewById(R.id.category_name);
            itemView.setOnClickListener(v -> {
                if (listener != null) listener.OnClick(v, getAdapterPosition());
            });
        }
    }
}