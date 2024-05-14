package co.wm21.https.fragments.manageOrder;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.FHelper.networks.Models.AppliedProductModel;
import co.wm21.https.FHelper.networks.Models.AppliedProductModelHead;
import co.wm21.https.R;
import co.wm21.https.activities.MainActivity;
import co.wm21.https.adapters.AppliedAdapter;
import co.wm21.https.adapters.OrderAdapter;
import co.wm21.https.databinding.FragmentAppliedBinding;
import co.wm21.https.dialog.LoadingDialog;
import co.wm21.https.helpers.User;
import co.wm21.https.interfaces.OnAppliedProductsView;
import co.wm21.https.presenter.AppliedProductsPresenter;

public class AppliedFragment extends Fragment implements OnAppliedProductsView {

    FragmentAppliedBinding binding;
    private API api;
    private User user;
    private AppliedAdapter appliedAdapter;
    double subtotal=0,myPoint=0;
    private ArrayList<AppliedProductModel> appliedList;

    AppliedProductsPresenter appliedProductsPresenter;
    LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_applied, container, false);
        api = ConstantValues.getAPI();
        user = new User(getContext());

        appliedProductsPresenter=new AppliedProductsPresenter(this);
        loadingDialog=new LoadingDialog(getActivity());
        allAppliedProduct();

        return binding.getRoot();
    }

    @SuppressLint("DefaultLocale")
    private void allAppliedProduct() {
        appliedList = new ArrayList<>();
        binding.appliedProductRecView.setVisibility(View.VISIBLE);
        binding.appliedProductRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        appliedAdapter = new AppliedAdapter(R.layout.item_applied_product_single_row, getContext(), appliedList);
        binding.appliedProductRecView.setAdapter(appliedAdapter);
        appliedProductsPresenter.appliedProductsDataLoad(user.getUsername());

    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = getParentFragmentManager();
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
    public void onAppliedProductsDataLoad(AppliedProductModelHead appliedProductModelHeads) {
        appliedList.addAll(appliedProductModelHeads.getData());
        binding.appliedProductRecView.setVisibility(View.VISIBLE);
        subtotal = appliedProductModelHeads.getSubTotal();
        myPoint = Double.parseDouble(appliedProductModelHeads.getMyPoint());

        if (appliedList.size()>0){
            binding.pLayout.setVisibility(View.VISIBLE);
            binding.emptyBox.setVisibility(View.GONE);
        }
        appliedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAppliedProductsStartLoading() {

        loadingDialog.startLoadingAlertDialog();
        binding.pLayout.setVisibility(View.GONE);
        binding.emptyBox.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAppliedProductsStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onAppliedProductsShowMessage(String errmsg) {
        Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();
    }
}