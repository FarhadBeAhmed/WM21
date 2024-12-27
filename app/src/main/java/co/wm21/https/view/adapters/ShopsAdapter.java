package co.wm21.https.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.view.activities.MainActivity;
import co.wm21.https.databinding.LayoutItemPremiumShopBinding;
import co.wm21.https.model.ShopsModel;

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.viewHolder> implements Filterable {
    ArrayList<ShopsModel> shopsModelsList;
    ArrayList<ShopsModel> allShops;
    Context context;
    LayoutItemPremiumShopBinding binding;
    @LayoutRes
    int layout;

    public ShopsAdapter(ArrayList<ShopsModel> shopsModelsList, Context context, int layout) {
        this.shopsModelsList = shopsModelsList;
        this.allShops=new ArrayList<>(shopsModelsList);
        this.context = context;
        this.layout = layout;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layout, parent, false);
        return new viewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        ShopsModel shopsModel = shopsModelsList.get(position);
        ///shop/img/shop/
        Picasso.get().load(ConstantValues.web_url + "shop/img/shop/" + shopsModel.getImg()).into(holder.imageView);
        Picasso.get().load(ConstantValues.web_url + "shop/img/shop/" + shopsModel.getPhoto()).into(holder.shopCoverPhoto);
        holder.shopName.setText(shopsModel.getName());
        holder.shopType.setText(shopsModel.getType_name());
        String address = shopsModel.getUnion() + "," + shopsModel.getThana() + "," + shopsModel.getDistrict();
        holder.address.setText(shopsModel.getUnion() + "," + shopsModel.getThana() + "," + shopsModel.getDistrict());
        holder.mobile.setText(shopsModel.getMobile());
        /*holder.productsButton.setOnClickListener(view -> {
            ShopsProductsFragment shopsProductsFragment = new ShopsProductsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ConstantValues.admin_login.SHOP_ID, Integer.parseInt(shopsModel.getShop_id()));
            bundle.putInt(ConstantValues.admin_login.TYPE_ID, shopsModel.getType_id());
            bundle.putString(ConstantValues.admin_login.MOBILE, shopsModel.getMobile());
            bundle.putString(ConstantValues.admin_login.SHOP_TYPE, shopsModel.getType_name());
            bundle.putString(ConstantValues.admin_login.IMAGE, shopsModel.getImg());
            bundle.putString(ConstantValues.admin_login.PHOTO, shopsModel.getPhoto());
            bundle.putString(ConstantValues.admin_login.ADDRESS, address);
            shopsProductsFragment.setArguments(bundle);

            switchFragment(shopsProductsFragment, "ShopsProductsFragment");

        });*/


    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(((MainActivity) context).binding.fragmentContainer.getId(), fragment, tag).addToBackStack(tag).commit();
    }

    @Override
    public int getItemCount() {
        return shopsModelsList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ShopsModel> filterList = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filterList.addAll(allShops);
            } else {
                for (ShopsModel shop : allShops) {
                    if (shop.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filterList.add(shop);

                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            shopsModelsList.clear();
            shopsModelsList.addAll((Collection<? extends ShopsModel>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public class viewHolder extends RecyclerView.ViewHolder {

        TextView shopName, shopType, address, mobile;
        ImageView imageView, shopCoverPhoto;
        RelativeLayout productsButton;

        public viewHolder(@NonNull LayoutItemPremiumShopBinding itemView) {
            super(itemView.getRoot());
            shopName = itemView.shopName;
            shopType = itemView.shopType;
            address = itemView.address;
            mobile = itemView.mobileNumber;
            imageView = itemView.image;
            shopCoverPhoto = itemView.shopCoverPhoto;
            productsButton = itemView.productsButton;
        }
    }


}
