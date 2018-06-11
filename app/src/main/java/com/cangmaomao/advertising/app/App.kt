package com.cangmaomao.advertising.app

import android.app.Application
import android.content.Context
import com.cangmaomao.network.request.config.Config
import com.cangmaomao.network.request.interceptor.AbsInterceptor
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import okhttp3.Interceptor


class App : Application() {

    val isFlag = true

    override fun onCreate() {
        super.onCreate()

        Config.S_HTTP_ROOT_URL = "http://202.96.155.111/"

        val config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build()
        Fresco.initialize(this, config)

    }

    init {
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
}