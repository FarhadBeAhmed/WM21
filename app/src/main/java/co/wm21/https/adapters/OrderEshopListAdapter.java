package co.wm21.https.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wm21.https.FHelper.networks.Models.EshopListModel;
import co.wm21.https.R;
import co.wm21.https.fragments.manageOrder.OrderFragment;
import co.wm21.https.model.OrderEshopModel;

public class OrderEshopListAdapter extends RecyclerView.Adapter<OrderEshopListAdapter.EshopViewHolder> {
    Context context;
    ArrayList<EshopListModel> orderEshopList;

    private int checkPosition = 0;

    public OrderEshopListAdapter(Context context, ArrayList<EshopListModel> orderEshopList) {
        this.context = context;
        this.orderEshopList = orderEshopList;
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

    @Override
    public int getItemCount() {
        return orderEshopList.size();
    }


    class EshopViewHolder extends RecyclerView.ViewHolder {
        ImageView selectImage;
        TextView shopName, mobileNumber, address;

        public EshopViewHolder(@NonNull View itemView) {
            super(itemView);

            selectImage = itemView.findViewById(R.id.selectImg);
            shopName = itemView.findViewById(R.id.sName);
            mobileNumber = itemView.findViewById(R.id.sShopNumber);
            address = itemView.findViewById(R.id.sAddress);


        }

        void bind(EshopListModel eshopModel) {
            if (checkPosition == -1) {
                selectImage.setVisibility(View.GONE);
            } else {
                if (checkPosition == getAbsoluteAdapterPosition()) {
                    selectImage.setVisibility(View.VISIBLE);
                } else {
                    selectImage.setVisibility(View.GONE);
                }
            }
            shopName.setText(eshopModel.getName());
            mobileNumber.setText(eshopModel.getMobile());
            address.setText(eshopModel.getAddress());
            itemView.setOnClickListener(view -> {
                ArrayList<EshopListModel> selectedEshop = new ArrayList<>();
                ArrayList<EshopListModel> NonSelectedEshop = new ArrayList<>();

                eshopModel.setSelected(true);
                selectImage.setVisibility(View.VISIBLE);
                // removeUnSelectedItem();

                for (EshopListModel eshopModels : orderEshopList) {
                    if (eshopModels.isSelected()) {
                        selectedEshop.add(eshopModels);
                        OrderFragment.selectHoise = true;
                        OrderFragment.eshopID = orderEshopList.get(getAbsoluteAdapterPosition()).getId();

                    }
                }

                orderEshopList.clear();
                orderEshopList.addAll(selectedEshop);
                notifyDataSetChanged();

                if (checkPosition != getAbsoluteAdapterPosition()) {
                    notifyItemChanged(checkPosition);
                    //checkPosition=getAbsoluteAdapterPosition();
                    checkPosition = 0;

                }

            });

        }

    }

    private void removeUnSelectedItem() {
        ArrayList<EshopListModel> orderEshopModels = new ArrayList<>();
        for (EshopListModel eshopModel : orderEshopList) {
            if (eshopModel.isSelected()) {
                orderEshopModels.add(eshopModel);
            }
        }

        orderEshopList.clear();
        orderEshopList.addAll(orderEshopModels);
        notifyDataSetChanged();
    }


    public EshopListModel getSelected() {
        if (checkPosition != -1) {
            return orderEshopList.get(checkPosition);
        }
        return null;
    }

}
