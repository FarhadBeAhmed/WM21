package co.wm21.https.view.adapters.category.drawer_category;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.DrawerCatModel;
import co.wm21.https.R;
import co.wm21.https.view.activities.MainActivity;
import co.wm21.https.view.adapters.ItemClickListener;
import co.wm21.https.view.fragments.products.CategoryProductFragment;
import co.wm21.https.presenter.interfaces.OnDrawerCatListView;
import co.wm21.https.presenter.DrawerCatListPresenter;

public class DrawerCatAdapter extends RecyclerView.Adapter<DrawerCatAdapter.viewHolder> implements OnDrawerCatListView {
    private ArrayList<co.wm21.https.FHelper.networks.Models.DrawerCatModel> categoryList;
    ArrayList<DrawerCatModel> subCategoryList = new ArrayList<>();
    private LayoutInflater mInflater;
    public ItemClickListener listener;
    private DrawerCatListPresenter drawerCatListPresenter;
    DrawerSubCatAdapter adapter;
    Context context;
    int adapterPosition;
    @LayoutRes
    int res;

    public DrawerCatAdapter(Context context, ArrayList<co.wm21.https.FHelper.networks.Models.DrawerCatModel> categoryList, @LayoutRes int res) {
        this.categoryList = categoryList;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.res = res;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(mInflater.inflate(res, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        DrawerCatModel category = categoryList.get(position);
        //adapterPosition=position;
        holder.text.setText(category.getName());
        boolean isExpanded = categoryList.get(position).isExpanded();
        holder.CatExpandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        drawerCatListPresenter=new DrawerCatListPresenter(this);

        if (categoryList.get(position).isExpanded()) {
            holder.text.setTextColor(Color.parseColor("#FE0000"));
            holder.expandBtn.setImageResource(R.drawable.ic_arrow_down_red);
            Picasso.get().load(ConstantValues.web_url+"image/category_icon/icon_"+category.getId()+"_red.png").placeholder(R.drawable.bg_shimmer).into(holder.image);

        } else {
            holder.text.setTextColor(Color.parseColor("#FFFFFF"));
            holder.expandBtn.setImageResource(R.drawable.ic_arrow_right_white);
            Picasso.get()
                    .load(ConstantValues.web_url+"image/category_icon/icon_"+category.getId()+".png")
                    .placeholder(R.drawable.bg_shimmer)
                    .into(holder.image);

        }
        int id = 1;
        subCategoryList.clear();
        holder.subcatRecView.removeAllViews();

        holder.subcatRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        adapter = new DrawerSubCatAdapter(context, subCategoryList, R.layout.layout_item_drawer_subcat);
        holder.subcatRecView.setAdapter(adapter);

        drawerCatListPresenter.onDrawerCatDataLoad(id, category.getId());


       /* co.wm21.https.FHelper.API api2 = co.wm21.https.FHelper.ConstantValues.getAPI();
        MySingleton.getInstance(context).addToRequestQueue(api2.categories(id, category.getId(), response -> {
            try {

                JSONArray jsonArray = response.getJSONArray("sub_cat");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    subCategoryList.add(new DrawerSubCatModel(
                            json.getString(ConstantValues.Categories.SCAT_ID),
                            json.getString(ConstantValues.Categories.SCAT_NAME),
                            json.getString(ConstantValues.Categories.CAT_ID)

                            ));
                }
                holder.subcatRecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                DrawerSubCatAdapter adapter = new DrawerSubCatAdapter(context, subCategoryList, R.layout.layout_item_drawer_subcat);
                holder.subcatRecView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }));*/


        holder.btn.setOnClickListener(view -> {
            ((MainActivity)context).binding.drawerLayout.closeDrawer(Gravity.LEFT);
            String cat_id = category.getId();
            String name = category.getName();
            Bundle bundle = new Bundle();
            bundle.putString(ConstantValues.ARGUMENT1, cat_id.toString());
            bundle.putString(ConstantValues.NAME, name);
            CategoryProductFragment categoryProductFragment = new CategoryProductFragment();
            categoryProductFragment.setArguments(bundle);
            switchFragment(categoryProductFragment,"CategoryProductFragment");
        });

    }



    public void switchFragment(Fragment fragment,String tag) {
        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(((MainActivity)context).binding.fragmentContainer.getId(), fragment,tag).addToBackStack(tag).commit();
    }

    @Override
    public int getItemCount() {
        if (categoryList != null) {
            return categoryList.size();
        } else return 0;
    }

    @Override
    public void onDrawerCatListDataLoad(List<DrawerCatModel> drawerCatModels) {
        subCategoryList.addAll(drawerCatModels);
       // adapter.notifyItemChanged();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDrawerCatListStartLoading() {

    }

    @Override
    public void onDrawerCatListStopLoading() {

    }

    @Override
    public void onDrawerCatListShowMessage(String errmsg) {
        Toast.makeText(context.getApplicationContext(), errmsg, Toast.LENGTH_SHORT).show();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;
        RelativeLayout btn;
        ImageView expandBtn;
        LinearLayout CatExpandableLayout;
        RecyclerView subcatRecView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.drCategory_icon);
            text = itemView.findViewById(R.id.drCategory_name);
            btn = itemView.findViewById(R.id.categoryBtn);
            expandBtn = itemView.findViewById(R.id.expandBtn_icon);
            subcatRecView = itemView.findViewById(R.id.subCatRecView);
            CatExpandableLayout = itemView.findViewById(R.id.expandableLayout);

            expandBtn.setOnClickListener(view -> {

                DrawerCatModel catModel = categoryList.get(getAbsoluteAdapterPosition());
                catModel.setExpanded(!catModel.isExpanded());
                notifyItemChanged(getAbsoluteAdapterPosition());
            });


            itemView.setOnClickListener(v -> {
                if (listener != null) listener.OnClick(v, getAbsoluteAdapterPosition());
            });

        }
    }
}
