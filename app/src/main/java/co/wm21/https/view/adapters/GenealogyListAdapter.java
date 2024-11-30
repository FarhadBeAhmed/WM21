package co.wm21.https.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.genealogy.GenealogyListData;
import co.wm21.https.databinding.GenealogyListSingleItemBinding;

public class GenealogyListAdapter extends RecyclerView.Adapter<GenealogyListAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(GenealogyListData item);
    }

    private List<GenealogyListData> iList;
    private Context context;
    private OnItemClickListener listener;

    GenealogyListSingleItemBinding binding;

    public GenealogyListAdapter(List<GenealogyListData> iList, Context context, OnItemClickListener listener) {
        this.iList = iList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GenealogyListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = GenealogyListSingleItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new GenealogyListAdapter.MyViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GenealogyListAdapter.MyViewHolder holder, int position) {
        GenealogyListData iModel = iList.get(position);

        holder.userIdText.setText(iModel.getUserId());
        holder.userNameText.setText(iModel.getUserName());
        if (iModel.getPosition().equals("1")){
            holder.posIdText.setText("Position: Left");

        }
        if (iModel.getPosition().equals("2")){
            holder.posIdText.setText("Position: Right");

        }
        holder.rpText.setText("RP: " + iModel.getRp().toString());
        holder.leftPointText.setText("Point: " + iModel.getLeftPoint());
        holder.leftMemberText.setText("Member: " + iModel.getLeftMember());
        holder.rightMemberText.setText("Point: " + iModel.getRightMember());
        holder.rightPointText.setText("Member: " + iModel.getRightPoint());

        // Bind the click listener
        holder.bind(iModel, listener);
    }

    @Override
    public int getItemCount() {
        return iList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout fullItemLayout;
        TextView posIdText;
        TextView userIdText;
        TextView userNameText;
        TextView rpText;
        TextView leftMemberText;
        TextView leftPointText;
        TextView rightMemberText;
        TextView rightPointText;
        AppCompatButton treeIDButton;

        public MyViewHolder(@NonNull GenealogyListSingleItemBinding itemView) {
            super(itemView.getRoot());
            posIdText = itemView.posId;
            fullItemLayout = itemView.fullItemId;
            userIdText = itemView.userId;
            userNameText = itemView.userNameId;
            rpText = itemView.rpId;
            leftMemberText = itemView.leftNenberId;
            rightMemberText = itemView.rightMemberId;
            rightPointText = itemView.rightPointId;
            leftPointText = itemView.leftPointId;
            treeIDButton = itemView.treeId;
        }

        public void bind(GenealogyListData item, OnItemClickListener listener) {
            fullItemLayout.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}
