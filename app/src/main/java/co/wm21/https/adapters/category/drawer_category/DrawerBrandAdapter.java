package co.wm21.https.adapters.category.drawer_category;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.FHelper.networks.Models.DrawerCatModel;
import co.wm21.https.R;
import co.wm21.https.activities.MainActivity;
import co.wm21.https.adapters.ItemClickListener;
import co.wm21.https.fragments.products.CategoryProductFragment;

public class DrawerBrandAdapter extends RecyclerView.Adapter<DrawerBrandAdapter.viewHolder> {
    private final ArrayList<DrawerCatModel> brandsList;
    private final LayoutInflater mInflater;
    public ItemClickListener listener;
    private String layoutType;

    Context context;
    @LayoutRes
    int res;

    public DrawerBrandAdapter(Context context, ArrayList<DrawerCatModel> brandsList, @LayoutRes int res) {
        this.brandsList = brandsList;
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
        DrawerCatModel category = brandsList.get(position);
        holder.text.setText(category.getName());





        holder.btn.setOnClickListener(view -> {
            ((MainActivity)context).binding.drawerLayout.closeDrawer(Gravity.LEFT);
            Bundle bundle = new Bundle();
            bundle.putString(ConstantValues.BRAND_ID, category.getId());
            bundle.putString(ConstantValues.NAME, category.getName());
            CategoryProductFragment categoryProductFragment = new CategoryProductFragment();
            categoryProductFragment.setArguments(bundle);
            //((FragmentActivity) context).getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.main_home_contain, subSubCategoryFragment, "SubCategoryFragment").addToBackStack("SubCategory").commit();
            switchFragment(categoryProductFragment,"CategoryProductFragment");
        });

    }

    @Override
    public int getItemCount() {
        if(brandsList !=null) {
            return brandsList.size();
        }
        else return 0;
    }
    public void switchFragment(Fragment fragment,String tag) {
        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(((MainActivity)context).binding.fragmentContainer.getId(), fragment,tag).addToBackStack(tag).commit();
    }
    public class viewHolder extends RecyclerView.ViewHolder {
        TextView text;
        LinearLayout btn;
        RecyclerView subcatRecView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.drSubCategory_name);
            btn = itemView.findViewById(R.id.categoryBtn);
            btn = itemView.findViewById(R.id.categoryBtn);
            itemView.setOnClickListener(v -> {
                if (listener != null) listener.OnClick(v, getAdapterPosition());
            });
        }
    }
}
