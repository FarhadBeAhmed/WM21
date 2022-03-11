package co.wm21.https.activities;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

import co.wm21.https.R;
import co.wm21.https.databinding.ActivityMainBinding;
import co.wm21.https.fragments.CartFragment;
import co.wm21.https.fragments.HomeFragment;
import co.wm21.https.fragments.LoginFragment;
import co.wm21.https.fragments.RegisterFragment;
import co.wm21.https.fragments.ShopFragment;
import co.wm21.https.fragments.WishlistFragment;
import co.wm21.https.helpers.User;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding binding;
    public User user;

    public Fragment homeFragment, eshopFragment, wishlistFragment, cartFragment, loginFragment;

    public MenuItem notificationItem, signoutItem;
    public Menu TopMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = new User(getApplicationContext());

        homeFragment = new HomeFragment();
        eshopFragment = new ShopFragment();
        wishlistFragment = new WishlistFragment();
        cartFragment = new CartFragment();
        loginFragment = new LoginFragment();

        setSupportActionBar(binding.mainToolbar);


        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_menu_home:
                    switchFragment(homeFragment);
                    return true;
                case R.id.bottom_menu_shop:
                    switchFragment(eshopFragment);
                    return true;
                case R.id.bottom_menu_account:
                    if (user.getSession().isLoggedIn()) {
                        switch (user.getMemberType()) {
//                            case "MLM": startActivity(new Intent(this, MlmActivity.class)); break;
//                            case "Aff": startActivity(new Intent(this, AffiliateActivity.class)); break;
                            case "Eco": // Notification Button
                                break;
                        }
                    } else {
                        switchFragment(loginFragment);
                    }
                    return true;
                case R.id.bottom_menu_cart:
                    switchFragment(cartFragment);
                    return true;
                case R.id.bottom_menu_wishlist:
                    switchFragment(wishlistFragment);
                    return true;
            }
            return false;
        });

        switchFragment(new RegisterFragment());

    }

    public void switchFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(binding.fragmentContainer.getId(), fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        @MenuRes int menuId;
//        if (user.getSession().isLoggedIn()) {
//            if (user.getMemberType().equals("Eco"))
//                menuId = R.menu.home_menu;
//            else menuId = R.menu.user_menu;
//        } else menuId = R.menu.home_menu;
        menuId = R.menu.toolbar_menu;
        getMenuInflater().inflate(menuId, TopMenu = menu);
        notificationItem = TopMenu.getItem(0);
        signoutItem = TopMenu.getItem(1);
        if (!user.getSession().isLoggedIn())
            TopMenu.removeItem(R.id.home_menu_sign_out);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.user_menu_sign_out:
            case R.id.home_menu_sign_out:
                user.getSession().logoutUser();
                finishAffinity();
                startActivity(new Intent(this, MainActivity.class));
                return true;
//            case R.id.user_menu_affiliate:
//                startActivity(new Intent(this, AffiliateActivity.class));
//                return true;
//            case R.id.user_menu_mlm:
//                startActivity(new Intent(this, MlmActivity.class));
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}