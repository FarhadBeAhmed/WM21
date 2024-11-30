package co.wm21.https.view.adapters;

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
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.R;
import co.wm21.https.view.activities.ProductDetailsActivity;
import co.wm21.https.helpers.Constant;
import co.wm21.https.helpers.SessionHandler;

public class AllHotDealAdapter extends RecyclerView.Adapter<AllHotDealAdapter.viewHolder> {
    Context context;
    public List<ProductModel> productList;
    private final LayoutInflater mInflater;
    @LayoutRes
    int itemRes;
    public ItemClickListener listener;
    SessionHandler sessionHandler;
    int pos;

    public AllHotDealAdapter(Context context, List<ProductModel> productList, int itemRes) {
        this.context = context;
        this.productList = productList;
        this.itemRes = itemRes;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(mInflater.inflate(itemRes, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        sessionHandler = new SessionHandler(context);
        pos = position;
        ProductModel product = productList.get(position);
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
        if (Integer.parseInt(product.getDiscount()) != 0) {
            holder.offerLayout.setVisibility(View.VISIBLE);
            double dis = Math.round((Double.parseDouble(product.getDiscount()) / Double.parseDouble(product.getPrice())) * 100);
            holder.offerPercent.setText(String.format("%s", dis));
        } else {
            holder.offerLayout.setVisibility(View.GONE);
        }
        //holder.productImage.setImageDrawable(product.getImg());
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
            Date date2 = simpleDateFormat.parse(String.valueOf(product.getOfferDate()));
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
                                long seconds = (TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(minutes + TimeUnit.DAYS.toMinutes(days) + TimeUnit.HOURS.toMinutes(hours)));
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
    public int getItemCount() {

        return productList.size();
    }
    public void setOnClickListener(ItemClickListener listener) {
        this.listener = listener;
    }
    public AllHotDealAdapter addOnClickListener(ItemClickListener listener) {
        this.listener = listener;
        return this;
    }
    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, previousPrice, btnAddToCart, productRPTV,shopName,eshopTV;
        LinearLayout productRPLayout;
        TextView day, hour, min, sec, offerPercent;
        RelativeLayout button;
        LinearLayout hasDate, expiredDate, offerLayout,rpLayout;
        public viewHolder(@NonNull View itemView) {
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
            hasDate = itemView.findViewById(R.id.hasDates);
            expiredDate = itemView.findViewById(R.id.dateExpired);
            offerLayout = itemView.findViewById(R.id.offerLayout);
            productRPTV = itemView.findViewById(R.id.productRP);
            productRPLayout = itemView.findViewById(R.id.productRPLayout);
            rpLayout = itemView.findViewById(R.id.rpLayout);
            eshopTV = itemView.findViewById(R.id.eshopTV);
            shopName = itemView.findViewById(R.id.shopNameTV);
            previousPrice.setPaintFlags(previousPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if (listener != null) itemView.setOnClickListener(v -> listener.OnClick(v, pos));
        }
    }
}
