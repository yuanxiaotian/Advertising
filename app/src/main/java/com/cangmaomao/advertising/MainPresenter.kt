package com.cangmaomao.advertising

import android.app.AlertDialog
import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import com.cangmaomao.lib.utils.toast
import com.cangmaomao.network.request.HttpManage
import com.cangmaomao.network.request.RxHttpMange
import com.cangmaomao.network.request.config.Config
import com.cangmaomao.network.request.utils.RxSchedulers
import java.io.DataOutputStream

class MainPresenter(val view: MainContract.MainView) : MainContract.MainPresenter {

    init {
        view.setPresenter(this)
    }


    override fun login(tag: String) {
        HttpManage.getInstance().create(ApiConfig::class.java)
                .login(view.map())
                .compose(RxSchedulers.io_main())
                .subscribe({
                    val r = it.result
                    when (r) {
                        "1" -> getOrgTreeAjax(tag)
                        else -> view.fail("登陆失败!")
                    }
                }, {
                    if (!TextUtils.isEmpty(it.message)) {
                        view.fail("$tag:${it.message}")
                    } else {
                        view.fail("服务器异常!")
                    }
                })
    }

    override fun getOrgTreeAjax(tag: String) {
        HttpManage.getInstance().create(ApiConfig::class.java)
                .getOrgTreeAjax()
                .compose(RxSchedulers.io_main())
                .subscribe({
                    if (TextUtils.isEmpty(it.error)) {
                        val c = it.obj.data
                        val info = arrayListOf<FleetInfo>()
                        var id = ""
                        for (i in c) {
                            if (!i.id.contains("org")) {
                                id = if (TextUtils.isEmpty(id)) i.id else id + "," + i.id
                            } else {
                                info.add(FleetInfo(i.name, i.id, 0, 0, 0, 0))
                            }
                        }
                        if (!TextUtils.isEmpty(id) && info.size > 0) {
                            view.carId(id, info)
                        } else {
                            view.fail("获取车辆信息失败！")
                        }
                    } else {
                        view.fail("获取车辆信息失败！")
                    }
                }, {
                    if (!TextUtils.isEmpty(it.message)) {
                        view.fail("$tag:${it.message}")
                    } else {
                        view.fail("服务器异常!")
                    }
                })


    }

    override fun carLocation(tag: String, map: Map<String, String>) {
        HttpManage.getInstance().create(ApiConfig::class.java)
                .carLocation(Config.S_HTTP_ROOT_URL + "Map/Monit/GetVehiclesLocationAjax", map)
                .compose(RxSchedulers.io_main())
                .subscribe({
                    RxHttpMange.getInstance().remove(tag)
                    if (it.obj != null && it.obj.data != null) {
                        view.carInfo(it)
                    }
                }, {
                    if (!TextUtils.isEmpty(it.message)) {
                        view.fail("$tag:${it.message}")
                    } else {
                        view.fail("服务器异常!")
                    }
                })
    }

    override fun carInfo(info: ArrayList<FleetInfo>, carLocation: CarLocation) {
        val objList = carLocation.obj.data
        for (i in info) {
            val id = i.id.split("-")[1]
            var num = 1
            var offLineCount = 1
            var pauseCount = 1
            var onLineCount = 1
            for (k in objList) {
                if (id.equals("${k.organ}")) {
                    i.count = num
                    num++
                    if (k.linkStatus == 1 && k.speed <= 0) {
                        i.pause = pauseCount
                        pauseCount++
                    } else if (k.linkStatus == 1 && k.speed > 0) {
                        i.onLine = onLineCount
                        onLineCount++
                    } else {
                        i.offLine = offLineCount
                        offLineCount++
                    }
                }
            }
        }
        val iterator = info.iterator()
        while (iterator.hasNext()) {
            val net = iterator.next()
            if (net.count == 0) {
                iterator.remove()
            }
        }
        view.fleet(info)
    }

    override fun installApk(path: String, context: Context) {
        context.toast("开始安装0!$path", Toast.LENGTH_LONG)
        context.toast("开始安装1!")
        val cmd = "pm install -r $path"
        var process: Process? = null
        var os: DataOutputStream? = null
        try {
            //静默安装需要root权限
            process = Runtime.getRuntime().exec("su")
            context.toast("开始安装2!")
            os = DataOutputStream(process!!.outputStream)
            context.toast("开始安装3!")
            os!!.write(cmd.toByteArray())
            context.toast("开始安装4!")
            os!!.writeBytes("\n")
            context.toast("开始安装5!")
            os!!.writeBytes("exit\n")
            context.toast("开始安装6!")
            os!!.flush()
            context.toast("开始安装7!")
            //执行命令
            process!!.waitFor()
            context.toast("开始安装8!")
        } catch (e: Exception) {
            e.printStackTrace()
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setMessage("${e.message}")
            alertDialog.show()
        } finally {
            try {
                if (os != null) {
                    os!!.close()
                }
                process?.destroy()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}