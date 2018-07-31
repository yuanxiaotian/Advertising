package com.cangmaomao.advertising

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.text.TextUtils
import com.cangmaomao.lib.utils.e
import com.cangmaomao.network.request.HttpManage
import com.cangmaomao.network.request.utils.RxSchedulers
import org.greenrobot.eventbus.EventBus
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.net.URL
import java.util.*

@Suppress("UNREACHABLE_CODE")
class DownService : Service() {


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            Thread {
                run {
                    try {
                        val bing = URL("http://xuli008.0324.bftii.com/v.apk")
                        val bytes = bing.readBytes()
                        val fileInputStream = FileOutputStream(intent.getStringExtra("path"))
                        fileInputStream.write(bytes)
                        fileInputStream.flush()
                        fileInputStream.close()
                        EventBus.getDefault().post(MainEvent(false))
                        stopSelf()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }
            }.start()
        }
        return super.onStartCommand(intent, flags, startId)
    }
}
