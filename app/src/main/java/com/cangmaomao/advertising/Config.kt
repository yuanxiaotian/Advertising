package com.cangmaomao.advertising

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AlertDialog
import android.view.View
import com.cangmaomao.lib.utils.AppNetworkMgr
import com.cangmaomao.lib.utils.e
import java.util.*

class Config : Activity(), ConfigContract.View {

    lateinit var p: ConfigContract.Presenter

    private lateinit var timerTask2: TimerTask

    private val timer2 = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
        val dialog = AlertDialog.Builder(this@Config).create()
        val view = View.inflate(this, R.layout.activity_wifi, null)
        dialog.setView(view)
        dialog.show()
        timerTask2 = object : TimerTask() {
            override fun run() {
                val isFlag = AppNetworkMgr.isWifiConnected(this@Config)
                if (isFlag) {
                    runOnUiThread {
                        timer2.cancel()
                        timerTask2.cancel()
                        ConfigPresenter(this@Config)
                        p.loadConfig(this@Config)
                        dialog.dismiss()
                    }

                }
            }
        }
        timer2.schedule(timerTask2, 0, 2000)
    }

    override fun setPresenter(presenter: ConfigContract.Presenter) {
        this.p = presenter
    }

    override fun fail(msg: String) {

    }

    override fun successful(count: Int, video: Int, v: Int) {
        val code = packageManager.getPackageInfo(packageName, 0).versionCode
        e("版本号：$code")
        if (v > code) {
            startService(Intent(this, DownService::class.java).putExtra("path", Environment.getExternalStorageDirectory().absolutePath + "/v.apk"))
        }
        startActivity(Intent(this, MainActivity::class.java)
                .putExtra("count", count)
                .putExtra("video", video))
        finish()
    }
}