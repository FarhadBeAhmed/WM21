package co.wm21.https.view.adapters.item_menu;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wm21.https.R;
import co.wm21.https.view.adapters.ItemClickListener;

public class EarningMenuAdapter extends RecyclerView.Adapter {

    ArrayList<ItemMenuView> mList;
    LayoutInflater mInflater;
    ItemClickListener mListener;
    @LayoutRes
    int layout;
    Context context;
    int offerLayout=0;

    public EarningMenuAdapter(Context context, ArrayList<ItemMenuView> itemMenuViews, @LayoutRes int layout) {
        mList = itemMenuViews;
        mInflater = LayoutInflater.from(context);
        this.layout = layout;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (offerLayout==0)
        return new ItemMenuHolder(mInflater.inflate(layout, parent, false));
        else return new offerItemMenuHolder(mInflater.inflate(layout, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemMenuView item = mList.get(position);
        if (holder.getClass()==ItemMenuHolder.class){
            ((ItemMenuHolder)holder).textView.setText(item.getTitle());
            ((ItemMenuHolder)holder).imageView.setImageDrawable(item.getImage() == null ? null : item.getImage());

            if (layout == R.layout.layout_item_community_work) {
                ((ItemMenuHolder)holder).textView.setTextColor(Color.parseColor(item.getColor()));
                ((ItemMenuHolder)holder).imageView.setColorFilter(Color.parseColor(item.getColor()));

            } else if (item.getColor() != null) {
                ((ItemMenuHolder)holder).cardItem.setCardBackgroundColor(Color.parseColor(item.getColor()));
            }else if (layout == R.layout.layout_item_digital_shop_service){
                ((ItemMenuHolder)holder).cardItem.setCardBackgroundColor(Color.parseColor(item.getColor()));
                ((ItemMenuHolder)holder).textView.setTextColor(Color.parseColor("#000000"));
            }


          ((ItemMenuHolder) holder).cardItem.setOnClickListener(view -> {
             // Toast.makeText(context, "Position: " + position, Toast.LENGTH_SHORT).show();
              switch (position) {
                  case 1:
                  case 2:
                  case 3:
                  case 4:
                  case 5:
                  case 6:
                  case 7:

                  case 8:
                      AlertDialog.Builder builder = new AlertDialog.Builder(context);
                      ViewGroup viewGroup = view.findViewById(android.R.id.content);
                      View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_under_progress, viewGroup, false);
                      RelativeLayout ok = dialogView.findViewById(R.id.okBtnId);
                      TextView msg = dialogView.findViewById(R.id.textMsg);
                      msg.setText("");
                      builder.setView(dialogView);
                      AlertDialog alertDialog = builder.create();
                      alertDialog.show();
                      ok.setOnClickListener(view1 -> {
                          alertDialog.dismiss();

                      });

                      break;
                  default:
              }
          });

        }
        if (holder.getClass() == offerItemMenuHolder.class) {
            ((offerItemMenuHolder)holder).imageView.setImageDrawable(item.getImage() == null ? null : item.getImage());
            ((offerItemMenuHolder)holder).textView.setText(item.getTitle());
            ((offerItemMenuHolder)holder).subTitleView.setText(item.getSubTitle());

        }



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public EarningMenuAdapter addOnClickListener(ItemClickListener mListener) {
        this.mListener = mListener;
        return this;
    }

    public void setOnClickListener(ItemClickListener mListener) {
        this.mListener = mListener;
    }

    class ItemMenuHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        CardView cardItem;

        public ItemMenuHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.category_name);
            imageView = itemView.findViewById(R.id.category_icon);
            cardItem = itemView.findViewById(R.id.card_item);
            if (mListener != null)
                itemView.setOnClickListener(view -> mListener.OnClick(view, getAbsoluteAdapterPosition()));
        }
    }
    class offerItemMenuHolder extends RecyclerView.ViewHolder {
        TextView textView,subTitleView;
        ImageView imageView;
        CardView cardItem;

        public offerItemMenuHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.category_name);
            imageView = itemView.findViewById(R.id.category_icon);
            cardItem = itemView.findViewById(R.id.card_item);
            subTitleView = itemView.findViewById(R.id.subcategory_name);
            if (mListener != null)
                itemView.setOnClickListener(view -> mListener.OnClick(view, getAbsoluteAdapterPosition()));
        }
    }


}
