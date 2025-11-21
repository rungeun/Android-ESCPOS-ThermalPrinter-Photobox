package space.rungeun.android_escpos_thermalprinter_photobox

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import space.rungeun.android_escpos_thermalprinter_photobox.databinding.ActivityMainBinding
import space.rungeun.android_escpos_thermalprinter_photobox.databinding.ActivityPrintSelectionBinding
import space.rungeun.android_escpos_thermalprinter_photobox.databinding.ActivityReprintSelectionBinding

class PrintSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrintSelectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrintSelectionBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}