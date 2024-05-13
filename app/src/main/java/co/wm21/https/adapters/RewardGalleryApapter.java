package com.wm21ltd.wm21.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnBottomReachedListener;
import com.wm21ltd.wm21.networks.Models.RewardGalleryDataListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardGalleryApapter extends RecyclerView.Adapter<RewardGalleryApapter.MyViewHolder> {

    private List<RewardGalleryDataListModel> rgList;
    private Context context;

    OnBottomReachedListener onBottomReachedListener;

    public RewardGalleryApapter(List<RewardGalleryDataListModel> rgList, Context context) {
        this.rgList = rgList;
        this.context = context;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
        this.onBottomReachedListener = onBottomReachedListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_row_RewardGallery)
        ImageView imageView;
        @BindView(R.id.txt_row_RewardGallery_Name)
        TextView textViewName;
        @BindView(R.id.txt_row_RewardGallery_Rank)
        TextView textViewRank;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_reward_gallery, viewGroup, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        if (i == rgList.size() - 1) {
            onBottomReachedListener.onBottomReached(i);
        }

        RewardGalleryDataListModel model = rgList.get(i);
        Glide.with(context).load(ConstantValues.URL+"api/"+model.getImage()).apply(new RequestOptions().error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher).fitCenter()).into(myViewHolder.imageView);

        myViewHolder.textViewName.setText(model.getName());
        myViewHolder.textViewRank.setText(model.getRank());
        if (model.getRank().length()>0){
            myViewHolder.textViewRank.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.textViewRank.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return rgList.size();
    }

}
