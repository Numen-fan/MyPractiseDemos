package com.jiajia.mypractisedemos.module.videocompressor

import android.graphics.Point
import android.hardware.Camera
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.jiajia.mypractisedemos.R
import com.jiajia.mypractisedemos.databinding.ActivityRecoderVideoAudio6Binding
import com.jiajia.mypractisedemos.module.kotlin.util.ToastUtils
import com.newki.glrecord.GLCamera1View
import com.newki.glrecord.model.MagicFilterType
import com.newki.glrecord.widget.FocusImageView
import com.newki.glrecord.widget.SlideGpuFilterGroup
import java.io.File

class RecoderVideoAudio6Activity : AppCompatActivity(), SlideGpuFilterGroup.OnFilterChangeListener,
    View.OnTouchListener {

    private lateinit var binding: ActivityRecoderVideoAudio6Binding

    private lateinit var mRecorderFocusIv: FocusImageView
    private lateinit var outFile: File
    private var screenHeight: Int = 0
    private var screenWidth: Int = 0
    private lateinit var mRecordCameraView: GLCamera1View
    private var isRecording: Boolean = false

    companion object {
        private const val TAG = "RecoderVideoAudio6Activ"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecoderVideoAudio6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }

    private fun init() {
        val mDisplayMetrics = applicationContext.resources.displayMetrics
        screenWidth = mDisplayMetrics.widthPixels
        screenHeight = mDisplayMetrics.heightPixels

        outFile = File(cacheDir, "${System.currentTimeMillis()}-record.mp4")
        if (!outFile.exists()) {
            outFile.createNewFile()
        }

        val flContainer = findViewById<FrameLayout>(R.id.fl_container)
        mRecorderFocusIv = findViewById(R.id.recorder_focus_iv)
        val startBtn = findViewById<Button>(R.id.start)
        val endBtn = findViewById<Button>(R.id.end)
        val playBtn = findViewById<Button>(R.id.play)
        val changeCamera = findViewById<Button>(R.id.change_camera)
        val changeFilter = findViewById<Button>(R.id.change_filter)


        startBtn.text = "特效录制"

        mRecordCameraView = GLCamera1View(this)
        mRecordCameraView.setOnTouchListener(this)
        mRecordCameraView.setOnFilterChangeListener(this)
        flContainer.addView(mRecordCameraView)

        // ==> 设置按钮的监听
        //切换前后摄像头
        changeCamera.setOnClickListener() {
            mRecordCameraView.switchCamera()
        }
        //切换滤镜
        changeFilter.setOnClickListener {
            mRecordCameraView.nextFilter()
        }
        //启动录制
        startBtn.setOnClickListener {
            startRecording()
        }
        //停止录制
        endBtn.setOnClickListener {
            stopRecording()
        }
        //去播放
        playBtn.setOnClickListener {
//            RecoderVideoPlarerActivity.startInstance(outFile.absolutePath)
        }
    }

    private fun startRecording() {
        isRecording = true
        mRecordCameraView.setSavePath(outFile.absolutePath)
        mRecordCameraView.startRecord()
    }

    private fun stopRecording() {
        isRecording = false
        mRecordCameraView.stopRecord()
        mRecordCameraView.postDelayed({ VideoRecorderUtils.saveVideo(this, File(mRecordCameraView.savePath)) }, 500);
    }

    override fun onFilterChange(type: MagicFilterType?) {
        runOnUiThread {
            if (type === MagicFilterType.NONE) {
                ToastUtils.showToast("当前没有设置滤镜--$type");
            } else {
                ToastUtils.showToast("当前滤镜切换为--$type")
            }
        }
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        mRecordCameraView.onTouch(event)

        if (mRecordCameraView.cameraId == 1) {
            return false
        }

        when (event.action) {
            MotionEvent.ACTION_UP -> {
                val sRawX = event.rawX
                val sRawY = event.rawY
                var rawY: Float = sRawY * screenWidth / screenHeight
                val rawX = rawY
                rawY = (screenWidth - sRawX) * screenHeight / screenWidth
                val point = Point(rawX.toInt(), rawY.toInt())

                mRecordCameraView.onFocus(point, Camera.AutoFocusCallback { success, camera ->
                    if (success) {
                        mRecorderFocusIv.onFocusSuccess()
                    } else {
                        mRecorderFocusIv.onFocusFailed()
                    }
                })

                mRecorderFocusIv.startFocus(Point(sRawX.toInt(), sRawY.toInt()))
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mRecordCameraView.onDestroy()
    }
}