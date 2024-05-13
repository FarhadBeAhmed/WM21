package co.wm21.https.adapters.category.drawer_category;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import co.wm21.https.adapters.category.CategoryView;
import co.wm21.https.helpers.Constant;

public class DrawerSubCatAdapter extends RecyclerView.Adapter<DrawerSubCatAdapter.viewHolder> {
    private final ArrayList<DrawerSubCatModel> categoryList ;
    private final ArrayList<DrawerBrandsModel> brandsModels = new ArrayList<>();
    private final LayoutInflater mInflater;
    public ItemClickListener listener;
    private String layoutType;
    Context context;
    @LayoutRes
    int res;

    public DrawerSubCatAdapter(Context context, ArrayList<DrawerSubCatModel> categoryList, @LayoutRes int res) {
        this.categoryList = categoryList;
        this.mInflater = LayoutInflater.from(context);
        this.layoutType = layoutType;
        this.context=context;
        this.res=res;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(mInflater.inflate(res, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        DrawerSubCatModel category = categoryList.get(position);
        holder.text.setText(category.getName());

        boolean isExpanded = categoryList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);


        if(categoryList.get(position).isExpanded()) {
            holder.text.setTextColor(Color.parseColor("#FE0000"));
        }else {
            holder.text.setTextColor(Color.parseColor("#000000"));
        }


        int id = 1;
        brandsModels.clear();
        holder.subcatRecView.removeAllViews();
        co.wm21.https.FHelper.API api2 = co.wm21.https.FHelper.ConstantValues.getAPI();
        MySingleton.getInstance(context).addToRequestQueue(api2.categories(id, category.getCatId(), response -> {
            try {

                JSONArray jsonArray = response.getJSONArray("sub_cat");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    brandsModels.add(new DrawerBrandsModel(
                            json.getString(ConstantValues.Categories.BRAND_ID),
                            json.getString(ConstantValues.Categories.BRAND_NAME)));
                }
                holder.subcatRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                DrawerSubCatAdapter adapter=new DrawerSubCatAdapter(context, brandsModels, R.layout.layout_item_drawer_brands);
                holder.subcatRecView.setHasFixedSize(true);
                holder.subcatRecView.setAdapter(adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }));

    }

    @Override
    public int getItemCount() {
        if(categoryList !=null) {
            return categoryList.size();
        }
        else return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;
        LinearLayout btn;
        RecyclerView subcatRecView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.drSubCategory_icon);
            text = itemView.findViewById(R.id.drSubCategory_name);
            btn = itemView.findViewById(R.id.categoryBtn);
            btn = itemView.findViewById(R.id.categoryBtn);
            itemView.setOnClickListener(v -> {
                if (listener != null) listener.OnClick(v, getAdapterPosition());
            });
        }
    }
}
