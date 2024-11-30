package co.wm21.https.view.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import co.wm21.https.R;
import co.wm21.https.databinding.ActivitySplashscreenBinding;

public class SplashscreenActivity extends AppCompatActivity {

    private ActivitySplashscreenBinding binding;

    private Handler h1, h2, h3, h4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setNavigationBarColor(ResourcesCompat.getColor(getResources(), R.color.primary_red, getTheme()));
        getWindow().setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.primary_red, getTheme()));

        boolean isGreaterThanAndroid12 = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S);
        ImageView splashLogo = binding.splashLogo;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getSplashScreen().setOnExitAnimationListener(splashScreenView -> {
                h1 = new Handler();
                h2 = new Handler();
                h3 = new Handler();
                if (isInternetAvailable()) {
                    splashScreenView.animate().scaleX(2.1f).scaleY(2.1f).setDuration(1000).start();
                    h1.postDelayed(() -> {
                        splashScreenView.animate().scaleX(2).scaleY(2).setDuration(1000).start();
                        h2.postDelayed(() -> {
                            splashScreenView.animate().scaleX(2.08989f).scaleY(2.08989f).setDuration(1000).start();
                            h3.postDelayed(() -> {
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                                h4 = new Handler();
                                h4.postDelayed(splashScreenView::remove, 200);
                            }, 1000);
                        }, 1000);
                    }, 1000);
                } else {
                    splashScreenView.animate().translationY(-330f).scaleX(2.08989f).scaleY(2.08989f).setDuration(2000).start();
                    splashLogo.setY(-340f);
                    h1.postDelayed(() -> {
                        splashScreenView.remove();
                        binding.noInternetLayout.setVisibility(View.VISIBLE);
                        binding.refreshButton.setOnClickListener(view -> {
                            if (isInternetAvailable())
                                startActivity(new Intent(this, MainActivity.class));
                        });
                    }, 2000);
                }
            });
            new Handler().postDelayed(() -> {
                if (h1 == null) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
            }, 500);
        } else {
            h1 = new Handler();
            h2 = new Handler();
            h3 = new Handler();
            if (isInternetAvailable()) {
                splashLogo.animate().scaleX(1.1f).scaleY(1.1f).setDuration(1000).start();
                h1.postDelayed(() -> {
                    splashLogo.animate().scaleX(1).scaleY(1).setDuration(1000).start();
                    h2.postDelayed(() -> {
                        splashLogo.animate().scaleX(1.05f).scaleY(1.05f).setDuration(1000).start();
                        h3.postDelayed(() -> {
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        }, 1000);
                    }, 1000);
                }, 1000);
            } else {
                splashLogo.animate().translationY(-300f).setDuration(2000).start();
                h1.postDelayed(() -> {
                    binding.noInternetLayout.setVisibility(View.VISIBLE);
                    binding.refreshButton.setOnClickListener(view -> {
                        if (isInternetAvailable())
                            startActivity(new Intent(this, MainActivity.class));
                    });
                }, 2000);
            }
        }
    }

    @Override
    public void onBackPressed() {
        try {
            h1.removeCallbacksAndMessages(null);
            h2.removeCallbacksAndMessages(null);
            h3.removeCallbacksAndMessages(null);
            h4.removeCallbacksAndMessages(null);
        } catch (Exception e) {
        }
        super.onBackPressed();
    }

    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}