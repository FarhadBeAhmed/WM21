package co.wm21.https;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import co.wm21.https.databinding.ActivityMainBinding;
import co.wm21.https.databinding.ActivitySplashscreenBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}