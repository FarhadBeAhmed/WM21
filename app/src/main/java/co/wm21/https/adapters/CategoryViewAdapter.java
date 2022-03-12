package co.wm21.https.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wm21.https.*;
import co.wm21.https.helpers.Constant;

public class CategoryViewAdapter extends RecyclerView.Adapter<CategoryViewAdapter.CategoryViewHolder> {

    private ArrayList<String> mList;
    private LayoutInflater mInflater;
    public ItemClickListener listener;
    private String layoutType;

    public CategoryViewAdapter(Context context, ArrayList<String> mList, String layoutType) {
        this.mList = mList;
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
        String text = mList.get(position);
        holder.text.setText(text);
    }

    @Override
    public int getItemViewType(int position) {
        if(Constant.GRID_LAYOUT.equals(layoutType))
            return R.layout.layout_item_category;
        return R.layout.layout_item_category;
    }

    @Override
    public int getItemCount() { return mList.size(); }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.category_name);
            itemView.setOnClickListener(v -> {
                if (listener != null) listener.OnClick(v, getAdapterPosition());
            });
        }
    }
}