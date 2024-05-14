package co.wm21.https.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import co.wm21.https.FHelper.networks.Models.EshopListModel;
import co.wm21.https.R;
import co.wm21.https.activities.SearchShopActivity;
import co.wm21.https.adapters.product.ProductAdapter;
import co.wm21.https.model.OrderEshopModel;

public class SearchOrderEshopListAdapter extends RecyclerView.Adapter<SearchOrderEshopListAdapter.EshopViewHolder> implements Filterable {
    Context context;
    ArrayList<EshopListModel> orderEshopList;
    public ItemClickListener listener;
    private int checkPosition = 0;

    ArrayList<EshopListModel> allShops;

    public SearchOrderEshopListAdapter(Context context, ArrayList<EshopListModel> orderEshopList) {
        this.context = context;
        this.orderEshopList = orderEshopList;
        this.allShops =orderEshopList;
    }

    void setOrderEshopList(ArrayList<EshopListModel> orderEshopList) {
        this.orderEshopList = new ArrayList<>();
        this.orderEshopList = orderEshopList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EshopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EshopViewHolder(LayoutInflater.from(context).inflate(R.layout.item_search_shop_single_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EshopViewHolder holder, int position) {
        EshopListModel eshopModel = orderEshopList.get(position);
        holder.bind(eshopModel);

    }


    public void setOnClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public SearchOrderEshopListAdapter addOnClickListener(ItemClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public int getItemCount() {
        return orderEshopList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<EshopListModel> filterList = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filterList.addAll(allShops);
            } else {
                for (EshopListModel shop : allShops) {
                    if (shop.getName().toLowerCase().contains(charSequence.toString().toLowerCase())||shop.getMobile().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filterList.add(shop);

                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            orderEshopList.clear();
            orderEshopList.addAll((Collection<? extends EshopListModel>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    class EshopViewHolder extends RecyclerView.ViewHolder {
        ImageView selectImage;
        TextView shopName, mobileNumber, address;

        public EshopViewHolder(@NonNull View itemView) {
            super(itemView);

            selectImage = itemView.findViewById(R.id.selectImg);
            shopName = itemView.findViewById(R.id.sName);
            mobileNumber = itemView.findViewById(R.id.sShopNumber);
            address = itemView.findViewById(R.id.sAddress);
            if (listener != null)
                itemView.setOnClickListener(v -> listener.OnClick(v, getAbsoluteAdapterPosition()));


        }

        void bind(EshopListModel eshopModel) {
            if (checkPosition==-1){
                selectImage.setVisibility(View.GONE);
            }else {
                if (checkPosition== getAbsoluteAdapterPosition()){
                    selectImage.setVisibility(View.VISIBLE);
                }else {
                    selectImage.setVisibility(View.GONE);
                }
            }
            shopName.setText(eshopModel.getName().toString());
            mobileNumber.setText(eshopModel.getMobile().toString());
            address.setText(eshopModel.getAddress().toString());

          /*  itemView.setOnClickListener(view -> {
                ((SearchShopActivity)context).onBackPressed();
            });


            itemView.setOnClickListener(view -> {
                eshopModel.setSelected(true);
                selectImage.setVisibility(View.VISIBLE);
                ArrayList<EshopListModel> orderEshopModels = new ArrayList<>();
                for (EshopListModel eshopModels:orderEshopList){
                    if(eshopModels.isSelected()){
                        orderEshopModels.add(eshopModels);
                       // selectHoise=true;
                    }
                }

                orderEshopList.clear();
                orderEshopList.addAll(orderEshopModels);
                notifyDataSetChanged();

                if( checkPosition!=getAbsoluteAdapterPosition()){
                    notifyItemChanged(checkPosition);
                    //checkPosition=getAbsoluteAdapterPosition();
                    checkPosition=0;

                }

            });
*/
        }
    }
}
