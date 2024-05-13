package com.wm21ltd.wm21.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnBottomReachedListener;
import com.wm21ltd.wm21.interfaces.SearchSmsCallListener;
import com.wm21ltd.wm21.networks.Models.BrandAmbassadorListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class BrandAmbassadorAdapter extends RecyclerView.Adapter<BrandAmbassadorAdapter.MyViewHolder> {

    private List<BrandAmbassadorListModel> brandAmbassadorList;
    Context mContext;
    private SearchSmsCallListener searchSmsCallListener;
    private OnBottomReachedListener onBottomReachedListener;

    public BrandAmbassadorAdapter(Context mContext, List<BrandAmbassadorListModel> brandAmbassadorList
            , SearchSmsCallListener searchSmsCallListener) {
        this.mContext = mContext;
        this.brandAmbassadorList = brandAmbassadorList;
        this.searchSmsCallListener = searchSmsCallListener;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.brand_ambassador_id)
        TextView textViewAmassadorID;
        @BindView(R.id.brand_ambassador_name)
        TextView textViewUserName;
        @BindView(R.id.brand_ambassador_rank)
        TextView textViewUserRank;
        @BindView(R.id.brand_ambassador_image)
        CircleImageView AmbassadorImage;
        @BindView(R.id.brand_ambassador_call)
        ImageView callToAmassador;
        @BindView(R.id.smsToAmbassador)
        ImageView smsToAmbassador;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public BrandAmbassadorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_ambassador_layout, parent, false);
        return new BrandAmbassadorAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BrandAmbassadorAdapter.MyViewHolder holder, final int position) {

        if (position == brandAmbassadorList.size() -1){
            onBottomReachedListener.onBottomReached(position);
        }

        final BrandAmbassadorListModel mModel = brandAmbassadorList.get(position);
        holder.textViewAmassadorID.setText("ID: " + mModel.getID());
        holder.textViewUserName.setText("Name: " + mModel.getName());
        holder.textViewUserRank.setText("Rank: " + mModel.getRank());
        String userImage = ConstantValues.URL + mModel.getImage();


        Glide.with(mContext).load(userImage).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)).into(holder.AmbassadorImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mModel.getMobile().isEmpty()){

                    Toast.makeText(mContext, "Mobile Number not provided!", Toast.LENGTH_SHORT).show();
                }
                else{
                    searchSmsCallListener.makeCall(mModel.getMobile());
                }
            }
        });



        holder.smsToAmbassador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(Intent.ACTION_VIEW);
                I.setData(Uri.parse("smsto:"+mModel.getMobile()));
                // I.putExtra(Intent.EXTRA_STREAM,contactListModel.getContactNumber().toString());
                I.putExtra("sms_body", "");

                if (I.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(I);
                    Log.e("Sms_Send", "SMS SEND!");
                } else {
                    Log.e("Sms_SEND", "SMS FAIL!");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return brandAmbassadorList.size();
    }
}