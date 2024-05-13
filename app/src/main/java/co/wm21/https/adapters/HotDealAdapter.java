package co.wm21.https.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.viewpager2.widget.ViewPager2;

import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import co.wm21.https.R;
import co.wm21.https.adapters.product.ProductView;
import co.wm21.https.fragments.ProductDetailsActivity;
import co.wm21.https.helpers.Constant;

public class HotDealSliderAdapter extends SliderViewAdapter<HotDealSliderAdapter.HotDealViewHolder> {
    Context context;
    public List<ProductView> productList;
    private final LayoutInflater mInflater;
    @LayoutRes
    int itemRes;
    public ItemClickListener listener;
    int pos;
    private ViewPager2 viewPager2;

    public HotDealSliderAdapter(Context context, List<ProductView> productList, int itemRes) {
        this.context = context;
        this.productList = productList;
        this.itemRes = itemRes;
        mInflater = LayoutInflater.from(context);
    }
    public HotDealSliderAdapter(Context context, List<ProductView> productList, int itemRes,ViewPager2 viewPager2) {
        this.context = context;
        this.productList = productList;
        this.viewPager2 = viewPager2;
        this.itemRes = itemRes;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public HotDealViewHolder onCreateViewHolder(ViewGroup parent) {
        return new HotDealViewHolder(mInflater.inflate(itemRes, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(HotDealViewHolder holder, int position) {

        pos = position;
        ProductView product = productList.get(position);
        holder.productName.setText(product.getProductName());
        holder.previousPrice.setText(String.format("৳ %s", product.getPrice()));
        holder.productPrice.setText(String.format("৳ %s", product.getCurrentPrice()));
        holder.productImage.setImageDrawable(product.getImage());
        holder.button.setOnClickListener(view -> {
            context.startActivity(new Intent(context, ProductDetailsActivity.class)
                    .putExtra(Constant.Product.PARCEL, productList.get(position)));

        });

        //Calendar currentDate = Calendar.getInstance();

        long c = Calendar.getInstance().getTimeInMillis();

        Calendar calendar = Calendar.getInstance();

        System.out.println(calendar.getTime());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = simpleDateFormat.format(c);
        try {
            Date date1 = simpleDateFormat.parse(currentDate);
            Date date2 = simpleDateFormat.parse(product.getOffer_date());
            Calendar today = Calendar.getInstance();
            Calendar offerDate = Calendar.getInstance();


            if (date2 != null && date1 != null) {
                today.setTime(date1);
                offerDate.setTime(date2);

                long different = offerDate.getTimeInMillis() - today.getTimeInMillis();
                if (different >= 0) {
                    holder.expiredDate.setVisibility(View.GONE);
                    holder.hasDate.setVisibility(View.VISIBLE);
                    new CountDownTimer(different, 1000) {

                        @Override
                        public void onTick(long l) {
                            ((Activity) context).runOnUiThread(() -> {
                                //change View Data



                                long days = TimeUnit.MILLISECONDS.toDays(l);
                                long hours = (TimeUnit.MILLISECONDS.toHours(l) - TimeUnit.DAYS.toHours(days));
                                long minutes = (TimeUnit.MILLISECONDS.toMinutes(l) - TimeUnit.HOURS.toMinutes(TimeUnit.DAYS.toHours(days) + hours));
                                long seconds = (TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(minutes+TimeUnit.DAYS.toMinutes(days) + TimeUnit.HOURS.toMinutes(hours)));
                                @SuppressLint("DefaultLocale")
                                String timer = String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d", days, hours, minutes, seconds);

                                final String[] horMimSec = timer.split(":");
                                holder.day.setText(horMimSec[0]);
                                holder.hour.setText(horMimSec[1]);
                                holder.min.setText(horMimSec[2]);
                                //holder.sec.setText(horMimSec[3]);


                            });
                        }

                        @Override
                        public void onFinish() {

                        }
                    }.start();

                } else {
                    holder.expiredDate.setVisibility(View.VISIBLE);
                    holder.hasDate.setVisibility(View.GONE);

                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


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
        TextView productName, productPrice, previousPrice, btnAddToCart;
        TextView day, hour, min, sec;
        RelativeLayout button;
        LinearLayout hasDate, expiredDate;

        public HotDealViewHolder(View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.h_productImage);
            productName = itemView.findViewById(R.id.h_productName);
            productPrice = itemView.findViewById(R.id.h_productPrice);
            previousPrice = itemView.findViewById(R.id.h_previousPrice);
            btnAddToCart = itemView.findViewById(R.id.h_addToCart);
            button = itemView.findViewById(R.id.h_prButton);
            day = itemView.findViewById(R.id.daysId);
            hour = itemView.findViewById(R.id.hourId);
            min = itemView.findViewById(R.id.minID);
          //  sec = itemView.findViewById(R.id.secID);
            hasDate = itemView.findViewById(R.id.hasDates);
            expiredDate = itemView.findViewById(R.id.dateExpired);
            previousPrice.setPaintFlags(previousPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if (listener != null) itemView.setOnClickListener(v -> listener.OnClick(v, pos));

        }
    }


}
