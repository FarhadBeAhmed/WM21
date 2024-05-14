package co.wm21.https.activities;

import android.Manifest;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import co.wm21.https.databinding.ActivityMainBinding;
import co.wm21.https.databinding.ActivitySearchShopBinding;
import co.wm21.https.databinding.ActivityShopSearchScanBinding;
import co.wm21.https.fragments.shops.PremierShop;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ShopSearchScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ActivityShopSearchScanBinding binding;
    //ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopSearchScanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*scannerView = new ZXingScannerView(this);
        setContentView(scannerView);*/

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                       // scannerView.startCamera();
                       binding.scannerId.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void handleResult(Result rawResult) {
        PremierShop.scannerText = rawResult.getText();
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.scannerId.stopCamera();
    }


    @Override
    protected void onResume() {
        super.onResume();
        binding.scannerId.setResultHandler(this);
        binding.scannerId.startCamera();
    }
}