package com.jiajia.mypractisedemos.module.flutter

import android.content.Context
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.StringCodec

object FlutterTools {

    private const val TAG = "FlutterTools"

    private const val ENGINE_ID = "default_engine_id"
    private const val METHOD_CHANNEL = "com.basic.message.channel"

    // flutter引擎
    private var flutterEngine: FlutterEngine? = null

    // 消息通道
    private var basicMessageChannel: BasicMessageChannel<String>? = null


    fun initFlutterEngine(context: Context) {
        if (flutterEngine == null) {
            flutterEngine = FlutterEngine(context)
            flutterEngine!!.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
            basicMessageChannel = BasicMessageChannel(flutterEngine!!.dartExecutor, METHOD_CHANNEL, StringCodec.INSTANCE)

            FlutterEngineCache.getInstance().put(ENGINE_ID, flutterEngine)

            // 接收flutter的消息通知
            basicMessageChannel!!.setMessageHandler { message, reply -> processFlutterMessage(message, reply)}

        }
    }

    /**
     * 处理接收flutter的消息
     */
    private fun processFlutterMessage(message: String?, reply: BasicMessageChannel.Reply<String>) {
        if (message == null) {
            return
        }
        LogUtils.debug(TAG, message);
    }

    /**
     * 发送消息给
     */
    fun sendMessageToFlutter(message: String) {
        basicMessageChannel!!.send(message)
    }

    private fun clearEngine() {
        flutterEngine = null;
    }

    fun destroyEngine() {
        if (flutterEngine != null) {
            flutterEngine!!.destroy()
            clearEngine()
        }
    }
}
