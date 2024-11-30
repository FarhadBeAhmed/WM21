package co.wm21.https.view.adapters.category.drawer_category;

import android.annotation.SuppressLint;
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

public class DrawerSubCatAdapter extends RecyclerView.Adapter<DrawerSubCatAdapter.viewHolder>implements OnDrawerCatListView {
    private final ArrayList<DrawerCatModel> categoryList;
    private final ArrayList<DrawerCatModel> brandsModels = new ArrayList<>();
    private final LayoutInflater mInflater;
    public ItemClickListener listener;
    private DrawerCatListPresenter drawerCatListPresenter;
    DrawerBrandAdapter adapter;
    private String layoutType;
    Context context;
    int id = 0;
    @LayoutRes
    int res;

    public DrawerSubCatAdapter(Context context, ArrayList<DrawerCatModel> categoryList, @LayoutRes int res) {
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

    @SuppressLint({"NotifyDataSetChanged", "RtlHardcoded"})
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        DrawerCatModel category = categoryList.get(position);
        holder.text.setText(category.getName());

        boolean isExpanded = categoryList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        if (categoryList.get(position).isExpanded()) {
            holder.text.setTextColor(Color.parseColor("#FE0000"));
            holder.expandBtn.setImageResource(R.drawable.ic_arrow_down_red);
        } else {
            holder.text.setTextColor(Color.parseColor("#FFFFFF"));
            holder.expandBtn.setImageResource(R.drawable.ic_arrow_right_white);
            //holder.notifyAll();

        }


        int id = 2;
        brandsModels.clear();
        holder.RecView.removeAllViews();

        holder.RecView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
       adapter = new DrawerBrandAdapter(context, brandsModels, R.layout.layout_item_drawer_brands);
        holder.RecView.setHasFixedSize(true);
        holder.RecView.setAdapter(adapter);
        drawerCatListPresenter=new DrawerCatListPresenter(this);

        drawerCatListPresenter.onDrawerCatDataLoad(id, category.getId());

       /* co.wm21.https.FHelper.API api2 = co.wm21.https.FHelper.ConstantValues.getAPI();
        MySingleton.getInstance(context).addToRequestQueue(api2.categories(id, category.getId(), response -> {
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
*/

        holder.btn.setOnClickListener(view -> {
            ((MainActivity)context).binding.drawerLayout.closeDrawer(Gravity.LEFT);
            Bundle bundle = new Bundle();
            bundle.putString(ConstantValues.SUB_CAT_ID, category.getId());
            bundle.putString(ConstantValues.NAME, category.getName());
            CategoryProductFragment categoryProductFragment = new CategoryProductFragment();
            categoryProductFragment.setArguments(bundle);
            //((FragmentActivity) context).getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.main_home_contain, subSubCategoryFragment, "SubCategoryFragment").addToBackStack("SubCategory").commit();
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
        brandsModels.addAll(drawerCatModels);
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
        TextView text;
        RelativeLayout btn;
        LinearLayout expandableLayout;
        ImageView expandBtn;
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
                DrawerCatModel catModel = categoryList.get(getAdapterPosition());
                catModel.setExpanded(!catModel.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });
            itemView.setOnClickListener(v -> {
                if (listener != null) listener.OnClick(v, getAdapterPosition());
            });
        }
    }


}
