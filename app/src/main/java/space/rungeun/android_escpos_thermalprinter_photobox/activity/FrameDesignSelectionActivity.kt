package space.rungeun.android_escpos_thermalprinter_photobox.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.jiangdg.ausbc.MultiCameraClient
import com.jiangdg.ausbc.callback.ICameraStateCallBack
import com.jiangdg.ausbc.widget.IAspectRatio
import space.rungeun.android_escpos_thermalprinter_photobox.R
import space.rungeun.android_escpos_thermalprinter_photobox.controller.FrameRecyclerViewAdapter
import space.rungeun.android_escpos_thermalprinter_photobox.databinding.ActivityFrameDesignSelectionBinding

class FrameDesignSelectionActivity : CameraPreViewActivity() {

    companion object {
        private const val TAG = "FrameDesignSelection"
    }

    private lateinit var binding: ActivityFrameDesignSelectionBinding

    // CameraActivity가 쓸 루트 뷰
    override fun inflateRootView(inflater: LayoutInflater): View {
        binding = ActivityFrameDesignSelectionBinding.inflate(inflater)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.frameRecyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.frameRecyclerView.adapter = FrameRecyclerViewAdapter(100) //TODO: 접근 가능한 프레임 수로 변경
        binding.toPrivacyPolicyConsentButton.setOnClickListener {
            val intent = Intent(this, PrivacyPolicyConsentActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.previousImageButton.setOnClickListener {
            // TODO: 이전 사진 보기
        }
        binding.nextImageButton.setOnClickListener {
            // TODO: 다음 사진 보기
        }
        binding.button4.setOnClickListener {
            // TODO: 프레임 그리기
        }
    }

    override fun onPause() {
        Log.d(TAG, "onPause - releasing camera")
        super.onPause()
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