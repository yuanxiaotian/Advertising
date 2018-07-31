package com.cangmaomao.advertising.app

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import com.cangmaomao.network.request.config.Config
import com.cangmaomao.network.request.cookie.AbsCookieJar
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.tencent.bugly.crashreport.CrashReport


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Thread.setDefaultUncaughtExceptionHandler(restartHandler)

        Config.S_HTTP_ROOT_URL = "http://202.96.155.111/"

        val config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build()
        Fresco.initialize(this, config)

        AbsCookieJar.mContext = applicationContext

        CrashReport.initCrashReport(applicationContext, "900018155", false);
    }

    private val restartHandler = object : Thread.UncaughtExceptionHandler {
        override fun uncaughtException(thread: Thread, ex: Throwable) {
            restartApplication()//发生崩溃异常时,重启应用
        }
    }

    private fun restartApplication() {
        val intent = getPackageManager().getLaunchIntentForPackage(applicationContext.getPackageName());
        val restartIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        val mgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
        System.exit(0);
    }

}