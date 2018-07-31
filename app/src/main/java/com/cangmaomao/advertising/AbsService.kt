package com.cangmaomao.advertising

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.text.TextUtils
import com.cangmaomao.network.request.HttpManage
import com.cangmaomao.network.request.utils.RxSchedulers
import org.greenrobot.eventbus.EventBus
import java.util.*

@Suppress("UNREACHABLE_CODE")
class AbsService : Service() {

    private val timer = Timer()

    private lateinit var timerTask: TimerTask


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val account = intent.extras.getString("account")
            val account1 = intent.extras.getString("account1")
            if (!TextUtils.isEmpty(account1)) {
                var i = 0
                lateinit var userName: String
                timerTask = object : TimerTask() {
                    override fun run() {
                        when (i) {
                            0 -> {
                                i = 1
                                userName = account
                            }
                            1 -> {
                                i = 0
                                userName = account1
                            }
                        }
                        val m = mapOf(
                                "loginType" to "sys_user",
                                "module" to "freight",
                                "username" to userName,
                                "password" to "123654",
                                "backUrl" to "/Map/MapArea/GetOrgTreeAjax"
                        )
                        HttpManage.getInstance()
                                .create(ApiConfig::class.java)
                                .login(m)
                                .compose(RxSchedulers.io_main())
                                .subscribe(
                                        {
                                            EventBus.getDefault().post(MainEvent(true))
                                        }, {
                                })
                    }
                }
                timer.schedule(timerTask, 0, 120 * 1000)
            } else {
                timerTask = object : TimerTask() {
                    override fun run() {
                        val m = mapOf(
                                "loginType" to "sys_user",
                                "module" to "freight",
                                "username" to account,
                                "password" to "123654",
                                "backUrl" to "/Map/MapArea/GetOrgTreeAjax"
                        )
                        HttpManage.getInstance()
                                .create(ApiConfig::class.java)
                                .login(m)
                                .compose(RxSchedulers.io_main())
                                .subscribe(
                                        {
                                            EventBus.getDefault().post(MainEvent(true))
                                        }, {
                                })
                    }
                }
                timer.schedule(timerTask, 0, 6900 * 1000)
            }

        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}