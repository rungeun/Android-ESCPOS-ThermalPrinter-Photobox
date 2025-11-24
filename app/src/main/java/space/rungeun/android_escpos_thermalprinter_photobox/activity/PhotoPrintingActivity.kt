package space.rungeun.android_escpos_thermalprinter_photobox.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import space.rungeun.android_escpos_thermalprinter_photobox.MainActivity
import space.rungeun.android_escpos_thermalprinter_photobox.R
import space.rungeun.android_escpos_thermalprinter_photobox.controller.PhotoManager
import space.rungeun.android_escpos_thermalprinter_photobox.controller.PrinterManager
import space.rungeun.android_escpos_thermalprinter_photobox.databinding.ActivityPhotoPrintingBinding
import space.rungeun.android_escpos_thermalprinter_photobox.databinding.ActivityPhotoShootBinding
import space.rungeun.android_escpos_thermalprinter_photobox.model.Photo

class PhotoPrintingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoPrintingBinding

    private lateinit var photos: List<Photo>
    private val scope = CoroutineScope(Dispatchers.Main + Job())

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

        photos = PhotoManager.getAllPhotos()
        printPhotos()
    }

    private fun proceedToNextStep() {
            val intent = Intent(this@PhotoPrintingActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
    }

    private fun printPhotos() {
// PhotoShootActivity 또는 PrinterManager에 이 로그 추가
        Log.d("PhotoDebug", "Original bitmap: ${photos[0].picture.width} x ${photos[0].picture.height}")
        scope.launch {
            val result = PrinterManager.printPhotos(
                context = this@PhotoPrintingActivity,
                photos = photos,
               // onProgress = { message ->
                //}
            )

            result.fold(
                onSuccess = {
                    Toast.makeText(this@PhotoPrintingActivity, "인쇄 완료", Toast.LENGTH_SHORT).show()
                    proceedToNextStep()
                    photos.forEach { it.incrementCount() }
                },
                onFailure = { error ->
                    Toast.makeText(this@PhotoPrintingActivity, "인쇄 실패: ${error.message}", Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        PhotoManager.clear()
    }

}