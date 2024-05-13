package co.wm21.https.adapters.category.drawer_category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.R;
import co.wm21.https.adapters.ItemClickListener;
import co.wm21.https.adapters.category.CategoryAdapter;
import co.wm21.https.adapters.category.CategoryView;
import co.wm21.https.helpers.Constant;

public class DrawerCatAdapter extends RecyclerView.Adapter<DrawerCatAdapter.viewHolder> {
    private ArrayList<DrawerCatModel> categoryList;
    private LayoutInflater mInflater;
    public ItemClickListener listener;
    private String layoutType;
    Context context;
    @LayoutRes
    int res;

    public DrawerCatAdapter(Context context, ArrayList<DrawerCatModel> categoryList, @LayoutRes int res) {
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
        DrawerCatModel category = categoryList.get(position);
        //Picasso.get().load("http://www.wm21.net/image/cat/"+category.getCategoryImageUrl()).into(holder.image);
       // Picasso.get().load(Constant.getDrawableFromUrl("http://www.wm21.net/image/cat/"+category.getCategoryImageUrl()).toString()).into(holder.image);
       holder.image.setImageDrawable(Constant.getDrawableFromUrl("image", "cat", category.getIcon()));
        holder.text.setText(category.getName());
        holder.btn.setOnClickListener(view -> {

            int id=0;
            ArrayList<CategoryView> categoryViews = new ArrayList<>();

            co.wm21.https.FHelper.API api2= co.wm21.https.FHelper.ConstantValues.getAPI();
           // binding.shimmerProduct.setVisibility(View.VISIBLE);
          //  binding.parentCategoriesRecyclerView.setVisibility(View.GONE);
            MySingleton.getInstance(context).addToRequestQueue(api2.categories(id,category.getId(), response -> {
                try {
                    JSONArray jsonArray=response.getJSONArray("sub_cat");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject json=jsonArray.getJSONObject(i);
                        categoryViews.add(new CategoryView(
                                json.getString(ConstantValues.Categories.CAT_NAME),
                                json.getString(ConstantValues.Categories.CAT_ICON)));

                    }
                    holder.subcatRecView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
                    holder.subcatRecView.setAdapter(new DrawerCatAdapter(context, categoryViews, R.layout.layout_item_drawer_subcat));
                  //  binding.shimmerProduct.setVisibility(View.GONE);
                  //  binding.parentCategoriesRecyclerView.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }));


        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;
        LinearLayout btn;
        RecyclerView subcatRecView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.drCategory_icon);
            text = itemView.findViewById(R.id.drCategory_name);
            btn = itemView.findViewById(R.id.categoryBtn);
            subcatRecView = itemView.findViewById(R.id.subCatRecView);
            itemView.setOnClickListener(v -> {
                if (listener != null) listener.OnClick(v, getAdapterPosition());
            });
        }
    }
}
