package com.cangmaomao.advertising

import com.cangmaomao.lib.base.BaseView
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody

interface MainContract {

    interface MainView : BaseView<MainPresenter> {

        fun map(): Map<String, String>

        fun fail(msg: String)

        fun carId(id: String)

        fun carInfo(carLocation: CarLocation)

    }

    interface MainPresenter {

        fun login(tag: String)

        fun getOrgTreeAjax(tag: String)

        fun carLocation(tag: String, map: Map<String, String>)
    }

}