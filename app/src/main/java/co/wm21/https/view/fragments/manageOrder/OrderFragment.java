package co.wm21.https.view.fragments.manageOrder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.EshopListModel;
import co.wm21.https.FHelper.networks.Models.LocationModel;
import co.wm21.https.FHelper.networks.Models.OrderConfirmModel;
import co.wm21.https.FHelper.networks.Models.OrderItemModel;
import co.wm21.https.FHelper.networks.Models.OrderItemModelHead;
import co.wm21.https.FHelper.networks.Models.ProfileDetailsHead;
import co.wm21.https.FHelper.networks.Models.ShopTypeModel;
import co.wm21.https.R;
import co.wm21.https.view.activities.SearchShopActivity;
import co.wm21.https.view.adapters.OrderAdapter;
import co.wm21.https.view.adapters.OrderEshopListAdapter;
import co.wm21.https.databinding.FragmentOrderBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.view.fragments.HomeFragment;
import co.wm21.https.helpers.User;
import co.wm21.https.presenter.interfaces.OnEshopListView;
import co.wm21.https.presenter.interfaces.OnLocationListView;
import co.wm21.https.presenter.interfaces.OnOrderConfirmView;
import co.wm21.https.presenter.interfaces.OnOrderItemListView;
import co.wm21.https.presenter.interfaces.OnProfileDetailsView;
import co.wm21.https.presenter.interfaces.OnShopTypeView;
import co.wm21.https.presenter.EshopListPresenter;
import co.wm21.https.presenter.LocationListPresenter;
import co.wm21.https.presenter.OrderConfirmPresenter;
import co.wm21.https.presenter.OrderItemListPresenter;
import co.wm21.https.presenter.ProfileDetailsPresenter;
import co.wm21.https.presenter.ShopTypePresenter;

public class OrderFragment extends Fragment implements OnOrderItemListView, OnLocationListView, OnShopTypeView, OnEshopListView, OnOrderConfirmView, OnProfileDetailsView {

    private FragmentOrderBinding binding;
    private API api;
    private User user;
    private String countryId = "", divId = "", disId = "", thanaId = "", unionId = "";
    private ArrayList<OrderItemModel> orderedList;
    private double total = 0;
    private OrderAdapter orderAdapter;
    public static String sName = "", sNumber = "", sAddress = "";
    public static boolean selectHoise = false;
    public static String eshopID = "";
    private String addressID = "";


    private OrderEshopListAdapter eshopListAdapter;

    private ArrayList<String> countryList, divList, disList, thanaList, unionList;
    private ArrayList<String> countryListId, divListId, disListId, thanaListId, unionListId;
    public static ArrayList<EshopListModel>eshopList;

    static public double totalPrice = 0, totalRP = 0, adjustedPrice = 0, adjustedRP = 0, payablePrice = 0, myPoint = 0;
    private String shipping = "", eshopType = "", address = "";
    static public int ifAdjust;



    LoadingDialog loadingDialog;
    OrderItemListPresenter orderItemListPresenter;
    LocationListPresenter locationListPresenter;
    ShopTypePresenter shopTypePresenter;
    EshopListPresenter eshopListPresenter;
    OrderConfirmPresenter orderConfirmPresenter;
    ProfileDetailsPresenter profileDetailsPresenter;
    ArrayAdapter<String> stringAdapter;


    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false);

        api = ConstantValues.getAPI();
        user = new User(getContext());

        ifAdjust = 0;
        binding.orderConfirmButton.setOnClickListener(this::orderConfirm);
        binding.searchShopButton.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), SearchShopActivity.class));

        });
        binding.countryTxt.setOnItemClickListener((adapterView, view, i, l) -> {
            binding.division.setVisibility(View.VISIBLE);
            binding.district.setVisibility(View.GONE);
            binding.thana.setVisibility(View.GONE);
            binding.union.setVisibility(View.GONE);

            String val = adapterView.getAdapter().getItem(i).toString();
            countryId = countryListId.get(i);
            getLocation(ConstantValues.profile.DIVISION, val);
        });
        binding.divisionTxt.setOnItemClickListener((adapterView, view, i, l) -> {
            binding.district.setVisibility(View.VISIBLE);
            binding.thana.setVisibility(View.GONE);
            binding.union.setVisibility(View.GONE);
            String val = adapterView.getAdapter().getItem(i).toString();
            divId = divListId.get(i);
            getLocation(ConstantValues.profile.DISTRICT, val);
        });
        binding.districtTxt.setOnItemClickListener((adapterView, view, i, l) -> {
            binding.thana.setVisibility(View.VISIBLE);
            binding.union.setVisibility(View.GONE);
            String val = adapterView.getAdapter().getItem(i).toString();
            disId = disListId.get(i);
            getLocation(ConstantValues.profile.THANA, val);
        });
        binding.thanaTxt.setOnItemClickListener((adapterView, view, i, l) -> {
            binding.union.setVisibility(View.VISIBLE);
            String val = adapterView.getAdapter().getItem(i).toString();
            thanaId = thanaListId.get(i);
            getLocation(ConstantValues.profile.UNION, val);
        });
        binding.unionTxt.setOnItemClickListener((adapterView, view, i, l) -> {
            String val = adapterView.getAdapter().getItem(i).toString();
            unionId = unionListId.get(i);
            getEshop(unionId, ConstantValues.location.UNION);
        });
        binding.shippingRGroupId.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            if (checkedId == binding.receiveEshopBtn.getId()) {
                shipping = "Eshop Delivery";
                binding.shippingAddressET.getEditText().setText("");
                binding.shippingAddressLayout.setVisibility(View.GONE);
            } else if (checkedId == binding.homeDeliveryBtn.getId()) {
                shipping = "Home Delivery";
                if (user.getUsername() != null && user.getPassword() != null) {
                    profileDetailsPresenter.profileDetailsDataLoad(user.getUsername());
                }
                binding.shippingAddressLayout.setVisibility(View.VISIBLE);
            }
        });
        binding.useRp.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                binding.adjustedTotal.setText(String.format("%.02f", adjustedPrice));
                binding.adjustedRP.setText(String.format("%.02f", adjustedRP));
                binding.payableTotal.setText(String.format("%.02f", payablePrice));
                binding.adjustedLayout.setVisibility(View.VISIBLE);
                binding.payableLayout.setVisibility(View.VISIBLE);
                ifAdjust = 1;
            } else {
                binding.adjustedLayout.setVisibility(View.GONE);
                binding.payableLayout.setVisibility(View.GONE);
                ifAdjust = 0;
            }
        });

        orderItemListPresenter = new OrderItemListPresenter(this);
        locationListPresenter = new LocationListPresenter(this);
        shopTypePresenter = new ShopTypePresenter(this);
        eshopListPresenter=new EshopListPresenter(this);
        orderConfirmPresenter=new OrderConfirmPresenter(this);
        profileDetailsPresenter=new ProfileDetailsPresenter(this);
        loadingDialog = new LoadingDialog((Activity) getContext());


        allOrderedProduct();

        return binding.getRoot();
    }


    private void orderConfirm(View view) {

        if (user.getUsername() != null && user.getPassword() != null) {
            if (eshopType.equals("teleShop")) {
                if (selectHoise) {
                    //eshopID = eshopListAdapter.getSelected().getId();
                    orderPush();
                } else {
                    showSnackBar("Please Select E-Shop");
                }
            } else if (eshopType.equals("preShop")) {
                orderPush();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!sName.equals("") || !sNumber.equals("") || !sAddress.equals("")) {
            binding.locationLayout.setVisibility(View.GONE);
            binding.searchedShopCard.setVisibility(View.VISIBLE);
            binding.sName.setText(sName);
            binding.sShopNumber.setText(sNumber);
            binding.sAddress.setText(sAddress);
        } else {

            binding.locationLayout.setVisibility(View.VISIBLE);
            binding.searchedShopCard.setVisibility(View.GONE);
        }

    }

    private void orderPush() {


        if (!shipping.equals("")) {
            if (binding.agreeTermBtnId.isChecked()) {
                orderConfirmPresenter.orderConfirmDataLoad(user.getUsername(), eshopID, shipping, address, ifAdjust);
            } else {

                binding.agreeTermBtnId.setError("should be agreed");
            }
        } else {

            showSnackBar("Please Confirm your shipping");
        }

    }

    @SuppressLint("DefaultLocale")
    private void allOrderedProduct() {
        orderedList = new ArrayList<>();
        binding.checkoutRecId.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        orderAdapter = new OrderAdapter(R.layout.item_order_product_list_single_row, getContext(), orderedList,
                binding.totalPriceTV,
                binding.contextView,
                binding.totalRPTV,
                binding.adjustedRP,
                binding.adjustedTotal,
                binding.payableTotal,
                binding.userRPLayout
        );

        orderItemListPresenter.orderItemDataLoad(user.getUsername());


    }

    private void eshopType() {

        shopTypePresenter.shopTypeDataLoad(user.getUsername());

    }

    @SuppressLint("SetTextI18n")
    private void getLocation(@NonNull String id, String val) {
        if (id.equals(ConstantValues.location.COUNTRY)) {
            countryList = new ArrayList<>();
            countryListId = new ArrayList<>();
            stringAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, countryList);
            binding.countryTxt.setAdapter(stringAdapter);
            addressID = id;
            locationListPresenter.locationDataLoad(id, val);

        } else if (id.equals(ConstantValues.profile.DIVISION)) {
            divList = new ArrayList<>();
            divListId = new ArrayList<>();
            stringAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, divList);
            binding.divisionTxt.setAdapter(stringAdapter);
            addressID = id;
            locationListPresenter.locationDataLoad(id, val);

        } else if (id.equals(ConstantValues.profile.DISTRICT)) {
            disList = new ArrayList<>();
            disListId = new ArrayList<>();
            stringAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, disList);
            binding.districtTxt.setAdapter(stringAdapter);
            addressID = id;
            locationListPresenter.locationDataLoad(id, val);

        } else if (id.equals(ConstantValues.profile.THANA)) {
            thanaList = new ArrayList<>();
            thanaListId = new ArrayList<>();

            stringAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, thanaList);

            binding.thanaTxt.setAdapter(stringAdapter);
            addressID = id;
            locationListPresenter.locationDataLoad(id, val);

        } else if (id.equals(ConstantValues.profile.UNION)) {
            unionList = new ArrayList<>();
            unionListId = new ArrayList<>();
            stringAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, unionList);
            binding.unionTxt.setAdapter(stringAdapter);
            addressID = id;
            locationListPresenter.locationDataLoad(id, val);
            getEshop(thanaId, ConstantValues.location.THANA);

        }
    }

    private void getEshop(String location, String locType) {
        eshopList=new ArrayList<>();
        binding.shopRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.shopRecView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        eshopListAdapter = new OrderEshopListAdapter(getContext(), eshopList);
        binding.shopRecView.setAdapter(eshopListAdapter);
        eshopListPresenter.eshopDataLoad(user.getUsername(), location, locType,"");

    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = getParentFragmentManager();
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
    }


    private void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.parentLayout, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
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
                switchFragment(new OrderFragment(), "OrderFragment");
                break;

            case R.id.menu_applied:
                switchFragment(new AppliedFragment(), "AppliedFragment");
                break;
            case R.id.menu_delivered:
                switchFragment(new DeliveredFragment(), "DeliveredFragment");
                break;
            case R.id.menu_received:
                switchFragment(new ReceivedFragment(), "ReceivedFragment");
                break;
        }
        return true;

    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onOrderItemListDataLoad(OrderItemModelHead orderItemModelHeads) {

        orderedList.addAll(orderItemModelHeads.getData());
        orderAdapter.notifyDataSetChanged();

        myPoint = Double.parseDouble(orderItemModelHeads.getMyPoint());
        binding.myPointTv.setText(String.format("%.02f", myPoint));

        if (orderItemModelHeads.getData().size() > 0) {
            binding.calculationLayout.setVisibility(View.VISIBLE);
            totalPrice = Double.parseDouble(orderItemModelHeads.getTotalAmount());
            totalRP = Double.parseDouble(orderItemModelHeads.getTotalRp());
            adjustedPrice = Double.parseDouble(orderItemModelHeads.getAdjAmount());
            adjustedRP = Double.parseDouble(orderItemModelHeads.getAdjRp());
            payablePrice = orderItemModelHeads.getPayable();
            binding.totalPriceTV.setText(String.format("%.02f", totalPrice));
            binding.totalRPTV.setText(String.format("%.02f", totalRP));
            if (myPoint < adjustedRP && myPoint <= 0) {
                binding.userRPLayout.setVisibility(View.GONE);
            } else if (myPoint >= adjustedRP) {
                binding.userRPLayout.setVisibility(View.VISIBLE);
            }

            binding.checkoutRecId.setVisibility(View.VISIBLE);

            if (!orderedList.isEmpty()) {
                binding.myRPLayout.setVisibility(View.VISIBLE);
                binding.checkoutRecId.setAdapter(orderAdapter);
                eshopType();
            }
        }

    }

    @Override
    public void onOrderItemListStartLoading() {
        loadingDialog.startLoadingAlertDialog();
        binding.calculationLayout.setVisibility(View.GONE);
    }

    @Override
    public void onOrderItemListStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onOrderItemListShowMessage(String errmsg) {
        Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLocationListDataLoad(List<LocationModel> locationModels) {

        if (addressID.equals(ConstantValues.profile.COUNTRY)) {
            loadingDialog.dismissDialog();
            for (int i = 0; i < locationModels.size(); i++) {
                countryList.add(locationModels.get(i).getValue());
                countryListId.add(locationModels.get(i).getId());
            }
            if (countryList.isEmpty()) {
                countryList.add("Not Applicable");
            }
        }
        if (addressID.equals(ConstantValues.profile.DIVISION)) {
            for (int i = 0; i < locationModels.size(); i++) {
                divList.add(locationModels.get(i).getValue());
                divListId.add(locationModels.get(i).getId());
            }


            if (divList.isEmpty()) {
                disList.add("Not Applicable");
            }
        }
        if (addressID.equals(ConstantValues.profile.DISTRICT)) {
            for (int i = 0; i < locationModels.size(); i++) {
                disList.add(locationModels.get(i).getValue());
                disListId.add(locationModels.get(i).getId());
            }
            if (disList.isEmpty()) {
                disList.add("Not Applicable");
            }
        }
        if (addressID.equals(ConstantValues.profile.THANA)) {
            for (int i = 0; i < locationModels.size(); i++) {
                thanaList.add(locationModels.get(i).getValue());
                thanaListId.add(locationModels.get(i).getId());
            }
            if (thanaList.isEmpty()) {
                thanaList.add("Not Applicable");
            }
        }
        if (addressID.equals(ConstantValues.profile.UNION)) {
            for (int i = 0; i < locationModels.size(); i++) {
                unionList.add(locationModels.get(i).getValue());
                unionListId.add(locationModels.get(i).getId());
            }
            if (unionList.isEmpty()) {
                unionList.add("Not Applicable");
            }
        }
        stringAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLocationListStartLoading() {
        if (!addressID.equals(ConstantValues.profile.COUNTRY)) {
            loadingDialog.startLoadingAlertDialog();
        }
    }

    @Override
    public void onLocationListStopLoading() {

        loadingDialog.dismissDialog();
    }

    @Override
    public void onLocationListShowMessage(String errmsg) {
        Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onShopTypeDataLoad(ShopTypeModel shopTypeModel) {
        if (shopTypeModel.getSid().equals("0")) {
            eshopType = "teleShop";
            binding.otherShopLayout.setVisibility(View.GONE);
            binding.country.setVisibility(View.VISIBLE);
            binding.searchShopButton.setVisibility(View.VISIBLE);
            getLocation(ConstantValues.location.COUNTRY, "");
        } else {
            eshopType = "preShop";
            binding.country.setVisibility(View.GONE);
            eshopID = shopTypeModel.getId();
            binding.otherShopLayout.setVisibility(View.VISIBLE);
            binding.shopName.setText(shopTypeModel.getName());
            binding.shopNumber.setText(shopTypeModel.getMobile());
            binding.shopAddress.setText(shopTypeModel.getAddress());
        }
    }

    @Override
    public void onShopTypeStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onShopTypeStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onShopTypeShowMessage(String errmsg) {
        Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onEshopListDataLoad(List<EshopListModel> eshopListModels) {
        if (eshopListModels.size()>0) {
            binding.shopRecView.setVisibility(View.VISIBLE);
            eshopList.addAll(eshopListModels);
            eshopListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onEshopListStartLoading() {
      //  loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onEshopListStopLoading() {
        //loadingDialog.dismissDialog();
    }

    @Override
    public void onEshopListShowMessage(String errmsg) {
        Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onOrderConfirmDataLoad(OrderConfirmModel orderConfirmModel) {
        if (orderConfirmModel.getError()==0) {
            switchFragment(new HomeFragment(), "HomeFragment");
            showSnackBar(orderConfirmModel.getData());
            sName = "";
            sNumber = "";
            sAddress = "";
        }
    }

    @Override
    public void onOrderConfirmStartLoading() {
          loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onOrderConfirmStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onOrderConfirmShowMessage(String errmsg) {
        Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfileDetailsDataLoad(ProfileDetailsHead profileDetailsHead) {
        address = profileDetailsHead.getData().getAddress();
        String word =profileDetailsHead.getData().getWord();
        String union =profileDetailsHead.getData().getUnion();
        String thana = profileDetailsHead.getData().getThana();
        String district = profileDetailsHead.getData().getDistrict();
        String division =profileDetailsHead.getData().getDivision();
        binding.shippingAddressET.getEditText().setText(address);
    }

    @Override
    public void onProfileDetailsStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onProfileDetailsStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onProfileDetailsShowMessage(String errmsg) {
        Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();
    }
}