package co.wm21.https.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import co.wm21.https.R;
import co.wm21.https.databinding.RowAccountIncomeBinding;
import co.wm21.https.model.IncomeBalaceReportDataListModel;

public class AccountIncomeAdapter extends RecyclerView.Adapter<AccountIncomeAdapter.MyViewHolder> {

    private List<IncomeBalaceReportDataListModel> iList;
    private Context context;
    private int type;
    RowAccountIncomeBinding binding;

    public AccountIncomeAdapter(List<IncomeBalaceReportDataListModel> iList, Context context, int type) {
        this.iList = iList;
        this.context = context;
        this.type = type;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowAccountIncomeBinding.inflate(LayoutInflater.from(context), parent, false);
      //  View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_account_income, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        IncomeBalaceReportDataListModel iModel = iList.get(position);
        holder.textViewName.setText(iModel.getTitle());//.replace("_", " ")
        holder.textViewBalance.setText(iModel.getMoney());

        if (iModel.getBold() != null){
            if (iModel.getBold().equals("1")) {
                holder.linearLayoutFullRow.setBackgroundColor(context.getResources().getColor(R.color.primary_red));
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

        holder.itemView.setOnClickListener(v -> {
            if (!iModel.getBold().equals("1")){
                  /*  Intent intent = new Intent(context, IncomeDetailsActivity.class);
                    intent.putExtra("title",iModel.getTitle().replace("_", " "));
                    intent.putExtra("type",String.valueOf(type));
                    context.startActivity(intent);*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return iList.size();
    }

    @SuppressLint("NonConstantResourceId")
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewBalance;
        View viewMiddle;
        View viewBottom;
        LinearLayout linearLayoutFullRow;

        public MyViewHolder(@NonNull RowAccountIncomeBinding itemView) {
            super(itemView.getRoot());
            textViewName=itemView.txtRowAccountIncomeName;
            textViewBalance=itemView.txtRowAccountIncomeBalance;
            viewMiddle=itemView.viewRowAccountIncome;
            viewBottom=itemView.viewRowAccountIncomeBottom;
            linearLayoutFullRow=itemView.linearLayoutAccountIncome;

        }
    }
}
