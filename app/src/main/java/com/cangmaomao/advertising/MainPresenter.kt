package com.cangmaomao.advertising

import android.text.TextUtils
import com.cangmaomao.lib.utils.e
import com.cangmaomao.network.request.HttpManage
import com.cangmaomao.network.request.base.BaseObserver
import com.cangmaomao.network.request.config.Config
import com.cangmaomao.network.request.utils.RxSchedulers
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import java.util.*
import kotlin.collections.ArrayList

class MainPresenter(val view: MainContract.MainView) : MainContract.MainPresenter {

    init {
        view.setPresenter(this)
    }


    override fun login(tag: String) {
        HttpManage.getInstance().create(ApiConfig::class.java)
                .login(view.map())
                .compose(RxSchedulers.io_main())
                .subscribe(object : BaseObserver<Login>(tag) {
                    override fun success(p0: Login) {
                        val r = p0.result
                        when (r) {
                            "1" -> getOrgTreeAjax(tag)
                            else -> view.fail(p0.msg)
                        }
                    }

                    override fun fail(p0: String) {
                        view.fail(p0)
                    }

                })

    }

    override fun getOrgTreeAjax(tag: String) {
        HttpManage.getInstance().create(ApiConfig::class.java)
                .getOrgTreeAjax()
                .compose(RxSchedulers.io_main())
                .subscribe(object : BaseObserver<CarInfo>(tag) {
                    override fun fail(p0: String) {
                        view.fail(p0)
                    }

                    override fun success(carInfo: CarInfo) {
                        if (TextUtils.isEmpty(carInfo.error)) {
                            val c = carInfo.obj.data
                            val info = arrayListOf<FleetInfo>()
                            var id = ""
                            for (i in c) {
                                if (!i.id.contains("org")) {
                                    id = if (TextUtils.isEmpty(id)) i.id else id + "," + i.id
                                } else {
                                    info.add(FleetInfo(i.name, i.id, 0, 0, 0, 0))
                                }
                            }
                            view.carId(id, info)
                        } else {
                            view.fail(carInfo.error)
                        }
                    }

                })
    }

    override fun carLocation(tag: String, map: Map<String, String>) {

        HttpManage.getInstance().create(ApiConfig::class.java)
                .carLocation(Config.S_HTTP_ROOT_URL + "Map/Monit/GetVehiclesLocationAjax", map)
                .compose(RxSchedulers.io_main())
                .subscribe(object : BaseObserver<CarLocation>(tag) {
                    override fun fail(p0: String) {
                        view.fail(p0)
                    }

                    override fun success(carlocation: CarLocation) {
                        view.carInfo(carlocation)
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
}