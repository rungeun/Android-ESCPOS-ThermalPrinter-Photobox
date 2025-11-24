package space.rungeun.android_escpos_thermalprinter_photobox.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import space.rungeun.android_escpos_thermalprinter_photobox.R
import space.rungeun.android_escpos_thermalprinter_photobox.databinding.ActivityPhotoPrintingBinding
import space.rungeun.android_escpos_thermalprinter_photobox.databinding.ActivityPhotoShootBinding

class PhotoPrintingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoPrintingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoPrintingBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}