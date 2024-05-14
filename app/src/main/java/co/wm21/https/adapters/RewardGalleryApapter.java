package co.wm21.https.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.RewardGalleryDataListModel;
import co.wm21.https.R;
import co.wm21.https.interfaces.OnBottomReachedListener;

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

        ImageView imageView;

        TextView textViewName;

        TextView textViewRank;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRank=itemView.findViewById(R.id.txt_row_RewardGallery_Rank);
            textViewName=itemView.findViewById(R.id.txt_row_RewardGallery_Name);
            imageView=itemView.findViewById(R.id.img_row_RewardGallery);

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
        Glide.with(context).load(ConstantValues.web_url+"api/"+model.getImage()).apply(new RequestOptions().error(R.mipmap.ic_launcher)
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
