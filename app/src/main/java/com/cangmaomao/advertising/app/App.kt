package com.cangmaomao.advertising.app

import android.app.Application
import android.content.Context
import com.cangmaomao.network.request.config.Config
import com.cangmaomao.network.request.interceptor.AbsInterceptor
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.taobao.sophix.SophixManager
import okhttp3.Interceptor
import com.taobao.sophix.PatchStatus
import com.taobao.sophix.listener.PatchLoadStatusListener
import android.content.Intent
import android.app.AlarmManager
import android.app.PendingIntent
import com.cangmaomao.lib.utils.shortToast


class App : Application() {

    val isFlag = true

    val appVersion = "1.0.0"

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub { mode, code, info, handlePatchVersion ->
                    // 补丁加载回调通知
                    if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                        // 表明补丁加载成功
                        if (base != null) {
                            shortToast(base, "表明补丁加载成功")
                        }
                        restartApplication()
                    } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                        if (base != null) {
                            shortToast(base, "表明补丁加载成功2")
                        }
                        restartApplication()
                        // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                        // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                    } else {
                        // 其它错误信息, 查看PatchStatus类说明
                    }
                }.initialize()

    }


    override fun onCreate() {
        super.onCreate()

        SophixManager.getInstance().queryAndLoadNewPatch();

        Thread.setDefaultUncaughtExceptionHandler(restartHandler);

        Config.S_HTTP_ROOT_URL = "http://202.96.155.111/"

        val config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build()
        Fresco.initialize(this, config)

        AbsInterceptor.readInterceptor = Interceptor { chain ->
            val builder = chain.request().newBuilder()
            val preferences = applicationContext.getSharedPreferences("cookies", Context.MODE_PRIVATE)
            val cookies = preferences.getStringSet("cookie", HashSet<String>())

            for (cookie in cookies) {
                builder.addHeader("Cookie", cookie)
            }

            chain.proceed(builder.build())
        }


        AbsInterceptor.writeInterceptor = Interceptor { chain ->
            val originalResponse = chain.proceed(chain.request())

            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                val cookies = HashSet<String>()

                for (header in originalResponse.headers("Set-Cookie")) {
                    cookies.add(header)
                }
                val preferences = applicationContext.getSharedPreferences("cookies", Context.MODE_PRIVATE).edit()
                preferences.putStringSet("cookie", cookies).apply()
            }
            originalResponse
        }


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