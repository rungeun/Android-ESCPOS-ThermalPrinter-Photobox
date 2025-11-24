package space.rungeun.android_escpos_thermalprinter_photobox.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import space.rungeun.android_escpos_thermalprinter_photobox.R
import space.rungeun.android_escpos_thermalprinter_photobox.constants.count
import space.rungeun.android_escpos_thermalprinter_photobox.controller.FrameSelectionController
import space.rungeun.android_escpos_thermalprinter_photobox.databinding.ActivityFrameSelectionBinding
import space.rungeun.android_escpos_thermalprinter_photobox.model.PrintSettings

class FrameSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFrameSelectionBinding
    private val frameSelectionController = FrameSelectionController(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrameSelectionBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.frameCountUpButton.setOnClickListener {
            frameSelectionController.run(count.change.value)
            updateDisplay()
        }
        binding.frameCountDownButton.setOnClickListener {
            frameSelectionController.run(-count.change.value)
            updateDisplay()
        }
        binding.toFrameDesignSelectionButton.setOnClickListener {
            PrintSettings.setFrameCount(frameSelectionController.currentCount)

            val intent = Intent(this, PrivacyPolicyConsentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateDisplay() {
        binding.frameCountText.text = frameSelectionController.currentCount.toString()
    }
}