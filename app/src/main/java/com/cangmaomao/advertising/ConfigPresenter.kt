package com.cangmaomao.advertising

import android.content.Context
import android.text.TextUtils
import java.io.*
import java.net.URL


class ConfigPresenter(val view: ConfigContract.View) : ConfigContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun loadConfig(context: Context) {
        Thread {
            run {
                try {
                    val bing = URL("http://xuli008.0324.bftii.com/config.xml")
                    val texts = bing.readText()
                    if (!TextUtils.isEmpty(texts)) {
                        val t = texts.split("#")
                        val count = t[0].trim().toInt()
                        val video = t[1].trim().toInt()
                        val v = t[2].trim().toInt()
                        view.successful(count, video, v)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

}