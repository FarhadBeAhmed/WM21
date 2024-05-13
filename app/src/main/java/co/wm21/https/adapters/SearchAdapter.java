package com.wm21ltd.wm21.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.RecycleViewItemClickListener;
import com.wm21ltd.wm21.interfaces.SearchSmsCallListener;
import com.wm21ltd.wm21.networks.Models.SearchDataModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private List<SearchDataModel> searchDataList;
    Context mContext;
    private SearchSmsCallListener searchSmsCallListener;
    private int formType;
    private RecycleViewItemClickListener itemClickListener;

    public SearchAdapter(Context mContext, List<SearchDataModel> searchDataList
            ,int formType, SearchSmsCallListener searchSmsCallListener, RecycleViewItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.searchDataList = searchDataList;
        this.searchSmsCallListener = searchSmsCallListener;
        this.formType = formType;
        this.itemClickListener = itemClickListener;

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.search_user_id)
        TextView textViewUserID;
        @BindView(R.id.search_user_name)
        TextView textViewUserName;
        @BindView(R.id.search_user_rank)
        TextView textViewUserRank;
        @BindView(R.id.search_user_image)
        CircleImageView userImage;
        @BindView(R.id.search_user_call)
        ImageView callToMember;
        @BindView(R.id.search_user_sms)
        ImageView smsToMember;
        @BindView(R.id.search_user_info)
        LinearLayout userInfo;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_view, parent, false);
        return new SearchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.MyViewHolder holder, final int position) {
        final SearchDataModel mModel = searchDataList.get(position);
        holder.textViewUserID.setText("ID: " + mModel.getId());
        holder.textViewUserName.setText("Name: " + mModel.getName());
        holder.textViewUserRank.setText("Rank: " + mModel.getRank());
        String userImage = ConstantValues.URL + mModel.getImage();


        Glide.with(mContext).load(userImage).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)).into(holder.userImage);

        if (formType == 100){
            holder.smsToMember.setVisibility(View.GONE);
            holder.callToMember.setVisibility(View.GONE);
        }
        else {
            holder.smsToMember.setVisibility(View.VISIBLE);
            holder.callToMember.setVisibility(View.VISIBLE);
        }

        holder.callToMember.setOnClickListener(new View.OnClickListener() {
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

        holder.smsToMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mModel.getMobile().isEmpty()){
                    Toast.makeText(mContext, "Mobile Number not provided!", Toast.LENGTH_SHORT).show();
                }
                else{
                    searchSmsCallListener.makeSms(mModel.getMobile());
                }
            }
        });

        holder.userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formType == 100){
                    itemClickListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchDataList.size();
    }
}