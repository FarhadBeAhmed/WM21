package com.wm21ltd.wm21.fragments;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.adapters.CustomerCareListAdapter;
import com.wm21ltd.wm21.interfaces.OnCustomerCareItemClickListner;
import com.wm21ltd.wm21.networks.Models.ContactListModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerCareFragment extends Fragment implements OnCustomerCareItemClickListner {

    private static final int CALL_PHONE_PERMISSION_CODE = 1;
    private RecyclerView customerCareRecycler;
    private ArrayList<ContactListModel> customerCareList;


    public CustomerCareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_care, container, false);

        customerCareRecycler = view.findViewById(R.id.customerCareRecycler);

        getCustomerCareList();

        CustomerCareListAdapter customerCareListAdapter = new CustomerCareListAdapter(getContext(), customerCareList,this);
        customerCareRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        customerCareRecycler.setAdapter(customerCareListAdapter);
        return view;
    }

    private void getCustomerCareList() {
        customerCareList = new ArrayList<>();
        customerCareList.add(new ContactListModel("Customer Care","+8801983212121"));
        customerCareList.add(new ContactListModel("Advance Customer","+8801961222000"));
        customerCareList.add(new ContactListModel("Admin","+8801923445511"));
        customerCareList.add(new ContactListModel("Accounts","+8801950445511"));
        customerCareList.add(new ContactListModel("Product Delivery","+8801948445511"));
        customerCareList.add(new ContactListModel("IT Support","+8801924445511"));
        customerCareList.add(new ContactListModel("Sale Service","+8801952445511"));
        customerCareList.add(new ContactListModel("Mission EDU / ICT","+8801963445511"));
        customerCareList.add(new ContactListModel("NISORGO / Tour","+8801931445511"));
        customerCareList.add(new ContactListModel("Training","+8801964445511"));
    }

    @Override
    public void callToCustomerCare(String number) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" +number));
            startActivity(callIntent);
        }
        else {
            requestCallphonePermission();
        }
    }

    private void requestCallphonePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CALL_PHONE)){
            new AlertDialog.Builder(getContext())
                    .setTitle("Permission Needed")
                    .setMessage("To make a call this permission is needed.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.CALL_PHONE},CALL_PHONE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        else {
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.CALL_PHONE},CALL_PHONE_PERMISSION_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CALL_PHONE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Perission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
