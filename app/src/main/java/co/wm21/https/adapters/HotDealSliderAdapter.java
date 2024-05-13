package https.macuva.com.macuva.Adapter.uddRecycler.ItemMenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import https.macuva.com.R;
import https.macuva.com.macuva.ConstantValues;
import https.macuva.com.macuva.LoadImage;
import https.macuva.com.macuva.Main.ProductDetailsActivity;

public class HotDealSliderAdapter extends SliderViewAdapter<HotDealSliderAdapter.HotDealViewHolder> {
    Context context;
    public List<ItemMenuView> items;
    public List<ItemMenuView> allItems;
    private final LayoutInflater mInflater;
    @LayoutRes
    int itemRes;
    private ItemHotDealAdapter.Item mClick;
    Date date1,date2;
    long diff;

    public HotDealSliderAdapter(Context context, List<ItemMenuView> items, int itemRes) {
        this.context = context;
        this.items = items;
        this.itemRes = itemRes;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public HotDealSliderAdapter.HotDealViewHolder onCreateViewHolder(ViewGroup parent) {
        return new HotDealViewHolder(mInflater.inflate(itemRes, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(HotDealSliderAdapter.HotDealViewHolder holder, int position) {

        ItemMenuView imv = items.get(position);
        new LoadImage(holder.imageView).execute(ConstantValues.getFileNameAsHost("image", "product", "small", imv.getImageResource()));
        holder.Title.setText(imv.getTitle());
        holder.Taka.setText("TK " + imv.getCash());
        holder.cashBAck.setText("CB: "+imv.getCashback());
        holder.oldPrice.setText("Tk "+imv.getCashNo());
        holder.offerCashBack.setText("Offer CB: "+imv.getHotDealCB());
        holder.offerBadge.bringToFront();
        double totalCBack= Double.parseDouble(imv.getCashback())+Double.parseDouble(imv.getHotDealCB());
        holder.totalCB.setText("Total CB: "+totalCBack);
        holder.offerBadge.setText("CB: "+totalCBack+"tk");

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String inputString1 = imv.getOfferEndDate();

        Date date = new Date();
        String inputString2 =myFormat.format(date);
        try {
            date1 = myFormat.parse(inputString1);
            date2 = myFormat.parse(inputString2);
            diff = date1.getTime() - date2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String leftDay= String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        double ld= Double.parseDouble(leftDay);
        if (ld<0){
            holder.dayLeft.setText("Expired");
        }else {
            holder.dayLeft.setText(leftDay);
        }




        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, ProductDetailsActivity.class);
                    intent.putExtra(https.macuva.com.ConstantValues.Product.PRODUCT_DETAILS_PARCELABLE, items.get(position));
                    context.startActivity(intent);
            }
        });


    }

    @Override
    public int getCount() {
        return items.size();
    }

    public class HotDealViewHolder extends ViewHolder {
        public ImageView imageView;
        public TextView Title, Taka, cashBAck,offerCashBack,totalCB,oldPrice,dayLeft;
        TextView offerBadge;
        LinearLayout button;

        public HotDealViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.hotDealImgId);
            Title = itemView.findViewById(R.id.prNameId);
            Taka = itemView.findViewById(R.id.prPariceId);
            cashBAck= itemView.findViewById(R.id.prCashbackId);
            offerCashBack= itemView.findViewById(R.id.prOfferCashBackId);
            totalCB= itemView.findViewById(R.id.prTotalCBID);
            oldPrice= itemView.findViewById(R.id.prOldPrireId);
            offerBadge= itemView.findViewById(R.id.badgeTextId);
            dayLeft= itemView.findViewById(R.id.dayLeftId);
            button= itemView.findViewById(R.id.ProductIdBtn);

        }
    }


}
