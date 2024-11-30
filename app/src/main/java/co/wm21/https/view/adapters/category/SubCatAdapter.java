package co.wm21.https.view.adapters.category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.wm21.https.FHelper.networks.Models.DrawerCatModel;
import co.wm21.https.R;
import co.wm21.https.view.adapters.ItemClickListener;

public class SubCatAdapter extends RecyclerView.Adapter<SubCatAdapter.viewHolder> {
    private final ArrayList<DrawerCatModel> categoryList;
    private final LayoutInflater mInflater;
    public ItemClickListener listener;
    private String layoutType;
    Context context;
    int id = 0;
    @LayoutRes
    int res;

    public SubCatAdapter(Context context, ArrayList<DrawerCatModel> categoryList, @LayoutRes int res) {
        this.categoryList = categoryList;
        this.mInflater = LayoutInflater.from(context);
        this.layoutType = layoutType;
        this.context = context;
        this.res = res;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(mInflater.inflate(res, parent, false));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        DrawerCatModel category = categoryList.get(position);
        holder.text.setText(category.getName());
        //holder.category_icon.setImageDrawable(category.getImage());
        String url="https://www.wm21.co/image/scat/"+category.getImage();
        Picasso.get().load(url).into(holder.category_icon);

    }

    @Override
    public int getItemCount() {
        if (categoryList != null) {
            return categoryList.size();
        } else return 0;

    }


    public class viewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView category_icon;
        LinearLayout btn;

        @SuppressLint("NotifyDataSetChanged")
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.category_name);
            category_icon = itemView.findViewById(R.id.category_icon);
            btn = itemView.findViewById(R.id.subCategoryBtn);

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.OnClick(v, getAdapterPosition());
            });
        }
    }


}
