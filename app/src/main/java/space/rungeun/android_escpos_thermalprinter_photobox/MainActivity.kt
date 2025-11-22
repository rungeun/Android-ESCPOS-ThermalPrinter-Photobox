package space.rungeun.android_escpos_thermalprinter_photobox

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import space.rungeun.android_escpos_thermalprinter_photobox.activity.PrintSelectionActivity
import space.rungeun.android_escpos_thermalprinter_photobox.activity.ReprintSelectionActivity
import space.rungeun.android_escpos_thermalprinter_photobox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.printButton.setOnClickListener {
            val intent = Intent(this, PrintSelectionActivity::class.java)
            startActivity(intent)
        }

        binding.reprintButton.setOnClickListener {
            val intent = Intent(this, ReprintSelectionActivity::class.java)
            startActivity(intent)
        }
    }


}