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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wm21.https.FHelper.networks.Models.ContactListModel;
import co.wm21.https.R;
import co.wm21.https.interfaces.OnCustomerCareItemClickListner;


public class CustomerCareListAdapter extends RecyclerView.Adapter<CustomerCareListAdapter.ContactListViewHolder> {
    private Context context;
    private ArrayList<ContactListModel> contactList;
    private OnCustomerCareItemClickListner onCustomerCareItemClickListner;

    public CustomerCareListAdapter(Context context, ArrayList<ContactListModel> contactList, OnCustomerCareItemClickListner onCustomerCareItemClickListner) {
        this.context = context;
        this.contactList = contactList;
        this.onCustomerCareItemClickListner = onCustomerCareItemClickListner;
    }

    @NonNull
    @Override
    public ContactListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_care_list_layout, parent, false);
        return new ContactListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListViewHolder holder, final int position) {

        final ContactListModel contactListModel = contactList.get(position);
        //holder.contactImage.setImageResource(contactListModel.getContactImage());
        holder.contactName.setText(contactListModel.getContactName());
        holder.contactNumber.setText(contactListModel.getContactNumber());
        holder.callToCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomerCareItemClickListner.callToCustomerCare(contactListModel.getContactNumber());
            }
        });
        holder.smsToCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(Intent.ACTION_VIEW);
                I.setData(Uri.parse("smsto:"+contactListModel.getContactNumber().toString()));
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
        return contactList.size();
    }

    public class ContactListViewHolder extends RecyclerView.ViewHolder {

        private ImageView contactImage, callToCare, smsToCare;
        private TextView contactName, contactNumber;

        public ContactListViewHolder(View itemView) {
            super(itemView);

            contactImage = itemView.findViewById(R.id.contact_image);
            callToCare = itemView.findViewById(R.id.callToCare);
            smsToCare = itemView.findViewById(R.id.smsToCare);
            contactName = itemView.findViewById(R.id.contact_name);
            contactNumber = itemView.findViewById(R.id.contact_number);



        }
    }
}
