package space.rungeun.android_escpos_thermalprinter_photobox.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import space.rungeun.android_escpos_thermalprinter_photobox.R
import space.rungeun.android_escpos_thermalprinter_photobox.constants.count
import space.rungeun.android_escpos_thermalprinter_photobox.controller.PrintSelectionController
import space.rungeun.android_escpos_thermalprinter_photobox.databinding.ActivityPrintSelectionBinding

class PrintSelectionActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityPrintSelectionBinding
    private val printSelectionController = PrintSelectionController(1)

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



        binding.countUpButton.setOnClickListener {
            printSelectionController.run(count.change.value)
            binding.countText.setText(printSelectionController.currentCount.toString())
        }
        binding.countDownButton.setOnClickListener {
            printSelectionController.run(-count.change.value)
            binding.countText.setText(printSelectionController.currentCount.toString())
        }
        binding.toFrameSelectionButton.setOnClickListener {
            val intent = Intent(this, FrameSelectionActivity::class.java)
            startActivity(intent)
        }


    }


}