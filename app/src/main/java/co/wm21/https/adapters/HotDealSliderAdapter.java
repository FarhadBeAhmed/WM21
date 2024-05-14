package co.wm21.https.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.viewpager2.widget.ViewPager2;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.R;
import co.wm21.https.adapters.product.ProductView;
import co.wm21.https.activities.ProductDetailsActivity;
import co.wm21.https.helpers.Constant;
import co.wm21.https.helpers.SessionHandler;

public class HotDealSliderAdapter extends SliderViewAdapter<HotDealSliderAdapter.HotDealViewHolder> {
    Context context;
    public List<ProductModel> productList;
    private final LayoutInflater mInflater;
    @LayoutRes
    int itemRes;
    public ItemClickListener listener;
    SessionHandler sessionHandler;
    int pos;
    private ViewPager2 viewPager2;
    ProductModel product;

    private final Handler mHandler = new Handler();
    private final List<HotDealViewHolder> lstHolders= new ArrayList<>();

    private final Runnable updateRemainingTimeRunnable = () -> {
        synchronized (lstHolders) {
            long currentTime = System.currentTimeMillis();
            for (HotDealViewHolder holder : lstHolders) {
                holder.updateTimeRemaining(currentTime);
            }
        }
    };

    public HotDealSliderAdapter(Context context, List<ProductModel> productList, int itemRes) {
        this.context = context;
        this.productList = productList;
        this.itemRes = itemRes;
        mInflater = LayoutInflater.from(context);
        startUpdateTimer();
    }

    public HotDealSliderAdapter(Context context, List<ProductModel> productList, int itemRes, ViewPager2 viewPager2) {
        this.context = context;
        this.productList = productList;
        this.viewPager2 = viewPager2;
        this.itemRes = itemRes;
        mInflater = LayoutInflater.from(context);
    }
    private void startUpdateTimer() {
        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(updateRemainingTimeRunnable);
            }
        }, 1000, 1000);
    }
    @Override
    public float getPageWidth(int position) {
        return (0.49f);
    }

    @Override
    public HotDealViewHolder onCreateViewHolder(ViewGroup parent) {
        return new HotDealViewHolder(mInflater.inflate(itemRes, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(HotDealViewHolder holder, int position) {
        sessionHandler = new SessionHandler(context);
        pos = position;
        product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.previousPrice.setText(String.format("৳ %s", product.getPrice()));
        holder.productPrice.setText(String.format("৳ %s", product.getPrice()));
        if (sessionHandler.isLoggedIn()) {
            holder.rpLayout.setVisibility(View.VISIBLE);
            holder.eshopTV.setVisibility(View.GONE);
            holder.productRPTV.setText(String.format("%s", product.getPoint()));
        } else {
            holder.rpLayout.setVisibility(View.GONE);
            holder.eshopTV.setVisibility(View.VISIBLE);
        }
        holder.shopName.setText("(" + product.getUploadBy() + ")");

        double discount= Double.parseDouble(product.getDiscount());
        double price= Double.parseDouble(product.getPrice());
        if ( discount != 0) {
            holder.offerLayout.setVisibility(View.VISIBLE);
            double dis = Math.round((discount / price) * 100);
            holder.offerPercent.setText(String.format("%s", dis));
        } else {
            holder.offerLayout.setVisibility(View.GONE);
        }
        Picasso.get().load(ConstantValues.URL+"image/product/small/"+product.getImg()).into(holder.productImage);

        //holder.productImage.setImageDrawable(product.getImg());
        holder.button.setOnClickListener(view -> {
           /* context.startActivity(new Intent(context, ProductDetailsActivity.class)
                    .putExtra(Constant.Product.PARCEL, productList.get(position)));
*/
        });

        holder.setData(product);

    }


    @Override
    public int getCount() {
        return productList.size();
    }

    public void setOnClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public HotDealSliderAdapter addOnClickListener(ItemClickListener listener) {
        this.listener = listener;
        return this;
    }


    public class HotDealViewHolder extends ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, previousPrice, btnAddToCart, productRPTV,shopName,eshopTV;
        LinearLayout productRPLayout;
        TextView day, hour, min, sec, offerPercent;
        RelativeLayout button;
        LinearLayout hasDate, expiredDate, offerLayout,rpLayout;
        ProductModel mProduct;


        public void setData(ProductModel item) {
            mProduct = item;
            updateTimeRemaining(System.currentTimeMillis());
        }
        @SuppressLint("SetTextI18n")
        public void updateTimeRemaining(long currentTime){
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date2 = simpleDateFormat.parse(String.valueOf(mProduct.getOfferDate()));
                Calendar offerDate = Calendar.getInstance();
                offerDate.setTime(date2);
                long timeDiff = offerDate.getTimeInMillis() - currentTime;
                if (timeDiff > 0) {
                    long days = TimeUnit.MILLISECONDS.toDays(timeDiff);
                    long hours = (TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.DAYS.toHours(days));
                    long minutes = (TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.DAYS.toHours(days) + hours));
                    long seconds = (TimeUnit.MILLISECONDS.toSeconds(timeDiff) - TimeUnit.MINUTES.toSeconds(minutes + TimeUnit.DAYS.toMinutes(days) + TimeUnit.HOURS.toMinutes(hours)));
                    day.setText(days+"");
                    hour.setText(hours+"");
                    min.setText(minutes+"");
                    sec.setText(seconds+"");
                    expiredDate.setVisibility(View.GONE);
                    hasDate.setVisibility(View.VISIBLE);
                } else {
                    //expiredDate..setText("Expired!!");
                    expiredDate.setVisibility(View.VISIBLE);
                    hasDate.setVisibility(View.GONE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


        public HotDealViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.h_productImage);
            productName = itemView.findViewById(R.id.h_productName);
            productPrice = itemView.findViewById(R.id.h_productPrice);
            previousPrice = itemView.findViewById(R.id.h_previousPrice);
            btnAddToCart = itemView.findViewById(R.id.h_addToCart);
            button = itemView.findViewById(R.id.h_prButton);
            day = itemView.findViewById(R.id.daysId);
            offerPercent = itemView.findViewById(R.id.offerPercent);
            hour = itemView.findViewById(R.id.hourId);
            min = itemView.findViewById(R.id.minID);
              sec = itemView.findViewById(R.id.secID);
            hasDate = itemView.findViewById(R.id.hasDates);
            expiredDate = itemView.findViewById(R.id.dateExpired);
            offerLayout = itemView.findViewById(R.id.offerLayout);
            productRPTV = itemView.findViewById(R.id.productRP);
            productRPLayout = itemView.findViewById(R.id.productRPLayout);
            shopName = itemView.findViewById(R.id.shopNameTV);
            rpLayout = itemView.findViewById(R.id.rpLayout);
            eshopTV = itemView.findViewById(R.id.eshopTV);
            previousPrice.setPaintFlags(previousPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if (listener != null) {
                itemView.setOnClickListener(v -> listener.OnClick(v, pos));
            }

        }
    }


}
