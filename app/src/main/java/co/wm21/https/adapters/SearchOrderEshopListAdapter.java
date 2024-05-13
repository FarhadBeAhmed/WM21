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

import co.wm21.https.R;
import co.wm21.https.model.OrderEshopModel;

public class OrderEshopListAdapter extends RecyclerView.Adapter<OrderEshopListAdapter.EshopViewHolder> {
    Context context;
    ArrayList<OrderEshopModel>orderEshopList;
    private int checkPosition=0;
    public static boolean selectHoise=false;

    public OrderEshopListAdapter(Context context, ArrayList<OrderEshopModel> orderEshopList) {
        this.context = context;
        this.orderEshopList = orderEshopList;
    }

    void setOrderEshopList(ArrayList<OrderEshopModel> orderEshopList){
        this.orderEshopList=new ArrayList<>();
        this.orderEshopList=orderEshopList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EshopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EshopViewHolder(LayoutInflater.from(context).inflate(R.layout.item_search_shop_single_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull EshopViewHolder holder, int position) {
        OrderEshopModel eshopModel=orderEshopList.get(position);
        holder.bind(eshopModel);

    }

    @Override
    public int getItemCount() {
        return orderEshopList.size();
    }


    class EshopViewHolder extends RecyclerView.ViewHolder{
        ImageView selectImage;
        TextView shopName,mobileNumber,address;

        public EshopViewHolder(@NonNull View itemView) {
            super(itemView);

            selectImage=itemView.findViewById(R.id.selectImg);
            shopName=itemView.findViewById(R.id.sName);
            mobileNumber=itemView.findViewById(R.id.sShopNumber);
            address=itemView.findViewById(R.id.sAddress);


        }

        void bind(OrderEshopModel eshopModel){
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
            itemView.setOnClickListener(view -> {
                eshopModel.setSelected(true);
                selectImage.setVisibility(View.VISIBLE);
               // removeUnSelectedItem();
                ArrayList<OrderEshopModel> orderEshopModels = new ArrayList<>();
                for (OrderEshopModel eshopModels:orderEshopList){
                    if(eshopModels.isSelected()){
                        orderEshopModels.add(eshopModels);
                        selectHoise=true;
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

        }

    }
    private void removeUnSelectedItem(){
        ArrayList<OrderEshopModel> orderEshopModels = new ArrayList<>();
        for (OrderEshopModel eshopModel:orderEshopList){
            if(eshopModel.isSelected()){
                orderEshopModels.add(eshopModel);
            }
        }

        orderEshopList.clear();
        orderEshopList.addAll(orderEshopModels);
        notifyDataSetChanged();
    }


    public OrderEshopModel getSelected(){
        if (checkPosition!=-1){
            return orderEshopList.get(checkPosition);
        }
        return null;
    }

}
