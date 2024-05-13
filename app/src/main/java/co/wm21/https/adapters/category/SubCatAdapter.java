package co.wm21.https.adapters.category.drawer_category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.R;
import co.wm21.https.adapters.ItemClickListener;

public class DrawerSubCatAdapter extends RecyclerView.Adapter<DrawerSubCatAdapter.viewHolder> {
    private final ArrayList<DrawerSubCatModel> categoryList;
    private final ArrayList<DrawerBrandsModel> brandsModels = new ArrayList<>();
    private final LayoutInflater mInflater;
    public ItemClickListener listener;
    private String layoutType;
    Context context;
    int id = 0;
    @LayoutRes
    int res;

    public DrawerSubCatAdapter(Context context, ArrayList<DrawerSubCatModel> categoryList, @LayoutRes int res) {
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
        DrawerSubCatModel category = categoryList.get(position);
        holder.text.setText(category.getName());

        boolean isExpanded = categoryList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        if (categoryList.get(position).isExpanded()) {
            holder.text.setTextColor(Color.parseColor("#FE0000"));
            holder.expandBtn.setImageResource(R.drawable.ic_arrow_down_red);
        } else {
            holder.text.setTextColor(Color.parseColor("#000000"));
            holder.expandBtn.setImageResource(R.drawable.ic_arrow_right_black);
            //holder.notifyAll();

        }


        int id = 2;
        brandsModels.clear();
        holder.RecView.removeAllViews();
        co.wm21.https.FHelper.API api2 = co.wm21.https.FHelper.ConstantValues.getAPI();
        MySingleton.getInstance(context).addToRequestQueue(api2.categories(id, category.getSubCatId(), response -> {
            try {

                JSONArray jsonArray = response.getJSONArray("brands");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    brandsModels.add(new DrawerBrandsModel(
                            json.getString(ConstantValues.Categories.BRAND_ID),
                            json.getString(ConstantValues.Categories.BRAND_NAME)));
                }
                holder.RecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                DrawerBrandAdapter adapter = new DrawerBrandAdapter(context, brandsModels, R.layout.layout_item_drawer_brands);
                holder.RecView.setHasFixedSize(true);
                holder.RecView.setAdapter(adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }));

    }

    @Override
    public int getItemCount() {
        if (categoryList != null) {
            return categoryList.size();
        } else return 0;

    }


    public class viewHolder extends RecyclerView.ViewHolder {
        TextView text;
        RelativeLayout btn;
        LinearLayout expandableLayout;
        ImageButton expandBtn;
        RecyclerView RecView;

        @SuppressLint("NotifyDataSetChanged")
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.drSubCategory_name);
            btn = itemView.findViewById(R.id.categoryBtn);
            expandableLayout = itemView.findViewById(R.id.expandableSubLayout);
            expandBtn = itemView.findViewById(R.id.expandSubBtn_icon);
            RecView = itemView.findViewById(R.id.drawerBrandRecView);
            expandBtn.setOnClickListener(view -> {
                DrawerSubCatModel catModel = categoryList.get(getAdapterPosition());
                catModel.setExpanded(!catModel.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });
            itemView.setOnClickListener(v -> {
                if (listener != null) listener.OnClick(v, getAdapterPosition());
            });
        }
    }


}
