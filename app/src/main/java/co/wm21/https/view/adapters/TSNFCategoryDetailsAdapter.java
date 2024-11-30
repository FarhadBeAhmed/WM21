package co.wm21.https.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import co.wm21.https.FHelper.networks.Models.TSNFCategoryDetailsListModel;
import co.wm21.https.R;
import co.wm21.https.presenter.interfaces.OnBottomReachedListener;
import co.wm21.https.presenter.interfaces.OnTSNFApplyClickListener;

public class TSNFCategoryDetailsAdapter extends RecyclerView.Adapter<TSNFCategoryDetailsAdapter.TSNFCategoryDetailsViewHolder> {

    private Context context;
    private List<TSNFCategoryDetailsListModel> tsnfCategoryDetailsList;
    private OnBottomReachedListener onBottomReachedListener;
    private OnTSNFApplyClickListener onTSNFApplyClickListener;
    private String type;

    public TSNFCategoryDetailsAdapter(Context context, String type, List<TSNFCategoryDetailsListModel> tsnfCategoryDetailsList, OnTSNFApplyClickListener onTSNFApplyClickListener) {
        this.context = context;
        this.tsnfCategoryDetailsList = tsnfCategoryDetailsList;
        this.onTSNFApplyClickListener = onTSNFApplyClickListener;
        this.type = type;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }


    @NonNull
    @Override
    public TSNFCategoryDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.tsnf_category_details_layout,parent,false);
        return new TSNFCategoryDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder( TSNFCategoryDetailsViewHolder holder, int position) {


        if (!type.equals("2")){

            if (position == tsnfCategoryDetailsList.size() -1){
                onBottomReachedListener.onBottomReached(position);
            }
        }

        final TSNFCategoryDetailsListModel tsnfCategoryDetailsListModel = tsnfCategoryDetailsList.get(position);

        boolean expanded = tsnfCategoryDetailsListModel.isExpanded();
        holder.expandableLayout.setVisibility(expanded ? View.VISIBLE : View.GONE);

        if (type.equals("1")){
            holder.tsnfApply.setVisibility(View.VISIBLE);
        }
        else {
            holder.tsnfApply.setVisibility(View.GONE);
        }
        if (type.equals("3")){
            holder.tsnfSupport.setVisibility(View.GONE);
            holder.tsnfVanue.setVisibility(View.GONE);
            holder.tsnfFee.setVisibility(View.GONE);
            holder.tsnfDetailsUi.setVisibility(View.GONE);
            holder.tsnfLocation.setVisibility(View.GONE);
        }

        holder.tsnfTitle.setText(tsnfCategoryDetailsListModel.getTitle());
        holder.tsnfDetails.setText(tsnfCategoryDetailsListModel.getDetails());
        holder.tsnfSupport.setText("Support : "+tsnfCategoryDetailsListModel.getSupport());
        holder.tsnfVanue.setText("Venue : "+tsnfCategoryDetailsListModel.getVanue());
        holder.tsnfLastDate.setText(tsnfCategoryDetailsListModel.getLastDate());
        holder.tsnfFee.setText("Fee : "+tsnfCategoryDetailsListModel.getFee()+" ৳");
        holder.tsnfLocation.setText(tsnfCategoryDetailsListModel.getThana()+", "+tsnfCategoryDetailsListModel.getDistrict()+", "+tsnfCategoryDetailsListModel.getDivision()+".");

        holder.tsnfApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTSNFApplyClickListener.tsnfApplyInfo(tsnfCategoryDetailsListModel.getID(), tsnfCategoryDetailsListModel.getCategory());
            }
        });

        holder.titleLayout.setOnClickListener(v -> {
            boolean expand = tsnfCategoryDetailsListModel.isExpanded();
            tsnfCategoryDetailsListModel.setExpanded(!expand);
            notifyItemChanged(position);
        });

    }

    @Override
    public int getItemCount() {
        return tsnfCategoryDetailsList.size();
    }

    public class TSNFCategoryDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView tsnfTitle, tsnfDetails, tsnfSupport, tsnfVanue, tsnfLastDate, tsnfFee, tsnfDetailsUi, tsnfLocation;
        private Button tsnfApply;
        private ConstraintLayout expandableLayout, titleLayout;
        public TSNFCategoryDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tsnfTitle = itemView.findViewById(R.id.tsnf_title);
            tsnfDetails = itemView.findViewById(R.id.tsnf_category_details);
            tsnfSupport = itemView.findViewById(R.id.tsnf_support);
            tsnfVanue = itemView.findViewById(R.id.tsnf_vanue);
            tsnfLastDate = itemView.findViewById(R.id.tsnf_last_date);
            tsnfFee = itemView.findViewById(R.id.tsnf_fee);
            tsnfApply = itemView.findViewById(R.id.tsnf_apply);
            tsnfDetailsUi = itemView.findViewById(R.id.tsnf_details_ui);
            tsnfLocation = itemView.findViewById(R.id.tsnf_location);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            titleLayout = itemView.findViewById(R.id.titleLayout);
        }
    }
}
