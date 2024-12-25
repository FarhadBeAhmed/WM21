package co.wm21.https.view.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.CartItemsHead;
import co.wm21.https.FHelper.networks.Models.DrawerCatModel;
import co.wm21.https.R;
import co.wm21.https.view.activities.mlm.HomeActivity;
import co.wm21.https.view.adapters.category.drawer_category.DrawerCatAdapter;
import co.wm21.https.databinding.ActivityMainBinding;
import co.wm21.https.databinding.FragmentDrawerCategoryBinding;
import co.wm21.https.databinding.FragmentDrawerMainmenuBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.view.fragments.CartFragment;
import co.wm21.https.view.fragments.HomeFragment;
import co.wm21.https.view.fragments.LoginFragment;
import co.wm21.https.view.fragments.WishlistFragment;
import co.wm21.https.view.fragments.member.profile.ProfileFragment;

import co.wm21.https.view.fragments.registration.RegisterFragment;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.helpers.User;
import co.wm21.https.presenter.interfaces.OnCartItemListView;
import co.wm21.https.presenter.interfaces.OnDrawerCatListView;
import co.wm21.https.presenter.CartItemListPresenter;
import co.wm21.https.presenter.DrawerCatListPresenter;

public class MainActivity extends AppCompatActivity  implements OnCartItemListView {

    public ActivityMainBinding binding;
    public User user;
    Fragment currentFragment;
    public MenuItem notificationItem, signOutItem,bottomMenuItemAcc;
    public Menu TopMenu;
    public Menu menu;
    private SessionHandler sessionHandler;
    private Integer itemIdWhenClosed;
    boolean backState;
    final LoadingDialog loadingDialog =new LoadingDialog(MainActivity.this);
    CartItemListPresenter cartItemListPresenter;
    public static BadgeDrawable badge;

    @SuppressLint({"NonConstantResourceId", "ObsoleteSdkInt"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = new User(getApplicationContext());
        sessionHandler = user.getSession();
        Window window = MainActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.status_bar_shadow));
        }


        cartItemListPresenter=new CartItemListPresenter(this);
        setSupportActionBar(binding.mainToolbar);
        switchDrFragment(new DrawerMainMenu(), "DrawerMainMenu");
        //getSupportFragmentManager().beginTransaction().add(R.id.drFrameLayout, new DrawerMainMenu(MainActivity.this), "DrawerMainMenu").addToBackStack("DrawerMainMenu").commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.mainToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                switchDrFragment(new DrawerMainMenu(), "DrawerMainMenu");
                //getSupportFragmentManager().beginTransaction().add(R.id.drFrameLayout, new DrawerMainMenu(MainActivity.this), "DrawerMainMenu").addToBackStack("DrawerMainMenu").commit();
            }


            @SuppressLint("NonConstantResourceId")
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // If id to show is marked, perform action
                if (itemIdWhenClosed != null) {
                    switch (itemIdWhenClosed) {
                        case R.id.menu_home:
                            switchFragment(new HomeFragment(), "HomeFragment");
                            break;
                        case R.id.menu_shop:
                           startActivity(new Intent(MainActivity.this, ShopsActivity.class));
                            break;
                        case R.id.menu_cart:
                            switchFragment(new CartFragment(), "CartFragment");
                            break;
                        case R.id.menu_wishlist:
                            switchFragment(new WishlistFragment(), "WishlistFragment");
                            break;
                        case R.id.menu_profile:
                            switchFragment(new ProfileFragment(), "ProfileFragment");
                            break;
                        case R.id.menu_login:
                            switchFragment(new LoginFragment(), "LoginFragment");
                            break;
                        case R.id.menu_register:
                            switchFragment(new RegisterFragment(), "RegisterFragment");
                            break;
                        case R.id.menu_mlm:
                        case R.id.menu_affiliate:
                            //switchFragment(new MlmFragment(), "MlmFragment");
                            break;
                        case R.id.menu_logout:
                            sessionHandler.logoutUser();
                            switchFragment(new LoginFragment(), "LoginFragment");
                            break;
                        default:
                    }
                    // Reset value
                    itemIdWhenClosed = null;
                }

                binding.headerMenusID.getTabAt(0).select();

                getSupportFragmentManager().beginTransaction().replace(R.id.drFrameLayout, new DrawerMainMenu(), "DrawerMainMenu").addToBackStack("DrawerMainMenu").commit();
            }
        };
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        menu=binding.bottomNavigationView.getMenu();
        bottomMenuItemAcc=menu.findItem(R.id.menu_account);

        binding.navigationView.setNavigationItemSelectedListener(item -> {


            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
        binding.headerMenusID.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        switchDrFragment(new DrawerMainMenu(), "DrawerMainMenu");
                        break;
                    case 1:
                        switchDrFragment(new DrawerCategory(), "DrawerCategory");
                        break;
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (user.getMemberType().equals("Eco")){
            menu=binding.bottomNavigationView.getMenu();
            bottomMenuItemAcc.setVisible(false);
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    switchFragment(new HomeFragment(), "HomeFragment");
                    return true;
                case R.id.menu_shop:
                    startActivity(new Intent(MainActivity.this,ShopsActivity.class));
                    return true;
                case R.id.menu_account:

                    if (user.getSession().isLoggedIn()) {
                        loadingDialog.startLoadingAlertDialog();
                        switch (user.getMemberType()) {
                            case "MLM":
                                loadingDialog.dismissDialog();
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
//                                switchFragment(new MlmFragment(), "MlmFragment");
                                break;
                            case "Aff":
                                loadingDialog.dismissDialog();
                                break;
                            case "Eco": // Notification Button
                                loadingDialog.dismissDialog();
                                break;
                        }
                    } else {
                        switchFragment(new LoginFragment(), "LoginFragment");
//                        switchFragment(new MlmFragment());
                    }
                    return true;
                case R.id.menu_notification:

                    return true;
                case R.id.menu_wishlist:
                    switchFragment(new WishlistFragment(), "WishlistFragment");
                    return true;
            }
            return false;
        });



        Intent intent = getIntent();
        if (getIntent().getExtras() != null) {
            if (intent.getStringExtra("fromCart").equals("cart"))
                switchFragment(new CartFragment(), "CartFragment");
        } else switchFragment(new HomeFragment(), "HomeFragment");

    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        // for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
        //  Log.d("TAG", "BackStacks: " + getSupportFragmentManager().getBackStackEntryAt(i));
        fm.beginTransaction().replace(binding.fragmentContainer.getId(), fragment, tag).addToBackStack(tag).commit();
    }

    public void switchDrFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(binding.drFrameLayout.getId(), fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        @MenuRes int menuId;
        menuId = R.menu.toolbar_menu;
        getMenuInflater().inflate(menuId, TopMenu = menu);
        notificationItem = TopMenu.getItem(0);
        signOutItem = TopMenu.getItem(1);
        if (!user.getSession().isLoggedIn())
            signOutItem.setVisible(false);
        else
            signOutItem.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint({"NonConstantResourceId", "RtlHardcoded"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_menu_sign_out:
                user.getSession().logoutUser();
                finishAffinity();
                startActivity(new Intent(this, MainActivity.class));
                return true;

            case R.id.menu_cart:
                getCartItems();
                switchFragment(new CartFragment(), "CartFragment");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    @Override
    protected void onResume() {
        super.onResume();
         badge = BadgeDrawable.create(this);
        BadgeUtils.attachBadgeDrawable(badge, binding.mainToolbar, R.id.menu_cart);
        badge.setBackgroundColor(Color.parseColor("#f6ac00"));
        badge.setVisible(true);
        badge.clearNumber();
        if (ProductDetailsActivity.totalSelected2 == 0) {
            badge.clearNumber();
        } else {
            badge.setNumber(ProductDetailsActivity.totalSelected2);
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    public void getCartItems() {
         badge = BadgeDrawable.create(this);
        BadgeUtils.attachBadgeDrawable(badge, binding.mainToolbar, R.id.menu_cart);
        badge.setBackgroundColor(Color.parseColor("#f6ac00"));
        badge.setVisible(true);
        badge.clearNumber();
        String uId;
        @SuppressLint("HardwareIds")
        String val = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        uId = val.replaceAll("[\\D]", "");

        cartItemListPresenter.CartItemDataLoad(uId);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onCartItemListDataLoad(CartItemsHead cartItems) {
        int totalSelected2=cartItems.getData().size();
        if (totalSelected2 == 0) {
            badge.clearNumber();
        } else {
            badge.setNumber(totalSelected2);
        }
    }

    @Override
    public void onCartItemListStartLoading() {

    }

    @Override
    public void onCartItemListStopLoading() {

    }

    @Override
    public void onCartItemListShowMessage(String errmsg) {
        //Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }

    public static class DrawerMainMenu extends Fragment {
        FragmentDrawerMainmenuBinding binding;
        // MainActivity mainActivity;
        User user;
        SessionHandler sessionHandler;

       /* public DrawerMainMenu(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }*/

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            binding = FragmentDrawerMainmenuBinding.inflate(inflater, container, false);

            return binding.getRoot();
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            user = new User(getContext());
            sessionHandler = user.getSession();

            if (sessionHandler.isLoggedIn()) {
                binding.menuLogin.setVisibility(View.GONE);
                binding.menuRegister.setVisibility(View.GONE);
                binding.menuLogout.setVisibility(View.VISIBLE);
                binding.profileLayout.setVisibility(View.VISIBLE);
                binding.accLayout.setVisibility(View.VISIBLE);
                switch (user.getMemberType()) {
                    case "MLM":
                        binding.profileLayout.setVisibility(View.VISIBLE);
                        binding.menuAffiliate.setVisibility(View.VISIBLE);
                        break;
                    case "Aff":
                        binding.menuAffiliate.setVisibility(View.VISIBLE);
                        break;
                    case "Eco": // Notification Button
                        break;
                }
            } else {
                binding.menuLogin.setVisibility(View.VISIBLE);
                binding.menuRegister.setVisibility(View.VISIBLE);
                binding.menuLogout.setVisibility(View.GONE);
                binding.profileLayout.setVisibility(View.GONE);
                binding.accLayout.setVisibility(View.GONE);
            }

            binding.menuHome.setOnClickListener(this::DrawerItemClicked);
            binding.menuShop.setOnClickListener(this::DrawerItemClicked);
            binding.menuAffiliate.setOnClickListener(this::DrawerItemClicked);
            binding.menuCart.setOnClickListener(this::DrawerItemClicked);
            binding.menuMlm.setOnClickListener(this::DrawerItemClicked);
            binding.menuLogin.setOnClickListener(this::DrawerItemClicked);
            binding.menuProfile.setOnClickListener(this::DrawerItemClicked);
            binding.menuRegister.setOnClickListener(this::DrawerItemClicked);
            binding.menuWishlist.setOnClickListener(this::DrawerItemClicked);
            binding.menuLogout.setOnClickListener(this::DrawerItemClicked);


        }

        @SuppressLint("NonConstantResourceId")
        private void DrawerItemClicked(View view) {

            DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawerLayout);

            if (((MainActivity) getActivity()).binding.drawerLayout.isOpen())
                ((MainActivity) getActivity()).binding.drawerLayout.closeDrawer(Gravity.LEFT);
            switch (view.getId()) {
                case R.id.menu_home:
                    switchFragment(new HomeFragment(), "HomeFragment");
                    break;
                case R.id.menu_shop:
                    startActivity(new Intent(getActivity(), ShopsActivity.class));
                    break;
                case R.id.menu_cart:
                    switchFragment(new CartFragment(), "CartFragment");
                    break;
                case R.id.menu_wishlist:
                    switchFragment(new WishlistFragment(), "WishlistFragment");
                    break;
                case R.id.menu_profile:
                    switchFragment(new ProfileFragment(), "ProfileFragment");
                    break;
                case R.id.menu_login:
                    switchFragment(new LoginFragment(), "LoginFragment");
                    break;
                case R.id.menu_register:
                    switchFragment(new RegisterFragment(), "RegisterFragment");
                    break;
                case R.id.menu_mlm:
                    //switchFragment(new MlmFragment(), "MlmFragment");
                    break;
                case R.id.menu_affiliate:
                    break;
                case R.id.menu_logout:
                    user.getSession().logoutUser();
                    requireActivity().finishAffinity();
                    requireActivity().startActivity(new Intent(getContext(), MainActivity.class));
                    break;
                default:
            }

        }

        public void switchFragment(Fragment fragment, String tag) {
            FragmentManager fm = requireActivity().getSupportFragmentManager();
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
                Log.d("TAG", "BackStacks: " + requireActivity().getSupportFragmentManager().getBackStackEntryAt(i));
            fm.beginTransaction().replace(((MainActivity) requireActivity()).binding.fragmentContainer.getId(), fragment, tag).addToBackStack(tag).commit();
        }
    }

    public static class DrawerCategory extends Fragment implements OnDrawerCatListView {
        FragmentDrawerCategoryBinding binding;
        API api;

        DrawerCatAdapter drawerCatAdapter;
        DrawerCatListPresenter drawerCatListPresenter;
        ArrayList<DrawerCatModel>drawerCatModelList;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            binding = FragmentDrawerCategoryBinding.inflate(getLayoutInflater());
            api = ConstantValues.getAPI();
            drawerCatListPresenter=new DrawerCatListPresenter(this);

            categories();
            return binding.getRoot();
        }


        private void categories() {
            drawerCatModelList=new ArrayList<>();
            drawerCatAdapter=new DrawerCatAdapter(getContext(), drawerCatModelList, R.layout.layout_item_drwayer_cat);
            binding.parentCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            binding.parentCategoriesRecyclerView.setAdapter(drawerCatAdapter);
            drawerCatListPresenter.onDrawerCatDataLoad(0,"");
        }

        @Override
        public void onDrawerCatListDataLoad(List<DrawerCatModel> drawerCatModels) {
            drawerCatModelList.addAll(drawerCatModels);
            drawerCatAdapter.notifyDataSetChanged();
        }

        @Override
        public void onDrawerCatListStartLoading() {
            binding.shimmerProduct.setVisibility(View.VISIBLE);
            binding.parentCategoriesRecyclerView.setVisibility(View.GONE);
        }

        @Override
        public void onDrawerCatListStopLoading() {
            binding.shimmerProduct.setVisibility(View.GONE);
            binding.parentCategoriesRecyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onDrawerCatListShowMessage(String errmsg) {
            Toast.makeText(getActivity(), errmsg, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onBackPressed() {
        getCartItems();
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT) || binding.drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT))
                binding.drawerLayout.closeDrawer(Gravity.LEFT);
            else if (binding.drawerLayout.isDrawerOpen(Gravity.RIGHT))
                binding.drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            if (currentFragment.getTag() != null) {
                if (currentFragment.getTag().equals("HomeFragment")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setMessage("Are you sure you want to Exit?");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            (dialog, id) -> finishAffinity());

                    builder1.setNegativeButton(
                            "No",
                            (dialog, id) -> dialog.cancel());
                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                } else if (
                        currentFragment.getTag().equals("ShopFragment") ||
                        currentFragment.getTag().equals("MlmFragment") ||
                        currentFragment.getTag().equals("WishlistFragment") ||
                        currentFragment.getTag().equals("CartFragment") ||
                        currentFragment.getTag().equals("LoginFragment") ||
                        currentFragment.getTag().equals("AffiliateFragment")) {
                    switchFragment(new HomeFragment(), "HomeFragment");
                    binding.bottomNavigationView.setSelectedItemId(R.id.menu_home);
                } else {
                    super.onBackPressed();
                }
            } else {
                super.onBackPressed();
            }
        }
    }

}