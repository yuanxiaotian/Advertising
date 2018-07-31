package com.cangmaomao.advertising

import android.content.Context
import com.cangmaomao.lib.base.BasePresenter
import com.cangmaomao.lib.base.BaseView

interface ConfigContract {

    interface View : BaseView<Presenter> {

        fun fail(msg: String)

        fun successful(count: Int, video: Int, v: Int)

    }

    interface Presenter : BasePresenter {

        fun loadConfig(context: Context)


    }
}