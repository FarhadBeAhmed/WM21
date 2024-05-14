package co.wm21.https.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.SearchDataModel;
import co.wm21.https.R;
import co.wm21.https.interfaces.RecycleViewItemClickListener;
import co.wm21.https.interfaces.SearchSmsCallListener;
import de.hdodenhof.circleimageview.CircleImageView;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private List<SearchDataModel> searchDataList;
    Context mContext;
    private SearchSmsCallListener searchSmsCallListener;
    private int formType;
    private RecycleViewItemClickListener itemClickListener;

    public SearchAdapter(Context mContext, List<SearchDataModel> searchDataList
            , int formType, SearchSmsCallListener searchSmsCallListener, RecycleViewItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.searchDataList = searchDataList;
        this.searchSmsCallListener = searchSmsCallListener;
        this.formType = formType;
        this.itemClickListener = itemClickListener;

    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView textViewUserID;

        TextView textViewUserName;
        TextView textViewUserRank;
        CircleImageView userImage;
        ImageView callToMember;
        ImageView smsToMember;
        LinearLayout userInfo;
        MyViewHolder(View view) {
            super(view);
            userInfo= view.findViewById(R.id.search_user_info);
            smsToMember= view.findViewById(R.id.search_user_sms);
            callToMember= view.findViewById(R.id.search_user_call);
            userImage= view.findViewById(R.id.search_user_image);
            textViewUserRank= view.findViewById(R.id.search_user_rank);
            textViewUserName= view.findViewById(R.id.search_user_name);
            textViewUserID= view.findViewById(R.id.search_user_id);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final SearchDataModel mModel = searchDataList.get(position);
        holder.textViewUserID.setText("ID: " + mModel.getId());
        holder.textViewUserName.setText("Name: " + mModel.getName());
        holder.textViewUserRank.setText("Rank: " + mModel.getRank());
        String userImage = ConstantValues.web_url + mModel.getImage();


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