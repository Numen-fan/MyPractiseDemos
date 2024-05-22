package com.jiajia.mypractisedemos.module.videocompressor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureFailure
import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.CaptureResult
import android.hardware.camera2.TotalCaptureResult
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import android.view.Surface
import android.view.TextureView
import androidx.appcompat.app.AppCompatActivity
import com.jiajia.mypractisedemos.databinding.ActivityMediaCodecVideoBinding
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils.error

class MediaCodecVideoActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MediaCodecVideoActivity"
    }

    private var mCameraManager: CameraManager? = null
    private var mCameraDevice: CameraDevice? = null
    private var mCameraCaptureSession: CameraCaptureSession? = null
    private var mCameraDeviceStateCallback: CameraDevice.StateCallback? = null
    private var mSessionStateCallback: CameraCaptureSession.StateCallback? = null
    private var mSessionCaptureCallback: CameraCaptureSession.CaptureCallback? = null
    private var mPreviewCaptureRequest: CaptureRequest.Builder? = null
    private val mRecorderCaptureRequest: CaptureRequest.Builder? = null
    private var mChildHandler: Handler? = null

    private var mCurrentSelectCamera: String? = null

    lateinit var binding: ActivityMediaCodecVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaCodecVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTextureViewStateListener()
        initChildHandler()
        initCameraDeviceStateCallback()
        initSessionStateCallback()
        initSessionCaptureCallback()
    }

    /**
     * 初始化TextureView的纹理生成监听，只有纹理生成准备好了。我们才能去进行摄像头的初始化工作让TextureView接收摄像头预览画面
     */
    private fun initTextureViewStateListener() {
        binding.textureview.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                //可以使用纹理
                initCameraManager()
                selectCamera()
                openCamera()
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                //纹理尺寸变化
            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                //纹理被销毁
                return false
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
                //纹理更新
            }
        }
    }

    private fun initCameraManager() {
        mCameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    private fun selectCamera() {
        if (mCameraManager != null) {
            error(TAG, "selectCamera: CameraManager is null")
        }
        try {
            val cameraIdList = mCameraManager!!.cameraIdList //获取当前设备的全部摄像头id集合
            if (cameraIdList.size == 0) {
                error(TAG, "selectCamera: cameraIdList length is 0")
            }
            for (cameraId in cameraIdList) { //遍历所有摄像头
                val characteristics =
                    mCameraManager!!.getCameraCharacteristics(cameraId) //得到当前id的摄像头描述特征
                val facing = characteristics.get(CameraCharacteristics.LENS_FACING) //获取摄像头的方向特征信息
                if (facing == CameraCharacteristics.LENS_FACING_BACK) { //这里选择了后摄像头
                    mCurrentSelectCamera = cameraId
                }
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    /**
     * 打开摄像头，这里打开摄像头后，我们需要等待mCameraDeviceStateCallback的回调
     */
    @SuppressLint("MissingPermission")
    private fun openCamera() {
        try {
            mCameraManager!!.openCamera(
                mCurrentSelectCamera!!,
                mCameraDeviceStateCallback!!, mChildHandler
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    /**
     * 初始化子线程Handler，操作Camera2需要一个子线程的Handler
     */
    private fun initChildHandler() {
        val handlerThread = HandlerThread("Camera2Demo")
        handlerThread.start()
        mChildHandler = Handler(handlerThread.looper)
    }

    private fun initCameraDeviceStateCallback() {
        mCameraDeviceStateCallback = object : CameraDevice.StateCallback() {
            override fun onOpened(camera: CameraDevice) {
                //摄像头被打开
                try {
                    mCameraDevice = camera
                    val cameraSize: Size = getMatchingSize2()!! //计算获取需要的摄像头分辨率
                    val surfaceTexture = binding.textureview.surfaceTexture //得到纹理
                    surfaceTexture!!.setDefaultBufferSize(cameraSize.width, cameraSize.height)
                    val previewSurface = Surface(surfaceTexture)
                    mPreviewCaptureRequest =
                        mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                    mPreviewCaptureRequest!!.set(
                        CaptureRequest.CONTROL_AF_MODE,
                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                    )
                    mPreviewCaptureRequest!!.addTarget(previewSurface)
                    mCameraDevice!!.createCaptureSession(
                        listOf(previewSurface),
                        mSessionStateCallback!!, mChildHandler
                    ) //创建数据捕获会话，用于摄像头画面预览，这里需要等待mSessionStateCallback回调
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
            }

            override fun onDisconnected(camera: CameraDevice) {
                //摄像头断开
            }

            override fun onError(camera: CameraDevice, error: Int) {
                //异常
            }
        }
    }

    /**
     * 计算需要的使用的摄像头分辨率
     *
     * @return
     */
    private fun getMatchingSize2(): Size? {
        var selectSize: Size? = null
        try {
            val cameraCharacteristics = mCameraManager!!.getCameraCharacteristics(
                mCurrentSelectCamera!!
            )
            val streamConfigurationMap =
                cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            val sizes = streamConfigurationMap!!.getOutputSizes(ImageFormat.JPEG)
            val displayMetrics = resources.displayMetrics //因为我这里是将预览铺满屏幕,所以直接获取屏幕分辨率
            val deviceWidth = displayMetrics.widthPixels //屏幕分辨率宽
            val deviceHeigh = displayMetrics.heightPixels //屏幕分辨率高
            error(
                TAG,
                "getMatchingSize2: 屏幕密度宽度=$deviceWidth"
            )
            error(
                TAG,
                "getMatchingSize2: 屏幕密度高度=$deviceHeigh"
            )
            /**
             * 循环40次,让宽度范围从最小逐步增加,找到最符合屏幕宽度的分辨率,
             * 你要是不放心那就增加循环,肯定会找到一个分辨率,不会出现此方法返回一个null的Size的情况
             * ,但是循环越大后获取的分辨率就越不匹配
             */
            for (j in 1..40) {
                for (i in sizes.indices) { //遍历所有Size
                    val itemSize = sizes[i]
                    //                    LogUtils.error(TAG, "当前itemSize 宽=" + itemSize.getWidth() + "高=" + itemSize.getHeight());
                    //判断当前Size高度小于屏幕宽度+j*5  &&  判断当前Size高度大于屏幕宽度-j*5  &&  判断当前Size宽度小于当前屏幕高度
                    if (itemSize.height < deviceWidth + j * 5 && itemSize.height > deviceWidth - j * 5) {
                        if (selectSize != null) { //如果之前已经找到一个匹配的宽度
                            if (Math.abs(deviceHeigh - itemSize.width) < Math.abs(deviceHeigh - selectSize.width)) { //求绝对值算出最接近设备高度的尺寸
                                selectSize = itemSize
                                continue
                            }
                        } else {
                            selectSize = itemSize
                        }
                    }
                }
                if (selectSize != null) { //如果不等于null 说明已经找到了 跳出循环
                    break
                }
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
        error(
            TAG,
            "getMatchingSize2: 选择的分辨率宽度=" + selectSize!!.width
        )
        error(
            TAG,
            "getMatchingSize2: 选择的分辨率高度=" + selectSize.height
        )
        return selectSize
    }

    private fun initSessionStateCallback() {
        mSessionStateCallback = object : CameraCaptureSession.StateCallback() {
            override fun onConfigured(session: CameraCaptureSession) {
                mCameraCaptureSession = session
                try {
                    //执行重复获取数据请求，等于一直获取数据呈现预览画面，mSessionCaptureCallback会返回此次操作的信息回调
                    mCameraCaptureSession!!.setRepeatingRequest(
                        mPreviewCaptureRequest!!.build(),
                        mSessionCaptureCallback,
                        mChildHandler
                    )
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
            }

            override fun onConfigureFailed(session: CameraCaptureSession) {}
        }
    }

    private fun initSessionCaptureCallback() {
        mSessionCaptureCallback = object : CameraCaptureSession.CaptureCallback() {
            override fun onCaptureStarted(
                session: CameraCaptureSession,
                request: CaptureRequest,
                timestamp: Long,
                frameNumber: Long
            ) {
                super.onCaptureStarted(session, request, timestamp, frameNumber)
            }

            override fun onCaptureProgressed(
                session: CameraCaptureSession,
                request: CaptureRequest,
                partialResult: CaptureResult
            ) {
                super.onCaptureProgressed(session, request, partialResult)
            }

            override fun onCaptureCompleted(
                session: CameraCaptureSession,
                request: CaptureRequest,
                result: TotalCaptureResult
            ) {
                super.onCaptureCompleted(session, request, result)
            }

            override fun onCaptureFailed(
                session: CameraCaptureSession,
                request: CaptureRequest,
                failure: CaptureFailure
            ) {
                super.onCaptureFailed(session, request, failure)
            }
        }
    }
}