package co.wm21.https.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.networks.Models.MemberDetailsListModel;
import co.wm21.https.R;
import co.wm21.https.activities.SendSmsActivity;
import co.wm21.https.interfaces.OnBottomReachedListener;
import co.wm21.https.interfaces.OnCustomerCareItemClickListner;

public class MemberDetailsAdapter extends RecyclerView.Adapter<MemberDetailsAdapter.MemberDetailsViewHolder> {
    private Context context;
    private List<MemberDetailsListModel> memberDetailsList;
    private OnBottomReachedListener onBottomReachedListener;
    private boolean multiSelect;
    private ArrayList<MemberDetailsListModel> selectedItems = new ArrayList<>();
    private OnCustomerCareItemClickListner onCustomerCareItemClickListner;


    public MemberDetailsAdapter(Context context, List<MemberDetailsListModel> memberDetailsList, OnCustomerCareItemClickListner onCustomerCareItemClickListner) {
        this.context = context;
        this.memberDetailsList = memberDetailsList;
        this.onCustomerCareItemClickListner = onCustomerCareItemClickListner;
    }

    private ActionMode.Callback actionModeCallbacks = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            multiSelect = true;
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.member_cab_menu, menu);


            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            menu.findItem(R.id.menu_msg).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (selectedItems.size()>0){
                Intent intent = new Intent(context, SendSmsActivity.class);
                intent.putExtra("personList",selectedItems);
                context.startActivity(intent);
                mode.finish();

            }
            else {
                Toast.makeText(context, "Select Members!", Toast.LENGTH_SHORT).show();
            }
            return true;
    }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            multiSelect = false;
            selectedItems.clear();
            notifyDataSetChanged();
        }
    };


    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }

    @NonNull
    @Override
    public MemberDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_member_details, parent, false);
        return new MemberDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberDetailsViewHolder holder, int position) {
        if (position == memberDetailsList.size() -1){
            onBottomReachedListener.onBottomReached(position);
        }
        MemberDetailsListModel memberDetailsListModel = memberDetailsList.get(position);
        holder.memberDetailName.setText(memberDetailsListModel.getName());
        holder.memberDetailId.setText("User ID : "+memberDetailsListModel.getUser());
        holder.memberDetailMobile.setText("Mobile : "+memberDetailsListModel.getMobile());
        holder.update(memberDetailsList.get(position));

        holder.callToMember.setOnClickListener(v -> {
            onCustomerCareItemClickListner.callToCustomerCare(memberDetailsListModel.getMobile());
        });

        holder.smsToMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(Intent.ACTION_VIEW);
                I.setData(Uri.parse("smsto:"+memberDetailsListModel.getMobile()));
                // I.putExtra(Intent.EXTRA_STREAM,contactListModel.getContactNumber().toString());
                I.putExtra("sms_body", "");

                if (I.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(I);
                    Log.e("Sms_Send", "SMS SEND!");
                } else {
                    Log.e("Sms_SEND", "SMS FAIL!");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return memberDetailsList.size();
    }

    public class MemberDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView memberDetailName;
        private TextView memberDetailId;
        private TextView memberDetailMobile;
        private ImageView callToMember,smsToMember;
        private ConstraintLayout memberItem;
        private ImageView checked;

        public MemberDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            memberDetailName = itemView.findViewById(R.id.memberDetailName);
            memberDetailId = itemView.findViewById(R.id.memberDetailId);
            memberDetailMobile = itemView.findViewById(R.id.memberDetailMobile);
            memberItem = itemView.findViewById(R.id.memberItem);
            checked = itemView.findViewById(R.id.checked);
            callToMember = itemView.findViewById(R.id.callToMember);
            smsToMember = itemView.findViewById(R.id.smsToMember);
        }

        void update(final MemberDetailsListModel value) {
            if (selectedItems.contains(value)) {
                memberItem.setBackgroundColor(Color.LTGRAY);
                checked.setVisibility(View.VISIBLE);
            } else {
                memberItem.setBackgroundColor(Color.WHITE);
                checked.setVisibility(View.GONE);
            }
            itemView.setOnLongClickListener(view -> {
                ((AppCompatActivity)view.getContext()).startSupportActionMode(actionModeCallbacks);
                selectItem(value);
                return true;
            });
            itemView.setOnClickListener(view -> selectItem(value));
        }


        void selectItem(MemberDetailsListModel item) {
            if (multiSelect) {
                if (selectedItems.contains(item)) {
                    selectedItems.remove(item);
                    memberItem.setBackgroundColor(Color.WHITE);
                    checked.setVisibility(View.GONE);
                } else {
                    selectedItems.add(item);
                    memberItem.setBackgroundColor(Color.LTGRAY);
                    checked.setVisibility(View.VISIBLE);
                }
            }
        }
    }


}
