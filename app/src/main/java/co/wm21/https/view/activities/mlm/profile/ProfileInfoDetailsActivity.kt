package co.wm21.https.view.activities.mlm.profile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.wm21.https.R
import co.wm21.https.databinding.ActivityPremierShopViewBinding
import co.wm21.https.databinding.ActivityProfileInfoDetailsBinding

class ProfileInfoDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileInfoDetailsBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileInfoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }
}