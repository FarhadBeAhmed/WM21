package com.wm21ltd.wm21.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.activities.IncomeDetailsActivity;
import com.wm21ltd.wm21.networks.Models.IncomeBalaceReportDataListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountIncomeAdapter extends RecyclerView.Adapter<AccountIncomeAdapter.MyViewHolder> {

    private List<IncomeBalaceReportDataListModel> iList;
    private Context context;
    private int type;

    public AccountIncomeAdapter(List<IncomeBalaceReportDataListModel> iList, Context context,int type) {
        this.iList = iList;
        this.context = context;
        this.type = type;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_rowAccountIncome_Name)
        TextView textViewName;
        @BindView(R.id.txt_rowAccountIncome_Balance)
        TextView textViewBalance;
        @BindView(R.id.view_rowAccountIncome)
        View viewMiddle;
        @BindView(R.id.view_rowAccountIncome_bottom)
        View viewBottom;

        @BindView(R.id.linearLayout_AccountIncome)
        LinearLayout linearLayoutFullRow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_account_income, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        IncomeBalaceReportDataListModel iModel = iList.get(position);
        holder.textViewName.setText(iModel.getTitle());//.replace("_", " ")
        holder.textViewBalance.setText(iModel.getMoney());

        if (iModel.getBold() != null){
            if (iModel.getBold().equals("1")) {
                holder.linearLayoutFullRow.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                holder.textViewName.setTextColor(context.getResources().getColor(R.color.white));
                holder.textViewBalance.setTextColor(context.getResources().getColor(R.color.white));
                holder.viewMiddle.setBackgroundColor(context.getResources().getColor(R.color.white));
                holder.viewBottom.setBackgroundColor(context.getResources().getColor(R.color.white));
            } else {
                holder.linearLayoutFullRow.setBackgroundColor(context.getResources().getColor(R.color.white));
                holder.textViewName.setTextColor(context.getResources().getColor(R.color.black));
                holder.textViewBalance.setTextColor(context.getResources().getColor(R.color.black));
                holder.viewMiddle.setBackgroundColor(context.getResources().getColor(R.color.black));
                holder.viewBottom.setBackgroundColor(context.getResources().getColor(R.color.black));
            }}

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!iModel.getBold().equals("1")){
                        Intent intent = new Intent(context, IncomeDetailsActivity.class);
                        intent.putExtra("title",iModel.getTitle().replace("_", " "));
                        intent.putExtra("type",String.valueOf(type));
                        context.startActivity(intent);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return iList.size();
    }
}
