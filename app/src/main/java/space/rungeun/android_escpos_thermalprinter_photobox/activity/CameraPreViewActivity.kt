package space.rungeun.android_escpos_thermalprinter_photobox.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jiangdg.ausbc.MultiCameraClient
import com.jiangdg.ausbc.base.CameraActivity
import com.jiangdg.ausbc.callback.ICameraStateCallBack
import com.jiangdg.ausbc.widget.IAspectRatio

abstract class CameraPreViewActivity : CameraActivity() {

    companion object {
        private const val TAG = "CameraPreViewActivity"
    }

    // 각 액티비티에서 레이아웃 inflate 해서 넘겨줌
    abstract fun inflateRootView(inflater: LayoutInflater): View

    // 실제 프리뷰 뷰
    abstract fun provideCameraView(): IAspectRatio

    // 프리뷰를 담는 컨테이너
    abstract fun provideCameraContainer(): ViewGroup

    override fun getRootView(layoutInflater: LayoutInflater): View {
        return inflateRootView(layoutInflater)
    }

    override fun getCameraView(): IAspectRatio? = provideCameraView()

    override fun getCameraViewContainer(): ViewGroup? = provideCameraContainer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}