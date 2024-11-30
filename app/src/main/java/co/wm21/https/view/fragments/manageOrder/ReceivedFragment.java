package co.wm21.https.view.fragments.manageOrder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.ReceivedItemsModel;
import co.wm21.https.FHelper.networks.Models.ReceivedItemsModelHead;
import co.wm21.https.R;
import co.wm21.https.view.adapters.RecievedAdapter;
import co.wm21.https.databinding.FragmentReceivedBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.helpers.User;
import co.wm21.https.presenter.interfaces.OnReceivedItemsView;
import co.wm21.https.presenter.ReceivedItemsPresenter;


public class ReceivedFragment extends Fragment implements OnReceivedItemsView {
    FragmentReceivedBinding binding;
    private API api;
    private User user;
    ArrayList<ReceivedItemsModel> receivedProModels;
    String sponsorName,sponsorNumber;
    RecievedAdapter recievedAdapter;
    LoadingDialog loadingDialog;
    ReceivedItemsPresenter receivedItemsPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_received, container, false);
        api = ConstantValues.getAPI();
        user = new User(getContext());
        loadingDialog=new LoadingDialog(getActivity());
        receivedItemsPresenter=new ReceivedItemsPresenter(this);

        allReceivedPro();

        return binding.getRoot();
    }

    private void allReceivedPro() {
        receivedProModels = new ArrayList<>();
        binding.receivedRecId.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recievedAdapter = new RecievedAdapter(R.layout.item_receive_page_single_row, getContext(), receivedProModels, binding.contextView);
        binding.receivedRecId.setAdapter(recievedAdapter);
        receivedItemsPresenter.receivedItemsDataLoad(user.getUsername());
    }
    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = getParentFragmentManager();
       /* for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();*/
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // TODO Add your menu entries here
        inflater.inflate(R.menu.manage_order, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_orders:
                switchFragment(new OrderFragment(),"OrderFragment");
                break;

            case R.id.menu_applied:
                switchFragment(new AppliedFragment(),"AppliedFragment");
                break;
            case R.id.menu_delivered:
                switchFragment(new DeliveredFragment(),"DeliveredFragment");
                break;
            case R.id.menu_received:
                switchFragment(new ReceivedFragment(),"ReceivedFragment");
                break;
        }
        return true;

    }

    @Override
    public void onReceivedItemsDataLoad(ReceivedItemsModelHead receivedItemsModelHead) {
        receivedProModels.addAll(receivedItemsModelHead.getData());
        recievedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onReceivedItemsStartLoading() {
        binding.receivedRecId.setVisibility(View.GONE);
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onReceivedItemsStopLoading() {
        binding.receivedRecId.setVisibility(View.VISIBLE);
        loadingDialog.dismissDialog();
    }

    @Override
    public void onReceivedItemsShowMessage(String errmsg) {
        Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();
    }
}