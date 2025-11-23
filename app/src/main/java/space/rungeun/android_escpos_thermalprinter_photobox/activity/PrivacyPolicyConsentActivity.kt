package space.rungeun.android_escpos_thermalprinter_photobox.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
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
        biding.PrivacyPolicyTextView.movementMethod = ScrollingMovementMethod.getInstance()
        biding.toPhotoShootButton.setOnClickListener {
            val intent = Intent(this, PhotoShootActivity::class.java)
            startActivity(intent)
        }
        biding.consentCheckBox.setOnClickListener {
            biding.toPhotoShootButton.setEnabled(biding.consentCheckBox.isChecked())
        }
    }
}