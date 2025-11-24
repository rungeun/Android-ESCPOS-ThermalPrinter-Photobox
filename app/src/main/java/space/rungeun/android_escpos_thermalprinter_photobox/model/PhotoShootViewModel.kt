package space.rungeun.android_escpos_thermalprinter_photobox.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PhotoShootViewModel : ViewModel() {

    companion object {
        private const val DEFAULT_COUNTDOWN = 3 // 기본 카운트다운 시간
        private const val STANDBY_TIME = 2000L // Standby 표시 시간 (밀리초)
    }

    private var isCountingDown = false

    /**
     * 카운트다운 시작
     * @param seconds 카운트다운 시간 (기본값: 3초)
     * @param onCountUpdate 카운트가 업데이트될 때 호출되는 콜백
     * @param onComplete 카운트다운이 완료되었을 때 호출되는 콜백
     */
    fun startCountdown(
        seconds: Int = DEFAULT_COUNTDOWN,
        onCountUpdate: (Int) -> Unit = {},
        onComplete: () -> Unit = {}
    ) {
        if (isCountingDown) return // 이미 카운트다운 중이면 무시

        isCountingDown = true

        viewModelScope.launch {
            for (count in seconds downTo 1) {
                onCountUpdate(count)
                delay(1000) // 1초 대기
            }
            isCountingDown = false
            onComplete()
        }
    }

    /**
     * Standby 상태 표시
     * @param duration Standby 표시 시간 (밀리초)
     * @param onStandby Standby 상태일 때 호출되는 콜백
     * @param onComplete Standby가 완료되었을 때 호출되는 콜백
     */
    fun showStandby(
        duration: Long = STANDBY_TIME,
        onStandby: () -> Unit = {},
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch {
            onStandby()
            delay(duration)
            onComplete()
        }
    }

    /**
     * 카운트다운 취소
     */
    fun cancelCountdown() {
        isCountingDown = false
    }

    /**
     * 현재 카운트다운 중인지 확인
     */
    fun isCountingDown(): Boolean = isCountingDown
}