package co.wm21.https.adapters.item_menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wm21.https.adapters.ItemClickListener;
import co.wm21.https.R;

public class ItemMenuAdapter extends RecyclerView.Adapter<ItemMenuAdapter.ItemMenuHolder> {

    ArrayList<ItemMenuView> mList;
    LayoutInflater mInflater;
    ItemClickListener mListener;
    @LayoutRes int layout;

    public ItemMenuAdapter(Context context, ArrayList<ItemMenuView> itemMenuViews, @LayoutRes int layout) {
        mList = itemMenuViews;
        mInflater = LayoutInflater.from(context);
        this.layout = layout;
    }

    @NonNull
    @Override
    public ItemMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemMenuHolder(mInflater.inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemMenuHolder holder, int position) {
        ItemMenuView mView = mList.get(position);
        holder.textView.setText(mView.getTitle());
        holder.imageView.setImageDrawable(mView.getImage() == null ? null : mView.getImage());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public ItemMenuAdapter addOnClickListener(ItemClickListener mListener) {
        this.mListener = mListener;
        return this;
    }

    public void setOnClickListener(ItemClickListener mListener) { this.mListener = mListener; }

    class ItemMenuHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ItemMenuHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.category_name);
            imageView = itemView.findViewById(R.id.category_icon);
            if (mListener != null) itemView.setOnClickListener(view -> mListener.OnClick(view, getAdapterPosition()));
        }
    }
}
