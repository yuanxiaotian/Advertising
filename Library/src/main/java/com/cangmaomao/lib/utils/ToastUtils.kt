package com.cangmaomao.lib.utils

import android.content.Context
import android.widget.Toast
import com.cangmaomao.network.request.base.BaseObserver

fun shortToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun longToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}