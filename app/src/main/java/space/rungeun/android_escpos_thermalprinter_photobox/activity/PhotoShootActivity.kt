package space.rungeun.android_escpos_thermalprinter_photobox.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import space.rungeun.android_escpos_thermalprinter_photobox.R
import space.rungeun.android_escpos_thermalprinter_photobox.databinding.ActivityPhotoShootBinding
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jiangdg.ausbc.MultiCameraClient
import com.jiangdg.ausbc.callback.ICameraStateCallBack
import com.jiangdg.ausbc.widget.IAspectRatio
import space.rungeun.android_escpos_thermalprinter_photobox.controller.FrameRecyclerViewAdapter
import android.graphics.Bitmap
import com.jiangdg.ausbc.callback.ICaptureCallBack
import kotlinx.coroutines.*
import space.rungeun.android_escpos_thermalprinter_photobox.controller.PhotoManager
import space.rungeun.android_escpos_thermalprinter_photobox.model.Photo
import java.io.File

class PhotoShootActivity : CameraPreViewActivity() {

    companion object {
        private const val TAG = "PhotoShootActivity"
        private const val PERMISSION_REQUEST_CODE = 100
    }

    private lateinit var binding: ActivityPhotoShootBinding
    private val viewModel: PhotoShootViewModel by viewModels()

    // Photo tracking variables
    private var currentPhotoIndex = 0
    private var maxPhotos = 6
    private val capturedPhotos = mutableListOf<Bitmap>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    // 임시 사진 저장 경로 (앱 내부 저장소 사용)
    private val tempPhotoDir: File by lazy {
        File(filesDir, "temp_photos").apply {
            if (!exists()) mkdirs()
        }
    }

    // CameraActivity가 쓸 루트 뷰
    override fun inflateRootView(inflater: LayoutInflater): View {
        binding = ActivityPhotoShootBinding.inflate(inflater)
        return binding.root
    }

    // 실제 카메라 프리뷰 뷰
    override fun provideCameraView(): IAspectRatio {
        val cameraView = binding.cameraPreviewInclude.cameraRender
        return cameraView
    }

    // 카메라 컨테이너
    override fun provideCameraContainer(): ViewGroup {
        val container = binding.cameraPreviewInclude.container
        return container
    }

    fun startCountdown() {
        viewModel.startCountdown(
            onCountUpdate = { count ->
                binding.countDownText.setText(count.toString())
            },
            onComplete = {
                capturePhoto()
            }
        )
    }

    fun standby() {
        viewModel.startCountdown(
            onCountUpdate = { count ->
                binding.countDownText.setText("Standby !")
            },
            onComplete = {
                binding.countDownText.setTextColor(getColor(R.color.standby))
                binding.countDownText.setAutoSizeTextTypeUniformWithConfiguration(
                    20,
                    300,
                    1,
                    TypedValue.COMPLEX_UNIT_DIP  // dp 단위
                )
                startCountdown()
            }
        )
    }

    private fun capturePhoto() {
        if (currentPhotoIndex >= maxPhotos) return

        val tempFile = File.createTempFile(
            "temp_photo_${System.currentTimeMillis()}_",
            ".jpg",
            cacheDir
        )

        captureImage(object : ICaptureCallBack {
            override fun onError(error: String?) {
                runOnUiThread {
                    startCountdown()
                }
            }

            override fun onBegin() {}

            override fun onComplete(path: String?) {
                val filePath = path ?: tempFile.absolutePath

                coroutineScope.launch(Dispatchers.IO) {
                    val bitmap = android.graphics.BitmapFactory.decodeFile(filePath)
                    runCatching { File(filePath).delete() }

                    if (bitmap == null) {
                        startCountdown()
                        return@launch
                    }

                    // Photo 객체 생성 및 PhotoManager에 저장
                    val photo = Photo(
                        frameNumber = currentPhotoIndex + 1, // 1부터 시작하는 프레임 번호
                        picture = bitmap
                    )
                    PhotoManager.addPhoto(photo)

                    // UI 업데이트용
                    capturedPhotos.add(bitmap)

                    withContext(Dispatchers.Main) {
                        val adapter = binding.filmedList.adapter as? FrameRecyclerViewAdapter
                        adapter?.updatePhoto(bitmap, currentPhotoIndex)

                        currentPhotoIndex++

                        if (currentPhotoIndex < maxPhotos) {
                            coroutineScope.launch {
                                delay(1000)
                                startCountdown()
                            }
                        } else {
                            binding.countDownText.text = "Done"
                            binding.countDownText.setTextColor(
                                getColor(android.R.color.holo_green_light)
                            )
                            proceedToNextStep()
                        }
                    }
                }
            }
        }, tempFile.absolutePath)
    }

    private fun proceedToNextStep() {
        coroutineScope.launch {
            delay(2000)
            val intent = Intent(this@PhotoShootActivity, PhotoPrintingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // 촬영된 사진들을 가져오는 메서드
    fun getCapturedPhotos(): List<Bitmap> = capturedPhotos.toList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 임시 디렉토리 생성
        if (!tempPhotoDir.exists()) {
            tempPhotoDir.mkdirs()
        }

        binding.filmedList.layoutManager = GridLayoutManager(this, 3)
        val adapter = FrameRecyclerViewAdapter(maxPhotos)
        binding.filmedList.adapter = adapter
        adapter.shootMode()

        standby()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()    // Coroutine scope 정리
        tempPhotoDir.listFiles()?.forEach { it.delete() }         // 임시 파일 정리
    }

    override fun onCameraState(
        self: MultiCameraClient.ICamera, code: ICameraStateCallBack.State, msg: String?
    ) {
        Log.d(TAG, "onCameraState: $code, msg: $msg")

        when (code) {
            ICameraStateCallBack.State.OPENED -> {
                Log.d(TAG, "Camera opened successfully")
            }

            ICameraStateCallBack.State.CLOSED -> {
                Log.d(TAG, "Camera closed")
            }

            ICameraStateCallBack.State.ERROR -> {
                Log.e(TAG, "Camera error: $msg")
            }
        }
    }


}