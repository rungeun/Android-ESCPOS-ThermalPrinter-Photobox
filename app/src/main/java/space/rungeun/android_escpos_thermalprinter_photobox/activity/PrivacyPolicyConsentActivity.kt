package space.rungeun.android_escpos_thermalprinter_photobox.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import space.rungeun.android_escpos_thermalprinter_photobox.R
import space.rungeun.android_escpos_thermalprinter_photobox.databinding.ActivityPrivacyPolicyConsentBinding

class PrivacyPolicyConsentActivity : AppCompatActivity() {
    private lateinit var biding: ActivityPrivacyPolicyConsentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biding = ActivityPrivacyPolicyConsentBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(biding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}