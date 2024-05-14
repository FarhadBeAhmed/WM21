package co.wm21.https.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.BrandAmbassadorListModel;
import co.wm21.https.R;
import co.wm21.https.interfaces.OnBottomReachedListener;
import co.wm21.https.interfaces.SearchSmsCallListener;
import de.hdodenhof.circleimageview.CircleImageView;
//import de.hdodenhof.circleimageview.CircleImageView;


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


        TextView textViewAmassadorID;

        TextView textViewUserName;
        TextView textViewUserRank;
        CircleImageView AmbassadorImage;
        ImageView callToAmassador;
        ImageView smsToAmbassador;
        MyViewHolder(View view) {
            super(view);
            smsToAmbassador=view.findViewById(R.id.smsToAmbassador);
            callToAmassador=view.findViewById(R.id.brand_ambassador_call);
            AmbassadorImage=view.findViewById(R.id.brand_ambassador_image);
            textViewUserRank=view.findViewById(R.id.brand_ambassador_rank);
            textViewUserName=view.findViewById(R.id.brand_ambassador_name);
            textViewAmassadorID=view.findViewById(R.id.brand_ambassador_id);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_ambassador_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

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